package com.nab.sso.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = -8850740904859933967L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String username;
	private String password;
	private String type;
	@Column(name = "is_enabled")
	private boolean isEnabled;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Authorities> roles;

	private int age;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Address address;

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", age=" + age + ", address=" + address + "]";
	}

	public User() {
		super();
	}

	public User(Long id, String email, String username, String password, int age, Address address) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.age = age;
		this.address = address;
	}
}
