package com.momentus.foundation.accessgroup.repository;

import com.momentus.foundation.accessgroup.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserId(String username);
}
