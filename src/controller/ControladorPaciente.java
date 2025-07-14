package controller;

import base_datos.utilidades.GestorConsultas;
import sql.ConexionBD;
import java.sql.*;

public class ControladorPaciente {

    public boolean existePaciente(int pacienteId) {
        String sql = GestorConsultas.obtenerConsulta("consulta.existe_paciente");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Devuelve true si encontró al paciente
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de paciente: " + e.getMessage());
            return false;
        }
    }

    public String obtenerNombrePaciente(int pacienteId) {
        // Cambiar esto:
        String sql = GestorConsultas.obtenerConsulta("consulta.buscar_paciente_por_id");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre"); // Obtener el nombre del resultado completo
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
