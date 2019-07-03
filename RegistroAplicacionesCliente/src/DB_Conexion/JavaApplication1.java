package DB_Conexion;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JavaApplication1 {

    public void Visualizar() {
        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = obj_DB_Connection.get_connection();
        PreparedStatement ps = null;

        try {
            String query = "select * from PCMANAGEMENT";
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("PC");
                Blob blob = rs.getBlob(4);
                InputStream is = blob.getBinaryStream();
                FileOutputStream fos = new FileOutputStream("ReporteSalida-" + name +".pdf");

                int b = 0;
                while ((b = is.read()) != -1) {
                    fos.write(b);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void CargarPDF() {
        DB_Connection obj_DB_Connection = new DB_Connection();
        Connection connection = obj_DB_Connection.get_connection();      
        
        String insert = "insert into PCMANAGEMENT (PC, FECHA, PDF) values (?,?,?)";
        FileInputStream fi = null;
        PreparedStatement ps = null;
        String ruta = "Reporte.pdf";
        String username = System.getProperty("user.name");
        try {
            File file = new File(ruta);
            fi = new FileInputStream(file);

            //LEER ARCHIVO rows.txt
            File fileTXT = new File("rows.txt"); 
            BufferedReader br = new BufferedReader(new FileReader(fileTXT)); 
            String ID = br.readLine();
            ID = username.toUpperCase() + "-"+ ID;
            String FECHA = br.readLine();
            br.close();
            fileTXT.delete();
          //Revisamos si el ID ya existe
          PreparedStatement st = connection.prepareStatement("select * from PCMANAGEMENT where PC=?");
          st.setString(1, ID);
          ResultSet r1=st.executeQuery();
          if(r1.next()) { //Si ya existe actualizamos
              PreparedStatement psUPDATE = null;
              String update = "UPDATE PCMANAGEMENT SET FECHA=?, PDF=? WHERE PC=?";
              psUPDATE = connection.prepareStatement(update);
              psUPDATE.setString(1, FECHA);
              psUPDATE.setBinaryStream(2, fi);
              psUPDATE.setString(3, ID);
              psUPDATE.executeUpdate();
          }else{ //sino insertamos
            ps = connection.prepareStatement(insert);
            ps.setString(1, ID);
            ps.setString(2, FECHA);
            ps.setBinaryStream(3, fi);
            ps.executeUpdate();
          }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
}
