package com.xiaoxueqi.blog.controller;

import com.xiaoxueqi.blog.Result.Result;
import com.xiaoxueqi.blog.Service.UserService;
import com.xiaoxueqi.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        System.out.println("Copy that!");

        User user = userService.get(username, requestUser.getPassword());

        if (null == user) {
            return new Result(400);
        } else {
            return new Result(200);
        }
    }
}
