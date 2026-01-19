package com.sportisfun.backend.DTOs;

import com.sportisfun.backend.models.FriendshipStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class FriendshipResponseDto {
    private Long id;
    private FriendUserDto requester;
    private FriendUserDto addressee;
    private FriendshipStatus status;
    private LocalDateTime createdAt;

    @Data
    @Builder
    public static class FriendUserDto {
        private Long id;
        private String username;
        private BigDecimal balance;
    }
}
