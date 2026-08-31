package com.example.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

//@RestController
@Controller
@RequestMapping("response")
public class ResponseController {
    @RequestMapping("index")
    public Object index(){
        return "/index.html";
    }

    @RequestMapping("returnJson")
    @ResponseBody
    public HashMap<String, String> returnJson() {
        HashMap<String , String> map = new HashMap<>();
        map.put("Java", "Java Value");
        map.put("MySQL", "MySQL Value");
        map.put("Redis", "Redis Value");
        return map;
    }
}
