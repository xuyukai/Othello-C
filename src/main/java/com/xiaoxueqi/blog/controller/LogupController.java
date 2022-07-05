package com.xiaoxueqi.blog.controller;

import com.xiaoxueqi.blog.pojo.User;
import com.xiaoxueqi.blog.Result.Result;
import com.xiaoxueqi.blog.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LogupController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/api/logup")
    @ResponseBody
    public Result logup(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        if(userService.isExist(username)){
            return new Result(400);
        }else {
            userService.add(requestUser);
            return new Result(200);
        }
    }
}

