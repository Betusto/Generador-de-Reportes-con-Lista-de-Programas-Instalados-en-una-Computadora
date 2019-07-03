package proyectomc;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class metodos_pdf {

    public void Impresion(String Ubicacion) throws FileNotFoundException, IOException {
        //Variables 
        String cadena;
        Calendar tiempo = Calendar.getInstance();

        //Objetos a Utilizar
        PdfWriter Escritor = new PdfWriter(Ubicacion);
        PdfDocument pdf_final = new PdfDocument(Escritor);
        try (Document documento = new Document(pdf_final)) {
            PdfFont fuente = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);

            //Aqui iteramos dentro del archivo de TXT
            try (BufferedReader br = new BufferedReader(new FileReader("files.txt"))) {
                String Linea;
                cadena = "Registro de Aplicaciones";
                documento.add(new Paragraph(cadena));
                cadena = "Hora de Registro: " + tiempo.get(Calendar.HOUR_OF_DAY) + " : " + tiempo.get(Calendar.MINUTE) + tiempo.get(Calendar.AM_PM);
                documento.add(new Paragraph(cadena));
                

                while ((Linea = br.readLine()) != null) {
                    documento.add(new Paragraph(Linea));
                }
            } catch (IOException e) {
                System.out.println("Error con: " + e);
            }
        }
    }
}
