package senla.course.sorted.order;

import senla.course.model.Order;
import java.util.Comparator;

public class SortedOrderByStatus implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
        return order1.getStatusOrder().compareTo(order2.getStatusOrder());
    }
}