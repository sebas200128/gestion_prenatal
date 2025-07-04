package modelo;

import base_datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT * FROM pacientes WHERE id = ?";
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();

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
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar paciente por ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar recursos en orden inverso
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
            conexionBD.desconectar();
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

        String query = "UPDATE pacientes SET " + campo + " = ? WHERE id = ?";
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean exito = false;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);

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
            }
        } catch (SQLException | NumberFormatException e) {
            System.err.println("Error al editar paciente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
            conexionBD.desconectar();
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

    // Método para registrar paciente en la base de datos = Franco
    public boolean registrar() {
        String query = "INSERT INTO pacientes (id, nombre, edad, dni, telefono, direccion, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean exito = false;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, this.id);
                pstmt.setString(2, this.nombre);
                pstmt.setInt(3, this.edad);
                pstmt.setString(4, this.dni);
                pstmt.setString(5, this.telefono);
                pstmt.setString(6, this.direccion);
                pstmt.setString(7, this.email);

                int filasAfectadas = pstmt.executeUpdate();
                exito = filasAfectadas > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar paciente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
            conexionBD.desconectar();
        }
        return exito;
    }

    // Método para obtener todos los pacientes
    public static List<Paciente> obtenerTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes";
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                rs = pstmt.executeQuery();

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
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los pacientes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
            conexionBD.desconectar();
        }
        return pacientes;
    }

    // Método para eliminar paciente
    // Método para eliminar paciente y sus registros relacionados
    public boolean eliminar() {
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        boolean exito = false;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                // Iniciar transacción
                conn.setAutoCommit(false);

                // Primero eliminar los registros relacionados en registro_cambios
                String queryRegistros = "DELETE FROM registro_cambios WHERE paciente_id = ?";
                try (PreparedStatement pstmtRegistros = conn.prepareStatement(queryRegistros)) {
                    pstmtRegistros.setInt(1, this.id);
                    int registrosEliminados = pstmtRegistros.executeUpdate();
                    System.out.println("Registros relacionados eliminados: " + registrosEliminados);
                }

                // Luego eliminar el paciente
                String queryPaciente = "DELETE FROM pacientes WHERE id = ?";
                try (PreparedStatement pstmtPaciente = conn.prepareStatement(queryPaciente)) {
                    pstmtPaciente.setInt(1, this.id);
                    int pacientesEliminados = pstmtPaciente.executeUpdate();

                    if (pacientesEliminados > 0) {
                        // Confirmar transacción
                        conn.commit();
                        exito = true;
                        System.out.println("Paciente con ID " + this.id + " eliminado exitosamente junto con sus registros.");
                    } else {
                        // Revertir transacción
                        conn.rollback();
                        System.out.println("No se encontró ningún paciente con ID " + this.id);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar paciente: " + e.getMessage());
            e.printStackTrace();

            // Revertir transacción en caso de error
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transacción revertida debido a error.");
                } catch (SQLException rollbackEx) {
                    System.err.println("Error al revertir transacción: " + rollbackEx.getMessage());
                }
            }
        } finally {
            // Restaurar autoCommit y cerrar conexión
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    System.err.println("Error al restaurar autoCommit: " + e.getMessage());
                }
            }
            conexionBD.desconectar();
        }
        return exito;
    }

// Método alternativo para verificar si un paciente tiene registros relacionados
    public boolean tieneRegistrosRelacionados() {
        String query = "SELECT COUNT(*) FROM registro_cambios WHERE paciente_id = ?";
        ConexionBD conexionBD = new ConexionBD("project_prenatal");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean tieneRegistros = false;

        try {
            conn = conexionBD.conectar();
            if (conn != null) {
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, this.id);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int count = rs.getInt(1);
                    tieneRegistros = count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar registros relacionados: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
            conexionBD.desconectar();
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
