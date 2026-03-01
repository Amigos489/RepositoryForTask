package sorted.order;

import model.Order;
import java.util.Comparator;

public class SortedOrderById implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return Integer.compare(order1.getId(), order2.getId());
    }
}
