package cn.edu.nju.polaris.repository;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class SubjectsRecordRepositoryTest {

    @Autowired
    private SubjectsRecordRepository subjectsRecordRepository;

    @Test
    public void testDeleteOneVoucherAllRecord() throws Exception {
        subjectsRecordRepository.deleteOneVoucherAllRecord(1L,"记-2");
    }


}
