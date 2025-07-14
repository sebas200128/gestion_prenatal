consulta.obtenerTodos=SELECT * FROM paciente;


# Para signos vitales
consulta.insertar_signos=INSERT INTO signos_vitales (paciente_id, fecha, hora, presion_arterial, frecuencia_cardiaca, frecuencia_respiratoria, temperatura, saturacion_oxigeno, peso, altura, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
consulta.editar_signos=UPDATE signos_vitales SET {campo} = ? WHERE id = ?
consulta.eliminar_signos=DELETE FROM signos_vitales WHERE id = ?
# obtener signos vitales
consulta.obtener_todos_signos_vitales=SELECT * FROM signos_vitales
consulta.buscar_signos_vitales_por_id=SELECT * FROM signos_vitales WHERE id = ?


# Registro Cardiaco
consulta.buscar_registro_cardiaco_por_id=SELECT * FROM signos_vitales WHERE id = ?
consulta.obtener_todos_registros_cardiacos=SELECT * FROM signos_vitales
consulta.editar_registro_cardiaco=UPDATE signos_vitales SET %s = ? WHERE id = ?
consulta.eliminar_registro_cardiaco=DELETE FROM signos_vitales WHERE id = ?

# Pacientes 
consulta.buscar_paciente_por_id=SELECT * FROM pacientes WHERE id = ?
consulta.editar_paciente=UPDATE pacientes SET %s = ? WHERE id = ?
consulta.registrar_paciente=INSERT INTO pacientes (id, nombre, edad, dni, telefono, direccion, email) VALUES (?, ?, ?, ?, ?, ?, ?)
consulta.obtener_todos_pacientes=SELECT * FROM pacientes
consulta.eliminar_registros_cambios=DELETE FROM registro_cambios WHERE paciente_id = ?
consulta.eliminar_paciente=DELETE FROM pacientes WHERE id = ?
consulta.verificar_registros_cambios=SELECT COUNT(*) FROM registro_cambios WHERE paciente_id = ?


# Consultas para Citas
consulta.insertar_cita=INSERT INTO citas (paciente_id, fecha, hora) VALUES (?, ?, ?)
consulta.obtener_citas_con_paciente=SELECT * FROM vista_citas
consulta.obtener_cita_por_paciente=SELECT fecha, hora FROM citas WHERE paciente_id = ?



