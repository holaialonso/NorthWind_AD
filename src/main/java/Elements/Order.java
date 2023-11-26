package Elements;

public class Order {

    public int id;
    public int idProduct;
    public String description;
    public Double totalPrice;

    //Constructor
    public Order(int id, int idProduct, String description, Double totalPrice){

        this.id=id;
        this.idProduct=idProduct;
        this.description=description;
        this.totalPrice=totalPrice;

    }

    //Setters

    //Getters


    //Vistas
    public static void print(Order order){

        System.out.println("ID: "+order.id);
        System.out.println("Producto: "+order.idProduct);
        System.out.println("Descripción: "+order.description);
        System.out.println("Precio total: "+order.totalPrice+"\n");

    }


    //Procesos
}
