package com.coursera.androidcapstone.dailyselfie.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coursera.androidcapstone.dailyselfie.model.Doctor;



@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

    // Find all doctors with a matching name (e.g., Doctor.name)
    public Collection<Doctor> findByName(String name);

    // Find a doctor with a matching email (e.g., Doctor.email)
    public Doctor findByEmail(String email);

}
