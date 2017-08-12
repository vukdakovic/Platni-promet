package com.iktpreobuka.platni_promet_2.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.iktpreobuka.platni_promet_2.entities.TransactionEntity;

public interface TransactionService {

	ResponseEntity<?> addNewTransaction(Double amount, String purpose, Long accountRecipient, Long accountSender);

	List<TransactionEntity> findTransactionsByRecipientAccount(Long accountNumber);

	List<TransactionEntity> findTransactionsBySenderAccount(Long accountNumber);

	List<TransactionEntity> findTransactionsByBankName(String bankName);
}
