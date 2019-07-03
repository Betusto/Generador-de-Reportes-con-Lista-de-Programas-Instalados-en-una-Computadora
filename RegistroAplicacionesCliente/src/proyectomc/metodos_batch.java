package proyectomc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class metodos_batch {

    public void EjecuccionDeBatch() throws IOException {
        //LLamo a mi archivo de obtener caracteristicas de mi PC
        String PATH = "cmd /c start /min BatchFile.bat";
        Runtime correr = Runtime.getRuntime();
        Process procesa = correr.exec(PATH);
    }

    public void DetectaCerrado() throws IOException, InterruptedException {
        String line;
        String Info = "";
        int presente = 0;

        Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
        try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            line = input.readLine();
            while ((line = input.readLine()) != null) {
                Info += line;
                if (line.charAt(0) == 'c' && line.contains("cmd.exe")) {
                    presente = 1;
                }
            }
        }
        if (presente == 0) {
            main.parada = 0;
        }

    }

}
