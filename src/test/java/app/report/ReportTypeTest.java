package app.report;

import app.dao.ReportTypeDao;
import app.entity.FormFieldPattern;
import app.entity.ReportType;
import app.form.FormFieldType;
import app.session.SessionService;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportTypeTest {

    @AfterClass
    public static void shutdownHibernate(){
        SessionService.closeSession();
    }

    @Test
    public void addReportTypeTest() {
        ReportTypeDao reportTypeDao = new ReportTypeDao();

        List<FormFieldPattern> formFieldsPattern = new ArrayList<>();

        formFieldsPattern.add(new FormFieldPattern("test", FormFieldType.TextArea));
        formFieldsPattern.add(new FormFieldPattern("test", FormFieldType.StandardInput));

        Optional<ReportType> optionalReportTypeDao = reportTypeDao.add("test", formFieldsPattern);
        Assert.assertTrue(optionalReportTypeDao.isPresent());

        Assert.assertTrue(reportTypeDao.removeReportType(optionalReportTypeDao.get()));
    }
}
