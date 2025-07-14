package controller;

import base_datos.utilidades.GestorConsultas;
import java.sql.*;
import modelo.Cita;
import sql.ConexionBD;

public class ControladorCita {

    public boolean agendarCita(int pacienteId, String fecha, String hora) {
        Cita cita = new Cita(pacienteId, fecha, hora);
        return cita.registrar();
    }

    public Cita obtenerCitaPorPaciente(int pacienteId) {
        Cita cita = null;
        String sql = GestorConsultas.obtenerConsulta("consulta.obtener_cita_por_paciente");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cita = new Cita();
                cita.setFecha(rs.getString("fecha"));
                cita.setHora(rs.getString("hora"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la cita: " + e.getMessage());
        }

        return cita;
    }
}
