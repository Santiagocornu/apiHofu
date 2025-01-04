package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import lombok.Getter;
import lombok.Setter;

public class EnvoiceProductDTO {
    @Getter @Setter private Long id;
    @Getter @Setter private Long envoiceId;  // Updated to match entity field name
    @Getter @Setter private Long productId;  // Updated to match entity field name
    @Getter @Setter private Long quantity;

}


