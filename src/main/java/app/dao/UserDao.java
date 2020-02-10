package app.dao;

import app.entity.User;
import app.entity.UserType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao extends GenericDao<User> {

    ObservableList<User> observableUsers = FXCollections.observableArrayList();

    public Optional<User> create(String name, String surname, String username, String password, UserType userType) {
        User user = new User(name, surname, username, password, userType);
        try {
            save(user);
            observableUsers.add(user);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> findByLogin(String login) {
        try {
            User user = currentSession()
                    .createQuery(
                            "SELECT u FROM User u WHERE u.login = :login and u.isDeleted = false",
                            User.class
                    )
                    .setParameter("login", login).getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public boolean updateUser(User user){
        try{
            update(user);
            observableUsers.set(observableUsers.indexOf(user), user);
            return true;
        }catch (PersistenceException e){
            return false;
        }
    }

    public boolean removeUser(User user){
        try{
            user.setDeleted(true);
            update(user);
            observableUsers.remove(user);
            return true;
        }catch (PersistenceException e){
            return false;
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            User user = currentSession().createQuery(
                    "SELECT u FROM User u " +
                            "WHERE u.login = :login and u.password = :password and u.isDeleted = false",
                    User.class
            ).setParameter("login", login).setParameter("password", password).getSingleResult();
            return Optional.of(user);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public List<User> getAll(){
        try{
            return currentSession()
                    .createQuery("SELECT u FROM User u where u.isDeleted = false ", User.class)
                    .getResultList();
        }catch (PersistenceException e){
            return Collections.emptyList();
        }
    }

    public ObservableList<User> getAllObservable(){
        this.observableUsers = FXCollections.observableArrayList(getAll());
        return observableUsers;
    }
}
