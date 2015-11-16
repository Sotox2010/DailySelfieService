package com.coursera.androidcapstone.dailyselfie.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.coursera.androidcapstone.dailyselfie.repository.DoctorRepository;
import com.coursera.androidcapstone.dailyselfie.repository.PatientRepository;



public class DoctorAndPatientDetailsService implements UserDetailsService {

    private final DoctorRepository doctors;
    private final PatientRepository patients;

    public DoctorAndPatientDetailsService(DoctorRepository doctors, PatientRepository patients) {
        this.doctors = doctors;
        this.patients = patients;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = doctors.findByEmail(username);

        if (user == null) {
            user = patients.findByEmail(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User: " + username + " not found!");
        }

        return user;
    }

}
