package com.zqw.common.toolkit;

import java.math.BigDecimal;

/**
 * <p>Title: 筛选条件</p>
 * <p>Description:用于SortedCds、Cds筛选数据。如果是简洁筛选条件，多个筛选条件之间是and的关系</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 用友政务</p>
 * @author chenhh
 * @version 6.1
 */
public class FilterClause implements Comparable{
	//简洁条件
	private String _strFieldName=null;      //字段名称
	private String _strOperaor=null;        //比较符：支持  =、>、>=、<、<=、<>、*=*、=*、*=、between 
	private Object _objFilterValue=null;    //筛选条件：单值为String,多值为String[]，为空，表示本次不筛选或者取全部数据

	//复杂条件
	private int _iLeftBracketCount=0;       //左括号数量
	private int _iRightBracketCount=0;      //右括号数量
	private boolean _bAnd=true;             //true表示and ,false表示or

	//
	private boolean _bComplex=false;        //是否复杂筛选条件。简洁筛选条件仅处理：字段、比较符、比较值
	
	private FilterClause(){
	}

	//简洁条件
	public FilterClause(String strFieldName,String strOperaor, Object objFilterValue) throws Exception{
		if (PubFunc.isEmptyString(strFieldName) || PubFunc.isEmptyString(strFieldName.trim())
				|| PubFunc.isEmptyString(strOperaor) || PubFunc.isEmptyString(strOperaor.trim())){
			throw new Exception("筛选参数名称、比较符不能为空");
		}
		if (false == (objFilterValue instanceof String || objFilterValue instanceof String[] 
				|| objFilterValue instanceof BigDecimal || objFilterValue instanceof BigDecimal[]
				|| objFilterValue instanceof Integer || objFilterValue instanceof Integer[])){
			throw new Exception("筛选条件值只能是String或者String[]类型");
		}
		
		_strFieldName=strFieldName.trim().toUpperCase();
		_strOperaor=strOperaor.trim().toUpperCase();
		_objFilterValue=objFilterValue;
	}

	//复杂条件，暂时不支持
	private FilterClause(String strLeftBracket,String strFieldName,String strOperaor, Object objFilterValue
			,String strRightBracket,String strAnd) throws Exception{
		if (PubFunc.isEmptyString(strFieldName) || PubFunc.isEmptyString(strFieldName.trim())
				|| PubFunc.isEmptyString(strOperaor) || PubFunc.isEmptyString(strOperaor.trim())){
			throw new Exception("筛选参数名称、比较符不能为空");
		}
		if (false == (objFilterValue instanceof String || objFilterValue instanceof String[])){
			throw new Exception("筛选条件值只能是String或者String[]类型");
		}
		
		_strFieldName=strFieldName.trim().toUpperCase();
		_strOperaor=strOperaor.trim().toUpperCase();
		_objFilterValue=objFilterValue;
		
		if (false == (PubFunc.isEmptyString(strLeftBracket) || PubFunc.isEmptyString(strLeftBracket.trim()))){
			_iLeftBracketCount=strLeftBracket.trim().length();
		}
		if (false == (PubFunc.isEmptyString(strRightBracket) || PubFunc.isEmptyString(strRightBracket.trim()))){
			_iRightBracketCount=strRightBracket.trim().length();
		}
		if (_iRightBracketCount >= _iLeftBracketCount){
			_iRightBracketCount=_iRightBracketCount-_iLeftBracketCount;
			_iLeftBracketCount=0;
		}else{
			_iLeftBracketCount=_iLeftBracketCount-_iRightBracketCount;
			_iRightBracketCount=0;
		}

		if (PubFunc.isEqualsString(strAnd, "1")){
			_bAnd=false;
		}
		
		_bComplex=true;
	}

	public boolean isComplex() {
		return _bComplex;
	}

	public String getFieldName() {
		return _strFieldName;
	}
	
	public String getOperaor() {
		return _strOperaor;
	}

	public Object getFilterValue() {
		return _objFilterValue;
	}

	public int getLeftBracketCount() {
		return _iLeftBracketCount;
	}

	public int getRightBracketCount() {
		return _iRightBracketCount;
	}

	public boolean isAnd() {
		return _bAnd;
	}

	public int compareTo(Object o) {
		if (null == o){
			return -1;
		}

		//检查字段、比较符等
		FilterClause newFilter=(FilterClause)o;
		if (false == PubFunc.isEqualsString(this._strFieldName, newFilter._strFieldName)
				|| false == PubFunc.isEqualsString(this._strOperaor, newFilter._strOperaor)
				|| this._bAnd != newFilter._bAnd
				|| this._iLeftBracketCount != newFilter._iLeftBracketCount
				|| this._iRightBracketCount != newFilter._iRightBracketCount
				|| this._bComplex != newFilter._bComplex){
			return -1;
		}
		
		//检查筛选值
		if (null == this._objFilterValue){
			if (null == newFilter._objFilterValue){
				return 0;
			}else{
				return -1;
			}
		}
		if (null == newFilter._objFilterValue){
			if (null == this._objFilterValue){
				return 0;
			}else{
				return -1;
			}
		}
		if (this._objFilterValue instanceof String){
			if (newFilter._objFilterValue instanceof String[]){
				return -1;
			}else{
				if (PubFunc.isEqualsString((String)this._objFilterValue, (String)newFilter._objFilterValue)){
					return 0;
				}else{
					return -1;
				}
			}
		}

		//比较数组
		String[] me=(String[])this._objFilterValue;
		String[] other=(String[])newFilter._objFilterValue;
		
		if (me.length != other.length){
			return -1;
		}
		
		for (int i=0;i<me.length;i++){
			if (false == PubFunc.isEqualsString(me[i], other[i])){
				return -1;
			}
		}
		
		return 0;
	}
}
