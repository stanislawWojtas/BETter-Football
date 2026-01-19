package com.sportisfun.backend.controllers;

import com.sportisfun.backend.DTOs.FriendshipResponseDto;
import com.sportisfun.backend.models.Friendship;
import com.sportisfun.backend.security.CustomUserDetails;
import com.sportisfun.backend.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendsController {
    private final FriendService friendService;

    @PostMapping("/send/{username}")
    public ResponseEntity<FriendshipResponseDto> sendFriendRequest(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String username){
        return friendService.sendRequest(userDetails.getId(), username);
    }

    @PostMapping("/accept/{requesterId}")
    public ResponseEntity<FriendshipResponseDto> acceptRequest(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long requesterId){
        return friendService.acceptRequest(requesterId, userDetails.getId());
    }

    @PostMapping("/decline/{requesterId}")
    public ResponseEntity<FriendshipResponseDto>  declineRequest(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long requesterId){
        return friendService.declineRequest(requesterId, userDetails.getId());
    }

    @GetMapping("/requests")
    public ResponseEntity<List<FriendshipResponseDto>> getRequests(@AuthenticationPrincipal CustomUserDetails userDetails){
        return friendService.getAllRequests(userDetails.getId());
    }

    @GetMapping("/friends")
    public ResponseEntity<List<FriendshipResponseDto>> getFriends(@AuthenticationPrincipal CustomUserDetails userDetails){
        return friendService.getAllFriends(userDetails.getId());
    }
}
