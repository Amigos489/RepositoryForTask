import java.util.Comparator;

public class SortedOrderByOrderStatus implements Comparator<Order> {

    @Override
    public int compare(Order order1, Order order2) {
        return Integer.compare(
            order1.getOrderStatus().ordinal(),
            order2.getOrderStatus().ordinal()
        );
    }

}