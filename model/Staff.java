package model;

public class Staff {
    private final int id; // final just means that the id will not change
    private final String name;
    private final String password;
    private final boolean isAdmin; // manager = true & cashier = false

    public Staff(int id, String name, String password, boolean isAdmin) {
        this.id       = id;
        this.name     = name;
        this.password = password;
        this.isAdmin  = isAdmin;
    }

    // Getters
    public int getId()          { return id; }
    public String getName()     { return name; }
    public String getPassword() { return password; }
    public boolean isAdmin()    { return isAdmin; }
}