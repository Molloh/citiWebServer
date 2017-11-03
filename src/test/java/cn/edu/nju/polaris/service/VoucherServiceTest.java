package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Voucher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Test
    public void testSave(){
        Voucher voucher = new Voucher();
        voucher.setCompanyId("001");
        voucher.setDate(Date.valueOf("2017-10-01"));
        voucher.setRemark("note");
        voucher.setVoucherId("记-2");
        voucher.setVoucherMaker("loo");
        voucherService.saveVoucher(voucher);
    }
}
