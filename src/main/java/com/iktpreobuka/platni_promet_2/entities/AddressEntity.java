package com.iktpreobuka.platni_promet_2.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.platni_promet_2.security.Views;

@Entity
@Table(name = "Address")
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Admin.class)
	private Integer id;
	
	@JsonView(Views.Client.class)
	private String street;
	
	@JsonView(Views.Client.class)
	private String city;
	
	@JsonView(Views.Client.class)
	private String country;
	
	@JsonBackReference
	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private List<ClientEntity> client = new ArrayList<>();
	
	@JsonBackReference
	@OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	private BankEntity bank;
	
	@Version
	private Integer version;

	public AddressEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public BankEntity getBank() {
		return bank;
	}

	public void setBank(BankEntity bank) {
		this.bank = bank;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
