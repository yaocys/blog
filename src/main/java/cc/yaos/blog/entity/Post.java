package cc.yaos.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author yao 2022/9/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Post {
    private int postId;
    private String title;
    private String summary;
    private String content;
    Date releaseTime;
}
