package com.proyectoMama.proyectoMama.controllers;

import com.proyectoMama.proyectoMama.entities.Person.Employer;
import com.proyectoMama.proyectoMama.entities.Person.EmployerDTO;
import com.proyectoMama.proyectoMama.services.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = {"http://localhost:3000", "https://front-hofu.vercel.app"})

public class EmployerController {
    @Autowired
    private EmployerService employerService;

    // Mapper Methods
    private EmployerDTO toDTO(Employer employer) {
        EmployerDTO dto = new EmployerDTO();
        dto.setId_person(employer.getId_person());
        dto.setNombre_person(employer.getNombre_person());
        dto.setApellido_person(employer.getApellido_person());
        dto.setTelefono_person(employer.getTelefono_person());
        dto.setLegajo_Employer(employer.getLegajo_Employer());
        dto.setGmail_Employer(employer.getGmail_Employer());
        dto.setTurno_Employer(employer.getTurno_Employer());
        return dto;
    }

    private Employer toEntity(EmployerDTO dto) {
        Employer employer = new Employer();
        employer.setId_person(dto.getId_person());
        employer.setNombre_person(dto.getNombre_person());
        employer.setApellido_person(dto.getApellido_person());
        employer.setTelefono_person(dto.getTelefono_person());
        employer.setLegajo_Employer(dto.getLegajo_Employer());
        employer.setGmail_Employer(dto.getGmail_Employer());
        employer.setTurno_Employer(dto.getTurno_Employer());
        return employer;
    }

    @GetMapping
    public List<EmployerDTO> getAllEmployers() {
        List<Employer> employers = employerService.findAll();
        return employers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long id) {
        Optional<Employer> employer = employerService.findById(id);
        return employer.map(value -> ResponseEntity.ok(toDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployerDTO createEmployer(@RequestBody EmployerDTO employerDTO) {
        Employer employer = toEntity(employerDTO);
        Employer savedEmployer = employerService.save(employer);
        return toDTO(savedEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerDTO> updateEmployer(@PathVariable Long id, @RequestBody EmployerDTO employerDetails) {
        Optional<Employer> employer = employerService.findById(id);
        if (employer.isPresent()) {
            Employer updatedEmployer = employer.get();
            updatedEmployer.setNombre_person(employerDetails.getNombre_person());
            updatedEmployer.setApellido_person(employerDetails.getApellido_person());
            updatedEmployer.setTelefono_person(employerDetails.getTelefono_person());
            updatedEmployer.setLegajo_Employer(employerDetails.getLegajo_Employer());
            updatedEmployer.setGmail_Employer(employerDetails.getGmail_Employer());
            updatedEmployer.setTurno_Employer(employerDetails.getTurno_Employer());
            Employer savedEmployer = employerService.save(updatedEmployer);
            return ResponseEntity.ok(toDTO(savedEmployer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        if (employerService.findById(id).isPresent()) {
            employerService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

