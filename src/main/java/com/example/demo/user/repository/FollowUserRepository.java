package com.example.demo.user.repository;

import com.example.demo.user.entity.FollowUser;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
    @Query("select f from FollowUser f where f.followee=:user and f.followee.id = :userId")
    FollowUser findByFollowerAndFolloweeId(User user, Long userId);

    @Query("select f from FollowUser f join fetch f.followee.profile where f.follower=:user")
    List<FollowUser> findAllByFollower(User user);
    @Query("select f.followee.id from FollowUser f where f.follower=:user")
    List<Long> findByUserIdsByUser(User user);
}
