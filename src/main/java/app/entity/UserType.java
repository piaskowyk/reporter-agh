package app.entity;

public enum UserType {
    Worker(1, "Worker"),
    Coordinator(2, "Coordinator"),
    Admin(3, "Admin");

    public final int level;
    public final String name;

    UserType(int level, String name) {
        this.level = level;
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
