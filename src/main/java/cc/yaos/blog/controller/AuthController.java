package cc.yaos.blog.controller;

import cc.yaos.blog.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 鉴权接口
 * @author yao
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${blog.auth.cookie-name:blog_admin}")
    private String cookieName;

    @Value("${blog.auth.session-max-age:604800}")
    private int sessionMaxAge;

    /**
     * 检查当前 Cookie 是否已认证（Editor 页面挂载时调用）
     */
    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> check(HttpServletRequest request) {
        if (hasValidSession(request)) {
            return ResponseEntity.ok(Map.of("authenticated", true));
        }
        return ResponseEntity.status(401).body(Map.of("authenticated", false));
    }

    /**
     * 提交 TOTP 6位码进行验证
     * 验证通过后写入 HttpOnly Cookie，有效期由配置决定
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(
            @RequestBody Map<String, String> body,
            HttpServletResponse response) {

        String code = body.get("code");
        if (code == null || !code.matches("\\d{6}")) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "请输入6位数字验证码"));
        }

        if (!authService.verifyTotp(code)) {
            return ResponseEntity.status(401)
                    .body(Map.of("success", false, "message", "验证码错误或已过期"));
        }

        String token = authService.createSession();
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(sessionMaxAge);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("success", true, "message", "验证成功"));
    }

    /**
     * 登出：清除 Cookie 和服务端会话
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (cookieName.equals(c.getName())) {
                    authService.removeSession(c.getValue());
                    Cookie clear = new Cookie(cookieName, "");
                    clear.setHttpOnly(true);
                    clear.setPath("/");
                    clear.setMaxAge(0);
                    response.addCookie(clear);
                    break;
                }
            }
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

    /**
     * 返回 TOTP 绑定二维码（仅初始设置时使用一次，建议本地访问）
     */
    @GetMapping("/qrcode")
    public ResponseEntity<Map<String, String>> qrCode() {
        try {
            String dataUri = authService.getQrCodeDataUri();
            return ResponseEntity.ok(Map.of("qrcode", dataUri));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // ---- 内部工具 ----

    private boolean hasValidSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;
        for (Cookie c : cookies) {
            if (cookieName.equals(c.getName()) && authService.isValidSession(c.getValue())) {
                return true;
            }
        }
        return false;
    }
}
