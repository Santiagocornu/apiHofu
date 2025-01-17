package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyectoMama.proyectoMama.entities.Person.Client;
import com.proyectoMama.proyectoMama.entities.Person.Employer;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
public class Envoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id_envoice;

    @Getter @Setter private String nombre_envoice;
    @Getter @Setter private String medioPago_envoice;
    @Getter @Setter private Double total_envoice;
    @Getter @Setter private String descripcion_envoice;

    @OneToMany(mappedBy = "envoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // Ignorar esta relación para evitar ciclos
    @Getter @Setter private Set<EnvoiceProduct> envoiceProducts = new HashSet<>();

    @OneToOne(mappedBy = "envoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    @JsonIgnore // Ignorar esta relación para evitar ciclos
    @Getter @Setter private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "client_id")
    @Getter @Setter private Client client;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "employer_id")
    @JsonIgnore // Ignorar si es necesario
    @Getter @Setter private Employer employer;
}




