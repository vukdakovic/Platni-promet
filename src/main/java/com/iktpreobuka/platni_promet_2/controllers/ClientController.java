package com.iktpreobuka.platni_promet_2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.platni_promet_2.entities.ClientEntity;
import com.iktpreobuka.platni_promet_2.repositories.ClientRepository;
import com.iktpreobuka.platni_promet_2.security.Views;
import com.iktpreobuka.platni_promet_2.services.ClientService;

@RestController
@RequestMapping(path = "/api/v1/client")
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@JsonView(Views.Admin.class)
	public Iterable<ClientEntity> getAllClientsForAdmin() {
		return clientRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/private")
	@JsonView(Views.Client.class)
	public Iterable<ClientEntity> getAllClientsForPrivate() {
		return clientRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/public")
	@JsonView(Views.Public.class)
	public Iterable<ClientEntity> getAllClientsForPublic() {
		return clientRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ClientEntity getClientById(@PathVariable Integer id) {
		return clientRepository.findOne(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/by-nameAndSurname")
	public ClientEntity findByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
		return clientRepository.findFirstByNameAndSurnameIgnoreCase(name, surname);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewClient(@RequestParam String name, @RequestParam String surname,
			@RequestParam String password, @RequestParam String email, @RequestParam String bankName,
			@RequestParam String street, @RequestParam String city, @RequestParam String country) {
		return clientService.addNewClient(name, surname, password, email, bankName, street, city, country);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyClient(@PathVariable Integer id, @RequestParam String name,
			@RequestParam String surname, @RequestParam String password, @RequestParam String email,
			@RequestParam String bankName, @RequestParam String street, @RequestParam String city,
			@RequestParam String country) {
		return clientService.modifyClient(id, name, surname, password, email, bankName, street, city, country);
	}

	/*
	 * @RequestMapping(method = RequestMethod.PUT, value = "/{id}/address")
	 * public ClientEntity addAddressToAClient(@PathVariable Integer
	 * id, @RequestParam Integer address) { ClientEntity client =
	 * clientRepository.findOne(id); AddressEntity adr =
	 * addressRepository.findOne(address); client.setAddress(adr);
	 * clientRepository.save(client); return client; }
	 * 
	 * @RequestMapping(method = RequestMethod.PUT, value = "/{id}/bank") public
	 * ClientEntity addBankToAClient(@PathVariable Integer id, @RequestParam
	 * Integer bank) { ClientEntity client = clientRepository.findOne(id);
	 * BankEntity bnk = bankRepository.findOne(bank); client.setBank(bnk);
	 * clientRepository.save(client); return client; }
	 */

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ClientEntity deleteClientById(@PathVariable Integer id) {
		return clientService.deleteClientById(id);
	}

}
