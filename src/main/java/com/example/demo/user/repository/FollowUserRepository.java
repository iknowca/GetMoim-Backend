package com.example.demo.user.repository;

import com.example.demo.user.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {
}
