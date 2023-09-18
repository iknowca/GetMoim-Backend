package com.example.demo.user.repository;

import com.example.demo.user.entity.BlockUser;
import com.example.demo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlockUserRepository extends JpaRepository<BlockUser, Long> {
    @Query("select b from BlockUser b where b.user=:user and b.blockedUser.id=:userId")
    BlockUser findByUserAndBlockUserId(User user, Long userId);
}
