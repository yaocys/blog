package cc.yaos.blog.controller;

import cc.yaos.blog.entity.Essay;
import cc.yaos.blog.service.EssayService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/17 11:05
 */
@RestController
@RequestMapping("/essay")
public class EssayController {

    @Resource
    EssayService essayService;

    @PostMapping("/saveEssay")
    public String save(@RequestBody Essay essay){
        if(essay.getId()==null ||"".equals(essay.getId())){
            essayService.insertEssay(essay);
        }
        return "发布成功";
    }

    @GetMapping("selectAll")
    public List<Essay> selectAll(){
        return essayService.selectAll();
    }

    @GetMapping("/selectOne")
    public Essay selectOne(String id){
        return essayService.selectOne(id);
    }

    @GetMapping("/listAll")
    public List<Essay> listAll(){
        return essayService.listAll();
    }
}
