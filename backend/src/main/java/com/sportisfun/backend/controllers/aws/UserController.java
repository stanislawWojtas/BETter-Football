package com.sportisfun.backend.controllers.aws;

import com.sportisfun.backend.models.User;
import com.sportisfun.backend.security.CustomUserDetails;
import com.sportisfun.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/balance")
    public ResponseEntity<Map<String, BigDecimal>> getUserBalance(@AuthenticationPrincipal CustomUserDetails userDetails){
        return userService.getBalance(userDetails.getId());
    }
}
