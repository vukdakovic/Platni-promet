package com.iktpreobuka.platni_promet_2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.platni_promet_2.entities.BankEntity;

public interface BankRepository extends CrudRepository<BankEntity, Integer> {

	BankEntity findFirstByNameIgnoreCase(String name);

	BankEntity findFirstByPibIgnoreCase(String pib);

}
