package cc.yaos.blog.dao;

import cc.yaos.blog.entity.Essay;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/17 10:58
 */
@Mapper
public interface EssayMapper {

    void insertEssay(Essay essay);

    List<Essay> selectAll();

    Essay selectOne(String id);

    List<Essay> listAll();
}
