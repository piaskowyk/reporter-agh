package app.accounts;

import app.dao.UserDao;
import app.entity.User;
import app.entity.UserType;
import app.session.SessionService;
import org.junit.*;

import java.util.Optional;

public class AccountsTests {

    @AfterClass
    public static void shutdownHibernate(){
        SessionService.closeSession();
    }

    @Test
    public void addUserTest(){
        UserDao userDao = new UserDao();
        userDao.create("Name","Surname","test@log.in","testpasswd", UserType.Admin);

        Optional<User> user = userDao.findByLoginAndPassword("test@log.in", "testpasswd");
        Assert.assertTrue(user.isPresent());

        userDao.removeUser(user.get());
    }

    @Test
    public void changeUserTest(){
        UserDao userDao = new UserDao();
        userDao.create("Name","Surname","test@log.in","testpasswd", UserType.Admin);
        Optional<User> user = userDao.findByLoginAndPassword("test@log.in", "testpasswd");
        Assert.assertTrue(user.isPresent());
        Assert.assertTrue(user.get().getName().equals("Name"));

        user.get().setName("Second");
        userDao.updateUser(user.get());

        user = userDao.findByLoginAndPassword("test@log.in", "testpasswd");
        Assert.assertTrue(user.isPresent());
        Assert.assertTrue(user.get().getName().equals("Second"));

        userDao.removeUser(user.get());
    }
}
