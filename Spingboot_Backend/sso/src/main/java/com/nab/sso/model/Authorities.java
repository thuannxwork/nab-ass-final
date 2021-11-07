package com.nab.sso.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Authorities implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005072159059903199L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHORITY_ID")
	private int authorityId;
	private String username;
	private String authority;

	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
	private User user;

	@Override
	public String toString() {
		return "Authorities [authorityId=" + authorityId + ", username=" + username + ", authority=" + authority + "]";
	}

}
