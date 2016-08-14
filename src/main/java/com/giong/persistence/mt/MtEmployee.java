package com.giong.persistence.mt;

import com.giong.constant.MasterDataStatus;
import com.giong.persistence.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the MT_EMPLOYEE database table.
 */
@Entity
@Table(name = "MT_EMPLOYEE")
@NamedQuery(name = "MtEmployee.findAll", query = "SELECT m FROM MtEmployee m")
@Data
public class MtEmployee extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "employee_code")
	private String employeeCode;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "sex")
	private String sex;

	@Column(name = "email")
	private String email;

	@Column(name = "employee_name")
	private String employeeName;

	@Column(name = "phone_no")
	private String phoneNo;

	@Column(name = "status")
	private String status = MasterDataStatus.ACTIVE;

	public MtEmployee() {
	}

	@Override
	public Object getPk() {
		return this.employeeCode;
	}

}