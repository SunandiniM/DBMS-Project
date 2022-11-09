import java.util.*;

public class Cart {
    public String Maintainance;
    public int MaintenanceCost;
    public ArrayList<String> RepairServiceList;
    public ArrayList<Integer> RepairServiceCostList;

    public Cart() {
        Maintainance = "";
        MaintenanceCost = 0;
        RepairServiceList = new ArrayList<String>();
        RepairServiceCostList = new ArrayList<Integer>();
    }

    public int getTotalCost() {
        int sum = MaintenanceCost;
        for (int i=0;i<RepairServiceList.size();i++) {
            sum += RepairServiceCostList.get(i);
        }
        return sum;
    }
}
