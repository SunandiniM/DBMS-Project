import java.util.*;

public class Cart {
    public String Maintainance;
    public int MaintenanceCost;
    public int MaintenanceDuration;
    public ArrayList<String> RepairServiceList;
    public ArrayList<Integer> RepairServiceCostList;
    public ArrayList<Integer> RepairServiceDurationList;
    public  String vinNumber;

    public Cart() {
        Maintainance = "";
        MaintenanceCost = 0;
        MaintenanceDuration = 0;
        RepairServiceList = new ArrayList<String>();
        RepairServiceCostList = new ArrayList<Integer>();
        RepairServiceDurationList = new ArrayList<Integer>();
        vinNumber = "";
    }

    public String getServicesList() {
        String ids = "(";
        if (Maintainance.length() != 0) {
            String currId = "113";
            if (Maintainance.equals("B")) {
                currId = "114";
            } else if (Maintainance.equals("C")) {
                currId = "115";
            }
            ids = ids.concat(currId);
        }
        for (String s: RepairServiceList) {
            if (ids.length() != 1) {
                ids = ids.concat(", " + s);
            } else {

            }
            ids = ids.concat(s);
        }
        ids = ids.concat(")");
        return ids;
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
