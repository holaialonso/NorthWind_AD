package Elements;

public class Employee {

    public int id;
    public String name;
    public String lastName;
    public String email;

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

    //Procesos
}
