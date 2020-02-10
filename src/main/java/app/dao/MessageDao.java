package app.dao;

import app.entity.Message;
import app.entity.Report;
import app.entity.User;

import javax.persistence.PersistenceException;
import java.util.Optional;

public class MessageDao extends GenericDao<Message> {

    private ReportDao reportDao = new ReportDao();

    public Optional<Message> create(User user, Report report, String msg){
        try{
            Message message = new Message(user,msg);
            save(message);
            report.addMessage(message);
            reportDao.updateReport(report);
            return Optional.of(message);
        } catch(PersistenceException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
