package com.iktpreobuka.platni_promet_2.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iktpreobuka.platni_promet_2.entities.AccountEntity;
import com.iktpreobuka.platni_promet_2.entities.AddressEntity;
import com.iktpreobuka.platni_promet_2.entities.BankEntity;
import com.iktpreobuka.platni_promet_2.entities.ClientEntity;
import com.iktpreobuka.platni_promet_2.repositories.AccountRepository;
import com.iktpreobuka.platni_promet_2.repositories.AddressRepository;
import com.iktpreobuka.platni_promet_2.repositories.BankRepository;
import com.iktpreobuka.platni_promet_2.repositories.ClientRepository;

@Service
public class BankServiceImpl implements BankService {

	@Autowired
	BankRepository bankRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<BankEntity> getBanksInCity(String city) {
		List<BankEntity> list = new ArrayList<>();
		Iterable<BankEntity> bankList = bankRepository.findAll();
		for (BankEntity bank : bankList) {
			if (bank.getAddress().getCity().equalsIgnoreCase(city))
				list.add(bank);
		}
		return list;
	}

	@Override
	public List<BankEntity> getBanksInCountry(String country) {
		List<BankEntity> list = new ArrayList<>();
		Iterable<BankEntity> bankList = bankRepository.findAll();
		for (BankEntity bank : bankList) {
			if (bank.getAddress().getCountry().equalsIgnoreCase(country))
				list.add(bank);
		}
		return list;
	}

	@Override
	public ResponseEntity<?> addNewBank(String name, String pib, String street, String city, String country) {
		BankEntity bank = new BankEntity();
		AddressEntity emptyAddress = new AddressEntity();
		if ((((name == "") || (pib == "")) || (street == "")) || ((city == "") || (country == ""))) {
			String poruka = "Jedan od parametara nije popunjen! Molimo vas da popunite sve parametre. Hvala na razumevanju.";
			return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
		}
		for (BankEntity bankEntity : bankRepository.findAll()) {
			if ((name.equalsIgnoreCase(bankEntity.getName())) || (pib.equalsIgnoreCase(bankEntity.getPib()))) {
				String poruka = "Vec postoji banka sa tim imenom i/ili PIB-om. Hvala na razumevanju.";
				return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
			}
		}
		bank.setName(name);
		bank.setPib(pib);
		emptyAddress.setStreet(street);
		emptyAddress.setCity(city);
		emptyAddress.setCountry(country);
		for (AddressEntity address : addressRepository.findAll()) {
			if ((emptyAddress.getStreet().equalsIgnoreCase(address.getStreet())
					&& emptyAddress.getCountry().equalsIgnoreCase(address.getCountry()))
					&& emptyAddress.getCity().equalsIgnoreCase(address.getCity())) {
				String poruka = "Adresa je zauzeta. Napravite banku na drugoj adresi!";
				return new ResponseEntity<String>(poruka, HttpStatus.CONFLICT);
			}
		}
		addressRepository.save(emptyAddress);
		bank.setAddress(emptyAddress);
		bankRepository.save(bank);
		return new ResponseEntity<BankEntity>(bank, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> modifyBank(Integer id, String name, String pib, String street, String city,
			String country) {
		BankEntity bank = bankRepository.findOne(id);
		AddressEntity address = new AddressEntity();
		Integer formerAddressId = bank.getAddress().getId();
		for (BankEntity bankEntity : bankRepository.findAll()) {
			if ((name.equalsIgnoreCase(bankEntity.getName())) || (pib.equalsIgnoreCase(bankEntity.getPib()))) {
				String poruka = "Vec postoji banka sa tim imenom i/ili PIB-om. Hvala na razumevanju.";
				return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
			}
		}
		if (name != "") {
			bank.setName(name);
		} else {
			name = bank.getName();
			bank.setName(name);
		}
		if (pib != "") {
			bank.setPib(pib);
		} else {
			pib = bank.getPib();
			bank.setPib(pib);
		}
		if (street != "") {
			address.setStreet(street);
		} else {
			street = bank.getAddress().getStreet();
			address.setStreet(street);
		}
		if (city != "") {
			address.setCity(city);
		} else {
			city = bank.getAddress().getCity();
			address.setCity(city);
		}
		if (country != "") {
			address.setCountry(country);
		} else {
			country = bank.getAddress().getCountry();
			address.setCountry(country);
		}
		for (AddressEntity addressI : addressRepository.findAll()) {
			if ((address.getStreet().equalsIgnoreCase(addressI.getStreet())
					&& address.getCountry().equalsIgnoreCase(addressI.getCountry()))
					&& address.getCity().equalsIgnoreCase(addressI.getCity())) {
				String poruka = "Adresa je zauzeta. Napravite banku na drugoj adresi!";
				return new ResponseEntity<String>(poruka, HttpStatus.CONFLICT);
			}
		}
		addressRepository.save(address);
		bank.setAddress(address);
		addressRepository.delete(formerAddressId);
		bankRepository.save(bank);
		return new ResponseEntity<BankEntity>(bank, HttpStatus.OK);
	}

	@Override
	public BankEntity deleteBankById(Integer id) {
		BankEntity bank = bankRepository.findOne(id);
		List<ClientEntity> bankClients = clientRepository.findByBank(bank);
		for (ClientEntity clientEntity : bankClients) {
			List<AccountEntity> clientAccounts = accountRepository.findByClient(clientEntity);
			accountRepository.delete(clientAccounts);
		}
		clientRepository.delete(bankClients);
		bankRepository.delete(id);
		return bank;
	}

}
