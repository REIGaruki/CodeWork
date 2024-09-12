package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info")
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping()
    public String getInfo() {
        return "Current server port is " + serverPort;
    }
}
