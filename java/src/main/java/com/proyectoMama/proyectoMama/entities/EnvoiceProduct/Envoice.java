package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyectoMama.proyectoMama.entities.Person.Client;
import com.proyectoMama.proyectoMama.entities.Person.Employer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "envoice")  // Asegúrate de que el nombre esté en minúsculas
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Envoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id_envoice;

    @Getter @Setter private String nombre_envoice;
    @Getter @Setter private String medioPago_envoice;

    @Getter @Setter private Double total_envoice;
    @Getter @Setter private String descripcion_envoice;

    @OneToMany(mappedBy = "envoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter private Set<EnvoiceProduct> envoiceProducts = new HashSet<>();

    @OneToOne(mappedBy = "envoice", cascade = CascadeType.ALL, optional = true)
    @JsonIgnore
    @Getter @Setter private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "client_id", nullable = true)
    @Getter @Setter private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "employer_id", nullable = true) // Permitir nulos
    @JsonIgnore
    @Getter @Setter private Employer employer;

}





