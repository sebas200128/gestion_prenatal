package modelo;

import sql.ConexionBD;
import base_datos.utilidades.GestorConsultas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroCardiaco {

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

    public RegistroCardiaco() {
    }

    public RegistroCardiaco(int id, int pacienteId, String fecha, String hora,
            String presionArterial, int frecuenciaCardiaca, int frecuenciaRespiratoria,
            double temperatura, int saturacionOxigeno, double peso, double altura, String observaciones) {
        this.id = id;
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

    // Getters y Setters
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

    public static RegistroCardiaco buscarPorId(int id) {
        RegistroCardiaco registro = null;
        String sql = GestorConsultas.obtenerConsulta("consulta.buscar_signos_vitales_por_id");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                registro = new RegistroCardiaco();
                registro.setId(rs.getInt("id"));
                registro.setPacienteId(rs.getInt("paciente_id"));
                registro.setFecha(rs.getString("fecha"));
                registro.setHora(rs.getString("hora"));
                registro.setPresionArterial(rs.getString("presion_arterial"));
                registro.setFrecuenciaCardiaca(rs.getInt("frecuencia_cardiaca"));
                registro.setFrecuenciaRespiratoria(rs.getInt("frecuencia_respiratoria"));
                registro.setTemperatura(rs.getDouble("temperatura"));
                registro.setSaturacionOxigeno(rs.getInt("saturacion_oxigeno"));
                registro.setPeso(rs.getDouble("peso"));
                registro.setAltura(rs.getDouble("altura"));
                registro.setObservaciones(rs.getString("observaciones"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registro;
    }

    public static List<RegistroCardiaco> obtenerTodos() {
        List<RegistroCardiaco> lista = new ArrayList<>();
        String sql = GestorConsultas.obtenerConsulta("consulta.obtener_todos_signos_vitales");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroCardiaco reg = new RegistroCardiaco(
                        rs.getInt("id"),
                        rs.getInt("paciente_id"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("presion_arterial"),
                        rs.getInt("frecuencia_cardiaca"),
                        rs.getInt("frecuencia_respiratoria"),
                        rs.getDouble("temperatura"),
                        rs.getInt("saturacion_oxigeno"),
                        rs.getDouble("peso"),
                        rs.getDouble("altura"),
                        rs.getString("observaciones")
                );
                lista.add(reg);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean editar(String campo, String nuevoValor) {
        //String sql = GestorConsultas.obtenerConsultaFormateada("consulta.editar_signo_vital", campo);
        String sql = GestorConsultas.obtenerConsultaFormateada("consulta.editar_signos", campo);

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (campo.equals("frecuencia_cardiaca") || campo.equals("frecuencia_respiratoria") || campo.equals("saturacion_oxigeno")) {
                ps.setInt(1, Integer.parseInt(nuevoValor));
            } else if (campo.equals("temperatura") || campo.equals("peso") || campo.equals("altura")) {
                ps.setDouble(1, Double.parseDouble(nuevoValor));
            } else {
                ps.setString(1, nuevoValor);
            }

            ps.setInt(2, this.id);
            return ps.executeUpdate() > 0;

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar() {
        String sql = GestorConsultas.obtenerConsulta("consulta.eliminar_signo_vital");

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, this.id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
