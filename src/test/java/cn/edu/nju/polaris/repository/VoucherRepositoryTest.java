package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;
import cn.edu.nju.polaris.entity.Voucher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class VoucherRepositoryTest {


    @Autowired
    private VoucherRepository voucherRepository;

    @Test
    public void testFindByVoucherIdAndCompanyId(){
        Voucher voucher = voucherRepository.findByVoucherIdAndCompanyId("记-2",1L);
        System.out.println(voucher.toString());
    }


    @Test
    public void testFindByCompanyId(){
        List<Voucher> list = voucherRepository.findByCompanyId(1L);
        for (Voucher voucher : list){
            System.out.println(voucher.toString());
        }
    }
}
