package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Sale")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id_sale;

    @Getter @Setter private String monto_sale;

    @Getter @Setter private String fecha_sale;

    @OneToOne
    @JoinColumn(name = "envoice_id", referencedColumnName = "id_envoice")
    @Getter @Setter private Envoice envoice;
}


