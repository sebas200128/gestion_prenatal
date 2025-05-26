package controller;

import base_datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorPaciente {

    public boolean existePaciente(int pacienteId) {
        String sql = "SELECT id FROM pacientes WHERE id = ?";

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Devuelve true si encontró al paciente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String obtenerNombrePaciente(int pacienteId) {
        String sql = "SELECT nombre FROM pacientes WHERE id = ?";

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
