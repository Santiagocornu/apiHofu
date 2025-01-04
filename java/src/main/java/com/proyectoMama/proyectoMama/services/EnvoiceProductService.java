package com.proyectoMama.proyectoMama.services;


import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Product;
import com.proyectoMama.proyectoMama.repositories.EnvoiceProductRepository;
import com.proyectoMama.proyectoMama.repositories.EnvoiceRepository;
import com.proyectoMama.proyectoMama.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProduct;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;

@Service
public class EnvoiceProductService {

    @Autowired
    private EnvoiceProductRepository envoiceProductRepository;

    @Autowired
    private EnvoiceRepository envoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<EnvoiceProductDTO> getAllEnvoiceProducts() {
        return envoiceProductRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EnvoiceProductDTO getEnvoiceProductById(Long id) {
        EnvoiceProduct envoiceProduct = envoiceProductRepository.findById(id).orElse(null);
        return envoiceProduct != null ? convertToDTO(envoiceProduct) : null;
    }

    public EnvoiceProductDTO createEnvoiceProduct(EnvoiceProductDTO dto) {
        EnvoiceProduct envoiceProduct = convertToEntity(dto);
        envoiceProduct = envoiceProductRepository.save(envoiceProduct);
        return convertToDTO(envoiceProduct);
    }

    public EnvoiceProductDTO updateEnvoiceProduct(Long id, EnvoiceProductDTO dto) {
        return envoiceProductRepository.findById(id).map(envoiceProduct -> {
            envoiceProduct.setEnvoice(envoiceRepository.findById(dto.getEnvoiceId()).orElse(null));
            envoiceProduct.setProduct(productRepository.findById(dto.getProductId()).orElse(null));
            envoiceProduct.setQuantity(dto.getQuantity());
            return convertToDTO(envoiceProductRepository.save(envoiceProduct));
        }).orElse(null);
    }

    public boolean deleteEnvoiceProduct(Long id) {
        return envoiceProductRepository.findById(id).map(envoiceProduct -> {
            envoiceProductRepository.delete(envoiceProduct);
            return true;
        }).orElse(false);
    }

    public EnvoiceProductDTO associateEnvoice(Long envoiceProductId, Long envoiceId) {
        return envoiceProductRepository.findById(envoiceProductId).map(envoiceProduct -> {
            Envoice envoice = envoiceRepository.findById(envoiceId).orElse(null);
            if (envoice != null) {
                envoiceProduct.setEnvoice(envoice);
                return convertToDTO(envoiceProductRepository.save(envoiceProduct));
            }
            return null;
        }).orElse(null);
    }

    public EnvoiceProductDTO associateProduct(Long envoiceProductId, Long productId) {
        return envoiceProductRepository.findById(envoiceProductId).map(envoiceProduct -> {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                envoiceProduct.setProduct(product);
                return convertToDTO(envoiceProductRepository.save(envoiceProduct));
            }
            return null;
        }).orElse(null);
    }

    private EnvoiceProductDTO convertToDTO(EnvoiceProduct envoiceProduct) {
        EnvoiceProductDTO dto = new EnvoiceProductDTO();
        dto.setId(envoiceProduct.getId());
        dto.setEnvoiceId(envoiceProduct.getEnvoice() != null ? envoiceProduct.getEnvoice().getId_envoice() : null);
        dto.setProductId(envoiceProduct.getProduct().getId_product());
        dto.setQuantity(envoiceProduct.getQuantity());
        return dto;
    }

    private EnvoiceProduct convertToEntity(EnvoiceProductDTO dto) {
        EnvoiceProduct envoiceProduct = new EnvoiceProduct();
        envoiceProduct.setId(dto.getId());
        envoiceProduct.setEnvoice(dto.getEnvoiceId() != null ? envoiceRepository.findById(dto.getEnvoiceId()).orElse(null) : null);
        envoiceProduct.setProduct(productRepository.findById(dto.getProductId()).orElse(null));
        envoiceProduct.setQuantity(dto.getQuantity());
        return envoiceProduct;
    }
}





