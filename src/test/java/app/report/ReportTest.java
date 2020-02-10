package app.report;

import app.dao.ReportDao;
import app.entity.FormFieldPattern;
import app.entity.Report;
import app.entity.ReportType;
import app.entity.User;
import app.form.FormFieldType;
import app.session.SessionService;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReportTest {

    @AfterClass
    public static void shutdownHibernate(){
        try {
            SessionService.closeSession();
        }
        catch (Exception e) {}
    }

    @Test(expected = Exception.class)
    public void addReportTypeWrongPatternTest() throws Exception {
        ReportDao reportDao = new ReportDao();
        User user = new User();
        ReportType reportType = new ReportType();

        List<String> contents = new ArrayList<>();
        contents.add("aaaa");
        contents.add("bbbb");

        Optional<Report> optionalReport = Optional.empty();
        optionalReport = reportDao.add(user, reportType, contents);

        Assert.assertFalse(optionalReport.isPresent());
        Assert.assertTrue(reportDao.removeReport(optionalReport.get()));
    }
}
