package com.zqw.common.model;

import com.zqw.common.intf.CheckItemModel;

public class DefaultCheckItem implements CheckItemModel {

	private String  keyValue;
	private boolean isChecked;
	private Object  item;
	private boolean canEdit = true;

	public DefaultCheckItem(Object item) {
		this.item = item;
	}
	
	public DefaultCheckItem(Object item, boolean isChecked) {
		this(item);
		this.isChecked = isChecked;
	}
	
	public DefaultCheckItem(String key, Object dispObj) {
		this.item = dispObj;
		this.keyValue = key;
	}
	
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	
	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public Object getItem() {
		return item;
	}

	@Override
	public void setItem(Object item) {
		this.item = item;
	}

	@Override
	public String getDispValue() {
		return item.toString();
	}

	@Override
	public String getKeyValue() {
		return keyValue;
	}

	@Override
	public String toString() {
		return item.toString();
	}

	@Override
	public boolean isEditable() {
		return canEdit;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DefaultCheckItem) {
			return this.keyValue.equals(((DefaultCheckItem) obj).getKeyValue());	
		} else {
			return false;
		}
	}

}
