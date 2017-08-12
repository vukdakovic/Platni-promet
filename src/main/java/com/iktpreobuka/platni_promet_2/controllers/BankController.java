package com.iktpreobuka.platni_promet_2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.platni_promet_2.entities.BankEntity;
import com.iktpreobuka.platni_promet_2.repositories.BankRepository;
import com.iktpreobuka.platni_promet_2.services.BankService;

@RestController
@RequestMapping(path = "/api/v1/bank")
public class BankController {

	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
	private BankService bankService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<BankEntity> getAllBanks() {
		return bankRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public BankEntity getBankById(@PathVariable Integer id) {
		return bankRepository.findOne(id);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bankName")
	public BankEntity getBankByName(@RequestParam String name) {
		return bankRepository.findFirstByNameIgnoreCase(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/bankPib")
	public BankEntity getBankByPib(@RequestParam String pib) {
		return bankRepository.findFirstByPibIgnoreCase(pib);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cityBanks")
	public List<BankEntity> getBanksInCity(@RequestParam String city) {
		return bankService.getBanksInCity(city);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/countryBanks")
	public List<BankEntity> getBanksInCountry(@RequestParam String country) {
		return bankService.getBanksInCountry(country);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewBank(@RequestParam String name, @RequestParam String pib,
			@RequestParam String street, @RequestParam String city, @RequestParam String country) {
		return bankService.addNewBank(name, pib, street, city, country);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyBank(@PathVariable Integer id, @RequestParam String name, @RequestParam String pib,
			@RequestParam String street, @RequestParam String city, @RequestParam String country) {
		return bankService.modifyBank(id, name, pib, street, city, country);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public BankEntity deleteBankById(@PathVariable Integer id) {
		return bankService.deleteBankById(id);
	}
}
