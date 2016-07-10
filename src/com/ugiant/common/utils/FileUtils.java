package com.ugiant.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 文件操作 工具类
 * @author lingyuwang
 *
 */
public class FileUtils {
	
	/**
	 * 检查文件夹是否存在，不存在则先创建
	 * @param file
	 */
	public static void safeMkdir(File file) {
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}
	
	/**
	 * 拷贝文件
	 * @param sourceFile
	 * @param toFile
	 * @throws IOException 
	 */
	public static void copyFile(File sourceFile, File toFile) {
		FileInputStream fis = null;
		BufferedInputStream bufis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bufos = null;
		try {
			fis = new FileInputStream(sourceFile);	
			bufis = new BufferedInputStream(fis);
			
			fos = new FileOutputStream(toFile);
			bufos = new BufferedOutputStream(fos);
			
			int ch = 0;
			while((ch=bufis.read())!=-1){
				bufos.write(ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufos != null) {
				try {
					bufos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bufis != null) {
				try {
					bufis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
