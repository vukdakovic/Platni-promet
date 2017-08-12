package com.iktpreobuka.platni_promet_2.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.platni_promet_2.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {

	AddressEntity findFirstByStreetAndCityAndCountryIgnoreCase(String street, String city, String country);

	AddressEntity findByCityIgnoreCase(String city);

	AddressEntity findByCountryIgnoreCase(String country);
}
