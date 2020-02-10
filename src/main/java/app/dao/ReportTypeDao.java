package app.dao;

import app.entity.FormFieldPattern;
import app.entity.ReportType;
import app.entity.User;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

public class ReportTypeDao extends GenericDao<ReportType> {

    public Optional<List<ReportType>> getAll() {
        try {
            List<ReportType> reportTypeList = currentSession().createQuery(
                    "SELECT rt FROM ReportType rt",
                    ReportType.class
            ).getResultList();
            return Optional.of(reportTypeList);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
    }

    public Optional<ReportType> add(String name, List<FormFieldPattern> formFieldsPattern) {
        ReportType reportType = new ReportType(name, formFieldsPattern);
        try {
            save(reportType);
        } catch (PersistenceException e) {
            return Optional.empty();
        }
        return Optional.of(reportType);
    }

    public boolean removeReportType(ReportType reportType){
        try{
            remove(reportType);
            return true;
        }catch (PersistenceException e){
            return false;
        }
    }

}
