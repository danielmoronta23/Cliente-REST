import com.google.gson.JsonObject;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import kong.unirest.HttpResponse;
import java.util.Scanner;

public class Main {
    private static Scanner entradaEscaner= new Scanner (System.in);
    private static String URLApi = "http://localhost:7000/api/estudiante/";
    public static void main(String[] args) {
        System.out.println("\nCliente REST\n");
        /**
         * Referencias: http://kong.github.io/unirest-java/
         */
        boolean estado = false;
        while (estado!=true){
            Menu();
            String opcion = entradaEscaner.nextLine ();
            switch (opcion){
                case "0":
                    estado = true;
                    break;
                case "1":
                    //Lista de todos los Estudiantes
                    listaEstudiante();
                    break;
                case "2":
                    //consultar Estudiante
                    System.out.println("Digite la matricula: ");
                    consultarEstudiante(entradaEscaner.nextLine().trim());
                    break;
                case "3":
                    //Crear Estudiante
                    System.out.println("Digite el nombre ");
                    String nombre = entradaEscaner.nextLine ();
                    System.out.println("Digite la matricula: ");
                    String matricula = entradaEscaner.nextLine ();
                    System.out.println("Digite la carrera: ");
                    String carrera = entradaEscaner.nextLine ();
                    System.out.println("Procesando peticcion...\n");
                    crearEstudiante(matricula,nombre,carrera);
                    break;
                case "4":
                    //Borrar Estudiante
                    System.out.println("Digite la matricula: ");
                    borrarEstudiante(entradaEscaner.nextLine().trim());
                    break;
                default:
                   break;
            }
        }
    }
    public static void Menu(){
        System.out.println("Digite una de la siguiente opciones:");
        System.out.println("0  ->  Salir");
        System.out.println("1  ->  Listar Estudiante");
        System.out.println("2  ->  Consultar Estudiante");
        System.out.println("3  ->  Crear Estudiante");
        System.out.println("4  ->  Borrar Estudiante");
    }
    public static void listaEstudiante(){
        System.out.println("Listando lista de estudiante\n");

        HttpResponse<JsonNode> repuestaServidor = Unirest.get(URLApi)
                .asJson();

        System.out.println("Repuesta del Servidor: \n"+repuestaServidor.getBody()+"\n");

    }
    public static void consultarEstudiante(String matricula){
        System.out.println("Consultado Estudiante con la matricula: " + matricula + "\n");

        HttpResponse<JsonNode> repuestaServidor = Unirest.get(URLApi+"{matricula}")
                .routeParam("matricula", matricula)
                .asJson();

        System.out.println("Repuesta del Servidor: \n"+repuestaServidor.getBody()+"\n");
    }
    public static void crearEstudiante(String matricula, String nombre, String carrera){

        JsonObject datoEstudiante = new JsonObject();
        datoEstudiante.addProperty("nombre", nombre);
        datoEstudiante.addProperty("matricula", matricula);
        datoEstudiante.addProperty("carrera", carrera);

        HttpResponse<JsonNode> repuestaServidor = Unirest.post(URLApi)
                .header("Content-Type", "application/json")
                .body(datoEstudiante)
                .asJson();

        System.out.println("Repuesta del Servidor: \n"+repuestaServidor.getBody()+"\n");
    }
    public static void borrarEstudiante(String matricula){
        System.out.println("Borrando Estudiante con la matricula: " + matricula + "\n");

        HttpResponse<JsonNode> repuestaServidor = Unirest.delete(URLApi+"{matricula}")
                .routeParam("matricula", matricula)
                .asJson();

        System.out.println("Repuesta del Servidor: \n"+repuestaServidor.getBody()+"\n");

    }

}
