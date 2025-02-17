package cc.yaos.blog.service;

import cc.yaos.blog.dao.EssayMapper;
import cc.yaos.blog.entity.Essay;
import cc.yaos.blog.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/21 13:38
 */
@Service
public class EssayServiceImpl implements EssayService{

    @Resource
    EssayMapper essayMapper;

    public void insertEssay(Essay essay){
        essay.setId(CommonUtil.generateUUID());
        essay.setCreateTime(new Date());
        essayMapper.insertEssay(essay);
    }

    public List<Essay> selectAll(){
        return essayMapper.selectAll();
    }

    public Essay selectOne(String id){
        return essayMapper.selectOne(id);
    }

    public List<Essay> listAll(){
        return essayMapper.listAll();
    }
}
