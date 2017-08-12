package com.iktpreobuka.platni_promet_2.services;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.platni_promet_2.entities.ClientEntity;

public interface ClientService {
	ResponseEntity<?> addNewClient(String name, String surname, String password, String email, String bankName,
			String street, String city, String country);

	ResponseEntity<?> modifyClient(Integer id, String name, String surname, String password, String email,
			String bankName, String street, String city, String country);

	ClientEntity deleteClientById(Integer id);

}
