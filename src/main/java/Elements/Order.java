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



    //Procesos
    //Método para guardar el pedido en la bbdd
    public void save(Connection connection) throws SQLException {
        this.insert(connection);
    }

    //Método para insertar el pedido en la tabla correspondiente de la bbdd
    private void insert(Connection connection) throws SQLException {

        //Defino la query
        String query= String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)", SchemeDB.TAB_Pedidos,
                SchemeDB.COL_IdProducto,
                SchemeDB.COL_Descripcion,
                SchemeDB.COL_PrecioTotal
        );

        //Preparo para insertarla con los datos
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.idProduct);
        preparedStatement.setString(2, this.description);
        preparedStatement.setDouble(3, this.totalPrice);


        //Ejecuto la query
        preparedStatement.execute();
    }
}
