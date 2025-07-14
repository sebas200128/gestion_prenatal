package controller;

import base_datos.utilidades.GestorConsultas;
import sql.ConexionBD;
import java.sql.*;

public class ControladorPaciente {

    public boolean existePaciente(int pacienteId) {
        String sql = GestorConsultas.obtenerConsulta("consulta.existe_paciente");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerNombrePaciente(int pacienteId) {
        String sql = GestorConsultas.obtenerConsulta("consulta.obtener_nombre_paciente");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
