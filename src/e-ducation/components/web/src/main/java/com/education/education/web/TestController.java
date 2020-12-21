package com.education.education.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("")
@EnableWebMvc
@CrossOrigin(origins = "*")
public class TestController {


    @Autowired
    public TestController(){
    }

    /********** Test ************/

    /**   PATH: /test/   **/
    /**   Get get test string **/
    // This should probs be deleted or heavily restricted
    @GetMapping("/test")
    public String getTest() {
        return "This is a test string.";
    }

}
