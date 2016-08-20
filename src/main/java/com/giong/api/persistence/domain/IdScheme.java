package com.giong.api.persistence.domain;

import com.giong.api.constant.MasterDataStatus;
import com.giong.api.persistence.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "id_scheme")
@NamedQuery(name = "IdScheme.findAll", query = "SELECT i FROM IdScheme i")
public class IdScheme extends AbstractEntity {
	@Id
	@Column(name = "seq_no")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int seq_no;

	@Column(name = "scheme_name")
	private String schemeName;

	@Column(name = "prefix")
	private String prefix;

	@Column(name = "suffix")
	private String suffix;

	@Column(name = "length")
	private int length = 10;

	@Column(name = "filled_char")
	private String filledChar = "0";

	@Column(name = "last_gen_no")
	private long lastGenNo = 0;

	@Column(name = "status")
	private String status = MasterDataStatus.ACTIVE;

	@Override
	public Object getPk() {
		return this.seq_no;
	}

	public int getSeq_no() {
		return seq_no;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getFilledChar() {
		return filledChar;
	}

	public void setFilledChar(String filledChar) {
		this.filledChar = filledChar;
	}

	public long getLastGenNo() {
		return lastGenNo;
	}

	public void setLastGenNo(long lastGenNo) {
		this.lastGenNo = lastGenNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}