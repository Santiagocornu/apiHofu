package com.proyectoMama.proyectoMama.entities.Person;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Salary")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id_salary;

    @Getter @Setter private String hora_salary;
    @Getter @Setter private String precioHora_salary;

    // Relación muchos a uno con Employer
    @ManyToOne
    @JoinColumn(name = "employer_id") // Nombre de la columna en la tabla Salary que referencia a Employer
    @Getter @Setter private Employer employer; // Referencia al empleado asociado
}
