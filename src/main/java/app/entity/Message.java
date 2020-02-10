package app.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    private String message;

    private Date date;

    public Message() {}

    public Message(User user, String message){
        this.user = user;
        this.message = message;
        this.date = new Date();
    }

    public User getUser(){return user;}

    public String getMessage(){return message;}

    public Date getDate() {return date;}



}
