package com.zqw.impexputils;

import com.zqw.commonClient.ShowMsgBox;
import java.awt.Container;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public abstract class AbstractExportTemplate {

	public static final String KEY_DEFAULT_DIR = "default";
	public static final String XLS_SUFFIX = ".xls";
	public static final String XLSX_SUFFIX = ".xlsx";
	private Preferences preferences;
	private JFileChooser chooser;
	protected Container parent;

	public AbstractExportTemplate() {
	}
	
	public AbstractExportTemplate(Container parent) {
		this.parent = parent;
	}

	
	protected static boolean isSuffixXLSRight(File file) {
		return isXLSFile(file);
	}

	protected Preferences getPreferences() {
		if (preferences == null) {
			preferences = Preferences.userNodeForPackage(this.getClass());
		}
		return preferences;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	public File getExportFilePath(String filename) throws Exception {
		
		// 获取选择路
		chooser = getFileChooser();
		chooser.setSelectedFile(new File(filename));
		chooser.setApproveButtonText("保存");
		chooser.setDialogTitle("保存文件");
		int re = chooser.showSaveDialog(new JDialog());
		File selectedFile = null;
		if (re == JFileChooser.APPROVE_OPTION) {
	
			selectedFile = chooser.getSelectedFile();
			if (selectedFile != null) {
				// 根据FileFilter来过滤文件
	
				if (chooser.getFileFilter().getDescription()
						.endsWith(XLS_SUFFIX)) {
					selectedFile = getSelectedExcelFile(selectedFile, XLS_SUFFIX);
				} else {
					selectedFile = getSelectedExcelFile(selectedFile, XLSX_SUFFIX);
				}
				if (selectedFile.exists()) {
					Boolean result = ShowMsgBox.show(ShowMsgBox.Question, "是否覆盖原有文件？");
					if (!result) {
						return null;
					}
				}
				getPreferences().put(KEY_DEFAULT_DIR, selectedFile.getParent());
			}
		}
		return selectedFile;
	}

	public File getSelectedExcelFile(File selectedFile, String filterfilesuffix) {
		File selectedFiles = null;
		if (selectedFile.getPath().endsWith(filterfilesuffix)) {
			selectedFiles = new File(selectedFile.getPath());
		} else {
			selectedFiles = new File(selectedFile.getPath() + filterfilesuffix);
		}
		return selectedFiles;
	}

	private static boolean isXLSFile(File file) {
		String name = file.getName().toLowerCase();
		return name.endsWith(XLS_SUFFIX);
	}

	protected static boolean isSuffixXLSXRight(File file) {
		return isXLSXFile(file);
	}

	private static boolean isXLSXFile(File file) {
		String name = file.getName().toLowerCase();
		return name.endsWith(XLSX_SUFFIX);
	}

	public JFileChooser getFileChooser() {
		if (chooser == null) {
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setAcceptAllFileFilterUsed(false);
	
			chooser.addChoosableFileFilter(new FileFilter() {
	
				public boolean accept(File f) {
					return f.isDirectory() || isSuffixXLSRight(f);
				}
	
				@Override
				public String getDescription() {
					return XLS_SUFFIX;
				}
			});
	
			chooser.addChoosableFileFilter(new FileFilter() {
	
				public boolean accept(File f) {
					return f.isDirectory() || isSuffixXLSXRight(f);
				}
	
				@Override
				public String getDescription() {
					return XLSX_SUFFIX;
				}
			});
		}
		// 用户选择文件目录偏好
		String preferencesDir = getPreferences().get(KEY_DEFAULT_DIR, System.getProperty("user.dir"));
		chooser.setCurrentDirectory(new File(preferencesDir));
		return chooser;
	}

	/**
	 * @ 过滤只显示Excel和文件夹
	 * 
	 */
	class XlsFileFilter extends FileFilter {
		private String endW;

		public XlsFileFilter(String endW) {
			this.endW = endW;
		}

		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			return f.getName().endsWith(endW); // 设置为选择以.xls为后缀的文件
		}

		public String getDescription() {
			return endW;
		}
	}
}