package com.egfavre.controllers;

import com.egfavre.entities.Photo;
import com.egfavre.entities.User;
import com.egfavre.services.PhotoRepository;
import com.egfavre.services.UserRepository;
import com.egfavre.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by user on 6/28/16.
 */
@RestController
public class IronGramRestController {
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;

    @RequestMapping(path="/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        User userFromDB = users.findFirstByName(user.getName());
        if (userFromDB == null){
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userFromDB.getPassword())){
            throw new Exception("Wrong Password");
        }

        session.setAttribute("username", user.getName());
        return user;
    }

    @RequestMapping(path="/logout", method = RequestMethod.POST)
    public void logout (HttpSession session){
        session.invalidate();
    }

    @RequestMapping(path="/photos", method = RequestMethod.GET)
    public ArrayList<Photo> getPhotos (HttpSession session, String show){
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        int viewTime = (int) (new Date().getTime()/1000);
        Iterable<Photo> photoList= photos.findByRecipient(user);
        Iterable<Photo> photoPublicList = photos.findByShow("show");
        ArrayList<Photo> fullList = new ArrayList<>();
        for (Photo p:photoList) {
            Integer firstSetViewTime = p.getViewTime();
            if (firstSetViewTime == null){
                p.setViewTime(viewTime);
                photos.save(p);
            }
            else {
                if (p.getTimeLimit() == null) {
                    p.setTimeLimit(10);
                }
                if (viewTime - firstSetViewTime > p.getTimeLimit()){
                    photos.delete(p);
                    }
                fullList.add(p);
                }

            for (Photo pPublic:photoPublicList) {
                firstSetViewTime = pPublic.getViewTime();
                if (firstSetViewTime == null){
                    pPublic.setViewTime(viewTime);
                    photos.save(pPublic);
                }
                else {
                    if (pPublic.getTimeLimit() == null) {
                        pPublic.setTimeLimit(10);
                    }
                    if (viewTime - firstSetViewTime > pPublic.getTimeLimit()){
                        photos.delete(pPublic);
                    }
                    fullList.add(pPublic);
                }
            }
            }
        return fullList;
    }
}
