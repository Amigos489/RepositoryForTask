package sorted.order;

import model.Order;
import java.util.Comparator;

public class SortedOrderByDateComplection implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return order1.getDateComplection().compareTo(order2.getDateComplection());
    }
}
