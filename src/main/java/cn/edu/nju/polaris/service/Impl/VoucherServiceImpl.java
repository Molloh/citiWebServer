package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.repository.VoucherRepository;
import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.voucher.ItemTotalVo;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }


    @Override
    public void saveVoucher(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public boolean addVoucher(Voucher voucher) {
        return false;
    }

    @Override
    public boolean deleteOneVoucher(String voucherId, long factoryId) {
        return false;
    }

    @Override
    public boolean deleteOneFactoryAllVoucher(long factoryId) {
        return false;
    }

    @Override
    public boolean modifyOneVoucher(Voucher voucher, String beforeVoucherId) {
        return false;
    }

    @Override
    public boolean addOneItem(VoucherItem voucherItem) {
        return false;
    }

    @Override
    public boolean addSeveralItems(ArrayList<VoucherItem> itemList) {
        return false;
    }

    @Override
    public boolean deleteOneVoucherItems(String voucherId, long factoryId) {
        return false;
    }

    @Override
    public boolean deleteOneItem(String voucherId, long factoryId, int line) {
        return false;
    }

    @Override
    public boolean modifyOneAmountAllItems(String voucherId, long factoryId, ArrayList<VoucherItem> itemList) {
        return false;
    }

    @Override
    public boolean saveOneVoucher(VoucherVO voucherVO) {
        //对于voucher和voucherItem需要进行分别处理
        Voucher voucher=new Voucher();
        voucher.setCompanyId(voucherVO.getCompany_id());





        return false;
    }

    @Override
    public ItemTotalVo getVoucherTotal(ArrayList<VoucherItem> itemList) {
        return null;
    }

    @Override
    public double getOneSubjectBalance(String subjectId, long factoryId) {
        return 0;
    }

    @Override
    public ArrayList<VoucherVO> getCurrentPeriodAllVoucher(long factoryId) {
        return null;
    }

    @Override
    public VoucherVO getOneVoucher(String voucherId, long factoryId) {
        return null;
    }

    @Override
    public ArrayList<VoucherVO> getSearchedVoucher(VoucherSearchVo searchVo, long factoryId) {
        return null;
    }

    @Override
    public boolean deleteSelectedVoucher(ArrayList<String> voucherIdList, long factoryId) {
        return false;
    }

    @Override
    public boolean amendOneVoucher(VoucherVO voucherVO, String beforeVoucherId) {
        return false;
    }

    @Override
    public int getCurrentNumber(String voucherCharacter, long factoryId) {
        return 0;
    }

    @Override
    public ArrayList<String> getAllPeriod(long factoryId) {
        return null;
    }

    @Override
    public ArrayList<String> getAllVoucherMaker(long factoryId) {
        return null;
    }


}
