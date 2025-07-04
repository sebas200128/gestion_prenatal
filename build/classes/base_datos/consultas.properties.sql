# Para signos vitales
consulta.buscar_signos_por_id=SELECT * FROM signos_vitales WHERE id = ?
consulta.insertar_signos=INSERT INTO signos_vitales (paciente_id, fecha, hora, presion_arterial, frecuencia_cardiaca, frecuencia_respiratoria, temperatura, saturacion_oxigeno, peso, altura, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
consulta.editar_signos=UPDATE signos_vitales SET {campo} = ? WHERE id = ?
consulta.eliminar_signos=DELETE FROM signos_vitales WHERE id = ?
