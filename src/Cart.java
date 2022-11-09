import java.util.*;

public class Cart {
    public String Maintainance;
    public int MaintenanceCost;
    public ArrayList RepairServiceList;

    public Cart() {
        Maintainance = "";
        MaintenanceCost = 0;
        RepairServiceList = new ArrayList<HashMap<String, Integer>>();
    }

    public int getTotalCost() {
        int sum = MaintenanceCost;
        for (int i=0;i<RepairServiceList.size();i++) {
            HashMap<String, Integer> serviceObj = (HashMap) RepairServiceList.get(i);
            sum += serviceObj.get("price");
        }
        return sum;
    }
}
