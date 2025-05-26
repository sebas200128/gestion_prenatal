package base_datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {

    String bd = "project_prenatal";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "sebas";
    String password = "mufZAp7Tu@oQBVM9";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public ConexionBD(String bd) {
        this.bd = bd;
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Se conectó a BD " + bd);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conectó a BD " + bd);
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }

    public void desconectar() {
        try {
            if (cx != null && !cx.isClosed()) {
                cx.close();
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
