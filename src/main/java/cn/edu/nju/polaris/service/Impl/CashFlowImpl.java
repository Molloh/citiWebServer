package cn.edu.nju.polaris.service.Impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import cn.edu.nju.polaris.service.CashFlowService;
import cn.edu.nju.polaris.vo.Pro_and_CashVo;

/**
 * 
 * @author hyf
 *
 */
public class CashFlowImpl implements CashFlowService{

	public List<Pro_and_CashVo> CashFlowTable_Info(String time, String company_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*public void CreateCashFlowTable(String company_id, String time, String path) {
		List<Pro_and_CashVo> list=CashFlowTable_Info(time,company_id);
		
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
	}*/
}
