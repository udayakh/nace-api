package com.deutsche.naceservices.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "nace_orders")
public class NaceOrder {

	@Id
	@Column(name = "id")
	private Integer order;

	@Column(name = "level")
	private Integer level;

	@Column(name = "code")
	private String code;

	@Column(name = "parent")
	private String parent;

	@Column(name = "description")
	private String description;

	@Column(name = "item_inclusions")
	private String item_inclusions;

	@Column(name = "item_inclusions_extra")
	private String item_inclusions_extra;

	@Column(name = "rulings")
	private String rulings;

	@Column(name = "item_exclusions")
	private String item_exclusions;

	@Column(name = "isic_rev_ref")
	private String isic_rev_ref;

	public NaceOrder() {		
	}	

	public NaceOrder(Integer order, Integer level, String code, String parent, String description, String item_inclusions,
			String item_inclusions_extra, String rullings, String item_exclusions, String isic_rev_ref) {
		this.order = order;
		this.level = level;
		this.code = code;
		this.parent = parent;
		this.description = description;
		this.item_inclusions = item_inclusions;
		this.item_inclusions_extra = item_inclusions_extra;
		this.rulings = rullings;
		this.item_exclusions = item_exclusions;
		this.isic_rev_ref = isic_rev_ref;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItem_inclusions() {
		return item_inclusions;
	}

	public void setItem_inclusions(String item_inclusions) {
		this.item_inclusions = item_inclusions;
	}

	public String getItem_inclusions_extra() {
		return item_inclusions_extra;
	}

	public void setItem_inclusions_extra(String item_inclusions_extra) {
		this.item_inclusions_extra = item_inclusions_extra;
	}

	public String getRulings() {
		return rulings;
	}

	public void setRulings(String rulings) {
		this.rulings = rulings;
	}

	public String getItem_exclusions() {
		return item_exclusions;
	}

	public void setItem_exclusions(String item_exclusions) {
		this.item_exclusions = item_exclusions;
	}

	public String getIsic_rev_ref() {
		return isic_rev_ref;
	}

	public void setIsic_rev_ref(String isic_rev_ref) {
		this.isic_rev_ref = isic_rev_ref;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((isic_rev_ref == null) ? 0 : isic_rev_ref.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((order == null) ? 0 : order.hashCode());
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
		NaceOrder other = (NaceOrder) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (isic_rev_ref == null) {
			if (other.isic_rev_ref != null)
				return false;
		} else if (!isic_rev_ref.equals(other.isic_rev_ref))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		return true;
	}
}