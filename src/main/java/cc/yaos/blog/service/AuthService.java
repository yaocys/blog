package cc.yaos.blog.service;

import dev.samstevens.totp.code.*;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TOTP 二步验证 + 会话管理
 * @author yao
 */
@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${blog.auth.totp-secret:}")
    private String totpSecret;

    @Value("${blog.auth.session-max-age:604800}")
    private long sessionMaxAge; // 秒

    // token -> 过期时间戳（毫秒）
    private final ConcurrentHashMap<String, Long> sessions = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        if (totpSecret == null || totpSecret.isBlank()) {
            totpSecret = new DefaultSecretGenerator().generate();
            log.warn("=======================================================");
            log.warn("  未配置 TOTP 密钥，已自动生成（重启后会变化！）");
            log.warn("  请访问 GET /auth/qrcode 用 Authenticator 扫码绑定，");
            log.warn("  然后将以下密钥填入 application.yml 的 blog.auth.totp-secret：");
            log.warn("  SECRET = {}", totpSecret);
            log.warn("=======================================================");
        }
    }

    /** 校验 TOTP 6位动态码 */
    public boolean verifyTotp(String code) {
        try {
            CodeVerifier verifier = new DefaultCodeVerifier(
                    new DefaultCodeGenerator(),
                    new SystemTimeProvider()
            );
            return verifier.isValidCode(totpSecret, code);
        } catch (Exception e) {
            log.error("TOTP 验证异常", e);
            return false;
        }
    }

    /** 验证成功后创建会话 token，写入 Cookie */
    public String createSession() {
        String token = UUID.randomUUID().toString();
        long expiry = System.currentTimeMillis() + sessionMaxAge * 1000L;
        sessions.put(token, expiry);
        return token;
    }

    /** 校验 Cookie 中的会话 token 是否有效 */
    public boolean isValidSession(String token) {
        if (token == null) return false;
        Long expiry = sessions.get(token);
        if (expiry == null) return false;
        if (System.currentTimeMillis() > expiry) {
            sessions.remove(token);
            return false;
        }
        return true;
    }

    /** 登出：移除会话 */
    public void removeSession(String token) {
        if (token != null) sessions.remove(token);
    }

    /** 生成二维码，返回 data URI（供初始绑定用） */
    public String getQrCodeDataUri() throws Exception {
        QrData data = new QrData.Builder()
                .label("Blog")
                .secret(totpSecret)
                .issuer("My Blog")
                .digits(6)
                .period(30)
                .build();

        ZxingPngQrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageBytes = generator.generate(data);
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        return "data:" + generator.getImageMimeType() + ";base64," + base64;
    }
}
