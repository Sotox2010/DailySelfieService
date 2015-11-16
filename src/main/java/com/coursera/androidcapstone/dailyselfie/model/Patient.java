package com.coursera.androidcapstone.dailyselfie.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Patient implements UserDetails {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String lastname;

    @Column(nullable=false)
    private long date_of_birth;

    @ManyToOne(targetEntity=Doctor.class)
    @JoinColumn(name="doctor_fk")
    private Doctor doctor;

    @OneToMany(targetEntity=Medicine.class, mappedBy="patient", cascade=CascadeType.ALL)
    private Set<Medicine> medicines = new HashSet<Medicine>();

    @OneToMany(targetEntity=CheckIn.class, mappedBy="patient", cascade=CascadeType.ALL)
    private Set<CheckIn> check_in_list = new HashSet<CheckIn>();

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    public Patient() {

    }

    public Patient(String name, String lastname, long date_of_birth) {
        this.name = name;
        this.lastname = lastname;
        this.date_of_birth = date_of_birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("date_of_birth")
    public long getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(long date) {
        this.date_of_birth = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        if (!doctor.getPatients().contains(this)) {
            doctor.getPatients().add(this);
        }
    }

    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicines) {
        this.medicines = medicines;
    }

    @JsonProperty("check_in_list")
    public Set<CheckIn> getCheckInList() {
        return check_in_list;
    }

    public void setCheckInList(Set<CheckIn> check_in_list) {
        this.check_in_list = check_in_list;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("PATIENT");
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonProperty("email")
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}
