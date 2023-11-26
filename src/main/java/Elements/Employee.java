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
    //Getters


    //Vistas
    public static void print(Employee employee){

        System.out.println("ID: "+employee.id);
        System.out.println("Nombre: "+employee.name);
        System.out.println("Apellidos: "+employee.lastName);
        System.out.println("Email: "+employee.email+"\n");

    }

    public static ArrayList<Employee> makeEmployee() throws IOException, SQLException {

        Boolean follow=true;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Employee> aux = new ArrayList();

        while(follow){

            System.out.println("Datos para crear un nuevo empleado");
            System.out.println("Nombre");
            String name=keyboard.readLine();
            System.out.println("Apellidos");
            String lastName=keyboard.readLine();

            //Comprobar el correo electrónico (debe ser un campo único)
            Boolean checkEmail=true;

            while(checkEmail){
                System.out.println("Correo electrónico");
                String email=keyboard.readLine().toLowerCase();
                checkEmail=Employee.checkEmail(aux, email);

                if(checkEmail){
                    System.out.println("El email que has introducido pertenece a otro usuario dado de alta previamente, por favor, introduce uno distinto");
                }
            }


            aux.add(new Employee(0, name, lastName, email));

            System.out.println("¿Quiere introducir otro empleado? Y/N");
            String cont=keyboard.readLine();
            follow=((cont.equals("Y"))||(cont.equals("y"))) ? true : false;

        }

        return aux;
    }

    //Procesos
    public static void save(Connection connection, Employee employee) throws SQLException {

        if(!Employee.issetEmail(connection, employee)){
            Employee.insert(connection, employee);

        }
    }

        //Método para comprobar que, con los datos que hemos introducido, no existe ningún otro empleado
        private static Boolean issetEmail(Connection connection, Employee employee) throws SQLException {

            String query= String.format("SELECT * FROM %s WHERE %s = '"+employee.email+"'",
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
        private static void insert(Connection connection, Employee employee) throws SQLException {

            //Defino la query
            String query= String.format("INSERT INTO %s (%s, %s, %s) VALUE (?, ?, ?)", SchemeDB.TAB_Empleados,
                    SchemeDB.COL_Nombre,
                    SchemeDB.COL_Apellidos,
                    SchemeDB.COL_Correo
            );

            //Preparo para insertarla con los datos
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employee.name);
            preparedStatement.setString(2, employee.lastName);
            preparedStatement.setString(3, employee.email);


            //Ejecuto la query
            preparedStatement.execute();
        }


        //Método para comprobrar si el email de un empleado existe dentro de una lista de empleados
        private static Boolean checkEmail(ArrayList<Employee> employees, String email) throws SQLException {



            //1. El email puede existir dentro de la lista de usuarios que estamos dando de alta

                Boolean aux=(employees.size()==0) ? false : true;

                for(int i=0; i<employees.size(); i++){

                    if(email.equals(employees.get(i).email)){
                        aux=true;
                    }
                }


            //2. El email puede existir dentro de un usuario en la base de datos
                if(!aux) {
                    Employee employee = new Employee(0, "", "", email);
                    Connection connection = new ConnectionDB().getConnection();
                    aux=Employee.issetEmail(connection, employee);
                }

            return aux;

        }

}
