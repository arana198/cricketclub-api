package com.cricketclub.user.controller;

import com.cricketclub.user.dto.User;
import com.cricketclub.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping(value="me")
public class MeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserControllerHateoasBuilder userControllerHateoasBuilder;

    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public User user(Principal principal) {
        return userService.me(principal);
    }

}
