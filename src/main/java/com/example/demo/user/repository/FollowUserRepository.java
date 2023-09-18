package com.example.demo.user.repository;

import com.example.demo.user.entity.FollowUser;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
    @Query("select f from FollowUser f where f.followee=:user and f.followee.id = :userId")
    FollowUser findByFollowerAndFolloweeId(User user, Long userId);
}
