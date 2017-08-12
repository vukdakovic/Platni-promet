package com.iktpreobuka.platni_promet_2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.platni_promet_2.security.Views;

@Entity
@Table(name = "Account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Admin.class)
	private Integer id;

	@JsonView(Views.Client.class)
	private Long accountNumber;

	@JsonView(Views.Client.class)
	private Double balance;

	/*
	 * @JsonView(Views.Client.class)
	 * 
	 * @JsonManagedReference
	 * 
	 * @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "bank") private BankEntity bank;
	 */

	@JsonView(Views.Client.class)
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "client")
	private ClientEntity client;

	@JsonView(Views.Client.class)
	@JsonBackReference
	@OneToMany(mappedBy = "accountRecipient", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<TransactionEntity> recipientTransactions = new ArrayList<>();

	@JsonView(Views.Client.class)
	@JsonBackReference
	@OneToMany(mappedBy = "accountSender", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	private List<TransactionEntity> senderTransactions = new ArrayList<>();

	@Version
	private Integer version;

	public AccountEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/*
	 * public BankEntity getBank() { return bank; }
	 * 
	 * public void setBank(BankEntity bank) { this.bank = bank; }
	 */

	public ClientEntity getClient() {
		return client;
	}

	public void setClient(ClientEntity client) {
		this.client = client;
	}

	public List<TransactionEntity> getRecipientTransactions() {
		return recipientTransactions;
	}

	public void setRecipientTransactions(List<TransactionEntity> recipientTransactions) {
		this.recipientTransactions = recipientTransactions;
	}

	public List<TransactionEntity> getSenderTransactions() {
		return senderTransactions;
	}

	public void setSenderTransactions(List<TransactionEntity> senderTransactions) {
		this.senderTransactions = senderTransactions;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
