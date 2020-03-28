package ru.maxim.barybians.api.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message extends BaseEntity implements Comparable<Message>{

    @ManyToOne
    @JoinColumn(name = "reciever_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Date time;

    @Column(name = "unread")
    private int unread;

    @Override
    public int compareTo(Message other) {
        return Long.compare(time.getTime(), other.getTime().getTime());
    }

    @Override
    public String toString(){
        return getId()+": "+getReceiver()+" "+getSender()+" "+getTime();
    }

    public String toJsonString() {
        return "{\"id\":" + getId()
                + ",\"senderId\":" + getSender().getId()
                + ",\"receiverId\":" + getReceiver().getId()
                + ",\"text\":\"" + getText() + "\""
                + ",\"time\":" + getTime().getTime()
                +"}";
    }
}