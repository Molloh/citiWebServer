package cn.edu.nju.polaris.util;

import cn.edu.nju.polaris.entity.IndustryIndex;
import cn.edu.nju.polaris.repository.IndustryIndexRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;


@Component
public class IndustryIndexInitialize {

    private final IndustryIndexRepository industryIndexRepository;

    @Autowired
    public IndustryIndexInitialize(IndustryIndexRepository industryIndexRepository) {
        this.industryIndexRepository = industryIndexRepository;
    }

    public void initialize() throws IOException {
        FileInputStream fileIn = new FileInputStream(IndustryIndexInitialize.class.getClassLoader().getResource("IndustryIndex.xls").getFile());
        Workbook workbook = new HSSFWorkbook(fileIn);

        for (int i = 0; i < workbook.getNumberOfSheets()-1; i++){
            Sheet sht = workbook.getSheetAt(i);
            for (Row r : sht){
                if (r.getRowNum() == 0){
                    continue;
                }

                IndustryIndex industryIndex = new IndustryIndex();
                industryIndex.setCategory(sht.getSheetName());
                industryIndex.setFirstIndustry(r.getCell(0).getStringCellValue());
                industryIndex.setSecondIndustry(r.getCell(1).getStringCellValue());
                industryIndex.setScale(r.getCell(2).getStringCellValue());
                industryIndex.setIndexName(r.getCell(3).getStringCellValue());
                industryIndex.setExcellentIndex(r.getCell(4).getNumericCellValue());
                industryIndex.setFineIndex(r.getCell(5).getNumericCellValue());
                industryIndex.setAverageIndex(r.getCell(6).getNumericCellValue());
                industryIndex.setLowIndex(r.getCell(7).getNumericCellValue());
                industryIndex.setBadIndex(r.getCell(8).getNumericCellValue());

                industryIndexRepository.save(industryIndex);
            }
        }
    }

}
