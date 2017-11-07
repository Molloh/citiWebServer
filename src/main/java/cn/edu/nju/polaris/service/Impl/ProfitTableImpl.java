package cn.edu.nju.polaris.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nju.polaris.entity.ProfitSheet;
import cn.edu.nju.polaris.repository.ProfitSheetRepository;
import cn.edu.nju.polaris.service.ProfitTableService;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
public class ProfitTableImpl implements ProfitTableService{
	
	private ProfitSheetRepository psr;
	private TableHelper helper;
	
	@Autowired
    public ProfitTableImpl(ProfitSheetRepository psr) {
        this.psr = psr;
        this.helper=new TableHelper();
    }
	
	
	public List<Pro_and_CashVo> ProfitTable_Info(String time, long company_id) {
		List<ProfitSheet> list=psr.findByCompanyIdAndPeriod(company_id, time);
		
		List<Pro_and_CashVo> res=new ArrayList<Pro_and_CashVo>();
		if(list.size()==0){
			
		}else{
			List<String> times=helper.getAllPeriodThisYear(time);
			Map<String,Double>map=new HashMap<String,Double>();
			
			List<ProfitSheet> temp=null;
			for(int i=0;i<times.size()-1;i++){
				temp=psr.findByCompanyIdAndPeriod(company_id, times.get(i));
				for(ProfitSheet p:temp){
					if(i==0){
						map.put(p.getName(), p.getBalance());
					}else{
						map.put(p.getName(), map.get(p.getName())+p.getBalance());
					}
				}
			}
			
			for(int i=0;i<list.size();i++){
				ProfitSheet p=list.get(i);
				String project=p.getName();	
				double period=p.getBalance();
				double year=map.get(project)+period;
				res.add(new Pro_and_CashVo(project,i+1,year,period));
			}
			
		}
		return res;
	}
	
	

	@Override
	public double getIncome(String time, long company_id) {
		ProfitSheet p=psr.findByCompanyIdAndPeriodAndName(company_id, time, "一、营业收入");
		if(p==null){
			return 0;
		}
		else{
			return p.getBalance();
		}
	}


	private List<Pro_and_CashVo> CalProByPeriod(String time, long company_id){
		return null;
	}


	@Override
	public double getProfit(String time, long company_id) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double[] getValues(long company_id, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	/*public void CreateProfitTable(String company_id, String time, String path) {
		
		List<Pro_and_CashVo> list=ProfitTable_Info(time,company_id);
		
		// 创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建一个工作表
        HSSFSheet sheet = workbook.createSheet("现金流量表");
        // 添加表头行
        HSSFRow hssfRow = sheet.createRow(0);
        // 设置单元格格式居中
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 添加表头内容
        HSSFCell headCell = hssfRow.createCell(0);
        headCell.setCellValue("项目");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(1);
        headCell.setCellValue("行次");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(2);
        headCell.setCellValue("本年累计金额");
        headCell.setCellStyle(cellStyle);

        headCell = hssfRow.createCell(3);
        headCell.setCellValue("本期金额");
        headCell.setCellStyle(cellStyle);


        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
            hssfRow = sheet.createRow((int) i + 1);
            Pro_and_CashVo t=list.get(i);
            // 创建单元格，并设置值
            HSSFCell cell = hssfRow.createCell(0);
            cell.setCellValue(t.getProject());
            cell.setCellStyle(cellStyle);

            cell = hssfRow.createCell(1);
            cell.setCellValue(t.getLine());
            cell = hssfRow.createCell(2);
            cell.setCellValue(t.getYear_amount());
            cell = hssfRow.createCell(3);
            cell.setCellValue(t.getPeriod_amount());
        }

        try {
            OutputStream stream = new FileOutputStream(path);
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	private boolean hasRecord(String time, String company_id){
		return false;
	}*/
}
