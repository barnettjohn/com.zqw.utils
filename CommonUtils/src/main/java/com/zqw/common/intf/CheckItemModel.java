package com.zqw.common.intf;

public interface CheckItemModel {
	public boolean isChecked();
	
	public void setChecked(boolean isChecked);
	
	public Object getItem();
	
	public void setItem(Object item);
	
	public String getDispValue();
	
	public String getKeyValue();
	
	public boolean isEditable(); // 是否可编辑
}
