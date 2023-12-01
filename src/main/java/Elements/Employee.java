package Elements;

import Database.ConnectionDB;
import Database.SchemeDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;

public class Employee {

    public int id;
    public String name;
    public String lastName;
    public static String email;

    //Constructor
    public Employee(int id, String name, String lastName, String email){

        this.id=id;
        this.name=name;
        this.lastName=lastName;
        this.email=email;
    }

    //Setters
    public void setEmail(String email){
        this.email=email;
    }


    //Getters


    //Vistas
    public void print(){

        System.out.println("ID: "+this.id);
        System.out.println("Nombre: "+this.name);
        System.out.println("Apellidos: "+this.lastName);
        System.out.println("Email: "+this.email+"\n");

    }



    //Procesos
    public void save(Connection connection) throws SQLException {

        if(!this.issetEmail(connection)){
            this.insert(connection);

        }
    }

        //Método para comprobar que, con los datos que hemos introducido, no existe ningún otro empleado
        public Boolean issetEmail(Connection connection) throws SQLException {

            String query= String.format("SELECT * FROM %s WHERE %s = '"+this.email+"'",
                                        SchemeDB.TAB_Empleados,
                                        SchemeDB.COL_Correo
                                        );

            Statement statementSelect = connection.createStatement();
            ResultSet resultSet = statementSelect.executeQuery(query);

            boolean aux=false;

            while(resultSet.next()){
                aux=true;
            }

            return aux;


        }

        //Método para insertar un nuevo empleado en la base de datos
        private void insert(Connection connection) throws SQLException {

            //Defino la query
            String query= String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)", SchemeDB.TAB_Empleados,
                    SchemeDB.COL_Nombre,
                    SchemeDB.COL_Apellidos,
                    SchemeDB.COL_Correo
            );

            //Preparo para insertarla con los datos
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.lastName);
            preparedStatement.setString(3, this.email);


            //Ejecuto la query
            preparedStatement.execute();
        }





}
