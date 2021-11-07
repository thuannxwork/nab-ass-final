package com.nab.sso.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address implements Serializable {

	private static final long serialVersionUID = 4265352674204944987L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;
	private String city;
	private String state;
	private String country;
	private int zipcode;
	@Column(name = "phone_number")
	private String phoneNumber;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Override
	public String toString() {
		return "Address [id=" + id + ", address=" + address + ", city=" + city + ", state=" + state + ", country="
				+ country + ", zipcode=" + zipcode + ", phonenumber=" + phoneNumber + ", user=" + user + "]";
	}
}
