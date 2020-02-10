package app;

import app.dao.ReportTypeDao;
import app.dao.UserDao;
import app.entity.FormFieldPattern;
import app.entity.ReportType;
import app.entity.User;
import app.entity.UserType;
import app.form.FormFieldType;
import app.session.SessionService;
import app.viewTool.ViewManager;
import app.viewTool.Views;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

//import org.hibernate.query.Query;

public class Main extends Application {

    @Override
    public void stop() throws Exception {
        super.stop();
        SessionService.closeSession();
        SessionService.closeSessionFactory();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager.init(stage);
        ViewManager.showView(Views.Login);
    }

    public static void main(String[] args) {
//        Session session = SessionService.getSession();
//        Transaction transaction = session.beginTransaction();
//
//        ReportType reportType1 = new ReportType();
//        reportType1.setName("extended");
//        reportType1.addFormFieldToPattern(new FormFieldPattern("Opis problemu", FormFieldType.TextArea));
//
//        ReportType reportType2 = new ReportType();
//        reportType2.setName("short");
//        reportType2.addFormFieldToPattern(new FormFieldPattern("Krotki opis", FormFieldType.StandardInput));
//
////
//        session.save(reportType1);
//        session.save(reportType2);
//        transaction.commit();
//
//        UserDao userDao = new UserDao();
//        userDao.create("Studka", "Studkowska", "studka@agh.edu.pl", "haselko", UserType.Admin);
//        userDao.create("Studek", "Studkowsk","studek@agh.edu.pl","passwd",UserType.Worker);
//        Optional<User> user = userDao.findByLogin("studka");
//        if(user.isPresent()){
//            System.out.println(user.get().getPassword());
//        }
//        SessionService.closeSession();
//        SessionService.closeSessionFactory();

//
////        Session session = SessionService.getSession();
////        Transaction transaction = session.beginTransaction();
//
//        ReportType reportType = new ReportType();
//        reportType.setName("default asd");
//        reportType.addFormFieldToPattern(new FormFieldPattern("Opis problemu2", FormFieldType.TextArea));
//        reportType.addFormFieldToPattern(new FormFieldPattern("Opis problemu oo", FormFieldType.TextArea));
//
//        session.save(reportType);
//        transaction.commit();
//
//
////        final Session session = SessionService.getSession();
//        final Transaction tx = session.beginTransaction();
//        User object = new User("", "", "", "", UserType.Coordinator);
//        session.save(object);
//        session.merge(object);
//        tx.commit();
//
//        ReportTypeDao reportTypeDao = new ReportTypeDao();
//        reportTypeDao.getAll();
//        reportTypeDao.getAll();
//
//        final Session session2 = SessionService.getSession();
//        final Transaction tx2 = session.beginTransaction();
//        User object2 = new User("", "", "", "", UserType.Coordinator);
//        session.save(object);
//        session.merge(object);
//        tx2.commit();

        launch();
    }

}