package com.yunki.repository;

import com.yunki.entity.Yunki;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

//ReactiveCrudRepository
@Repository
public interface IYunkiRepository extends MongoRepository<Yunki, Integer> {

}
