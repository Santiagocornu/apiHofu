package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Envoice_product")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class EnvoiceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "envoiceid", nullable = true)
    @Getter @Setter private Envoice envoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid")
@Getter @Setter private Product product;

    @Getter @Setter private Long quantity;
}



