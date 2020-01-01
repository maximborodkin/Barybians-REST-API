package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maxim.barybians.api.model.Message;


public interface MessageRepository extends JpaRepository<Message, Long> {

}
