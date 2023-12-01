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
    public void print(){

        System.out.println("ID: "+this.id);
        System.out.println("Nombre: "+this.name);
        System.out.println("Descripción: "+this.description);
        System.out.println("Cantidad: "+this.quantity);
        System.out.println("Precio: "+this.price+"\n");

    }


    //Procesos
    //Método para insertar el producto en la base de datos
    public void save(Connection connection, String table) throws SQLException {

        //Compruebo si la id existe dentro de la tabla, para que no me de un error al insertarlo
        if(!this.issetId(connection, table)){

            switch(table){

                case SchemeDB.TAB_Productos:
                    this.insert(connection);
                    break;

                case SchemeDB.TAB_Productos_Fav:
                    this.insertFav(connection);
                    break;
            }

        }

    }

    //Método para comprobar si la id del producto existe en la base de datos
    public Boolean issetId(Connection connection, String table) throws SQLException {

        String query= String.format("SELECT * FROM %s WHERE %s = "+this.id, table, this.getPrimaryKey(table));

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        boolean aux=false;

        while(resultSet.next()){
            aux=true;
        }

        return aux;


    }


    //Método para insertar una nueva fila en la base de datos con el producto
    private void insert(Connection connection) throws SQLException{

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
        preparedStatement.setInt(1, this.id);
        preparedStatement.setString(2, this.name);
        preparedStatement.setString(3, this.description);
        preparedStatement.setInt(4, this.quantity);
        preparedStatement.setDouble(5, this.price);

        //Ejecuto la query
        preparedStatement.execute();

    }

    //Método para insertar una nueva fila en la base de datos / tabla de favoritos
    private void insertFav(Connection connection) throws SQLException {

        //Defino la query
        String query= String.format("INSERT INTO %s (%s) VALUE (?)", SchemeDB.TAB_Productos_Fav,
                SchemeDB.COL_IdProducto

        );

        //Preparo para insertarla con los datos
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, this.id);

        //Ejecuto la query
        preparedStatement.execute();

    }
}
