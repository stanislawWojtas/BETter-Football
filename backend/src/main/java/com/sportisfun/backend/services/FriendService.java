package com.sportisfun.backend.services;

import com.sportisfun.backend.DTOs.FriendshipResponseDto;
import com.sportisfun.backend.models.Friendship;
import com.sportisfun.backend.models.FriendshipStatus;
import com.sportisfun.backend.models.User;
import com.sportisfun.backend.repositories.FriendshipRepository;
import com.sportisfun.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;


    public ResponseEntity<FriendshipResponseDto> sendRequest(Long senderId, String username){

        Optional<User> addresee = userRepository.findByUsername(username);
        if(addresee.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Long addreseeId = addresee.get().getId();


        //relacja już istnieje
        if(friendshipRepository.existsByRequesterIdAndAddresseeId(senderId, addreseeId) || friendshipRepository.existsByRequesterIdAndAddresseeId(addreseeId, senderId)){
            return ResponseEntity.status(409).build();
        }
        Optional<User> requester = userRepository.findById(senderId);
        if(requester.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Friendship friendship = Friendship.builder()
                .requester(requester.get())
                .addressee(addresee.get())
                .status(FriendshipStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Friendship saved = friendshipRepository.save(friendship);
        return ResponseEntity.ok().body(mapToDto(saved));
    }

    public ResponseEntity<FriendshipResponseDto> acceptRequest(Long requesterId, Long addresseeId){
        Optional<Friendship> friendshipOpt = friendshipRepository.findByRequesterIdAndAddresseeIdAndStatus(requesterId, addresseeId, FriendshipStatus.PENDING);
        if(friendshipOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Friendship friendship = friendshipOpt.get();
        friendship.setStatus(FriendshipStatus.ACCEPTED);

        Friendship saved = friendshipRepository.save(friendship);
        return ResponseEntity.ok(mapToDto(saved));
    }

    public ResponseEntity<FriendshipResponseDto> declineRequest(Long requesterId, Long addresseeId){
        Optional<Friendship> friendshipOpt = friendshipRepository.findByRequesterIdAndAddresseeIdAndStatus(requesterId, addresseeId, FriendshipStatus.PENDING);
        if(friendshipOpt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Friendship friendship = friendshipOpt.get();
        friendship.setStatus(FriendshipStatus.DECLINED);

        Friendship saved = friendshipRepository.save(friendship);
        return ResponseEntity.ok(mapToDto(saved));
    }

    public ResponseEntity<List<FriendshipResponseDto>> getAllRequests(Long userId){
        List<Friendship> friendships = friendshipRepository.findAllByAddresseeId(userId);
        return ResponseEntity.ok(friendships.stream().filter(friendship -> friendship.getStatus().equals(FriendshipStatus.PENDING)).map(friendship -> mapToDto(friendship)).toList());
    }

    public ResponseEntity<List<FriendshipResponseDto>> getAllFriends(Long userId){
        List<Friendship> friendships = friendshipRepository.findAllByAddresseeIdOrRequesterId(userId, userId);
        return ResponseEntity.ok(friendships.stream().filter(friendship -> friendship.getStatus().equals(FriendshipStatus.ACCEPTED)).map(friendship -> mapToDto(friendship)).toList());
    };


    private FriendshipResponseDto mapToDto(Friendship friendship){
        return FriendshipResponseDto.builder()
                .id(friendship.getId())
                .createdAt(friendship.getCreatedAt())
                .status(friendship.getStatus())
                .requester(FriendshipResponseDto.FriendUserDto.builder()
                        .id(friendship.getRequester().getId())
                        .username(friendship.getRequester().getUsername())
                        .balance(friendship.getRequester().getBalance())
                        .build())
                .addressee(FriendshipResponseDto.FriendUserDto.builder()
                        .id(friendship.getAddressee().getId())
                        .username(friendship.getAddressee().getUsername())
                        .balance(friendship.getAddressee().getBalance())
                        .build())
                .build();
    }
}
