package com.iktpreobuka.platni_promet_2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iktpreobuka.platni_promet_2.entities.AccountEntity;
import com.iktpreobuka.platni_promet_2.entities.AddressEntity;
import com.iktpreobuka.platni_promet_2.entities.BankEntity;
import com.iktpreobuka.platni_promet_2.entities.ClientEntity;
import com.iktpreobuka.platni_promet_2.repositories.AccountRepository;
import com.iktpreobuka.platni_promet_2.repositories.AddressRepository;
import com.iktpreobuka.platni_promet_2.repositories.BankRepository;
import com.iktpreobuka.platni_promet_2.repositories.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	BankRepository bankRepository;

	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public ResponseEntity<?> addNewClient(String name, String surname, String password, String email, String bankName,
			String street, String city, String country) {
		ClientEntity client = new ClientEntity();
		BankEntity bank = bankRepository.findFirstByNameIgnoreCase(bankName);
		AddressEntity adr = addressRepository.findFirstByStreetAndCityAndCountryIgnoreCase(street, city, country);
		if ((((name == "") || (surname == "")) || (street == "")) || ((city == "") || (country == ""))) {
			String poruka = "Jedan od parametara nije popunjen! Molimo vas da popunite sve parametre. Hvala na razumevanju.";
			return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
		}
		if (((bankName == "") || (password == "")) || (email == "")) {
			String poruka = "Jedan od parametara nije popunjen! Molimo vas da popunite sve parametre. Hvala na razumevanju.";
			return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
		}
		client.setName(name);
		client.setSurname(surname);
		client.setPassword(password);
		client.setEmail(email);
		client.setBank(bank);
		client.setAddress(adr);
		if (bank == null) {
			String poruka = "Ne postoji banka sa tim imenom!";
			return new ResponseEntity<String>(poruka, HttpStatus.NOT_FOUND);
		} else if (adr == null) {
			AddressEntity emptyAddress = new AddressEntity();
			emptyAddress.setStreet(street);
			emptyAddress.setCity(city);
			emptyAddress.setCountry(country);
			addressRepository.save(emptyAddress);
			client.setAddress(emptyAddress);
		}
		clientRepository.save(client);
		return new ResponseEntity<ClientEntity>(client, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> modifyClient(Integer id, String name, String surname, String password, String email,
			String bankName, String street, String city, String country) {
		ClientEntity client = clientRepository.findOne(id);
		AddressEntity emptyAddress = new AddressEntity();
		BankEntity bank = bankRepository.findFirstByNameIgnoreCase(bankName);
		AddressEntity formerAddress = client.getAddress();
		if (name != "") {
			client.setName(name);
		} else {
			name = client.getName();
			client.setName(name);
		}
		if (surname != "") {
			client.setSurname(surname);
		} else {
			surname = client.getSurname();
			client.setSurname(surname);
		}
		if (password != "") {
			client.setPassword(password);
		} else {
			password = client.getPassword();
			client.setPassword(password);
		}
		if (email != "") {
			client.setEmail(email);
		} else {
			email = client.getEmail();
			client.setEmail(email);
		}
		if (street != "") {
			emptyAddress.setStreet(street);
		} else {
			street = client.getAddress().getStreet();
			emptyAddress.setStreet(street);
		}
		if (city != "") {
			emptyAddress.setCity(city);
		} else {
			city = client.getAddress().getCity();
			emptyAddress.setCity(city);
		}
		if (country != "") {
			emptyAddress.setCountry(country);
		} else {
			country = client.getAddress().getCountry();
			emptyAddress.setCountry(country);
		}
		if ((bankName != "") && (bank != null)) {
			client.setBank(bank);
		} else if ((bankName != "") && (bank == null)) {
			String poruka = "Banka ne postoji u registru! Molimo Vas, unesite postojecu banku. Hvala na razumevanju.";
			return new ResponseEntity<String>(poruka, HttpStatus.NOT_FOUND);
		} else {
			bank = client.getBank();
			client.setBank(bank);
		}
		for (AddressEntity addressI : addressRepository.findAll()) {
			if ((emptyAddress.getStreet().equalsIgnoreCase(addressI.getStreet())
					&& emptyAddress.getCity().equalsIgnoreCase(addressI.getCity()))
					&& emptyAddress.getCountry().equalsIgnoreCase(addressI.getCountry())) {
				client.setAddress(addressI);
			} else {
				addressRepository.save(emptyAddress);
				client.setAddress(emptyAddress);
				addressRepository.delete(formerAddress);
			}
		}
		clientRepository.save(client);
		return new ResponseEntity<ClientEntity>(client, HttpStatus.OK);
	}
	
	@Override
	public ClientEntity deleteClientById(@PathVariable Integer id) {
		ClientEntity client = clientRepository.findOne(id);
		List<AccountEntity> clientAccounts = accountRepository.findByClient(client);
		client.setBank(null);
		clientRepository.save(client);
		accountRepository.delete(clientAccounts);
		clientRepository.delete(id);
		return client;
	}

}