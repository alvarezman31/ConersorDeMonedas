import com.google.gson.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class Principal {
    static int opcion = 0;
    static Scanner opcioniongresada = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("################################");
        System.out.println("Bienvenido(a) a Conversor de Monedas");
        System.out.println("################################");
        menu();
    }
    public static void menu() throws IOException {
        while (opcion!=7){
            System.out.println("## Escoja una Opción ##");
            System.out.println("1. DOLAR          -> PESO ARGENTINO");
            System.out.println("2. PESO ARGENTINO -> DOLAR");
            System.out.println("3. DOLAR          -> PESO CHILENO");
            System.out.println("4. PESO CHILENO   -> DOLAR");
            System.out.println("5. DOLAR          -> PESO COLOMBIANO");
            System.out.println("6. PESO COLOMBIANO-> DOLAR");
            System.out.println("7. Salir");
            opcion = opcioniongresada.nextInt();
            switch (opcion){
                case 1:
                    convertirMoneda("ARS","USD", opcion);
                    break;
                case 2:
                    convertirMoneda("USD","ARS", opcion);

                    break;
                case 3:
                    convertirMoneda("CLP","USD", opcion);
                    break;
                case 4:
                    convertirMoneda("USD","CLP", opcion);
                    break;
                case 5:
                    convertirMoneda("COP","USD", opcion);
                    break;
                case 6:
                    convertirMoneda("USD","COP", opcion);
                    break;
                case 7:
                    System.out.println("### Gracias por usar nuesta aplicación");
                    break;
                default:
                    System.out.println("### Opción Invalida ###");
                    break;
            }

        }
    }

    private static void convertirMoneda(String moneda,String monedaDesde, int opcion) throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/23f98883efbfcc1393dc5ec8/latest/" + monedaDesde;
        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        //Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        Gson gson = new Gson();
        ValoresMonedas miConversion = gson.fromJson(jsonobj.get("conversion_rates"), ValoresMonedas.class);
        System.out.println("Ingrese Valor a Convertir: ");
        double monto  = opcioniongresada.nextDouble();
        double montoConversion=0.0;
        switch (opcion){
            case 1:
                //convertirMoneda("ARS","USD");
                montoConversion = Double.parseDouble(miConversion.ARS());
                break;
            case 2:
                //convertirMoneda("USD","ARS",
                montoConversion = Double.parseDouble(miConversion.USD());
                break;
            case 3:
                //convertirMoneda("USD","CLP");
                montoConversion = Double.parseDouble(miConversion.CLP());
                break;
            case 4:
                //convertirMoneda("CLP","USD");
                montoConversion = Double.parseDouble(miConversion.USD());
                break;
            case 5:
               // convertirMoneda("USD","COP");
                montoConversion = Double.parseDouble(miConversion.COP());
                break;
            case 6:
               // convertirMoneda("COP","USD");
                montoConversion = Double.parseDouble(miConversion.USD());
                break;

        }
        System.out.printf(String.format("El valor de: %.2f [%s] equiale a %.2f [%s] %n%n",monto,monedaDesde,(monto*montoConversion),moneda));
    }
}
