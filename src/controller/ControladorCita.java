package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modelo.Cita;
import base_datos.ConexionBD;

public class ControladorCita {

    public boolean agendarCita(int pacienteId, String fecha, String hora) {
        Cita cita = new Cita(pacienteId, fecha, hora);
        return cita.registrar();
    }

    public Cita obtenerCitaPorPaciente(int pacienteId) {
        Cita cita = null;

        String sql = "SELECT fecha, hora FROM citas WHERE paciente_id = ?";

        ConexionBD conexionBD = new ConexionBD("project_prenatal");

        try (Connection con = conexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cita = new Cita();
                cita.setFecha(rs.getDate("fecha").toString());
                cita.setHora(rs.getTime("hora").toString());
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener la cita: " + e.getMessage());
        }

        return cita;
    }

}
