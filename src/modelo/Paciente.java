package modelo;

import base_datos.utilidades.GestorConsultas;
import sql.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Paciente {

    private int id;
    private String nombre;
    private int edad;
    private String dni;
    private String telefono;
    private String direccion;
    private String email;

    // Lista de campos válidos para evitar SQL injection
    private static final List<String> CAMPOS_VALIDOS = Arrays.asList(
            "nombre", "edad", "dni", "telefono", "direccion", "email"
    );

    // Constructor
    public Paciente(int id, String nombre, int edad, String dni, String telefono, String direccion, String email) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.dni = dni;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    // Método para buscar paciente por ID
    public static Paciente buscarPorId(int id) {
        Paciente paciente = null;
        String sql = GestorConsultas.obtenerConsulta("consulta.buscar_paciente_por_id");

        try (Connection conn = new ConexionBD("project_prenatal").conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return paciente;
    }

    // Método para editar un campo específico (versión segura)
    public boolean editar(String campo, String nuevoValor) {
        // Validar que el campo sea válido para prevenir SQL injection
        if (!CAMPOS_VALIDOS.contains(campo)) {
            System.err.println("Campo no válido: " + campo);
            return false;
        }

        String sql = GestorConsultas.obtenerConsultaFormateada("consulta.editar_paciente", campo);
        boolean exito = false;

        try (Connection conn = new ConexionBD("project_prenatal").conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Manejar diferentes tipos de datos
            if ("edad".equals(campo)) {
                pstmt.setInt(1, Integer.parseInt(nuevoValor));
            } else {
                pstmt.setString(1, nuevoValor);
            }
            pstmt.setInt(2, this.id);

            int filasAfectadas = pstmt.executeUpdate();
            exito = filasAfectadas > 0;

            // Actualizar el campo en el objeto actual
            if (exito) {
                actualizarCampoLocal(campo, nuevoValor);
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al editar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return exito;
    }

    // Método auxiliar para actualizar el campo en el objeto local
    private void actualizarCampoLocal(String campo, String nuevoValor) {
        switch (campo) {
            case "nombre":
                this.nombre = nuevoValor;
                break;
            case "edad":
                this.edad = Integer.parseInt(nuevoValor);
                break;
            case "dni":
                this.dni = nuevoValor;
                break;
            case "telefono":
                this.telefono = nuevoValor;
                break;
            case "direccion":
                this.direccion = nuevoValor;
                break;
            case "email":
                this.email = nuevoValor;
                break;
        }
    }

    // Método para registrar paciente en la base de datos
    public boolean registrar() {
        String sql = GestorConsultas.obtenerConsulta("consulta.insertar_paciente");
        boolean exito = false;

        try (Connection conn = new ConexionBD("project_prenatal").conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.id);
            pstmt.setString(2, this.nombre);
            pstmt.setInt(3, this.edad);
            pstmt.setString(4, this.dni);
            pstmt.setString(5, this.telefono);
            pstmt.setString(6, this.direccion);
            pstmt.setString(7, this.email);

            int filasAfectadas = pstmt.executeUpdate();
            exito = filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return exito;
    }

    // Método para obtener todos los pacientes
    public static List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = GestorConsultas.obtenerConsulta("consulta.obtener_todos_pacientes");

        try (Connection conn = new ConexionBD("project_prenatal").conectar(); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Paciente paciente = new Paciente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getString("email")
                );
                pacientes.add(paciente);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return pacientes;
    }

    // Método para eliminar paciente y sus registros relacionados
    public boolean eliminar() {
        boolean exito = false;
        String sqlEliminarRegistros = GestorConsultas.obtenerConsulta("consulta.eliminar_registros_paciente");
        String sqlEliminarPaciente = GestorConsultas.obtenerConsulta("consulta.eliminar_paciente");

        try (Connection conn = new ConexionBD("project_prenatal").conectar()) {
            // Iniciar transacción
            conn.setAutoCommit(false);

            // Primero eliminar los registros relacionados
            try (PreparedStatement pstmtRegistros = conn.prepareStatement(sqlEliminarRegistros)) {
                pstmtRegistros.setInt(1, this.id);
                pstmtRegistros.executeUpdate();
            }

            // Luego eliminar el paciente
            try (PreparedStatement pstmtPaciente = conn.prepareStatement(sqlEliminarPaciente)) {
                pstmtPaciente.setInt(1, this.id);
                int filasAfectadas = pstmtPaciente.executeUpdate();

                if (filasAfectadas > 0) {
                    conn.commit();
                    exito = true;
                } else {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return exito;
    }

    // Método alternativo para verificar si un paciente tiene registros relacionados
    public boolean tieneRegistrosRelacionados() {
        String sql = GestorConsultas.obtenerConsulta("consulta.contar_registros_paciente");
        boolean tieneRegistros = false;

        try (Connection conn = new ConexionBD("project_prenatal").conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                tieneRegistros = count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar registros relacionados: " + e.getMessage());
            e.printStackTrace();
        }
        return tieneRegistros;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Paciente{"
                + "id=" + id
                + ", nombre='" + nombre + '\''
                + ", edad=" + edad
                + ", dni='" + dni + '\''
                + ", telefono='" + telefono + '\''
                + ", direccion='" + direccion + '\''
                + ", email='" + email + '\''
                + '}';
    }
}
