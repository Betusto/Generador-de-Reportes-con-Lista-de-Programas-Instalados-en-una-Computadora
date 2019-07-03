package proyectomc;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

public class ObtencionDatos {

    public void MatrizDatos(String Ubicacion, String ruta) throws FileNotFoundException, IOException {
        //Variables
        String newLine = null;
        String parametros[] = null;
        String sistema = System.getProperty("os.name"), sistemaVersion = System.getProperty("os.version"), sistemaArquitectura = System.getProperty("os.arch"), serialNumber="";

        //PDF
        PdfFont titleFont = PdfFontFactory.createFont(FontConstants.COURIER_BOLD);
        PdfFont corpFont = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
        PdfFont tableFont = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        
        //Conseguir el ID del sistema
        try
        {
        Process process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialnumber" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        serialNumber = sc.next();
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
        //Variables 
        String cadena = "", minutos = "";
        Calendar tiempo = Calendar.getInstance();
        String mes = "", dia = "", meridiano = "";
        switch (tiempo.get(Calendar.DAY_OF_WEEK)) {
            case (Calendar.MONDAY): {
                dia = "LUNES";
                break;
            }
            case (Calendar.TUESDAY): {
                dia = "MARTES";
                break;
            }
            case (Calendar.WEDNESDAY): {
                dia = "MIERCOLES";
                break;
            }
            case (Calendar.THURSDAY): {
                dia = "JUEVES";
                break;
            }
            case (Calendar.FRIDAY): {
                dia = "VIERNES";
                break;
            }
            case (Calendar.SATURDAY): {
                dia = "SABADO";
                break;
            }
            case (Calendar.SUNDAY): {
                dia = "DOMINGO";
                break;
            }
        }

        switch (tiempo.get(Calendar.MONTH)) {
            case (Calendar.JANUARY): {
                mes = "ENERO";
                break;
            }
            case (Calendar.FEBRUARY): {
                mes = "FEBRERO";
                break;
            }
            case (Calendar.MARCH): {
                mes = "MARZO";
                break;
            }
            case (Calendar.APRIL): {
                mes = "ABRIL";
                break;
            }
            case (Calendar.MAY): {
                mes = "MAYO";
                break;
            }
            case (Calendar.JUNE): {
                mes = "JUNIO";
                break;
            }
            case (Calendar.JULY): {
                mes = "JULIO";
                break;
            }
            case (Calendar.AUGUST): {
                mes = "AGOSTO";
                break;
            }
            case (Calendar.SEPTEMBER): {
                mes = "SEPTIEMBRE";
                break;
            }
            case (Calendar.OCTOBER): {
                mes = "OCTUBRE";
                break;
            }
            case (Calendar.NOVEMBER): {
                mes = "NOVIEMBRE";
                break;
            }
            case (Calendar.DECEMBER): {
                mes = "DICIEMBRE";
                break;
            }

        }
        if (tiempo.get(Calendar.AM) == 0) {
            meridiano = "AM";
        } else {
            meridiano = "PM";
        }

        if (tiempo.get(Calendar.MINUTE) < 10) {
            minutos = "0" + tiempo.get(Calendar.MINUTE);
        } else {
            minutos = String.valueOf(tiempo.get(Calendar.MINUTE));
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            try {
                PdfWriter Escritor = new PdfWriter(Ubicacion);
                PdfDocument pdf_final = new PdfDocument(Escritor);

                //Inicio del PDF
                try (Document documento = new Document(pdf_final, new PageSize(595, 842))) {
                    documento.setMargins(0, 0, 0, 0);

                    //Creación de la tabla
                    Table table = new Table(5);
                    table.setMarginLeft(1);
                    table.setMarginRight(1);

                    //Inicio del PDF
                    cadena = "REGISTRO DE PROGRAMAS INSTALADOS";
                    documento.add(new Paragraph(cadena).setFont(titleFont).setFontSize(25));
                    cadena = "FECHA DE REPORTE: " + dia + " " + tiempo.get(Calendar.DAY_OF_MONTH) + " DE " + mes + " DEL " + tiempo.get(Calendar.YEAR) + ", " + tiempo.get(Calendar.HOUR) + ":" + minutos;
                    documento.add(new Paragraph(cadena).setFontSize(12).setFont(corpFont));
                    cadena = "SISTEMA OPERATIVO: " + sistema.toUpperCase() + " VERSIÓN: " + sistemaVersion.toUpperCase() + " ARQUITECTURA: " + sistemaArquitectura.toUpperCase() + " NÚM. SERIE: " + serialNumber;
                    documento.add(new Paragraph(cadena).setFontSize(12).setFont(corpFont));
                    
                    //CONSEGUIR EL ID DE LA BIOS Y LA FECHA EN UN TXT APARTE
                    PrintWriter writer = new PrintWriter("rows.txt", "UTF-8");
                    writer.println(serialNumber); //ID
                    writer.println(dia + " " + tiempo.get(Calendar.DAY_OF_MONTH) + " DE " + mes + " DEL " + tiempo.get(Calendar.YEAR) + ", " + tiempo.get(Calendar.HOUR) + ":" + minutos); //Fecha
                    writer.close();
                    
                    //Cuerpo del PDF
                    newLine = br.readLine();
                    newLine = br.readLine();
                    //Encabezados de la tabla
                    String titles[] ={"NOMBRE DE LA APLICACIÓN","VERSIÓN","COMPAÑIA","FECHA DE INSTALACIÓN","RUTA DE INSTALACIÓN"};
                    //Tabla  Titulos
                            for (int j = 0; j < titles.length; j++) {
                                Paragraph p = new Paragraph();
                                Text text = new Text(titles[j].trim().toUpperCase());
                                text.setFont(tableFont).setFontSize(8);
                                p.add(text);
                                table.addCell(p);
                            }
                    
                    
                    while ((newLine = br.readLine()) != null) {
                        cadena = "";
                        for (int j = 0; j < newLine.length(); j++) {
                            if (newLine.charAt(j) != ' ' && newLine.charAt(j) != '"' && newLine.charAt(j) != '"') {
                                //System.out.print(newLine.charAt(j));
                                cadena += newLine.charAt(j);
                            } else if (j < newLine.length() - 1 && j > 0 && newLine.charAt(j) == ' ' && newLine.charAt(j - 1) != ' ' && newLine.charAt(j + 1) != ' ') {
                                System.out.print(" ");
                                cadena += " ";
                            } else if (j < newLine.length() - 1 && j > 0 && newLine.charAt(j) == ' ' && newLine.charAt(j - 1) != ' ' && newLine.charAt(j + 1) == ' ') {
                                //System.out.print(",");
                                cadena += ",";
                            }
                        }
                        //En este punto tenemos la cadena con cada uno de los valores de nuestro programa
                        parametros = cadena.split(",");
                        //Removemos los espacios en blanco al principio y al final de nuestro datos
                        for (int j = 0; j < parametros.length; j++) {
                            parametros[j] = parametros[j].trim().toUpperCase();
                        }

                        if (parametros.length == 5) {
                            /*
                            //System.out.println("");
                            documento.add(new Paragraph(" "));
                            //System.out.println("Nombre de la Aplicación  :" + parametros[2]);
                            documento.add(new Paragraph("Nombre de la Aplicación  :" + parametros[2]));
                            //System.out.println("Versión de la Aplicación :" + parametros[3]);
                            documento.add(new Paragraph("Versión de la Aplicación :" + parametros[3]));
                            //System.out.println("Ruta de Instalación      :" + parametros[1]);
                            documento.add(new Paragraph("Ruta de Instalación      :" + parametros[1]));
                            //System.out.println("Fecha de Instalacion     :" + parametros[0]);
                            documento.add(new Paragraph("Fecha de Instalacion     :" + parametros[0]));
                             */
                            //Tabla  
                            for (int j = 0; j < parametros.length; j++) {
                                Paragraph p = new Paragraph();
                                Text text = new Text(parametros[j].trim().toUpperCase());
                                text.setFont(corpFont).setFontSize(8);
                                p.add(text);
                                table.addCell(p);
                            }
                        } else if (parametros.length == 3) {
                            //Tabla  
                            for (int j = 0; j < 3; j++) {
                                Paragraph p = new Paragraph();
                                Text text = new Text(parametros[j].trim().toUpperCase());
                                text.setFont(corpFont).setFontSize(8);
                                p.add(text);
                                table.addCell(p);
                            }

                                table.addCell("");
                                table.addCell("");
                        }else if (parametros.length == 4) {
                            //Tabla  
                            for (int j = 0; j < 4; j++) {
                                Paragraph p = new Paragraph();
                                Text text = new Text(parametros[j].trim().toUpperCase());
                                text.setFont(corpFont).setFontSize(8);
                                p.add(text);
                                table.addCell(p);
                            }
                                table.addCell("");
                        }else{
                                
                                }                          
                                

                    }
                    //Creación de la tabla
                    documento.add(table);

                    //Creacion de la firma
                    String Firmas = "\n________________________________________________\nMe comprometo con el software instalado en este equipo con fecha de __ / __ / __";
                    Paragraph p2 = new Paragraph();
                    Text text1 = new Text(Firmas);
                    text1.setFont(corpFont).setFontSize(12);
                    p2.add(text1);
                    documento.add(p2);
                }

            } catch (IOException e) {
            }
            br.close();

        } catch (IOException e) {
            System.out.println("Error con: " + e);
        }

    }

}
