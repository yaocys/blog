package cc.yaos.blog.service;

import cc.yaos.blog.entity.Essay;
import java.util.*;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/17 13:31
 */
public interface EssayService {

    void insertEssay(Essay essay);

    List<Essay> selectAll();

    Essay selectOne(String id);

    List<Essay> listAll();

}
