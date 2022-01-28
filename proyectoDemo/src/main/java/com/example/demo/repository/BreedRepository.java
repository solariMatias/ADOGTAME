package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Breed;
import com.example.demo.entity.Dog;

@Repository
public interface BreedRepository extends CrudRepository<Breed, Long> {

}
