package app.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = User.TABLE_NAME)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = Columns.ID)
    private int id;

    @Column(name = Columns.NAME, nullable = false, length = 50)
    private String name;

    @Column(name = Columns.SURNAME, nullable = false, length = 50)
    private String surname;

    @Column(name = Columns.LOGIN, nullable = false, length = 50)
    private String login;

    @Column(name = Columns.PASSWORD, nullable = false, length = 50)
    private String password;

    @Column(name = Columns.USER_TYPE, nullable = false)
    private UserType userType;

    @Column(name = Columns.IS_DELETED, nullable = false)
    private boolean isDeleted;

    public User() {
    }

    public User(String name, String surname, String login, String password, UserType userType) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.isDeleted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public static final String TABLE_NAME = "user";

    public static class Columns {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String SURNAME = "surname";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String USER_TYPE = "user_type";
        public static final String IS_DELETED = "is_deleted";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId();
    }


}