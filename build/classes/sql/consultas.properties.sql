# Consultas generales
consulta.obtenerTodos=SELECT * FROM paciente

# Signos Vitales / Registros Cardiacos (son la misma tabla)
consulta.insertar_signos=INSERT INTO signos_vitales (paciente_id, fecha, hora, presion_arterial, frecuencia_cardiaca, frecuencia_respiratoria, temperatura, saturacion_oxigeno, peso, altura, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
consulta.editar_signos=UPDATE signos_vitales SET {campo} = ? WHERE id = ?
consulta.eliminar_signos=DELETE FROM signos_vitales WHERE id = ?
consulta.obtener_todos_signos_vitales=SELECT * FROM signos_vitales
consulta.buscar_signos_por_id=SELECT * FROM signos_vitales WHERE id = ?

# Pacientes
consulta.buscar_paciente_por_id=SELECT * FROM pacientes WHERE id = ?
consulta.editar_paciente=UPDATE pacientes SET {campo} = ? WHERE id = ?
consulta.registrar_paciente=INSERT INTO pacientes (id, nombre, edad, dni, telefono, direccion, email) VALUES (?, ?, ?, ?, ?, ?, ?)
consulta.obtener_todos_pacientes=SELECT * FROM pacientes
consulta.eliminar_registros_paciente=DELETE FROM registro_cambios WHERE paciente_id = ?
consulta.eliminar_paciente=DELETE FROM pacientes WHERE id = ?
consulta.verificar_registros_paciente=SELECT COUNT(*) FROM registro_cambios WHERE paciente_id = ?

# Citas
consulta.insertar_cita=INSERT INTO citas (paciente_id, fecha, hora) VALUES (?, ?, ?)
consulta.obtener_citas_con_paciente=SELECT * FROM vista_citas
consulta.obtener_cita_por_paciente=SELECT fecha, hora FROM citas WHERE paciente_id = ?

# Para eliminar registros relacionados
consulta.eliminar_signos_por_paciente=DELETE FROM signos_vitales WHERE paciente_id = ?
consulta.eliminar_registros_paciente=DELETE FROM registro_cambios WHERE paciente_id = ?
consulta.eliminar_paciente=DELETE FROM pacientes WHERE id = ?

consulta.obtener_nombre_paciente=SELECT nombre FROM pacientes WHERE id = ?
consulta.existe_paciente=SELECT id FROM pacientes WHERE id = ?

# Consultas para pacientes
consulta.buscar_paciente_por_id=SELECT * FROM pacientes WHERE id = ?
consulta.obtener_nombre_paciente=SELECT nombre FROM pacientes WHERE id = ?