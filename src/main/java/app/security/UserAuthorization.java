package app.security;

import app.dao.UserDao;
import app.entity.User;
import app.entity.UserType;
import app.viewTool.ViewManager;

import java.util.Optional;

public class UserAuthorization implements AuthorizationProvider {
    private User user = null;
    private UserDao userDao = new UserDao();

    public UserAuthorization() {}

    @Override
    public boolean isAuthorized() {
        return user != null;
    }

    @Override
    public boolean authorize(String login, String password) {
        Optional<User> optionalUser = userDao.findByLoginAndPassword(login, password);
        if(optionalUser.isPresent()){
            user = optionalUser.get();
            return true;
        }

        return false;
    }

    @Override
    public void logOut() {
        user = null;
    }

    @Override
    public User getUser() {
        return user;
    }
}
