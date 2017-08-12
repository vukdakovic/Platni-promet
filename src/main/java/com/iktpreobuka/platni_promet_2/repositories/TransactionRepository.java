package com.iktpreobuka.platni_promet_2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.platni_promet_2.entities.TransactionEntity;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

}
