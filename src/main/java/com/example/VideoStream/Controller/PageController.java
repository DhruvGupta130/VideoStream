package com.example.VideoStream.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/video")
    public String video() {
        return "video";
    }
    @GetMapping("/play")
    public String play() {
        return "play";
    }
}
