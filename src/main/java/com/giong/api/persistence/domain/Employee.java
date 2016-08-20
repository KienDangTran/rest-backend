package com.giong.api.persistence.domain;

import com.giong.api.constant.MasterDataStatus;
import com.giong.api.persistence.AbstractEntity;
import com.giong.api.persistence.oauth.User;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the MT_EMPLOYEE database table.
 */
@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee extends AbstractEntity {
	@Id
	@Column(name = "employee_code")
	private String employeeCode;

	@Column(name = "employee_name")
	private String employeeName;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "sex")
	private String sex;

	@Column(name = "email")
	private String email;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "status")
	private String status = MasterDataStatus.ACTIVE;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public Object getPk() {
		return this.employeeCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}