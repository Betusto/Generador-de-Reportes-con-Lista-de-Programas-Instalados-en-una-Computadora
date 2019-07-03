package proyectomc;


import DB_Conexion.JavaApplication1;
import java.io.IOException;

/*import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;*/
public class main {

    static int parada = 1;
    public static String Ubicacion = "Reporte.pdf";
    public static String ruta = "files.csv";
    
    public static void main(String[] args) throws IOException, InterruptedException {
        main proceso = new main();
        proceso.Ejecutable();
    }
    public void Ejecutable()throws IOException, InterruptedException {
        
        //Objetos
        Pruebas PruebaSistema = new Pruebas();
        ObtencionDatos Formato = new ObtencionDatos();
        metodos_batch Objeto = new metodos_batch();
        CrearBat Ejecutable = new CrearBat();
        
        //Inicio del Programa
        Ejecutable.CrearBat();
        
        System.out.println("Proyecto Medio Curso:*******************************************************");
        System.out.println("Comienzo del proceso de Analisis...");
        
        Objeto.EjecuccionDeBatch();

        //Aqui trabajamos con los metodos 
        do {
            Objeto.DetectaCerrado();
        } while (parada == 1);
         
        //Impresion del PDF
        System.out.println("Comienzo del apartado PDF");
        Formato.MatrizDatos(Ubicacion, ruta);
        System.out.println("\nTodas las tareas han sido completas.");

        PruebaSistema.PruebasSistema();
        
        //Apertura del reporte pdf
        OpenReport metodoReport = new OpenReport();
        metodoReport.OpenReportFile();
        
        //Cargar PDF
        JavaApplication1 metodoDB = new JavaApplication1();
        System.getProperty("user.name");
        metodoDB.CargarPDF();
        
        
    }

}
