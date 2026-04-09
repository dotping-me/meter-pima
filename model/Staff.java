package model;

public class Staff {
    private final int id; // final just means that the id will not change
    private final String name;
    private final String password;
    private final int isAdmin;

    public Staff(int id, String name, String password, int isAdmin) {
        this.id       = id;
        this.name     = name;
        this.password = password;
        this.isAdmin  = isAdmin;
    }

    // Getters
    public int getId()          { return this.id; }
    public String getName()     { return this.name; }
    public String getPassword() { return this.password; }
    public int isAdmin()        { return this.isAdmin; }
}