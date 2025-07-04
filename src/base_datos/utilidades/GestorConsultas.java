package base_datos.utilidades;

import java.io.InputStream;
import java.util.Properties;

public class GestorConsultas {

    private static Properties propiedades = new Properties();

    static {
        try (InputStream input = GestorConsultas.class.getClassLoader().getResourceAsStream("sql/consultas.properties")) {
            if (input != null) {
                propiedades.load(input);
            } else {
                System.err.println("No se encontró el archivo consultas.properties");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String obtenerConsulta(String clave) {
        return propiedades.getProperty(clave);
    }

    public static String obtenerConsultaFormateada(String clave, String campo) {
        String consulta = propiedades.getProperty(clave);
        return consulta != null ? consulta.replace("{campo}", campo) : null;
    }
}
