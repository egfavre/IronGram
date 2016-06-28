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
    public Iterable<Photo> getPhotos (HttpSession session){
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        return photos.findByRecipient(user);
    }
}
