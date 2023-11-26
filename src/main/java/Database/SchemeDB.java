package Database;

public interface SchemeDB {

    //Parámetros para la conexión
    String database="almacen";
    String server="127.0.0.1:3306";
    String user="root";
    String password="";

    //Tablas de la BBDD
    String TAB_Productos="Productos";
    String TAB_Empleados="Empleados";
    String TAB_Pedidos="Pedidos";
    String TAB_Productos_Fav="Productos_Fav";

    //Campos de la BBDD
    String COL_ID="ID";
    String COL_Nombre="nombre";
    String COL_Descripcion="descripcion";
    String COL_Cantidad="cantidad";
    String COL_Precio="precio";
    String COL_Apellidos="apellidos";
    String COL_Correo="correo";
    String COL_IdProducto="id_producto"; //Productos(ID)
    String COL_PrecioTotal="precio_total";
}
