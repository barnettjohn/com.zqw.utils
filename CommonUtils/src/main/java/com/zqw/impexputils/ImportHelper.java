package com.zqw.impexputils;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.zqw.commonClient.ShowMsgBox;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportHelper {
	private static  int titleRow = 0 ;//记录EXCEL中标题行号
	private static int sheetRow =0 ;//记录需要导入第几个页签的数据
	private static Component comp_parent;
	public static boolean is_Cancel =false;
	private static Preferences preferences;
	public static final String KEY_DEFAULT_DIR = "default";

	/**
	 *文件选择 
	 * @param parent
	 * @return  文件全路径
	 */
	public static String openFile(Component parent){
		comp_parent = parent;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO 自动生成的方法存根
				return "Microsoft Excel";
			}

			@Override
			public boolean accept(File arg0) {
				// TODO 自动生成的方法存根
				if(arg0.isDirectory()){
					return true;
				}

				return arg0.getName().toLowerCase().endsWith(".xls")
						|| arg0.getName().toLowerCase().endsWith(".xlsx");
			}
		});
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 用户选择文件目录偏好
		String preferencesDir = getPreferences().get(KEY_DEFAULT_DIR, System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(new File(preferencesDir));
		int result = fileChooser.showOpenDialog(parent);
		if(result==JFileChooser.CANCEL_OPTION){
			is_Cancel = true;
			return null;
		}
		is_Cancel = false;
		File filename = fileChooser.getSelectedFile();
		if(filename == null || filename.length()==0){
			JOptionPane.showMessageDialog(parent, "选择需要导入的EXCEL文件", "选择需要导入的EXCEL文件", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		getPreferences().put(KEY_DEFAULT_DIR, filename.getParent());
		return filename.getAbsolutePath();
	}
	/**
	 *文件多选 
	 * @param parent
	 * @return  所选择的文件
	 */
	public static File[] openFileForMulti(Component parent){
		comp_parent = parent;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO 自动生成的方法存根
				return "Microsoft Excel";
			}

			@Override
			public boolean accept(File arg0) {
				// TODO 自动生成的方法存根
				if(arg0.isDirectory()){
					return true;
				}

				return arg0.getName().toLowerCase().endsWith(".xls")
						|| arg0.getName().toLowerCase().endsWith(".xlsx");
			}
		});
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(true);
		// 用户选择文件目录偏好
		String preferencesDir = getPreferences().get(KEY_DEFAULT_DIR, System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(new File(preferencesDir));
		int result = fileChooser.showOpenDialog(parent);
		if(result==JFileChooser.CANCEL_OPTION){
			is_Cancel = true;
			return null;
		}
		is_Cancel= false;
		File[] files = fileChooser.getSelectedFiles();
		if(files == null || files.length==0){
			JOptionPane.showMessageDialog(parent, "选择需要导入的EXCEL文件", "选择需要导入的EXCEL文件", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		getPreferences().put(KEY_DEFAULT_DIR, files[0].getParent());
		return files;
	}
	/**
	 * 根据传入的excel文件全路径名称，转换为List集合，集合内每条数据为一个Map，每个Map的key为列名，值为：具体值
	 * @param file 文件全路径名
	 * @return 导入数据的list集合  map<列名,值>
	 * @throws IOException
	 */
	public static List<Map<String,Object>> excelToList(File file) throws Exception{
		List<Map<String, Object>> rtnList = null;
		if(file==null ){
			return null;
		}else{
			if(file.getName().toLowerCase().endsWith(".xls")){
				rtnList = HSSFReadXls(file);
			}else{
				rtnList = XSSFReadXlsx(file);
			}
		}

		return rtnList;
	}
	/**
	 * 根据传入的excel文件全路径名称，转换为List集合，集合内每条数据为一个Map，每个Map的key为列名，值为：具体值
	 * @param file 文件全路径名
	 * @return 导入数据的list集合  map<列名,值>
	 * @throws IOException
	 */
	public static HashMap<String, List<Map<String,Object>>> excelToMapList(File file) throws Exception {
		HashMap<String, List<Map<String,Object>>>  rsMap = null;
		if(file==null){
			return null;
		}else{
			if(file.getName().toLowerCase().endsWith(".xls")){
				rsMap = HSSFReadXlsForMultiSheet(file);
			}else{
				rsMap = XSSFReadXlsxForMultiSheet(file);
			}
		}

		return rsMap;
	}
	/**
	 * 解析并读取EXCEL2007以上版本-单sheet
	 * @param excelFile
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static List<Map<String, Object>> XSSFReadXlsx(File excelFile) throws Exception{
		List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
		FileInputStream fis =  null;
		try {
			fis = new FileInputStream(excelFile);
			XSSFWorkbook hwb = new XSSFWorkbook(fis);
			FormulaEvaluator evaluator = hwb.getCreationHelper().createFormulaEvaluator();
			//获取需要导入的页签
			XSSFSheet sheet = hwb.getSheetAt(sheetRow);
			//获取表头字段名字
			String[] excelColNames = getExcelNamesForX(sheet.getRow(titleRow));
			//从标题行下一行开始循环每一行，取每一列的数据放到map中，在添加到list中
			for(int i=titleRow + 1;i<=sheet.getLastRowNum();i++){
				Map<String, Object> valuesMap = new HashMap<String, Object>();
				Object[] valueObjs = null;
				XSSFRow curRow = sheet.getRow(i);//获取当前行对象
				valueObjs = getCellValueFromRowForX(curRow,evaluator);
				//将valueOjb和excelColNames对应放到map中
				valuesMap = mergeColAndValue(excelColNames,valueObjs);
				rtnList.add(valuesMap);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			throw new Exception(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					//Logger.error(e);
					throw new Exception(e.getMessage());
				}
			}
		}
		return rtnList;
	}
	/**
	 * 解析并读取EXCEL2007以上版本-多sheet
	 * @param excelFile
	 * @return  key页签名称  value导入的数据map
	 * @throws Exception
	 * @throws IOException
	 */
	public static HashMap<String,List<Map<String, Object>>> XSSFReadXlsxForMultiSheet(File excelFile) throws Exception{

		HashMap<String,List<Map<String, Object>>>  rsMap = new HashMap<String, List<Map<String,Object>>>();
		FileInputStream fis =  null;
		try {
			fis = new FileInputStream(excelFile);
			XSSFWorkbook hwb = new XSSFWorkbook(fis);
			FormulaEvaluator evaluator = hwb.getCreationHelper().createFormulaEvaluator();
			int sheets = hwb.getNumberOfSheets();
			for(int j=0;j<sheets;j++){
				List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
				//获取需要导入的页签
				XSSFSheet sheet = hwb.getSheetAt(j);
				//获取表头字段名字
				String[] excelColNames = getExcelNamesForX(sheet.getRow(titleRow));
				//从标题行下一行开始循环每一行，取每一列的数据放到map中，在添加到list中
				for(int i=titleRow + 1;i<=sheet.getLastRowNum();i++){
					Map<String, Object> valuesMap = new HashMap<String, Object>();
					Object[] valueObjs = null;
					XSSFRow curRow = sheet.getRow(i);//获取当前行对象
					valueObjs = getCellValueFromRowForX(curRow,evaluator);
					//将valueOjb和excelColNames对应放到map中
					valuesMap = mergeColAndValue(excelColNames,valueObjs);
					rtnList.add(valuesMap);
				}
				rsMap.put(sheet.getSheetName(), rtnList);
			}

		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			throw new Exception(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					//Logger.error(e);
					throw new Exception(e.getMessage());
				}
			}
		}
		return rsMap;
	}
	/**
	 * 解析并读取EXCEL2003-单sheet
	 * @param excelFile
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static List<Map<String, Object>> HSSFReadXls(File excelFile) throws IOException{
		List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
		FileInputStream fis =  null;
		try {
			fis = new FileInputStream(excelFile);
			HSSFWorkbook hwb = new HSSFWorkbook(fis);
			FormulaEvaluator evaluator = hwb.getCreationHelper().createFormulaEvaluator();
			//获取需要导入的页签
			HSSFSheet sheet = hwb.getSheetAt(sheetRow);
			//获取表头字段名字
			String[] excelColNames = getExcelNamesForH(sheet.getRow(titleRow));
			//从标题行下一行开始循环每一行，取每一列的数据放到map中，在添加到list中
			for(int i=titleRow + 1;i<=sheet.getLastRowNum();i++){
				Map<String, Object> valuesMap = new HashMap<String, Object>();
				Object[] valueObjs = null;
				HSSFRow curRow = sheet.getRow(i);//获取当前行对象
				valueObjs = getCellValueFromRowForH(curRow,evaluator);
				//将valueOjb和excelColNames对应放到map中
				valuesMap = mergeColAndValue(excelColNames,valueObjs);
				rtnList.add(valuesMap);
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			throw new IOException(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					throw new IOException(e.getMessage());
				}
			}
		}
		return rtnList;
	}
	/**
	 * 解析并读取EXCEL2003-多sheet
	 * @param excelFile
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static HashMap<String,List<Map<String, Object>>> HSSFReadXlsForMultiSheet(File excelFile) throws Exception{

		HashMap<String,List<Map<String, Object>>> rsMap = new HashMap<String, List<Map<String,Object>>>();
		FileInputStream fis =  null;
		try {
			fis = new FileInputStream(excelFile);
			HSSFWorkbook hwb = new HSSFWorkbook(fis);
			FormulaEvaluator evaluator = hwb.getCreationHelper().createFormulaEvaluator();
			int sheets = hwb.getNumberOfSheets();
			for(int j=0;j<sheets;j++){
				List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
				//获取需要导入的页签
				HSSFSheet sheet = hwb.getSheetAt(j);
				//获取表头字段名字
				String[] excelColNames = getExcelNamesForH(sheet.getRow(titleRow));
				//从标题行下一行开始循环每一行，取每一列的数据放到map中，在添加到list中
				for(int i=titleRow + 1;i<=sheet.getLastRowNum();i++){
					Map<String, Object> valuesMap = new HashMap<String, Object>();
					Object[] valueObjs = null;
					HSSFRow curRow = sheet.getRow(i);//获取当前行对象
					valueObjs = getCellValueFromRowForH(curRow,evaluator);
					//将valueOjb和excelColNames对应放到map中
					valuesMap = mergeColAndValue(excelColNames,valueObjs);
					rtnList.add(valuesMap);
				}
				rsMap.put(sheet.getSheetName(), rtnList);
			}

		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			throw new Exception(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					throw new Exception(e.getMessage());
				}
			}
		}
		return rsMap;
	}
	/**
	 * 将列和值转换成map使用
	 * @param cols
	 * @param values
	 * @return map<列名，值>
	 */
	public static Map<String, Object> mergeColAndValue(String[] cols,Object[] values) throws IOException{
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		if(cols==null || cols.length==0 || values==null || values.length==0){
			return null;
		}else{
//			if(cols.length!=values.length){
//				throw new Exception("导入文件列与值个数不匹配！");
//			}
			for(int i=0;i<cols.length;i++){
				if(cols[i]!=null && cols[i].length()>0 && !cols[i].equals("NONE_IMP")){
					if(i >= values.length ){
						rtnMap.put(cols[i], null);
					}else{
						if (values[i] instanceof Double || values[i] instanceof Float) {
							rtnMap.put(cols[i], new BigDecimal((Double)values[i]));
						} else {
							rtnMap.put(cols[i], values[i]);
						}

					}

				}
			}
		}
		return rtnMap;
	}
	/**
	 * 根据传入的行对象，返回当前行每个单元格的值 Excel2003版本
	 * @param row
	 * @return map<单元格>
	 */
	public static Object[] getCellValueFromRowForH(HSSFRow row,FormulaEvaluator fel){
		Object[] rtnObjs = new Object[row.getLastCellNum()];
		SimpleDateFormat sdf = null;
		int cols = row.getLastCellNum();
		for(int i=0;i<cols;i++){
			if(row.getCell(i)!=null){
				if(row.getCell(i).getCellType()==XSSFCell.CELL_TYPE_NUMERIC
						|| row.getCell(i).getCellType()==XSSFCell.CELL_TYPE_FORMULA){
					if(HSSFDateUtil.isCellDateFormatted(row.getCell(i))){//处理时间和日期
						if (row.getCell(i).getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
							sdf = new SimpleDateFormat("HH:mm");
						} else {// 日期
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						Date date = row.getCell(i).getDateCellValue();
						rtnObjs[i] = sdf.format(date);

					}else if(row.getCell(i).getCellType()== HSSFCell.CELL_TYPE_FORMULA){
						rtnObjs[i] = fel.evaluateFormulaCell(row.getCell(i));
					}else{
						//纯数值的处理
						rtnObjs[i] = row.getCell(i).getNumericCellValue();
					}
				}else{
					rtnObjs[i] = row.getCell(i).getStringCellValue();//均按照字符进行处理
				}
			}else{
				rtnObjs[i]=null;
			}
		}
		return rtnObjs;
	}
	/**
	 * 根据传入的行对象，返回当前行每个单元格的值EXCEL2007以上版本
	 * @param row
	 * @return map<单元格>
	 */
	public static Object[] getCellValueFromRowForX(XSSFRow row,FormulaEvaluator fel){
		Object[] rtnObjs = new Object[row.getLastCellNum()];
		int cols = row.getLastCellNum();
		SimpleDateFormat sdf = null;
		for(int i=0;i<cols;i++){
			if(row.getCell(i)!=null){
				if(row.getCell(i).getCellType()==XSSFCell.CELL_TYPE_NUMERIC
						|| row.getCell(i).getCellType()==XSSFCell.CELL_TYPE_FORMULA){
					if(HSSFDateUtil.isCellDateFormatted(row.getCell(i))){//处理时间和日期
						if (row.getCell(i).getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
							sdf = new SimpleDateFormat("HH:mm");
						} else {// 日期
							sdf = new SimpleDateFormat("yyyy-MM-dd");
						}
						Date date = row.getCell(i).getDateCellValue();
						rtnObjs[i] = sdf.format(date);

					}else if(row.getCell(i).getCellType()== HSSFCell.CELL_TYPE_FORMULA){
						rtnObjs[i] = fel.evaluateFormulaCell(row.getCell(i));
					}else{
						//纯数值的处理
						rtnObjs[i] = row.getCell(i).getNumericCellValue();
					}
				}else{
					rtnObjs[i] = row.getCell(i).getStringCellValue();//均按照字符进行处理
				}
			}else{
				rtnObjs[i]=null;
			}
		}
		return rtnObjs;
	}
	/**
	 * 从EXCEL2007以上中抽取列标题（只取第一行作为列标题）
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public static String[] getExcelNamesForX(XSSFRow row) throws Exception{
		if(row==null || row.getLastCellNum()<0){
			throw new Exception("未获取EXCEL中数据，请确定EXCEL中是否存在数据或者关闭EXCEL后重新尝试!");
		}
		String[] colsNames = new String[row.getLastCellNum()];
		for(int i=0;i<row.getLastCellNum();i++){
			XSSFCell cell = row.getCell(i);
			if(cell==null || cell.getCellType()!=HSSFCell.CELL_TYPE_STRING
					|| cell.getStringCellValue()==null || cell.getStringCellValue().length()==0){
				//如果列标题为空或者非字符类型，不导入此列
				colsNames[i] = "NONE_IMP";
			}else{
				String cellvalue = cell.getStringCellValue().trim();
				colsNames[i] = cellvalue;
			}

		}
		return colsNames;
	}
	/**
	 * 从EXCEL2003以上中抽取列标题（只取第一行作为列标题）
	 * @param row
	 * @return
	 * @throws Exception
	 */
	public static String[] getExcelNamesForH(HSSFRow row) throws IOException{
		if(row==null || row.getLastCellNum()<0){
			ShowMsgBox.show(ShowMsgBox.Warning, "未获取EXCEL中数据，请关闭EXCEL后重新尝试!");
		}
		String[] colsNames = new String[row.getLastCellNum()];
		for(int i=0;i<row.getLastCellNum();i++){
			HSSFCell cell = row.getCell(i);
			if(cell==null || cell.getCellType()!=HSSFCell.CELL_TYPE_STRING
					|| cell.getStringCellValue()==null || cell.getStringCellValue().length()==0){
				//如果列标题为空或者非字符类型，不导入此列
				colsNames[i] = "NONE_IMP";
			}else{
				String cellvalue = cell.getStringCellValue().trim();
				colsNames[i] = cellvalue;
			}

		}
		return colsNames;
	}

	public static int getTitleRow() {
		return titleRow;
	}
	public static void setTitleRow(int titleRow) {
		ImportHelper.titleRow = titleRow;
	}
	public static int getSheetRow() {
		return sheetRow;
	}
	public static void setSheetRow(int sheetRow) {
		ImportHelper.sheetRow = sheetRow;
	}

	private static Preferences getPreferences() {
		if (preferences == null) {
			preferences = Preferences.userNodeForPackage(ImportHelper.class);
		}
		return preferences;
	}

}

