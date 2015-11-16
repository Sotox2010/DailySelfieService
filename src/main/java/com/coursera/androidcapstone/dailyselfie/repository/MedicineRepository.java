package com.coursera.androidcapstone.dailyselfie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coursera.androidcapstone.dailyselfie.model.Medicine;



@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {

}
