public class ModelBookStore {

    private Warehouse wh;
    private OrderManagement om;

    ModelBookStore(Warehouse wh) {

        this.wh = wh;
        this.om = new OrderManagement();

    }

    public Warehouse getWarehouse() {
        return wh;
    }

    public OrderManagement getOrderManagement() {
        return this.om;
    }

}