package cc.yaos.blog.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * 解决cors跨域问题
 * @author yaocy yaosunique@gmail.com
 * 2023/1/12 13:45
 */
@WebFilter(filterName = "CorsFilter")
@Configuration
public class CorsFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String curOrigin = request.getHeader("Origin");
        if(StringUtils.isNotBlank(curOrigin))
            logger.info("### 跨域过滤器->当前访问来源->" + curOrigin + " ### " + new Date());
        // 如果需要跨域权限，可以判断一下来源
/*        if(curOrigin.contains("127.0.0.1:8080")){
            response.setHeader("Access-Control-Allow-Origin", "*");
        }*/
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
