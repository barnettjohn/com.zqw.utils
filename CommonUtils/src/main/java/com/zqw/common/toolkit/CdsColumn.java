package com.zqw.common.toolkit;

import java.io.Serializable;

/**
 * 
 * cds列
 * 
 * @author zhangpana
 * 
 */
public class CdsColumn implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 列名
	 */
	private String columnName;
	/**
	 * 类型
	 */
	private int columnType;
	/**
	 * 长度
	 */
	private int columnLength;
	/**
	 * 精度
	 */
	private int columnScale;

	public CdsColumn() {
		super();
	}

	public CdsColumn(String columnName, int columnType, int columnLength, int columnScale) {
		super();
		this.columnName = columnName;
		this.columnType = columnType;
		this.columnLength = columnLength;
		this.columnScale = columnScale;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnType() {
		return columnType;
	}

	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public int getColumnScale() {
		return columnScale;
	}

	public void setColumnScale(int columnScale) {
		this.columnScale = columnScale;
	}

}
