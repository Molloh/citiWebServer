package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.VoucherItem;
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
public class VoucherItemRepositoryTest {

    @Autowired
    private VoucherItemRepository voucherItemRepository;

    @Test
    public void testFindByCompanyIdAndVoucherId(){
        List<VoucherItem> list = voucherItemRepository.findByCompanyIdAndVoucherId(1L,"记-1");
        for (VoucherItem item : list){
            System.out.println(item.toString());
        }
    }

    @Test
    public void testFindByCompanyId(){
        List<VoucherItem> list = voucherItemRepository.findByCompanyId(1L);
        for (VoucherItem item : list){
            System.out.println(item.toString());
        }
    }
}
