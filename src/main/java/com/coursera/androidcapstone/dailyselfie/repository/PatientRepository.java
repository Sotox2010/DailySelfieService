package com.coursera.androidcapstone.dailyselfie.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coursera.androidcapstone.dailyselfie.model.Doctor;
import com.coursera.androidcapstone.dailyselfie.model.Patient;


@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    // Find all patients with a matching name (e.g., Patient.name)
    public Collection<Patient> findByName(String name);

    public Collection<Patient> findByDoctor(Doctor doctor);

    public Patient findByEmail(String email);

}
