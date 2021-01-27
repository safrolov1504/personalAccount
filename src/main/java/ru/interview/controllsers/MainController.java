package ru.interview.controllsers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage(){
        log.debug("Открыта старотовая страницы");
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "index";
    }

}
