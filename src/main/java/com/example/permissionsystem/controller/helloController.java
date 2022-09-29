package com.example.permissionsystem.controller;

import com.example.permissionsystem.utils.R;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class helloController {


    @GetMapping("world")
//    @Secured("ROLE_admin")
    @PreAuthorize("hasRole('admin')")
    public R helloWorld(){
        return R.ok("hello world");
    }
}
