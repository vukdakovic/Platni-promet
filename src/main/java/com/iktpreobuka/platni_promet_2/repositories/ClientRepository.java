package com.iktpreobuka.platni_promet_2.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.platni_promet_2.entities.BankEntity;
import com.iktpreobuka.platni_promet_2.entities.ClientEntity;

public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {

	ClientEntity findFirstByNameAndSurnameIgnoreCase(String name, String surname);

	List<ClientEntity> findByBank(BankEntity bank);

}
