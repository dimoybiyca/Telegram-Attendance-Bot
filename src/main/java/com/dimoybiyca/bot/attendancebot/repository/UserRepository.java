package com.dimoybiyca.bot.attendancebot.repository;

import com.dimoybiyca.bot.attendancebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByChatId(long chatId);

    User findByVariant(int variant);
}
