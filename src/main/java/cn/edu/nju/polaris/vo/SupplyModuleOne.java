package cn.edu.nju.polaris.vo;

/**
 * 
 * @author hyf
 * 
 * Profit 盈利能力
 * Operate 运营能力
 * Develop 发展能力
 * Sinking 偿债能力
 * 
 */
public class SupplyModuleOne {
	private double[] Profit;
	private double[] Operate;
	private double[] Develop;
	private double[] Sinking;
	
	public SupplyModuleOne(double[] p,double[] o,double[] d,double[] s){
		this.Profit=p;
		this.Operate=o;
		this.Develop=d;
		this.Sinking=s;
	}

	public double[] getProfit() {
		return Profit;
	}

	public void setProfit(double[] profit) {
		Profit = profit;
	}

	public double[] getOperate() {
		return Operate;
	}

	public void setOperate(double[] operate) {
		Operate = operate;
	}

	public double[] getDevelop() {
		return Develop;
	}

	public void setDevelop(double[] develop) {
		Develop = develop;
	}

	public double[] getSinking() {
		return Sinking;
	}

	public void setSinking(double[] sinking) {
		Sinking = sinking;
	}


	
}
