package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
public class EnvoiceDTO {
    @Getter @Setter private Long id_envoice;
    @Getter @Setter private String nombre_envoice;
    @Getter @Setter private String medioPago_envoice;
    @Getter @Setter private Double total_envoice;
    @Getter @Setter private Long client_id;
    @Getter @Setter Long employer_id;
    @Getter @Setter List<EnvoiceProductDTO> products;
}



