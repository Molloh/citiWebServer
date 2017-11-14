package cn.edu.nju.polaris.vo;

import java.util.List;

/**
 * 
 * @author hyf
 * type 原材料或产品名称
 * companys 来源公司（名称）
 */
public class RaworProductAndFromVo {
	private String type;
	private List<String> companys;
	
	public RaworProductAndFromVo(){
		
	}
	
	public RaworProductAndFromVo(String type,List<String> companys){
		this.type=type;
		this.companys=companys;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getCompanys() {
		return companys;
	}

	public void setCompanys(List<String> companys) {
		this.companys = companys;
	}
	
	
}
