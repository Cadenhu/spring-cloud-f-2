package com.microservices.springcloudprovider.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bert.hu
 * @Description:
 * @date 2018/11/2822:44
 **/
@RestController
public class HelloController {

    @Value("${server.port}")
    private String port;
    @GetMapping("/hi")
    String SayHi(String name) {
        return "Hello " + name + " with port:" + port;
    }
}
