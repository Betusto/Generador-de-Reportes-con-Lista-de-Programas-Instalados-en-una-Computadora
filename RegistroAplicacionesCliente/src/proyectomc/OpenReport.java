/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomc;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author REDH
 */
public class OpenReport {

    public void OpenReportFile() throws IOException {
        File file = new File("Reporte.pdf");
        if (file.toString().endsWith(".pdf")) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
        } else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }

        File fichero = new File("files.csv");
        fichero.delete();
        File fichero2 = new File("BatchFile.bat");
        fichero2.delete();

    }

}
