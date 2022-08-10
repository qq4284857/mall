package com.mall.author.user.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/user")
public class UsersController {

    @RequestMapping("/getUser")
    @ResponseBody
    public Object getUser(Authentication authentication, HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token=header.substring(header.lastIndexOf("bearer")+7);


        return Jwts.parser()
                .setSigningKey("jwt_key".getBytes(StandardCharsets.UTF_8))
                .parse(token)
                .getBody();
    }
}
