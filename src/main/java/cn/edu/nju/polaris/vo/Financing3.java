package cn.edu.nju.polaris.vo;

/**
 * 
 * @author hyf
 * 
 * 保兑仓融资
 * id  申请融资的公司id
 * name 公司名称
 * 
 * goods 	计划购买货物
 * from  	货物来源
 * money  	拟购货物金额
 * rate 	保障金比例
 */
public class Financing3 {
	
	private double id;
	private String name;
	
	private String goods;
	private String from;
	private double money;
	private double rate;
	
	public Financing3(){
		
	}
	
	public Financing3(double id,String name,String goods,String from,double money,double rate){
		this.id=id;
		this.name=name;
		this.goods=goods;
		this.from=from;
		this.money=money;
		this.rate=rate;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
