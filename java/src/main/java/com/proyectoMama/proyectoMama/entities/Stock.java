package com.proyectoMama.proyectoMama.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "Stock")
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id_Stock;

    @Getter @Setter private String nombre_stock;
    @Getter @Setter private String cantidad_stock;

}
