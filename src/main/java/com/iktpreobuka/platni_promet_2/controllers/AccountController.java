package com.iktpreobuka.platni_promet_2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.platni_promet_2.entities.AccountEntity;
import com.iktpreobuka.platni_promet_2.repositories.AccountRepository;
import com.iktpreobuka.platni_promet_2.services.AccountService;

@RestController
@RequestMapping(path = "/api/v1/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<AccountEntity> getAllAccounts() {
		return accountRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public AccountEntity getAccountById(@PathVariable Integer id) {
		AccountEntity account = accountRepository.findOne(id);
		return account;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/clients/{id}")
	public List<AccountEntity> findAccountsByClient(@PathVariable Integer id) {
		List<AccountEntity> list = new ArrayList<>();
		Iterable<AccountEntity> accountList = accountRepository.findAll();
		for (AccountEntity account : accountList) {
			if (account.getClient().getId().equals(id))
				list.add(account);
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewAccount(@RequestParam Long accountNumber, @RequestParam Double balance,
			@RequestParam Integer clientId) {
		return accountService.addNewAccount(accountNumber, balance, clientId);
	}

	/*
	 * @RequestMapping(method = RequestMethod.PUT, value = "/{id}/client")
	 * public AccountEntity addClientToAAccount(@PathVariable Integer
	 * id, @RequestParam Integer client) { AccountEntity account =
	 * accountRepository.findOne(id); ClientEntity clt =
	 * clientRepository.findOne(client); account.setClient(clt);
	 * accountRepository.save(account); // automatski ce biti sacuvan i klijent
	 * return account; }
	 */
	// Nepotrebna metoda za ACCOUNT jer ne treba da budemo u mogucnosti da
	// menjamo account.

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public AccountEntity deleteAccount(@PathVariable Integer id) {
		AccountEntity account = accountRepository.findOne(id);
		account.setClient(null);
		accountRepository.save(account);
		accountRepository.delete(id);
		return account;
	}

}
