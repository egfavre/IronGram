package com.egfavre.controllers;

import com.egfavre.services.PhotoRepository;
import com.egfavre.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by user on 6/28/16.
 */
@RestController
public class IronGramRestController {
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;
}
