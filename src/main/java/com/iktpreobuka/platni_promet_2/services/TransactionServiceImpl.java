package com.iktpreobuka.platni_promet_2.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iktpreobuka.platni_promet_2.entities.AccountEntity;
import com.iktpreobuka.platni_promet_2.entities.TransactionEntity;
import com.iktpreobuka.platni_promet_2.repositories.AccountRepository;
import com.iktpreobuka.platni_promet_2.repositories.BankRepository;
import com.iktpreobuka.platni_promet_2.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	BankRepository bankRepository;

	@Override
	public ResponseEntity<?> addNewTransaction(Double amount, String purpose, Long accountRecipient,
			Long accountSender) {
		TransactionEntity transaction = new TransactionEntity();
		if (((amount == null) || (purpose == "")) || ((accountRecipient == null) || (accountSender == null))) {
			String poruka = "Jedan od parametara nije popunjen! Molimo vas da popunite sve parametre. Hvala na razumevanju.";
			return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
		}
		transaction.setAmount(amount);
		transaction.setPurpose(purpose);
		AccountEntity accountR = accountRepository.findFirstByAccountNumber(accountRecipient);
		AccountEntity accountS = accountRepository.findFirstByAccountNumber(accountSender);
		Double balanceR = accountR.getBalance();
		Double balanceS = accountS.getBalance();
		Double amountT = transaction.getAmount();
		if (balanceS < amountT) {
			String poruka = "Nemate dovoljno sredstava za slanje. Molimo, dopunite racun. Hvala.";
			return new ResponseEntity<String>(poruka, HttpStatus.BAD_REQUEST);
		}
		balanceS -= amountT;
		balanceR += amountT;
		accountR.setBalance(balanceR);
		accountS.setBalance(balanceS);
		transaction.setAccountRecipient(accountR);
		transaction.setAccountSender(accountS);
		transaction.setDate(new Date());
		transactionRepository.save(transaction);
		return new ResponseEntity<TransactionEntity>(transaction, HttpStatus.OK);
	}

	@Override
	public List<TransactionEntity> findTransactionsByRecipientAccount(Long accountNumber) {
		List<TransactionEntity> list = new ArrayList<>();
		Iterable<TransactionEntity> transactionList = transactionRepository.findAll();
		for (TransactionEntity transaction : transactionList) {
			if (transaction.getAccountRecipient().getAccountNumber().equals(accountNumber))
				list.add(transaction);
		}
		return list;
	}

	@Override
	public List<TransactionEntity> findTransactionsBySenderAccount(@PathVariable Long accountNumber) {
		List<TransactionEntity> list = new ArrayList<>();
		Iterable<TransactionEntity> transactionList = transactionRepository.findAll();
		for (TransactionEntity transaction : transactionList) {
			if (transaction.getAccountSender().getAccountNumber().equals(accountNumber))
				list.add(transaction);
		}
		return list;
	}

	@Override
	public List<TransactionEntity> findTransactionsByBankName(@PathVariable String bankName) {
		List<TransactionEntity> list = new ArrayList<>();
		Iterable<TransactionEntity> transactionsList = transactionRepository.findAll();
		for (TransactionEntity transaction : transactionsList) {
			if (transaction.getAccountSender().getClient().getBank().getName().equalsIgnoreCase(bankName))
				list.add(transaction);
		}
		return list;
	}
}
