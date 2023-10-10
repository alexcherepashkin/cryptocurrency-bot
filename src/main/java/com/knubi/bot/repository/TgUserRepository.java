package com.knubi.bot.repository;

import com.knubi.bot.model.TgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TgUserRepository extends JpaRepository<TgUser, Long> {

    Optional<TgUser> findById(Long id);
}
