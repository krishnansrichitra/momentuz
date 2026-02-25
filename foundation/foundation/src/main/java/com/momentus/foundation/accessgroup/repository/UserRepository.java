package com.momentus.foundation.accessgroup.repository;

import com.momentus.foundation.accessgroup.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserId(String username);

  @Query(
      """
    select u
    from User u
    where u.orgId.id = :orgId and  ( lower(u.userId) like lower(concat('%', :search, '%'))
       or lower(u.firstName) like lower(concat('%', :search, '%'))
       or lower(u.lastName) like lower(concat('%', :search, '%')))
""")
  List<User> searchUsers(@Param("search") String search, @Param("orgId") Long orgId);
}
