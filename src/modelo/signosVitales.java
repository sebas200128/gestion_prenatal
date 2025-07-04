package modelo;

import base_datos.ConexionBD;
import base_datos.utilidades.GestorConsultas;
import java.sql.*;

public class signosVitales {

    private int id;
    private int pacienteId;
    private String fecha;
    private String hora;
    private String presionArterial;
    private int frecuenciaCardiaca;
    private int frecuenciaRespiratoria;
    private double temperatura;
    private int saturacionOxigeno;
    private double peso;
    private double altura;
    private String observaciones;

    public signosVitales(int pacienteId, String fecha, String hora, String presionArterial,
            int frecuenciaCardiaca, int frecuenciaRespiratoria, double temperatura,
            int saturacionOxigeno, double peso, double altura, String observaciones) {
        this.pacienteId = pacienteId;
        this.fecha = fecha;
        this.hora = hora;
        this.presionArterial = presionArterial;
        this.frecuenciaCardiaca = frecuenciaCardiaca;
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
        this.temperatura = temperatura;
        this.saturacionOxigeno = saturacionOxigeno;
        this.peso = peso;
        this.altura = altura;
        this.observaciones = observaciones;
    }

    public boolean registrar() {
        String sql = GestorConsultas.obtenerConsulta("consulta.insertar_signos");
        ConexionBD conexionBD = new ConexionBD("project_prenatal");

        try (Connection conn = conexionBD.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            ps.setString(2, fecha);
            ps.setString(3, hora);
            ps.setString(4, presionArterial);
            ps.setInt(5, frecuenciaCardiaca);
            ps.setInt(6, frecuenciaRespiratoria);
            ps.setDouble(7, temperatura);
            ps.setInt(8, saturacionOxigeno);
            ps.setDouble(9, peso);
            ps.setDouble(10, altura);
            ps.setString(11, observaciones);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar signos vitales: " + e.getMessage());
            return false;
        }
    }

    // Getters y setters (si los necesitas)
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

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public int getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public int getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(int saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
