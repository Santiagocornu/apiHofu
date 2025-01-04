package com.proyectoMama.proyectoMama.services;


import com.proyectoMama.proyectoMama.entities.Person.Employer;
import com.proyectoMama.proyectoMama.repositories.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public Optional<Employer> findById(Long id) {
        return employerRepository.findById(id);
    }

    public Employer save(Employer employer) {
        return employerRepository.save(employer);
    }

    public void deleteById(Long id) {
        employerRepository.deleteById(id);
    }
}

