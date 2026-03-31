public class Staff {
    private final int staffId; // final just means that the id will not change
    private final String name;

    public Staff(int staffId, String name) {
        this.staffId = staffId;
        this.name = name;
    }

    public int getStaffId() {
        return this.staffId;
    }

    public String getName() {
        return this.name;
    }
}