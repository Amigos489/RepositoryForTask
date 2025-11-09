import java.util.Comparator;

public class SortedOrderByPriceOrder implements Comparator<Order>{

    @Override
    public int compare(Order order1, Order order2) {
        if (order1.getPriceOrder() == order2.getPriceOrder()) {
            return 0;
        } else if (order1.getPriceOrder() <= order2.getPriceOrder()) {
            return -1;
        } else {
            return 1;
        }
    }

}