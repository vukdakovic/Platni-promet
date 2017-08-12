package com.iktpreobuka.platni_promet_2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.platni_promet_2.entities.TransactionEntity;
import com.iktpreobuka.platni_promet_2.repositories.TransactionRepository;
import com.iktpreobuka.platni_promet_2.services.TransactionService;

@RestController
@RequestMapping(path = "/api/v1/transaction")
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<TransactionEntity> getAllTransactions() {
		return transactionRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public TransactionEntity getTransactionByTransactionId(@PathVariable Long id) {
		TransactionEntity transaction = transactionRepository.findOne(id);
		return transaction;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/byBankName/{bankName}")
	public List<TransactionEntity> findTransactionByBankName(@PathVariable String bankName) {
		return transactionService.findTransactionsByBankName(bankName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{accountNumber}/recipientAccount")
	public List<TransactionEntity> findTransactionsByRecipientAccount(@PathVariable Long accountNumber) {
		return transactionService.findTransactionsByRecipientAccount(accountNumber);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{accountNumber}/senderAccount")
	public List<TransactionEntity> findTransactionsBySenderAccount(@PathVariable Long accountNumber) {
		return transactionService.findTransactionsBySenderAccount(accountNumber);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewTransaction(@RequestParam Double amount, @RequestParam String purpose,
			@RequestParam Long accountRecipient, @RequestParam Long accountSender) {
		return transactionService.addNewTransaction(amount, purpose, accountRecipient, accountSender);
	}

	/*
	 * @RequestMapping(method = RequestMethod.PUT, value = "/{id}/account")
	 * public TransactionEntity addAccountsToATransaction(@PathVariable Long
	 * id, @RequestParam Integer accountRecipient,
	 * 
	 * @RequestParam Integer accountSender) { TransactionEntity transaction =
	 * transactionRepository.findOne(id); AccountEntity accountR =
	 * accountRepository.findOne(accountRecipient); AccountEntity accountS =
	 * accountRepository.findOne(accountSender);
	 * transaction.setAccountRecipient(accountR);
	 * transaction.setAccountSender(accountS);
	 * transactionRepository.save(transaction); return transaction; } 
	 * 
	 * NAMA NE
	 * TREBA PUT NA TRANSAKCIJU, JER NE SME DA POSTOJI MOGUCNOST PROMENE VEC
	 * IZVRSENE TRANSAKCIJE
	 * 
	 * @RequestMapping(method = RequestMethod.DELETE, value = "/{id}") public
	 * TransactionEntity deleteTransactionById(@PathVariable Long id) {
	 * TransactionEntity bank = transactionRepository.findOne(id);
	 * transactionRepository.delete(id); return bank; } 
	 * 
	 * NAMA NE TREBA DELETE ZA
	 * TRANSAKCIJU, JER NEMA POTREBE DA SE OBRISU VEC IZVRSENE TRANSAKCIJE
	 */


}
