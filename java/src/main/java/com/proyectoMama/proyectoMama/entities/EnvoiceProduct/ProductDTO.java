package com.proyectoMama.proyectoMama.entities.EnvoiceProduct;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class ProductDTO {
    @Getter @Setter private Long id_product;
    @Getter @Setter private String nombre_product;
    @Getter @Setter private String descripcion_product;
   @Getter @Setter private double precio;

}


