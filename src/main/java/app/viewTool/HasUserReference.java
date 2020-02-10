package app.viewTool;

import app.entity.User;

public interface HasUserReference {
    User user = null;
    void setUser(User user);
}
