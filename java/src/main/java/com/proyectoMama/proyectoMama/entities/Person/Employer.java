package com.proyectoMama.proyectoMama.entities.Person;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Employer")
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employer extends Person {

    @Getter @Setter private String legajo_Employer;
    @Getter @Setter private String gmail_Employer;
    @Getter @Setter private String turno_Employer;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Getter @Setter private Set<Envoice> envoices = new HashSet<>();

    public Employer(String nombre_person, String apellido_person, String telefono_person,
                    String legajo_Employer, String gmail_Employer, String turno_Employer) {
        super(nombre_person, apellido_person, telefono_person);
        this.legajo_Employer = legajo_Employer;
        this.gmail_Employer = gmail_Employer;
        this.turno_Employer = turno_Employer;
    }
}

