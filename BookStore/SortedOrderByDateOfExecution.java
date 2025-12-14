import java.util.Comparator;

public class SortedOrderByDateOfExecution implements Comparator<Order>{

    @Override
    public int compare(Order order1, Order order2) {

            return order1.getDateOfExecution().compareTo(order2.getDateOfExecution());

    }

}