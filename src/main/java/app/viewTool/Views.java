package app.viewTool;

public enum Views {
    Login("login", "login", "Login", 0),
    MainView("mainView", "", "Dashboard", 1),
    AddReport("addReport", "", "Add new report", 1),
    MyReportsList("myReportsList", "", "My reports list", 1),
    ReportsListToConsider("reportsListToConsider", "", "Reports list to consider", 2),
    Accounts("accounts", "", "Accounts", 3);

    public final String path;
    public final String cssPath;
    public final String title;
    public final int permissionLevel;

    Views(String path, String cssPath, String title, int permissionLevel){
        this.path = path;
        this.cssPath = cssPath;
        this.title = title;
        this.permissionLevel=permissionLevel;
    }
}