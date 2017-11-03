package cn.edu.nju.polaris.service.Impl;

import java.util.List;

/**
 * 
 * @author hyf
 *
 */
public class TableHelper {
	
	/**
	 * 
	 * @param time  时间yyyy-mm
	 * @return 返回包括该月的，之前的当年的所有的期
	 */
	public List<String> getAllPeriodThisYear(String time){
		String[]temp=time.split("-");
		String year=temp[0];
		String month=temp[1];
		
		int mon=Integer.parseInt(month);
		return null;
	}
}
