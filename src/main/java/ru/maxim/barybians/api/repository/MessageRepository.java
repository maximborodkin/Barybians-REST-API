package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maxim.barybians.api.model.Message;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("FROM Message WHERE (sender_id = :firstUser AND reciever_id = :secondUser) OR (sender_id = :secondUser AND reciever_id = :firstUser)")
    List<Message> getDialog(Long firstUser, Long secondUser);

}
