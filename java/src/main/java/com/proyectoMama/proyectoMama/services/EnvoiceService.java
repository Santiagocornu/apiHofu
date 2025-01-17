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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvoiceService {

    @Autowired
    private EnvoiceRepository envoiceRepository;

    @Autowired
    private EnvoiceProductRepository envoiceProductRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployerRepository employerRepository;

    public List<EnvoiceDTO> getAllEnvoices() {
        return envoiceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EnvoiceDTO getEnvoiceById(Long id) {
        Envoice envoice = envoiceRepository.findById(id).orElse(null);
        return envoice != null ? convertToDTO(envoice) : null;
    }

    public EnvoiceDTO createEnvoice(EnvoiceDTO envoiceDTO) {
        Envoice envoice = convertToEntity(envoiceDTO);
        Envoice savedEnvoice = envoiceRepository.save(envoice);
        return convertToDTO(savedEnvoice);
    }

    public EnvoiceDTO updateEnvoice(Long id, EnvoiceDTO envoiceDTO) {
        return envoiceRepository.findById(id).map(envoice -> {
            envoice.setNombre_envoice(envoiceDTO.getNombre_envoice());
            envoice.setMedioPago_envoice(envoiceDTO.getMedioPago_envoice());
            envoice.setTotal_envoice(envoiceDTO.getTotal_envoice());
            envoice.setDescripcion_envoice(envoiceDTO.getDescripcion_envoice());
            envoice.setClient(envoiceDTO.getClient_id() != null ? clientRepository.findById(envoiceDTO.getClient_id()).orElse(null) : null);
            envoice.setEmployer(envoiceDTO.getEmployer_id() != null ? employerRepository.findById(envoiceDTO.getEmployer_id()).orElse(null) : null);
            return convertToDTO(envoiceRepository.save(envoice));
        }).orElse(null);
    }

    public boolean deleteEnvoice(Long id) {
        return envoiceRepository.findById(id).map(envoice -> {
            List<EnvoiceProduct> envoiceProducts = envoiceProductRepository.findByEnvoiceId(id);
            envoiceProductRepository.deleteAll(envoiceProducts);
            envoiceRepository.delete(envoice);
            return true;
        }).orElse(false);
    }

    public List<EnvoiceProductDTO> getProductsByEnvoiceId(Long envoiceId) {
        return envoiceProductRepository.findByEnvoiceId(envoiceId).stream()
                .map(envoiceProduct -> {
                    EnvoiceProductDTO dto = new EnvoiceProductDTO();
                    dto.setId(envoiceProduct.getId());
                    dto.setEnvoiceId(envoiceProduct.getEnvoice().getId_envoice());
                    dto.setProductId(envoiceProduct.getProduct().getId_product());
                    dto.setQuantity(envoiceProduct.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EnvoiceDTO associateClient(Long envoiceId, Long clientId) {
        return envoiceRepository.findById(envoiceId).map(envoice -> {
            Client client = clientRepository.findById(clientId).orElse(null);
            if (client != null) {
                envoice.setClient(client);
                return convertToDTO(envoiceRepository.save(envoice));
            }
            return null;
        }).orElse(null);
    }

    public EnvoiceDTO associateEmployer(Long envoiceId, Long employerId) {
        return envoiceRepository.findById(envoiceId).map(envoice -> {
            Employer employer = employerRepository.findById(employerId).orElse(null);
            if (employer != null) {
                envoice.setEmployer(employer);
                return convertToDTO(envoiceRepository.save(envoice));
            }
            return null;
        }).orElse(null);
    }

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

    private Envoice convertToEntity(EnvoiceDTO dto) {
        Envoice envoice = new Envoice();
        envoice.setId_envoice(dto.getId_envoice());
        envoice.setNombre_envoice(dto.getNombre_envoice());
        envoice.setMedioPago_envoice(dto.getMedioPago_envoice());
        envoice.setTotal_envoice(dto.getTotal_envoice());
        envoice.setDescripcion_envoice(dto.getDescripcion_envoice());
        envoice.setClient(dto.getClient_id() != null ? clientRepository.findById(dto.getClient_id()).orElse(null) : null);
        envoice.setEmployer(dto.getEmployer_id() != null ? employerRepository.findById(dto.getEmployer_id()).orElse(null) : null);
        return envoice;
    }
}







