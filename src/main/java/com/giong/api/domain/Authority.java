package com.giong.api.domain;

import com.giong.api.constant.Status;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authority")
@NamedQuery(name = "Authority.findAll", query = "SELECT a FROM Authority a")
public class Authority extends AbstractEntity implements GrantedAuthority, Comparable<GrantedAuthority> {
	@Id
	@Column(name = "authority_code")
	private String authorityCode;

	@Column(name = "authority_desc")
	private String authorityDesc;

	@Column(name = "status")
	private String status = Status.ACTIVE;

	@ManyToMany(mappedBy = "authorities", cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();

	public Authority() {
	}

	public Authority(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	@Override
	public Object getPk() {
		return this.authorityCode;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public int compareTo(GrantedAuthority o) {
		// Neither should ever be null as each entry is checked before adding it to the set.
		// If the authority is null, it is a custom authority and should precede others.
		if (o.getAuthority() == null) { return -1; }

		if (this.getAuthority() == null) { return 1; }

		return this.getAuthority().compareTo(o.getAuthority());
	}

	@Override
	public String getAuthority() {
		return this.authorityCode;
	}
}
