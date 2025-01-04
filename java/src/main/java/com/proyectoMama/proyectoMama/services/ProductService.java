package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Product;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.ProductDTO;
import com.proyectoMama.proyectoMama.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return product != null ? convertToDTO(product) : null;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(product -> {
            product.setNombre_product(productDTO.getNombre_product());
            product.setDescripcion_product(productDTO.getDescripcion_product());
            product.setPrecio(productDTO.getPrecio());
            return convertToDTO(productRepository.save(product));
        }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return true;
        }).orElse(false);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId_product(product.getId_product());
        dto.setNombre_product(product.getNombre_product());
        dto.setDescripcion_product(product.getDescripcion_product());
        dto.setPrecio(product.getPrecio());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId_product(dto.getId_product());
        product.setNombre_product(dto.getNombre_product());
        product.setDescripcion_product(dto.getDescripcion_product());
        product.setPrecio(dto.getPrecio());
        return product;
    }
}




