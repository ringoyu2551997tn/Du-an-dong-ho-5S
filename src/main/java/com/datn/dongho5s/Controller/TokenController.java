package com.datn.dongho5s.Controller;

import com.datn.dongho5s.Security.AccountFilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    private final AccountFilterService accountFilterService;

    public TokenController(AccountFilterService accountFilterService) {
        this.accountFilterService = accountFilterService;
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, Authentication authentication) {
        // Lấy token từ request
        String token = extractTokenFromRequest(request);

        // Kiểm tra tính hợp lệ của token và người dùng được xác thực
        if (token != null && authentication != null) {
            // Xác thực người dùng
            boolean isUserValid = accountFilterService.isTokenValid(token, (UserDetails) authentication.getPrincipal());
            if (isUserValid) {
                // Tạo lại token mới và trả về cho client
                String newToken = accountFilterService.refreshToken(token);
                if (newToken != null) {
                    return ResponseEntity.ok(newToken);
                }
            }
        }

        // Trả về lỗi nếu token không hợp lệ hoặc không thể làm mới token
        return ResponseEntity.badRequest().body("Không thể làm mới token hoặc token không hợp lệ.");
    }

    // Phương thức hỗ trợ để lấy token từ request header
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
