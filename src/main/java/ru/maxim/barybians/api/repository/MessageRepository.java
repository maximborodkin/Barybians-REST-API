package ru.maxim.barybians.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maxim.barybians.api.model.Message;

import javax.persistence.EntityManager;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("FROM Message WHERE (sender_id = :firstUser AND reciever_id = :secondUser) OR (sender_id = :secondUser AND reciever_id = :firstUser)")
    List<Message> getDialog(long firstUser, long secondUser);

    @Query(value =
            "SELECT m.sender_id FROM messages m WHERE m.reciever_id = :id " +
            "UNION " +
            "SELECT m.reciever_id FROM messages m WHERE m.sender_id = :id",
            nativeQuery = true)
    List<Long> getAllUserInterlocutors(long id);

    @Query(value =
            "SELECT * FROM messages WHERE (sender_id = :firstUser AND reciever_id = :secondUser) " +
                    "OR (sender_id = :secondUser AND reciever_id = :firstUser)" +
                    " ORDER BY time DESC LIMIT 1",
            nativeQuery = true)
    Message getLastMessageInDialog(long firstUser, long secondUser);

}
