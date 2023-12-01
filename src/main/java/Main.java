import Database.ConnectionDB;
import Database.SchemeDB;
import Elements.Order;
import Reader.DBReader;
import Reader.JSONReader;
import Elements.Product;
import Elements.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class Main {

    static Connection connection = new ConnectionDB().getConnection(); //Conexión a la base de datos
    static DBReader dbReader = new DBReader();



    //Main
    public static void main(String[] args) throws IOException, SQLException {

        Boolean follow=true;
        int option=0;

        while(follow){

            //Imprimo en pantalla el menú principal
            option=printMenu();

            //Acciones a realizar según la opción elegida en el menú principal
            switch(option){

                //Importar todos los productos de dummy products
                case 1:

                    JSONReader reader=new JSONReader("https://dummyjson.com/products");
                    ArrayList<Product> products = reader.readProducts(); //leo los productos y creo los objetos
                    saveProductsDB(connection, SchemeDB.TAB_Productos, products);

                break;

                //Crear un nuevo empleado e insertarlo en la bbdd
                case 2:

                    makeEmployees(connection);

                break;

                //Crear un nuevo pedido e insertarlo en la bbdd
                case 3:

                    makeOrders(connection);


                break;

                //Informes de empleados, productos y pedidos
                case 4:

                    int informe=printMenuInformes();

                    switch(informe){

                        case 1: //empleados

                            ArrayList<Employee> employees = dbReader.getAllEmployees();
                            printEmployees(employees);

                        break;

                        case 2: //productos

                            products = dbReader.getAllProducts();
                            printProducts(products);

                        break;

                        case 3: // pedidos

                            ArrayList<Order> orders = dbReader.getAllOrders();
                            printOrders(orders);

                        break;
                    }

                break;

                //Lista de productos con precio inferior a 600€
                case 5:

                    products=dbReader.getProductsByPrice(" < 600");
                    printProducts(products);

                break;

                //Insertar en <productos_fav> los productos con valor superior a 1.000€
                case 6:

                    products=dbReader.getProductsByPrice(" > 1000");
                    saveProductsDB(connection, SchemeDB.TAB_Productos_Fav, products);

                break;


            }

            //Compruebo si quiero seguir con la ejecución del programa
            follow=printFollow();

        }


    }


    //Método para imprimir el menú de opciones a realizar dentro de la empresa
    private static int printMenu() throws IOException {

        int aux=0;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        while(aux==0){

            System.out.println("---- MENÚ ----");
            System.out.println("1. Importar los productos de DummyJson a la base de datos");
            System.out.println("2. Crear nuevo empleado");
            System.out.println("3. Crear nuevo pedido");
            System.out.println("4. Informes de: empleados, productos y pedidos");
            System.out.println("5. Lista de productos con precio inferior a 600€");
            System.out.println("6. Insertar en <productos_fav> los productos con valor superior a 1.000€");
            System.out.println("¿Qué opción eliges?");
            aux= Integer.parseInt(keyboard.readLine());

            //Controlar que no estamos eligiendo una opción diferente a las disponibles
            if((aux<1)||(aux>6)){
                System.out.println("Lo sentimos, debes elegir una opción válida.");
                aux=0;
            }

        }

        return aux;

    }

    //Método para imprimir el menú con el tipo de informe que queremos mostrar por pantalla
    private static int printMenuInformes() throws IOException {

        int aux=0;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        while(aux==0){

            System.out.println("---- MENÚ INFORMES----");
            System.out.println("1. Todos los empleados");
            System.out.println("2. Todos los productos");
            System.out.println("3. Todos los pedidos");
            aux= Integer.parseInt(keyboard.readLine());

            //Controlar que no estamos eligiendo una opción diferente a las disponibles
            if((aux<1)||(aux>3)){
                System.out.println("Lo sentimos, debes elegir una opción válida.");
                aux=0;
            }

        }

        return aux;

    }

    //Método para comprobar si queremos seguir con la ejecución del programa
    private static Boolean printFollow() throws IOException {

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("¿Quiere seguir realizando otra acción3? Y/N");
        String cont=keyboard.readLine();

        return ((cont.equals("Y"))||(cont.equals("y"))) ? true : false;

    }

    //Método para guardar los productos dentro de la base de datos
    private static void saveProductsDB(Connection connection, String table, ArrayList<Product> products) throws SQLException, IndexOutOfBoundsException {

        if(products.size()>0) {
            for (int i = 0; i < products.size(); i++) {
                products.get(i).save(connection, table);
            }

            System.out.println("Se han guardado "+products.size()+" producto(s)");

        }
        else{
            System.out.println("No hay productos que guardar");
        }

    }


    //Método para mostrar los productos por pantalla
    private static void printProducts(ArrayList<Product> products){

        for(int i=0; i<products.size(); i++){
            products.get(i).print();
        }
    }

    //Método para imprimir los empleados por pantalla
    private static void printEmployees(ArrayList<Employee> employees){

        if(employees.size()>0) {
            for (int i = 0; i < employees.size(); i++) {
                employees.get(i).print();
            }
        }
        else{
            System.out.println("Ahora mismo no tenemos resultados para mostrar");
        }
    }


    //Método para imprimir por pantalla los pedidos que tenemos creados
    private static void printOrders(ArrayList<Order> orders){

        if(orders.size()>0) {
            for (int i = 0; i < orders.size(); i++) {
                Order.print(orders.get(i));
            }
        }
        else{
            System.out.println("Ahora mismo no tenemos resultados para mostrar");
        }

    }


    //Método para crear empleados
    public static void makeEmployees(Connection connection) throws IOException, SQLException {

        Boolean follow=true;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


        while(follow){

            System.out.println("Datos para crear un nuevo empleado");
            System.out.println("Nombre");
            String name=keyboard.readLine();
            System.out.println("Apellidos");
            String lastName=keyboard.readLine();

            //Creo el empleado
            Employee employee = new Employee(0, name, lastName, "");

            //Comprobar el correo electrónico (debe ser un campo único)
            Boolean checkEmail=true;

            while(checkEmail){
                System.out.println("Correo electrónico");
                String email=keyboard.readLine().toLowerCase();
                employee.setEmail(email);
                checkEmail=employee.issetEmail(connection);

                if(checkEmail){
                    System.out.println("El email que has introducido pertenece a otro usuario dado de alta previamente, por favor, introduce uno distinto");
                }
            }

            //Guardo el empleado
            employee.save(connection);

            System.out.println("¿Quiere introducir otro empleado? Y/N");
            String cont=keyboard.readLine();
            follow=((cont.equals("Y"))||(cont.equals("y"))) ? true : false;

        }


    }


    //Método para crear pedidos
    public static void makeOrders(Connection connection) throws IOException, SQLException {

        Boolean follow=true;
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));


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
            Order order =new Order(0, idProduct, description, totalPrice);
            order.save(connection);


            //Compruebo si quiero seguir introduciendo pedidos
            System.out.println("¿Quiere introducir otro pedido? Y/N");
            String cont=keyboard.readLine();
            follow=((cont.equals("Y"))||(cont.equals("y"))) ? true : false;

        }



    }
}


