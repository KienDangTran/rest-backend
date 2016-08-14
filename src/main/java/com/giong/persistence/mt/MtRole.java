package com.giong.persistence.mt;

import com.giong.constant.MasterDataStatus;
import com.giong.persistence.AbstractEntity;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the MT_ROLE database table.
 */
@Entity
@Table(name = "MT_ROLE")
@NamedQuery(name = "MtRole.findAll", query = "SELECT m FROM MtRole m")
public class MtRole extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "role_code")
	private String roleCode;

	@Column(name = "role_desc")
	private String roleDesc;

	@Column(name = "status")
	private String status = MasterDataStatus.ACTIVE;

	//bi-directional many-to-many association to MtPermission
	@ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "MT_ROLE_PERMISSION_GRANTED", joinColumns = {
		@JoinColumn(name = "role_code") }, inverseJoinColumns = { @JoinColumn(name = "permission_code") })
	private List<MtPermission> mtPermissions;

	//bi-directional many-to-many association to MtUser
	@ManyToMany(mappedBy = "mtRoles")
	private List<MtUser> mtUsers;

	public MtRole() {
	}

	@Override
	public Object getPk() {
		return this.getRoleCode();
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MtPermission> getMtPermissions() {
		return this.mtPermissions;
	}

	public void setMtPermissions(List<MtPermission> mtPermissions) {
		this.mtPermissions = mtPermissions;
	}

	public List<MtUser> getMtUsers() {
		return this.mtUsers;
	}

	public void setMtUsers(List<MtUser> mtUsers) {
		this.mtUsers = mtUsers;
	}

}