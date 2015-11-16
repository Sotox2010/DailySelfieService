package com.coursera.androidcapstone.dailyselfie.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.coursera.androidcapstone.dailyselfie.model.Question;


@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {

}
