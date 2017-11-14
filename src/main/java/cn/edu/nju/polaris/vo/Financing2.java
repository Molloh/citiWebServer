package cn.edu.nju.polaris.vo;

/**
 * 
 * @author hyf
 * 
 * 动产质押融资
 * id    申请融资的公司id
 * name  公司名
 * type  	库存种类
 * net  	库存净额
 * money	库存质押额
 * 
 */
public class Financing2 {
	private double id;
	private String name;
	
	private String type;
	private double net;
	private double money;
	
	public Financing2(){
		
	}
	
	public Financing2(double id,String name,String type,double net,double money){
		this.id=id;
		this.name=name;
		this.type=type;
		this.net=net;
		this.money=money;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
