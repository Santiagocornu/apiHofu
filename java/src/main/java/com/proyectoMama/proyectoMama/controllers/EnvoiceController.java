package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceDTO;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;
import com.proyectoMama.proyectoMama.services.EnvoiceProductService;
import com.proyectoMama.proyectoMama.services.EnvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envoices")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})
public class EnvoiceController {

    @Autowired
    private EnvoiceService envoiceService;

    @Autowired
    private EnvoiceProductService envoiceProductService;

    @PostMapping("/createWithProducts")
    public ResponseEntity<?> createEnvoiceWithProducts(
            @RequestBody EnvoiceDTO envoiceDTO,
            @RequestParam List<Long> productIds,
            @RequestParam List<Long> quantities) {
        try {
            // Validar longitud de listas
            if (productIds.size() != quantities.size()) {
                return ResponseEntity.badRequest().body("Product IDs and quantities must match.");
            }

            // Check employer existence
            if (!envoiceService.existsEmployerById(envoiceDTO.getEmployer_id())) {
                return ResponseEntity.badRequest().body("Employer not found.");
            }

            // Check client existence only if client_id is not null
            if (envoiceDTO.getClient_id() != null && !envoiceService.existsClientById(envoiceDTO.getClient_id())) {
                return ResponseEntity.badRequest().body("Client not found.");
            }

            // Create envoice
            EnvoiceDTO createdEnvoice = envoiceService.createEnvoice(envoiceDTO);
            if (createdEnvoice == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create envoice.");
            }

            // Create associated products
            for (int i = 0; i < productIds.size(); i++) {
                EnvoiceProductDTO envoiceProductDTO = new EnvoiceProductDTO();
                envoiceProductDTO.setEnvoiceId(createdEnvoice.getId_envoice());
                envoiceProductDTO.setProductId(productIds.get(i));
                envoiceProductDTO.setQuantity(quantities.get(i));
                envoiceProductService.createEnvoiceProduct(envoiceProductDTO);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdEnvoice);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    // Obtener todas las envoices
    @GetMapping
    public List<EnvoiceDTO> getAllEnvoices() {
        try {
            return envoiceService.getAllEnvoices();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener todas las envoices", e);
        }
    }

    // Obtener una envoice por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> getEnvoiceById(@PathVariable Long id) {
        try {
            EnvoiceDTO envoiceDTO = envoiceService.getEnvoiceById(id);
            return envoiceDTO != null ? ResponseEntity.ok(envoiceDTO) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener envoice por ID", e);
        }
    }

    // Actualizar una envoice existente
    @PutMapping("/{id}")
    public ResponseEntity<EnvoiceDTO> updateEnvoice(@PathVariable Long id, @RequestBody EnvoiceDTO envoiceDTO) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.updateEnvoice(id, envoiceDTO);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar envoice", e);
        }
    }

    // Eliminar una envoice por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvoice(@PathVariable Long id) {
        try {
            List<EnvoiceProductDTO> envoiceProducts = envoiceProductService.getAllEnvoiceProducts().stream()
                    .filter(ep -> ep.getEnvoiceId().equals(id))
                    .collect(Collectors.toList());

            for (EnvoiceProductDTO envoiceProduct : envoiceProducts) {
                envoiceProductService.deleteEnvoiceProduct(envoiceProduct.getId());
            }

            if (envoiceService.deleteEnvoice(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar envoice", e);
        }
    }

    // Asociar un cliente a una envoice
    @PutMapping("/{id}/associateClient")
    public ResponseEntity<EnvoiceDTO> associateClient(@PathVariable Long id, @RequestParam Long clientId) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.associateClient(id, clientId);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al asociar cliente a envoice", e);
        }
    }

    // Asociar un empleador a una envoice
    @PutMapping("/{id}/associateEmployer")
    public ResponseEntity<EnvoiceDTO> associateEmployer(@PathVariable Long id, @RequestParam Long employerId) {
        try {
            EnvoiceDTO updatedEnvoice = envoiceService.associateEmployer(id, employerId);
            return updatedEnvoice != null ? ResponseEntity.ok(updatedEnvoice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al asociar empleador a envoice", e);
        }
    }
}
