package Reader;

import Database.ConnectionDB;
import Database.SchemeDB;
import Elements.Product;
import Elements.Employee;
import Elements.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

//Clase que nos devuelve información que necesitamos directamente de nuestra DB para diferentes procesos
public class DBReader {

    static Connection connection = new ConnectionDB().getConnection();

    //Procesos
    //Método que devuelve un arraylist de productos que cumplen una condición con respecto a su precio
    public static ArrayList<Product> getProductsByPrice(String condition) throws SQLException {

        String query= String.format("SELECT %s, %s, %s, %s, %s FROM %s WHERE %s "+condition,
                SchemeDB.COL_ID, SchemeDB.COL_Nombre, SchemeDB.COL_Descripcion, SchemeDB.COL_Cantidad, SchemeDB.COL_Precio,
                SchemeDB.TAB_Productos,
                SchemeDB.COL_Precio);

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        ArrayList<Product> aux = new ArrayList();

        while(resultSet.next()){

            Product product=new Product(resultSet.getInt(SchemeDB.COL_ID),
                    resultSet.getString(SchemeDB.COL_Nombre),
                    resultSet.getString(SchemeDB.COL_Descripcion),
                    resultSet.getInt(SchemeDB.COL_Cantidad),
                    resultSet.getDouble(SchemeDB.COL_Precio)
            );

            aux.add(product);
        }

        return aux;
    }


    //Método para devolver en un arraylist todos los empleados de la empresa
    public static ArrayList<Employee> getAllEmployees() throws SQLException {

        String query= String.format("SELECT %s, %s, %s, %s FROM %s ",
                SchemeDB.COL_ID, SchemeDB.COL_Nombre, SchemeDB.COL_Apellidos, SchemeDB.COL_Correo,
                SchemeDB.TAB_Empleados
                );

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        ArrayList<Employee> aux = new ArrayList();

        while(resultSet.next()){
            Employee employee=new Employee(resultSet.getInt(SchemeDB.COL_ID),
                                            resultSet.getString(SchemeDB.COL_Nombre),
                                            resultSet.getString(SchemeDB.COL_Apellidos),
                                            resultSet.getString(SchemeDB.COL_Correo)
                                             );

            aux.add(employee);
        }

        return aux;
    }


    //Método para devolver un arraylist con todos los productos que tenemos en la base de datos
    public static ArrayList<Product> getAllProducts() throws SQLException {

        String query= String.format("SELECT %s, %s, %s, %s, %s FROM %s ",
                SchemeDB.COL_ID, SchemeDB.COL_Nombre, SchemeDB.COL_Descripcion, SchemeDB.COL_Cantidad, SchemeDB.COL_Precio,
                SchemeDB.TAB_Productos
        );

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        ArrayList<Product> aux = new ArrayList();

        while(resultSet.next()){
            Product product=new Product(resultSet.getInt(SchemeDB.COL_ID),
                    resultSet.getString(SchemeDB.COL_Nombre),
                    resultSet.getString(SchemeDB.COL_Descripcion),
                    resultSet.getInt(SchemeDB.COL_Cantidad),
                    resultSet.getDouble(SchemeDB.COL_Precio)
            );

            aux.add(product);
        }

        return aux;

    }


    //Método para devolver un arraylist con todos los pedidos que tenemos en la base de datos
    public static ArrayList<Order> getAllOrders() throws SQLException {

        String query= String.format("SELECT %s, %s, %s, %s FROM %s ",
                SchemeDB.COL_ID, SchemeDB.COL_IdProducto, SchemeDB.COL_Descripcion, SchemeDB.COL_PrecioTotal,
                SchemeDB.TAB_Pedidos
        );

        Statement statementSelect = connection.createStatement();
        ResultSet resultSet = statementSelect.executeQuery(query);

        ArrayList<Order> aux = new ArrayList();

        while(resultSet.next()){
            Order order=new Order(resultSet.getInt(SchemeDB.COL_ID),
                                resultSet.getInt(SchemeDB.COL_IdProducto),
                                resultSet.getString(SchemeDB.COL_Descripcion),
                                resultSet.getDouble(SchemeDB.COL_PrecioTotal)
                        );

            aux.add(order);
        }

        return aux;

    }
}
