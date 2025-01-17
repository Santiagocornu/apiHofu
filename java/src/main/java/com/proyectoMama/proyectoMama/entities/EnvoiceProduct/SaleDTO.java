package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class SaleDTO {
    @Getter @Setter private Long id_sale;

    @Getter @Setter private String monto_sale;

    @Getter @Setter private String fecha_sale;

    @Getter @Setter private Long envoice_id;
}

