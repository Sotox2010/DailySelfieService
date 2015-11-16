package com.coursera.androidcapstone.dailyselfie.repository;

import org.springframework.data.repository.CrudRepository;

import com.coursera.androidcapstone.dailyselfie.model.Photo;
import com.coursera.androidcapstone.dailyselfie.model.User;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    public Photo findByNameAndOwner(String name, User owner);

}
