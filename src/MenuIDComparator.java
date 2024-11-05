import java.util.Comparator;

public class MenuIDComparator implements Comparator<FoodItem> {
    @Override
    public int compare(FoodItem entry1, FoodItem entry2) {
        return Integer.compare(entry1.getId(), entry2.getId());
    }
}