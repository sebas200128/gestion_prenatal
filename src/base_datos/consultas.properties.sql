# Para signos vitales
consulta.buscar_signos_por_id=SELECT * FROM signos_vitales WHERE id = ?
consulta.insertar_signos=INSERT INTO signos_vitales (paciente_id, fecha, hora, presion_arterial, frecuencia_cardiaca, frecuencia_respiratoria, temperatura, saturacion_oxigeno, peso, altura, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
consulta.editar_signos=UPDATE signos_vitales SET {campo} = ? WHERE id = ?
consulta.eliminar_signos=DELETE FROM signos_vitales WHERE id = ?


# Registro Cardiaco
consulta.buscar_registro_cardiaco_por_id=SELECT * FROM signos_vitales WHERE id = ?
consulta.obtener_todos_registros_cardiacos=SELECT * FROM signos_vitales
consulta.editar_registro_cardiaco=UPDATE signos_vitales SET %s = ? WHERE id = ?
consulta.eliminar_registro_cardiaco=DELETE FROM signos_vitales WHERE id = ?
