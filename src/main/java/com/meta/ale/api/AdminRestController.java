package com.meta.ale.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @GetMapping("/admin-get")
    public String testAccess() {
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        return "admin";
    }
}
