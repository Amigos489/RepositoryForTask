import java.util.Comparator;

public class SortedOrderByPrice implements Comparator<Order>{

    @Override
    public int compare(Order order1, Order order2) {
        return order1.getPriceOrder().compareTo(order2.getPriceOrder());
    }

}