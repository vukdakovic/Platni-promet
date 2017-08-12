package com.iktpreobuka.platni_promet_2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.platni_promet_2.entities.AddressEntity;
import com.iktpreobuka.platni_promet_2.repositories.AddressRepository;

@RestController
@RequestMapping(path = "/api/v1/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<AddressEntity> getAllAddresses() {
		return addressRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public AddressEntity getAddressById(@PathVariable Integer id) {
		AddressEntity address = addressRepository.findOne(id);
		return address;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/by-city")
	public List<AddressEntity> getAddressesByCity(@RequestParam String city) {
		List<AddressEntity> addresses = new ArrayList<>();
		Iterable<AddressEntity> allAddresses = addressRepository.findAll();
		for (AddressEntity addressEntity : allAddresses) {
			if (addressEntity.getCity().equalsIgnoreCase(city)) {
				addresses.add(addressEntity);
			}
		}
		return addresses;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/by-country")
	public List<AddressEntity> getAddressesByCountry(@RequestParam String country) {
		List<AddressEntity> addresses = new ArrayList<>();
		Iterable<AddressEntity> allAddresses = addressRepository.findAll();
		for (AddressEntity addressEntity : allAddresses) {
			if (addressEntity.getCountry().equalsIgnoreCase(country)) {
				addresses.add(addressEntity);
			}
		}
		return addresses;
	}

	/*@RequestMapping(method = RequestMethod.POST)
	public AddressEntity addNewAddress(@RequestParam String street, @RequestParam String city,
			@RequestParam String country) {
		AddressEntity address = new AddressEntity();
		address.setStreet(street);
		address.setCity(city);
		address.setCountry(country);
		addressRepository.save(address);
		return address;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public AddressEntity modifyAddress(@PathVariable Integer id, @RequestParam String street, @RequestParam String city,
			@RequestParam String country) {
		AddressEntity address = addressRepository.findOne(id);
		if (street != "") {
			address.setStreet(street);
		} else {
			street = address.getStreet();
			address.setStreet(street);
		}
		if (city != "") {
			address.setCity(city);
		} else {
			city = address.getCity();
			address.setCity(city);
		}
		if (country != "") {
			address.setCountry(country);
		} else {
			country = address.getCountry();
			address.setCountry(country);
		}
		addressRepository.save(address);
		return address;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public AddressEntity deleteAddressById(@PathVariable Integer id) {
		AddressEntity address = addressRepository.findOne(id);
		addressRepository.delete(id);
		return address;
	}*/
	
	
	//NE ZELIMO DA MOZEMO DA DODAJEMO, MENJAMO ILI BRISEMO SAME ADRESE!


}
