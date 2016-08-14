package com.giong.persistence.mt;

import com.giong.persistence.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * The persistent class for the MT_USER database table.
 */
@Entity
@Table(name = "MT_USER")
@NamedQuery(name = "MtUser.findAll", query = "SELECT m FROM MtUser m")
public class MtUser extends AbstractEntity implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "password")
	private String password;

	@Column(name = "theme")
	private String theme;

	@Column(name = "username")
	private String username;

	//uni-directional one-to-one association to MtEmployee
	@OneToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "employee_code")
	private MtEmployee mtEmployee;

	//bi-directional many-to-many association to MtRole
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "MT_USER_ROLE", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
		@JoinColumn(name = "role_code") })
	private List<MtRole> mtRoles;

	public MtUser() {
	}

	@Override
	public Object getPk() {
		return this.getUserId();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTheme() {
		return this.theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public MtEmployee getMtEmployee() {
		return this.mtEmployee;
	}

	public void setMtEmployee(MtEmployee mtEmployee) {
		this.mtEmployee = mtEmployee;
	}

	public List<MtRole> getMtRoles() {
		return this.mtRoles;
	}

	public void setMtRoles(List<MtRole> mtRoles) {
		this.mtRoles = mtRoles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.mtRoles == null || this.mtRoles.isEmpty())
			return Collections.emptySet();
		final TreeSet<MtPermission> authorities = new TreeSet<MtPermission>();
		for (final MtRole role : this.mtRoles)
			authorities.addAll(role.getMtPermissions());

		return Collections.unmodifiableSet(authorities);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");
		sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
		sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
		sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

		if (!this.getAuthorities().isEmpty()) {
			sb.append("Granted Permissions: ");

			boolean first = true;
			for (final GrantedAuthority auth : this.getAuthorities()) {
				if (!first)
					sb.append(",");
				first = false;

				sb.append(auth.getAuthority());
			}
		} else
			sb.append("Not granted any permissions");

		return sb.toString();
	}

	/**
	 * Returns {@code true} if the supplied object is a {@code MtUser} instance with the same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username, representing the same principal.
	 */
	@Override
	public boolean equals(Object rhs) {
		if (rhs instanceof MtUser)
			return this.username.equals(((MtUser) rhs).username);
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

}