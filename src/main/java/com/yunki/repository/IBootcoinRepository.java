package com.yunki.repository;

import com.yunki.entity.Bootcoin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IBootcoinRepository extends MongoRepository<Bootcoin, Integer> {
}
