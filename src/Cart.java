import java.util.*;

public class Cart {
    public String Maintainance;
    public int MaintenanceCost;
    public int MaintenanceDuration;
    public ArrayList<String> RepairServiceList;
    public ArrayList<Integer> RepairServiceCostList;
    public ArrayList<Integer> RepairServiceDurationList;

    public Cart() {
        Maintainance = "";
        MaintenanceCost = 0;
        MaintenanceDuration = 0;
        RepairServiceList = new ArrayList<String>();
        RepairServiceCostList = new ArrayList<Integer>();
        RepairServiceDurationList = new ArrayList<Integer>();
    }

    public int getTotalCost() {
        int sum = MaintenanceCost;
        for (int i=0;i<RepairServiceList.size();i++) {
            sum += RepairServiceCostList.get(i);
        }
        return sum;
    }

    public int getTotalDuration() {
        int sum = MaintenanceDuration;
        for (int i=0;i<RepairServiceList.size();i++) {
            sum += RepairServiceDurationList.get(i);
        }
        return sum;
    }
}
