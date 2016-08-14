package com.giong.persistence.mt;

import com.giong.constant.MasterDataStatus;
import com.giong.persistence.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the MT_PERMISSION database table.
 */
@Entity
@Table(name = "MT_PERMISSION")
@NamedQuery(name = "MtPermission.findAll", query = "SELECT m FROM MtPermission m")
public class MtPermission extends AbstractEntity implements GrantedAuthority, Comparable<GrantedAuthority> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "permission_code")
	private String permissionCode;

	@Column(name = "permission_desc")
	private String permissionDesc;

	@Column(name = "status")
	private String status = MasterDataStatus.ACTIVE;

	//bi-directional many-to-many association to MtRole
	@ManyToMany(mappedBy = "mtPermissions", cascade = { CascadeType.REFRESH })
	private List<MtRole> mtRoles;

	public MtPermission() {
	}

	@Override
	public Object getPk() {
		return this.getPermissionCode();
	}

	public String getPermissionCode() {
		return this.permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionDesc() {
		return this.permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<MtRole> getMtRoles() {
		return this.mtRoles;
	}

	public void setMtRoles(List<MtRole> mtRoles) {
		this.mtRoles = mtRoles;
	}

	@Override
	public String getAuthority() {
		return this.permissionCode;
	}

	@Override
	public int compareTo(GrantedAuthority o) {
		// Neither should ever be null as each entry is checked before adding it to
		// the set.
		// If the authority is null, it is a custom authority and should precede
		// others.
		if (o.getAuthority() == null)
			return -1;

		if (this.getAuthority() == null)
			return 1;

		return this.getAuthority().compareTo(o.getAuthority());
	}

}