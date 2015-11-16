package com.coursera.androidcapstone.dailyselfie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coursera.androidcapstone.dailyselfie.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // Find a doctor with a matching email (e.g., User.email)
    public User findByEmail(String email);

}
