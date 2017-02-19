package com.giong.api.domain;

import com.giong.api.constant.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends AbstractEntity {
	@Id
	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_desc")
	private String roleDesc;

	@Column(name = "status")
	private String status = Status.ACTIVE;

	@ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(name = "role_authority", joinColumns = {
			@JoinColumn(name = "role_code")
	}, inverseJoinColumns = {@JoinColumn(name = "authority_code")})
	private List<Authority> authorities = new ArrayList<>();

	@ManyToMany(mappedBy = "roles")
	private List<User> users = new ArrayList<>();

	@Override
	public Object getPk() {
		return this.roleCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}