package com.ugiant.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 编号生成 工具类
 * @author lingyuwang
 *
 */
public class CodeUtils {
	
	/**
	 * 生成编码
	 * @param format
	 * @return
	 */
	public static String generateCode(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 生成随机数
	 * @param digit 位数(1-9)
	 * @return
	 */
	public static String getRandomCode(Integer digit){
		String code = "";
		if(digit < 9 && digit > 0){
			Integer base = Integer.MAX_VALUE;
			Long l = (long) (Math.pow(10, digit) - 1);
			if(l < Integer.MAX_VALUE){
				base = (int) Math.pow(10, digit) - 1;
			}
			
			Random rm = new Random();
			Integer num = rm.nextInt(base);
			code = num.toString();
			int lenght = num.toString().length();
			for(int i = digit; i > lenght; i--){
				code = "0" + code;
			}
		}
		
		return code;
	}
	
}
