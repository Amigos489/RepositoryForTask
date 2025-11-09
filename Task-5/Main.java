public class Main {
    public static void main(String[] args) {

        Warehouse wh = new Warehouse(0, 200);


        ViewBookStore vbs = new ViewBookStore();
        ModelBookStore mbs = new ModelBookStore(wh);

        
        ControllerBookStore cont = new ControllerBookStore(vbs, mbs);
        cont.startMenu();

    }
}