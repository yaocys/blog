package cc.yaos.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author yaocy yaosunique@gmail.com
 * 2023/1/17 11:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Essay {
    private String id;
    private String title;
    private String content;
    private Date createTime;
    private Integer views;
}
