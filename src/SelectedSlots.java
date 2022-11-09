import java.util.List;

public class SelectedSlots {
    public List<List<Integer>> selectedSlots;
    public List<String> selectedIds;

    public SelectedSlots(List<String> ids, List<List<Integer>> slots) {
        selectedIds = ids;
        selectedSlots = slots;
    }
}
