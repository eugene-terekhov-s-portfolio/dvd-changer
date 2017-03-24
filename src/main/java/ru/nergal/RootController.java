package ru.nergal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by terekhov-ev on 24.03.2017.
 */
@Controller(value = "/")
public class RootController {
    @RequestMapping(value = "/")
    public String showHomePage() {
        return "home";
    }
}
