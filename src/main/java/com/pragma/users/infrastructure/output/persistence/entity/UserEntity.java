
package com.pragma.users.infrastructure.output.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String firstName;

    @Column(name = "apellido", nullable = false, length = 100)
    private String lastName;

    @Column(name = "numero_documento", nullable = false, length = 20)
    private String documentNumber;

    @Column(name = "celular", nullable = false, length = 13)
    private String phone;

    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "clave", nullable = false, length = 255)
    private String password;

    @Column(name = "id_rol", nullable = false)
    private Long roleId;
}
