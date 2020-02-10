package app.exception;

public class IViewControllerNotImplementedException extends RuntimeException {
    private String controllerName;

    public IViewControllerNotImplementedException(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getMessage() {
        return "This controller (" + controllerName + ") is not instance of ViewOnloadEvent";
    }
}
