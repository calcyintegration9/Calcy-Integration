package com.CalcyIntegration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.CalcyIntegration.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
