package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    static Connection connection;

    //Creo la conexión a la bbdd
    private static void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url=String.format("jdbc:mysql://%s/%s", SchemeDB.server, SchemeDB.database);
            connection= DriverManager.getConnection(url, SchemeDB.user, SchemeDB.password);
        }
        catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConnection() {

        if(connection==null){
            //la creo
            createConnection();
        }
        return connection;
    }
}


