package Elements;

import Database.ConnectionDB;
import Database.SchemeDB;
import com.mysql.cj.protocol.Resultset;

import java.sql.*;

public class Product {

    public  int id;
    public  String name;
    public  String description;
    public  int quantity;
    public  double price;


    //Constructor
    public Product(int id, String name, String descripcion, int quantity, double price){

        this.id=id;
        this.name=name;
        this.description=descripcion;
        this.quantity=quantity;
        this.price=price;
    }



    //Setters


    //Getters
    private static String getPrimaryKey(String table){

        String aux="";

        switch(table){

            case SchemeDB.TAB_Productos:
                aux=SchemeDB.COL_ID; //primary key
                break;

            default:
                aux=SchemeDB.COL_IdProducto;  //foreign key
                break;
        }

        return aux;
    }




    //Vistas
    public static void print(Product product){

        System.out.println("ID: "+product.id);
        System.out.println("Nombre: "+product.name);
        System.out.println("Descripción: "+product.description);
        System.out.println("Cantidad: "+product.quantity);
        System.out.println("Precio: "+product.price+"\n");

    }


    //Procesos
    //Método para insertar el producto en la base de datos
    public static void save(Connection connection, String table, Product product) throws SQLException {

        //Compruebo si la id existe dentro de la tabla, para que no me de un error al insertarlo
        if(!Product.issetId(connection, table, product)){

            switch(table){

                case SchemeDB.TAB_Productos:
                    Product.insert(connection, product);
                    break;

                case SchemeDB.TAB_Productos_Fav:
                    Product.insertFav(connection, product);
                    break;
            }

        }

    }

    //Método para comprobar si la id del producto existe en la base de datos
    private static Boolean issetId(Connection connection, String table, Product product) throws SQLException {

        String query= String.format("SELECT * FROM %s WHERE %s = "+product.id, table, product.getPrimaryKey(table));

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        boolean aux=false;

        while(resultSet.next()){
            aux=true;
        }

        return aux;


    }


    //Método para insertar una nueva fila en la base de datos con el producto
    private static void insert(Connection connection, Product product) throws SQLException{

        //Defino la query
        String query= String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUE (?, ?, ?, ?, ?)", SchemeDB.TAB_Productos,
                SchemeDB.COL_ID,
                SchemeDB.COL_Nombre,
                SchemeDB.COL_Descripcion,
                SchemeDB.COL_Cantidad,
                SchemeDB.COL_Precio
        );

        //Preparo para insertarla con los datos
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, product.id);
        preparedStatement.setString(2, product.name);
        preparedStatement.setString(3, product.description);
        preparedStatement.setInt(4, product.quantity);
        preparedStatement.setDouble(5, product.price);

        //Ejecuto la query
        preparedStatement.execute();

    }

    //Método para insertar una nueva fila en la base de datos / tabla de favoritos
    private static void insertFav(Connection connection, Product product) throws SQLException {

        //Defino la query
        String query= String.format("INSERT INTO %s (%s) VALUE (?)", SchemeDB.TAB_Productos_Fav,
                SchemeDB.COL_IdProducto

        );

        //Preparo para insertarla con los datos
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, product.id);

        //Ejecuto la query
        preparedStatement.execute();

    }
}
