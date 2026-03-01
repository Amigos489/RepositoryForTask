package senla.course.sorted.order;

import senla.course.model.Order;
import java.util.Comparator;

public class SortedOrderByDateComplection implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        if (order1.getDateComplection() == null && (order2.getDateComplection() != null)) {
            return -1;
        } else if (order1.getDateComplection() != null && (order2.getDateComplection() == null)) {
            return 1;
        } else if (order1.getDateComplection() == null && (order2.getDateComplection() == null)) {
            return 0;
        }
        return order1.getDateComplection().compareTo(order2.getDateComplection());
    }
}
