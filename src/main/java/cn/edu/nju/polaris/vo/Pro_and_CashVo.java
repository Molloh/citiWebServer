package cn.edu.nju.polaris.vo;

/**
 * 
 * @author hyf
 * project       项目
 * line          行次
 * year_amount   本年累计金额
 * period_amount 本期金额
 *
 */
public class Pro_and_CashVo {
	private String project;
	private int line;
	private double year_amount;
	private double period_amount;
	
	public Pro_and_CashVo(String p,int l,double y,double pa){
		project=p;
		line=l;
		year_amount=y;
		period_amount=pa;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public double getYear_amount() {
		return year_amount;
	}

	public void setYear_amount(double year_amount) {
		this.year_amount = year_amount;
	}

	public double getPeriod_amount() {
		return period_amount;
	}

	public void setPeriod_amount(double period_amount) {
		this.period_amount = period_amount;
	}
	
	
}
