package chewyt.Template.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import static chewyt.Template.Constants.*;


@Controller
@RequestMapping(path = "/")
public class indexController {
    
    @GetMapping
    public String getHome(){
        return "index";
    }
}
