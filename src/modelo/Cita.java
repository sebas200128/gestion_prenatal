package modelo;

import base_datos.utilidades.GestorConsultas;
import sql.ConexionBD;
import java.sql.*;

public class Cita {

    private int id;
    private int pacienteId;
    private String fecha;
    private String hora;

    public Cita(int pacienteId, String fecha, String hora) {
        this.pacienteId = pacienteId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita() {
    }

    public boolean registrar() {
        String sql = GestorConsultas.obtenerConsulta("consulta.insertar_cita");
        ConexionBD conexionBD = new ConexionBD("project_prenatal");

        try (Connection conn = conexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pacienteId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            return false;
        }
    }

    public static ResultSet obtenerCitasConPaciente() {
        String sql = GestorConsultas.obtenerConsulta("consulta.obtener_citas_con_paciente");
        ConexionBD conexionBD = new ConexionBD("project_prenatal");

        try {
            Connection conn = conexionBD.conectar();
            Statement st = conn.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error al obtener citas: " + e.getMessage());
            return null;
        }
    }

    // Getters y Setters (se mantienen igual)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
