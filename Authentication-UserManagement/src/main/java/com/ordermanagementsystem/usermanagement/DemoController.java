package com.ordermanagementsystem.usermanagement;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    @GetMapping("/v1")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from ");
    }
}
