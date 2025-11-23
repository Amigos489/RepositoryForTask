import java.util.ArrayList;

public class OrderView {

    /* Отобразить список заказов */
    public static void printOrders(ArrayList<Order> orders) {

        for (Order order : orders) {
            System.out.println("\n");
            printOrderInfo(order);
            System.out.println("\n");
        }

    }

    /* Отобразить информацию о заказе */
    public static void printOrderInfo(Order order) {

        System.out.println("ID заказа: " + order.getOrderID());
        System.out.println("Email: " + order.getCustomerEmail());
        System.out.println("Цена: " + order.getPriceOrder());
        System.out.println("Статус: " + order.getOrderStatus());
        System.out.println("Дата исполнения: " + order.getDateOfExecution());
        
    }

}