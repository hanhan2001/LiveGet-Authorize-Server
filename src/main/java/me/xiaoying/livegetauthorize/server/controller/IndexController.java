package me.xiaoying.livegetauthorize.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Page index
 */
@RestController
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}