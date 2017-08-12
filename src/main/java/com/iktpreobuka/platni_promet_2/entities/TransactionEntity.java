package com.iktpreobuka.platni_promet_2.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.platni_promet_2.security.Views;

@Entity
@Table(name = "Transaction")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Admin.class)
	private Long id;

	@JsonView(Views.Client.class)
	private Double amount;

	@JsonView(Views.Client.class)
	private String purpose;

	@JsonView(Views.Client.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date date;

	/*
	 * @JsonView(Views.Client.class)
	 * 
	 * @JsonManagedReference
	 * 
	 * @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "clientRecipient") private ClientEntity
	 * clientRecipient;
	 * 
	 * @JsonView(Views.Client.class)
	 * 
	 * @JsonManagedReference
	 * 
	 * @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "clientSender") private ClientEntity clientSender;
	 */

	@JsonView(Views.Client.class)
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "accountRecipient")
	private AccountEntity accountRecipient;

	@JsonView(Views.Client.class)
	@JsonManagedReference
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "accountSender")
	private AccountEntity accountSender;

	@Version
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * public ClientEntity getClientRecipient() { return clientRecipient; }
	 * 
	 * public void setClientRecipient(ClientEntity clientRecipient) {
	 * this.clientRecipient = clientRecipient; }
	 * 
	 * public ClientEntity getClientSender() { return clientSender; }
	 * 
	 * public void setClientSender(ClientEntity clientSender) {
	 * this.clientSender = clientSender; }
	 */

	public AccountEntity getAccountRecipient() {
		return accountRecipient;
	}

	public void setAccountRecipient(AccountEntity accountRecipient) {
		this.accountRecipient = accountRecipient;
	}

	public AccountEntity getAccountSender() {
		return accountSender;
	}

	public void setAccountSender(AccountEntity accountSender) {
		this.accountSender = accountSender;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public TransactionEntity() {
		super();
	}

}
