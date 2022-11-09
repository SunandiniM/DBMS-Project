public class MechanicFreeSlot {
    public int startSlot;
    public int endSlot;
    public String EMPID;

    public int week;

    public int day;

    public MechanicFreeSlot() {
        startSlot = -1;
        endSlot = -1;
        EMPID = "";
        week = -1;
        day = -1;
    }

    public MechanicFreeSlot(int startSlot, int endSlot, int week, int day, String EMPID) {
        this.startSlot = startSlot;
        this.endSlot = endSlot;
        this.week = week;
        this.day = day;
        this.EMPID = EMPID;
    }
}
