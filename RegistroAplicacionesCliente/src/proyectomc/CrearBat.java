/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CrearBat {

    public void CrearBat() throws IOException {
        File Archivo = new File("BatchFile.bat");
        FileWriter fr = new FileWriter(Archivo);
        BufferedWriter bw = new BufferedWriter(fr);
        bw.write("@echo off\n"
                + "echo Por favor espere\n"
                + "echo Ejecutando Proceso . . .\n"
                + "powershell.exe -Command \"Get-ItemProperty HKLM:\\Software\\Wow6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\* | Select-Object DisplayName, DisplayVersion, Publisher, InstallDate,InstallLocation | export-CSV files.csv\"\n"
                + "exit");
        bw.close();
    }

}
