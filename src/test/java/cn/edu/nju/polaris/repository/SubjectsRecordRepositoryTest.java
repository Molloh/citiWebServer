package cn.edu.nju.polaris.repository;


import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.entity.SubjectsRecord;
import org.apache.poi.hssf.record.SubRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SubjectsRecordRepositoryTest {

    @Autowired
    private SubjectsRecordRepository subjectsRecordRepository;

//    @Test
//    public void testDeleteOneVoucherAllRecord() throws Exception {
//        subjectsRecordRepository.deleteOneVoucherAllRecord(1L,"记-2");
//    }


    @Test
    public void testFindNewestSubjectRecord() throws Exception {
        SubjectsRecord record = subjectsRecordRepository.findNewestSubjectRecord(1L,"1001");
        System.out.println(record.toString());
    }


    @Test
    public void testFindAllThroughOneMonth() throws Exception {
        List<SubjectsRecord> list = subjectsRecordRepository.findAllThroughOneMonth(1L,"2017-11");
        for (SubjectsRecord record : list){
            System.out.println(record.toString());
        }
    }

    @Test
    public void testFindAllThroughOneYear() throws Exception {
        List<SubjectsRecord> list = subjectsRecordRepository.findAllThroughOneYear(1L,"2017");
        for (SubjectsRecord record : list){
            System.out.println(record.toString());
        }
    }


    @Test
    public void testFindAllByCompanyId() throws Exception {
        List<SubjectsRecord> list = subjectsRecordRepository.findAllByCompanyId(1L);
        for (SubjectsRecord record : list){
            System.out.println(record.toString());
        }
    }


    @Test
    public void testFindAllByCompanyIdAndSubjectsId() throws Exception {
        List<SubjectsRecord> list = subjectsRecordRepository.findAllByCompanyIdAndSubjectsId(1L,"1001");
        for (SubjectsRecord record : list){
            System.out.println(record.toString());
        }
    }
}
