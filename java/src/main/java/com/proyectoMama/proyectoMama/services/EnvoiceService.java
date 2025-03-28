package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.Envoice;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceDTO;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProduct;
import com.proyectoMama.proyectoMama.entities.EnvoiceProduct.EnvoiceProductDTO;
import com.proyectoMama.proyectoMama.entities.Person.Client;
import com.proyectoMama.proyectoMama.entities.Person.Employer;
import com.proyectoMama.proyectoMama.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvoiceService {

    @Autowired
    private EnvoiceRepository envoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployerRepository employerRepository;

    // Check if an employer exists by ID
    public boolean existsEmployerById(Long employerId) {
        return employerRepository.existsById(employerId);
    }

    // Check if a client exists by ID
    public boolean existsClientById(Long clientId) {
        return clientRepository.existsById(clientId);
    }

    // Retrieve all envoices
    public List<EnvoiceDTO> getAllEnvoices() {
        try {
            return envoiceRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener todas las envoices", e);
        }
    }

    // Retrieve an envoice by ID
    public EnvoiceDTO getEnvoiceById(Long id) {
        try {
            Envoice envoice = envoiceRepository.findById(id).orElse(null);
            return envoice != null ? convertToDTO(envoice) : null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener envoice por ID", e);
        }
    }

    // Create a new envoice
    public EnvoiceDTO createEnvoice(EnvoiceDTO envoiceDTO) {
        try {
            Envoice envoice = convertToEntity(envoiceDTO);
            Envoice savedEnvoice = envoiceRepository.save(envoice);
            return convertToDTO(savedEnvoice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear envoice", e);
        }
    }

    // Update an existing envoice
    public EnvoiceDTO updateEnvoice(Long id, EnvoiceDTO envoiceDTO) {
        try {
            return envoiceRepository.findById(id).map(envoice -> {
                envoice.setNombre_envoice(envoiceDTO.getNombre_envoice());
                envoice.setMedioPago_envoice(envoiceDTO.getMedioPago_envoice());
                envoice.setTotal_envoice(envoiceDTO.getTotal_envoice());
                envoice.setDescripcion_envoice(envoiceDTO.getDescripcion_envoice());
                envoice.setClient(envoiceDTO.getClient_id() != null
                        ? clientRepository.findById(envoiceDTO.getClient_id()).orElse(null)
                        : null);
                envoice.setEmployer(envoiceDTO.getEmployer_id() != null
                        ? employerRepository.findById(envoiceDTO.getEmployer_id()).orElse(null)
                        : null);
                Envoice updatedEnvoice = envoiceRepository.save(envoice);
                return convertToDTO(updatedEnvoice);
            }).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar envoice", e);
        }
    }

    // Delete an envoice by ID
    public boolean deleteEnvoice(Long id) {
        try {
            return envoiceRepository.findById(id).map(envoice -> {
                envoiceRepository.delete(envoice);
                return true;
            }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar envoice", e);
        }
    }

    // Associate a client with an envoice
    public EnvoiceDTO associateClient(Long envoiceId, Long clientId) {
        try {
            return envoiceRepository.findById(envoiceId).map(envoice -> {
                Client client = clientRepository.findById(clientId).orElse(null);
                if (client != null) {
                    envoice.setClient(client);
                    Envoice updatedEnvoice = envoiceRepository.save(envoice);
                    return convertToDTO(updatedEnvoice);
                }
                return null;
            }).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al asociar cliente a envoice", e);
        }
    }

    // Associate an employer with an envoice
    public EnvoiceDTO associateEmployer(Long envoiceId, Long employerId) {
        try {
            return envoiceRepository.findById(envoiceId).map(envoice -> {
                Employer employer = employerRepository.findById(employerId).orElse(null);
                if (employer != null) {
                    envoice.setEmployer(employer);
                    Envoice updatedEnvoice = envoiceRepository.save(envoice);
                    return convertToDTO(updatedEnvoice);
                }
                return null;
            }).orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al asociar empleador a envoice", e);
        }
    }

    // Convert Envoice entity to EnvoiceDTO
    private EnvoiceDTO convertToDTO(Envoice envoice) {
        EnvoiceDTO dto = new EnvoiceDTO();
        dto.setId_envoice(envoice.getId_envoice());
        dto.setNombre_envoice(envoice.getNombre_envoice());
        dto.setMedioPago_envoice(envoice.getMedioPago_envoice());
        dto.setTotal_envoice(envoice.getTotal_envoice());
        dto.setDescripcion_envoice(envoice.getDescripcion_envoice());
        dto.setClient_id(envoice.getClient() != null ? envoice.getClient().getId_person() : null);
        dto.setEmployer_id(envoice.getEmployer() != null ? envoice.getEmployer().getId_person() : null);
        return dto;
    }

    // Convert EnvoiceDTO to Envoice entity
    private Envoice convertToEntity(EnvoiceDTO dto) {
        Envoice envoice = new Envoice();
        envoice.setId_envoice(dto.getId_envoice());
        envoice.setNombre_envoice(dto.getNombre_envoice());
        envoice.setMedioPago_envoice(dto.getMedioPago_envoice());
        envoice.setTotal_envoice(dto.getTotal_envoice());
        envoice.setDescripcion_envoice(dto.getDescripcion_envoice());
        envoice.setClient(dto.getClient_id() != null
                ? clientRepository.findById(dto.getClient_id()).orElse(null)
                : null);
        envoice.setEmployer(dto.getEmployer_id() != null
                ? employerRepository.findById(dto.getEmployer_id()).orElse(null)
                : null);
        return envoice;
    }
}

