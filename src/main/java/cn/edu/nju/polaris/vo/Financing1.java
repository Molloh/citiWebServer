package cn.edu.nju.polaris.vo;

/**
 * 
 * @author hyf
 * 
 * 应收帐款融资
 * id  申请的公司id
 * name 申请的公司名称
 * targetCompany  应收帐款对象
 * net 	应收帐款净额
 * money 	应收帐款抵押额
 */
public class Financing1 {
	private long id;
	private String name;
	
	private String targetCompany;
	private double net;
	private double money;
	
	public Financing1(){
		
	}
	public Financing1(long id,String name,String targetCompany,double net,double money){
		this.id=id;
		this.name=name;
		this.targetCompany=targetCompany;
		this.net=net;
		this.money=money;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTargetCompany() {
		return targetCompany;
	}
	public void setTargetCompany(String targetCompany) {
		this.targetCompany = targetCompany;
	}
	public double getNet() {
		return net;
	}
	public void setNet(double net) {
		this.net = net;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	
}
