package ru.maxim.barybians.api.model;

public class DialogPreview {

    private User interlocutor;
    private Message lastMessage;

    public DialogPreview(User interlocutor, Message lastMessage) {
        this.interlocutor = interlocutor;
        this.lastMessage = lastMessage;
    }

    public User getInterlocutor() {
        return interlocutor;
    }

    public Message getLastMessage() {
        return lastMessage;
    }
}
