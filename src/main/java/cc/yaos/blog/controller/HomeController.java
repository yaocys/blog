package cc.yaos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yao 2022/9/24
 */
@Controller
public class HomeController {
    @RequestMapping("/index")
    public String JumpToHomePage(){
        return "/index";
    }
}
