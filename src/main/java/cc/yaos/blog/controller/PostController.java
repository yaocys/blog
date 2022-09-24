package cc.yaos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yao 2022/9/24
 */
@Controller
public class PostController {
    @RequestMapping("/post")
    public String selectPost(){return "/site/post";}
}
