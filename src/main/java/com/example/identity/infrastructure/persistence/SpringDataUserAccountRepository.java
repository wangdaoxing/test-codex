package com.example.identity.infrastructure.persistence;

import com.example.identity.domain.account.UserAccount;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface SpringDataUserAccountRepository extends CrudRepository<UserAccount, UUID> {

    Optional<UserAccount> findByUsername(String username);

    @Query("SELECT * FROM user_accounts WHERE email = :email")
    Optional<UserAccount> findByEmail(@Param("email") String email);

    boolean existsByUsername(String username);

    @Query("SELECT CASE WHEN COUNT(1) > 0 THEN TRUE ELSE FALSE END FROM user_accounts WHERE email = :email")
    boolean existsByEmail(@Param("email") String email);
}
