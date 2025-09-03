-- Script de inicialización de la base de datos Sistema Médico
-- Este archivo es opcional, ya que Hibernate puede crear las tablas automáticamente

CREATE DATABASE IF NOT EXISTS sistema_medico_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE sistema_medico_db;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_identificacion INT NOT NULL UNIQUE,
    nombre_cliente VARCHAR(100) NOT NULL,
    edad_cliente INT NOT NULL,
    telefono_cliente VARCHAR(20),
    genero_cliente VARCHAR(10),
    direccion_cliente VARCHAR(200),
    INDEX idx_numero_identificacion (numero_identificacion),
    INDEX idx_nombre_cliente (nombre_cliente)
);

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    rol VARCHAR(20) DEFAULT 'CLIENTE',
    identificacion INT NOT NULL UNIQUE,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    cliente_id BIGINT,
    INDEX idx_username (username),
    INDEX idx_identificacion (identificacion),
    INDEX idx_email (email),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE SET NULL
);

-- Tabla de citas
CREATE TABLE IF NOT EXISTS citas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_identificacion INT NOT NULL,
    nombre_paciente VARCHAR(100) NOT NULL,
    edad_paciente INT NOT NULL,
    tipo_cita VARCHAR(50) NOT NULL,
    genero_paciente VARCHAR(10),
    fecha_cita VARCHAR(100) NOT NULL,
    motivo_cita VARCHAR(500),
    estado_cita VARCHAR(20) DEFAULT 'PROGRAMADA',
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    cliente_id BIGINT,
    INDEX idx_numero_identificacion (numero_identificacion),
    INDEX idx_fecha_cita (fecha_cita),
    INDEX idx_estado_cita (estado_cita),
    INDEX idx_tipo_cita (tipo_cita),
    INDEX idx_fecha_creacion (fecha_creacion),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE SET NULL
);

-- Datos de prueba (opcional)
INSERT INTO clientes (numero_identificacion, nombre_cliente, edad_cliente, telefono_cliente, genero_cliente, direccion_cliente) 
VALUES 
(12345678, 'Juan Pérez', 35, '3001234567', 'M', 'Calle 123 #45-67'),
(87654321, 'María García', 28, '3007654321', 'F', 'Carrera 89 #12-34')
ON DUPLICATE KEY UPDATE nombre_cliente = VALUES(nombre_cliente);

INSERT INTO usuarios (username, password, nombre, email, rol, identificacion, cliente_id)
VALUES 
('admin', 'admin123', 'Administrador', 'admin@sistemmedico.com', 'ADMIN', 99999999, NULL),
('juan.perez', '123456', 'Juan Pérez', 'juan@email.com', 'CLIENTE', 12345678, 1),
('maria.garcia', '123456', 'María García', 'maria@email.com', 'CLIENTE', 87654321, 2)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

INSERT INTO citas (numero_identificacion, nombre_paciente, edad_paciente, tipo_cita, genero_paciente, fecha_cita, motivo_cita, estado_cita, cliente_id)
VALUES 
(12345678, 'Juan Pérez', 35, 'Consulta General', 'M', '2025-09-15 10:00', 'Revisión médica anual', 'PROGRAMADA', 1),
(87654321, 'María García', 28, 'Consulta Especializada', 'F', '2025-09-20 14:30', 'Seguimiento tratamiento', 'PROGRAMADA', 2)
ON DUPLICATE KEY UPDATE nombre_paciente = VALUES(nombre_paciente);
