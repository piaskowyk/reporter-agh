package app.security;

import app.entity.User;

public interface AuthorizationProvider {
    boolean isAuthorized();
    boolean authorize(String login, String password);
    void logOut();
    User getUser();
}