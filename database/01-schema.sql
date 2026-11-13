CREATE TABLE roles
(
    id          BIGINT      NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(30) NOT NULL,
    descripcion VARCHAR(150),

    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT uk_roles_nombre UNIQUE (nombre)
);

CREATE TABLE usuarios
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(100) NOT NULL,
    apellido         VARCHAR(100) NOT NULL,
    numero_documento VARCHAR(20)  NOT NULL,
    celular          VARCHAR(13)  NOT NULL,
    fecha_nacimiento DATE NULL,
    correo           VARCHAR(150) NOT NULL,
    clave            VARCHAR(255) NOT NULL,
    id_rol           BIGINT       NOT NULL,

    CONSTRAINT pk_usuarios PRIMARY KEY (id),
    CONSTRAINT uk_usuarios_correo UNIQUE (correo),
    CONSTRAINT fk_usuarios_roles
        FOREIGN KEY (id_rol) REFERENCES roles (id)
);

CREATE INDEX idx_usuarios_rol
    ON usuarios (id_rol);

INSERT INTO roles (nombre, descripcion)
VALUES ('ADMIN', 'Administrador del sistema'),
       ('PROPIETARIO', 'Propietario de restaurante'),
       ('EMPLEADO', 'Empleado de restaurante'),
       ('CLIENTE', 'Cliente de la plazoleta');
