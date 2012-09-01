package org.framework.entity;

import java.io.Serializable;

import org.zyweistartframework.persistence.annotation.Id;
import org.zyweistartframework.persistence.annotation.MappedSuperclass;

@MappedSuperclass
public abstract class Root implements Serializable,Cloneable{

	private static final long serialVersionUID = 43948582629964283L;

	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Root other = (Root) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}