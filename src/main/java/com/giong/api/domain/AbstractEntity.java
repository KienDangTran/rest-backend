package com.giong.api.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Transient
	protected boolean selected;

	@Transient
	protected boolean persisted;

	@PostLoad
	protected void postLoad() {
		this.persisted = true;
	}

	@PrePersist
	protected void prePersist() {
		this.persisted = true;
	}

	@PreUpdate
	protected void preUpdate() {
		this.persisted = true;
	}

	@Id
	public abstract Object getPk();

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPersisted() {
		return persisted;
	}

	public void setPersisted(boolean persisted) {
		this.persisted = persisted;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		sb.append("[").append(this.getPk().toString()).append("]");
		return sb.toString();
	}

	/**
	 * AbstractPk
	 */
	public abstract class AbstractPk implements Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			Field f;
			final Object value = null;
			for (int i = 0; i < this.getClass().getFields().length; i++) {
				f = this.getClass().getFields()[i];
				sb.append(f.getName()).append("=");
				try {
					sb.append(f.get(value).toString());
				} catch (final Exception e) {
					sb.append("n/a");
				}
			}
			return sb.toString();
		}
	}
}
