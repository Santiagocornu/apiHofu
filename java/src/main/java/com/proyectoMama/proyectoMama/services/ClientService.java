package com.proyectoMama.proyectoMama.services;

import com.proyectoMama.proyectoMama.entities.Person.Client;
import com.proyectoMama.proyectoMama.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        try {
            if (clientRepository.existsById(id)) { // Verifica si el cliente existe
                clientRepository.deleteById(id);
            } else {
                throw new EntityNotFoundException("Cliente no encontrado con ID: " + id);
            }
        } catch (DataIntegrityViolationException e) {
            // Puedes lanzar una excepción personalizada o manejarla aquí
            throw new DataIntegrityViolationException("No se puede eliminar el cliente: tiene dependencias en otras tablas.");
        }
    }
}


