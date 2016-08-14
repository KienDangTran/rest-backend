package com.giong.persistence.mt;

import com.giong.constant.MasterDataStatus;
import com.giong.persistence.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "MT_ID_SCHEME")
@NamedQuery(name = "MtIdScheme.findAll", query = "SELECT m FROM MtIdScheme m")
@Data
public class MtIdScheme extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "seq_no")
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

	/* CONTRUCTORS */
	public MtIdScheme() {
	}

	@Override
	public Object getPk() {
		return this.seq_no;
	}
}
