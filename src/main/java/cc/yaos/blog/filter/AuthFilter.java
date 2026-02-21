package cc.yaos.blog.filter;

import cc.yaos.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 写操作鉴权过滤器
 * 保护 POST /essay/** 接口，必须携带有效的会话 Cookie。
 * /auth/** 接口不受保护（用于认证流程本身）。
 * @author yao
 */
@Order(2)
@Component
public class AuthFilter implements Filter {

    @Autowired
    private AuthService authService;

    @Value("${blog.auth.cookie-name:blog_admin}")
    private String cookieName;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();
        String path = request.getRequestURI();

        // 仅保护 essay 的写操作；/auth/** 始终放行（否则 verify 接口无法使用）
        boolean needsAuth = "POST".equalsIgnoreCase(method) && path.startsWith("/essay/");

        if (!needsAuth) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        // 校验会话 Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName()) && authService.isValidSession(c.getValue())) {
                    chain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"请先完成 TOTP 验证\"}");
    }
}
