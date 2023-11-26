package Reader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Elements.Product;

public class JSONReader {

    private static String url;

    //Constructor
    public JSONReader(String url){
        this.url=url;
    }


    //Método para leer los productos del json de dummyProducts
    public static ArrayList<Product> readProducts() {

        ArrayList<Product> products = new ArrayList();


        try{
            URL url = new URL(JSONReader.url);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection(); //cabecera de la página
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //contenido del json

            //Leo el json y me aseguro de que se lee bien
            StringBuffer stringBuffer = new StringBuffer();
            String linea=null;

            //Leo el archivo
            while((linea=reader.readLine())!=null){
                stringBuffer.append(linea);
            }

            //String to json
            JSONObject response = new JSONObject(stringBuffer.toString());
            JSONArray data = new JSONArray(response.getJSONArray("products"));

            //Todos los productos
            for(int i=0; i<data.length(); i++){

                JSONObject item = data.getJSONObject(i);

                int id=item.getInt("id");
                String name=item.getString("title");
                String description=item.getString("description");
                int quantity=item.getInt("stock");
                double price = item.getDouble("price");

                products.add(new Product(id, name, description, quantity, price));

            }

        }catch (IOException e){

            System.out.println("Error en la conexion I/O");
        }

        return products;
    }
}
