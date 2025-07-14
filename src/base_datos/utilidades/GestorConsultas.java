package base_datos.utilidades;

import java.io.InputStream;
import java.util.Properties;

public class GestorConsultas {

    private static final Properties propiedades = new Properties();
    private static final String ARCHIVO = "sql/consultas.properties.sql";

    static {
        try (InputStream input = GestorConsultas.class.getClassLoader().getResourceAsStream(ARCHIVO)) {
            if (input == null) {
                throw new RuntimeException("Archivo no encontrado: " + ARCHIVO);
            }
            propiedades.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando consultas SQL", e);
        }
    }

    public static String obtenerConsulta(String clave) {
        System.out.println("Buscando consulta: " + clave); // Debug
        String consulta = propiedades.getProperty(clave);
        if (consulta == null) {
            System.err.println("Consultas disponibles: " + propiedades.stringPropertyNames()); // Debug
            throw new IllegalArgumentException("Consulta no encontrada: " + clave);
        }
        return consulta.trim();
    }

    public static String obtenerConsultaFormateada(String clave, String campo) {
        String consulta = obtenerConsulta(clave);
        return consulta.replace("{campo}", campo);
    }
}
