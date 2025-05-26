package modelo;

import base_datos.ConexionBD;
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

    // Constructor vacío
    public RegistroCardiaco() {
    }

    // Constructor completo
    public RegistroCardiaco(int id, int pacienteId, String fecha, String hora,
            String presionArterial, int frecuenciaCardiaca,
            int frecuenciaRespiratoria, double temperatura,
            int saturacionOxigeno, double peso, double altura,
            String observaciones) {
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

    // Getters
    public int getId() {
        return id;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public int getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public int getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public void setSaturacionOxigeno(int saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    // Métodos heredados para compatibilidad
    public String getPresion() {
        return presionArterial;
    }

    public int getFrecuencia() {
        return frecuenciaCardiaca;
    }

    public void setPresion(String presion) {
        this.presionArterial = presion;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuenciaCardiaca = frecuencia;
    }

    // Buscar por ID
    public static RegistroCardiaco buscarPorId(int id) {
        RegistroCardiaco registro = null;
        try (Connection conn = new ConexionBD("project_prenatal").conectar()) {
            String sql = "SELECT * FROM signos_vitales WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
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
        } catch (Exception e) {
            System.err.println("Error al buscar registro: " + e.getMessage());
            e.printStackTrace();
        }
        return registro;
    }

    // Obtener todos los registros
    public static List<RegistroCardiaco> obtenerTodos() {
        List<RegistroCardiaco> lista = new ArrayList<>();
        String sql = "SELECT * FROM signos_vitales ORDER BY id DESC";

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RegistroCardiaco registro = new RegistroCardiaco();
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
                lista.add(registro);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener registros: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    // Editar un campo específico
    public boolean editar(String campo, String nuevoValor) {
        String columnaDB = mapearCampoAColumna(campo);
        String sql = "UPDATE signos_vitales SET " + columnaDB + " = ? WHERE id = ?";

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Establecer el valor según el tipo de campo
            switch (campo) {
                case "frecuencia_cardiaca":
                case "frecuencia_respiratoria":
                case "saturacion_oxigeno":
                    ps.setInt(1, Integer.parseInt(nuevoValor));
                    break;
                case "temperatura":
                case "peso":
                case "altura":
                    ps.setDouble(1, Double.parseDouble(nuevoValor));
                    break;
                default:
                    ps.setString(1, nuevoValor);
                    break;
            }
            ps.setInt(2, this.id);

            boolean resultado = ps.executeUpdate() > 0;

            // Actualizar el objeto actual si la operación fue exitosa
            if (resultado) {
                actualizarCampoLocal(campo, nuevoValor);
            }

            return resultado;

        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al editar registro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Mapear nombres de campos del formulario a nombres de columnas de la BD
    private String mapearCampoAColumna(String campo) {
        switch (campo) {
            case "presion_arterial":
                return "presion_arterial";
            case "frecuencia_cardiaca":
                return "frecuencia_cardiaca";
            case "frecuencia_respiratoria":
                return "frecuencia_respiratoria";
            case "temperatura":
                return "temperatura";
            case "saturacion_oxigeno":
                return "saturacion_oxigeno";
            case "peso":
                return "peso";
            case "altura":
                return "altura";
            case "observaciones":
                return "observaciones";
            case "fecha":
                return "fecha";
            case "hora":
                return "hora";
            default:
                return campo;
        }
    }

    // Actualizar campo local después de edición exitosa
    private void actualizarCampoLocal(String campo, String nuevoValor) {
        switch (campo) {
            case "presion_arterial":
                this.presionArterial = nuevoValor;
                break;
            case "frecuencia_cardiaca":
                this.frecuenciaCardiaca = Integer.parseInt(nuevoValor);
                break;
            case "frecuencia_respiratoria":
                this.frecuenciaRespiratoria = Integer.parseInt(nuevoValor);
                break;
            case "temperatura":
                this.temperatura = Double.parseDouble(nuevoValor);
                break;
            case "saturacion_oxigeno":
                this.saturacionOxigeno = Integer.parseInt(nuevoValor);
                break;
            case "peso":
                this.peso = Double.parseDouble(nuevoValor);
                break;
            case "altura":
                this.altura = Double.parseDouble(nuevoValor);
                break;
            case "observaciones":
                this.observaciones = nuevoValor;
                break;
            case "fecha":
                this.fecha = nuevoValor;
                break;
            case "hora":
                this.hora = nuevoValor;
                break;
        }
    }

    // Eliminar registro
    public boolean eliminar() {
        String sql = "DELETE FROM signos_vitales WHERE id = ?";

        try (Connection con = new ConexionBD("project_prenatal").conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, this.id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar registro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
