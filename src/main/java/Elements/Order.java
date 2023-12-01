package Elements;

import Database.ConnectionDB;
import Database.SchemeDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ArrayList<Order> makeOrder() throws IOException, SQLException {

        Boolean follow=true;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        Connection connection = new ConnectionDB().getConnection();
        ArrayList<Order> aux = new ArrayList();

        while(follow){

            System.out.println("Datos para crear un nuevo pedido");

            //Id del producto
                Boolean checkProduct=false;
                int idProduct=0;

                while(!checkProduct){
                    System.out.println("Id. Producto");
                    idProduct=Integer.parseInt(keyboard.readLine());
                    Product product=new Product(idProduct, "", "", 0, 0);
                    checkProduct=product.issetId(connection, SchemeDB.TAB_Productos);

                    if(!checkProduct){
                        System.out.println("Debes introducir un id de producto válido");
                    }
                }

            //Resto de valores
            System.out.println("Descripción");
            String description= keyboard.readLine();

            System.out.println("Precio total");
            Double totalPrice=Double.parseDouble(keyboard.readLine());

            //Creo el pedido
            aux.add(new Order(0, idProduct, description, totalPrice));


            //Compruebo si quiero seguir introduciendo pedidos
            System.out.println("¿Quiere introducir otro pedido? Y/N");
            String cont=keyboard.readLine();
            follow=((cont.equals("Y"))||(cont.equals("y"))) ? true : false;

        }

        return aux;

    }

    //Procesos
    //Método para guardar el pedido en la bbdd
    public static void save(Connection connection, Order order) throws SQLException {
        Order.insert(connection, order);
    }

    //Método para insertar el pedido en la tabla correspondiente de la bbdd
    private static void insert(Connection connection, Order order) throws SQLException {

        //Defino la query
        String query= String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)", SchemeDB.TAB_Pedidos,
                SchemeDB.COL_IdProducto,
                SchemeDB.COL_Descripcion,
                SchemeDB.COL_PrecioTotal
        );

        //Preparo para insertarla con los datos
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, order.idProduct);
        preparedStatement.setString(2, order.description);
        preparedStatement.setDouble(3, order.totalPrice);


        //Ejecuto la query
        preparedStatement.execute();
    }
}
