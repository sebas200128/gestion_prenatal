## crer tabla de pacientes
CREATE TABLE pacientes (
    id INT PRIMARY KEY,
    nombre VARCHAR(100),
    edad INT,
    dni VARCHAR(20),
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    email VARCHAR(100)
);


## tabla para registro de cambios
CREATE TABLE registro_cambios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT,
    campo_modificado VARCHAR(100),
    valor_anterior VARCHAR(255),
    valor_nuevo VARCHAR(255),
    fecha_cambio DATETIME,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

## Trigger para registrar cambios
DELIMITER //

CREATE TRIGGER trigger_registrar_cambios
AFTER UPDATE ON pacientes
FOR EACH ROW
BEGIN
    IF OLD.nombre <> NEW.nombre THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'nombre', OLD.nombre, NEW.nombre, NOW());
    END IF;

    IF OLD.edad <> NEW.edad THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'edad', OLD.edad, NEW.edad, NOW());
    END IF;

    IF OLD.dni <> NEW.dni THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'dni', OLD.dni, NEW.dni, NOW());
    END IF;

    IF OLD.telefono <> NEW.telefono THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'telefono', OLD.telefono, NEW.telefono, NOW());
    END IF;

    IF OLD.direccion <> NEW.direccion THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'direccion', OLD.direccion, NEW.direccion, NOW());
    END IF;

    IF OLD.email <> NEW.email THEN
        INSERT INTO registro_cambios (paciente_id, campo_modificado, valor_anterior, valor_nuevo, fecha_cambio)
        VALUES (OLD.id, 'email', OLD.email, NEW.email, NOW());
    END IF;
END //

DELIMITER ;

## trigger para validar inserccion de nuevos pacientes

DELIMITER //

CREATE TRIGGER trigger_validar_insercion
BEFORE INSERT ON pacientes
FOR EACH ROW
BEGIN
    IF NEW.dni IS NULL OR NEW.nombre IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El DNI y el nombre son obligatorios.';
    END IF;
END //

DELIMITER ;

## vistas para todos los pacientes

CREATE VIEW vista_todos_pacientes AS
SELECT id, nombre, edad, dni, telefono, direccion, email
FROM pacientes;

// vista para pacientes activos
CREATE VIEW vista_pacientes_activos AS
SELECT id, nombre, edad, dni, telefono, direccion, email
FROM pacientes
WHERE estado = 'activo'; -- Asumiendo que hay un campo 'estado' en la tabla

## citas

CREATE TABLE citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    estado VARCHAR(20) DEFAULT 'Programada',
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

## Vista SQL (para mostrar citas con datos del paciente)

CREATE VIEW vista_citas AS
SELECT 
    c.id AS cita_id,
    p.nombre,
    p.dni,
    c.fecha,
    c.hora,
    c.estado
FROM citas c
JOIN pacientes p ON c.paciente_id = p.id;

## Trigger para auditar cambios en citas

CREATE TABLE auditoria_citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cita_id INT,
    accion VARCHAR(10),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER //
CREATE TRIGGER trg_auditoria_citas
AFTER INSERT ON citas
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_citas (cita_id, accion)
    VALUES (NEW.id, 'INSERT');
END;
//
DELIMITER ;


## signos vitales

CREATE TABLE signos_vitales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_id INT NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    presion_arterial VARCHAR(7),  -- ejemplo: '120/80'
    frecuencia_cardiaca INT,
    frecuencia_respiratoria INT,
    temperatura DECIMAL(4,1),
    saturacion_oxigeno INT,
    peso DECIMAL(5,2),
    altura DECIMAL(4,2),
    observaciones TEXT,
    FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

## Vista para mostrar signos vitales con datos de paciente

CREATE VIEW vista_signos_vitales AS
SELECT 
    sv.id AS signo_id,
    p.nombre,
    p.dni,
    sv.fecha,
    sv.hora,
    sv.presion_arterial,
    sv.frecuencia_cardiaca,
    sv.frecuencia_respiratoria,
    sv.temperatura,
    sv.saturacion_oxigeno,
    sv.peso,
    sv.altura,
    sv.observaciones
FROM signos_vitales sv
JOIN pacientes p ON sv.paciente_id = p.id;

## tabla para uditoria de signos vitales
CREATE TABLE auditoria_signos_vitales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    signo_id INT,
    accion VARCHAR(10),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

## Trigger para auditar inserciones en signos_vitales
DELIMITER //

CREATE TRIGGER trg_auditoria_signos_vitales_insert
AFTER INSERT ON signos_vitales
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_signos_vitales (signo_id, accion)
    VALUES (NEW.id, 'INSERT');
END;
//

DELIMITER ;




