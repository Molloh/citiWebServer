package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.*;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.voucher.ItemTotalVo;
import cn.edu.nju.polaris.vo.voucher.VoucherItemVo;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.util.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;
    private final VoucherItemRepository voucherItemRepository;
    private final SubjectsRepository subjectsRepository;
    private final SubjectsBalanceRepository subjectsBalanceRepository;
    private final SubjectsRecordRepository subjectsRecordRepository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherItemRepository voucherItemRepository, SubjectsRepository subjectsRepository, SubjectsBalanceRepository subjectsBalanceRepository, SubjectsRecordRepository subjectsRecordRepository) {
        this.voucherRepository = voucherRepository;
        this.voucherItemRepository = voucherItemRepository;
        this.subjectsRepository=subjectsRepository;
        this.subjectsBalanceRepository=subjectsBalanceRepository;
        this.subjectsRecordRepository=subjectsRecordRepository;
    }

    private boolean addVoucher(Voucher voucher) {
        if (voucher == null){
            return false;
        }else {
            Voucher tempVoucher = voucherRepository.findByVoucherIdAndCompanyId(voucher.getVoucherId(),voucher.getCompanyId());
            if (tempVoucher != null){
                throw new ResourceConflictException("该凭证已存在!");
            }
            voucherRepository.save(voucher);
            return true;
        }
    }

    private boolean deleteOneVoucher(String voucherId, long factoryId) {
        VoucherMultiKeysClass key = new VoucherMultiKeysClass();
        key.setVoucherId(voucherId);
        key.setCompanyId(factoryId);

        Voucher voucher = voucherRepository.findOne(key);
        if (voucher == null){
            throw new ResourceNotFoundException("该凭证不存在!");
        }

        voucherRepository.delete(key);
        return true;
    }

    private boolean deleteOneFactoryAllVoucher(long factoryId) {
        voucherRepository.deleteAllByCompanyId(factoryId);
        return true;
    }

    private boolean modifyOneVoucher(Voucher voucher) {
        Voucher tempVoucher = voucherRepository.findByVoucherIdAndCompanyId(voucher.getVoucherId(),voucher.getCompanyId());
        if (tempVoucher == null){
            throw new ResourceNotFoundException("该凭证不存在!");
        }else{
            voucherRepository.save(voucher);
        }
        return true;
    }

    private boolean addOneItem(VoucherItem voucherItem) {
        if (voucherItem == null){
            return false;
        }else {
            VoucherItemMultiKeysClass key = new VoucherItemMultiKeysClass();
            key.setVoucherId(voucherItem.getVoucherId());
            key.setCompanyId(voucherItem.getCompanyId());
            key.setLines(voucherItem.getLines());

            VoucherItem tempItem = voucherItemRepository.findOne(key);
            if (tempItem != null){
                throw new ResourceConflictException("该凭证条目已存在!");
            }
            voucherItemRepository.save(voucherItem);
        }
        return true;
    }

    private boolean addSeveralItems(ArrayList<VoucherItem> itemList) {
        for (VoucherItem item : itemList){
            VoucherItemMultiKeysClass key = new VoucherItemMultiKeysClass();
            key.setVoucherId(item.getVoucherId());
            key.setCompanyId(item.getCompanyId());
            key.setLines(item.getLines());

            VoucherItem tempItem = voucherItemRepository.findOne(key);
            if (tempItem != null){
                throw new ResourceConflictException("该凭证条目已存在!");
            }
        }
        voucherItemRepository.save(itemList);
        return true;
    }

    private boolean deleteOneVoucherItems(String voucherId, long factoryId) {
        Voucher voucher = voucherRepository.findByVoucherIdAndCompanyId(voucherId,factoryId);
        if (voucher == null){
            throw new ResourceNotFoundException("该凭证不存在!");
        }
        voucherItemRepository.deleteAllByCompanyIdAndVoucherId(factoryId,voucherId);
        return true;
    }

    private boolean deleteOneItem(String voucherId, long factoryId, int line) {
        VoucherItemMultiKeysClass key = new VoucherItemMultiKeysClass();
        key.setVoucherId(voucherId);
        key.setCompanyId(factoryId);
        key.setLines(line);

        VoucherItem item = voucherItemRepository.findOne(key);
        if (item != null){
            throw new ResourceConflictException("该凭证条目已存在!");
        }
        voucherItemRepository.delete(key);
        return true;
    }

    private boolean modifyOneAmountAllItems(String voucherId, long factoryId, ArrayList<VoucherItem> itemList) {
        List<VoucherItem> list = voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,voucherId);
        if (list == null) {
            throw new ResourceNotFoundException("该凭证不存在!");
        }
        voucherItemRepository.save(itemList);
        return true;
    }


    /**
     * 需要对凭证 凭证条目 余额 辅助信息进行修改
     * @param voucherVO
     * @return
     */
    @Override
    public boolean saveOneVoucher(VoucherVO voucherVO) {
        //对于voucher和voucherItem需要进行分别处理
        Voucher voucher=new Voucher();
        voucher.setCompanyId(voucherVO.getCompany_id());
        voucher.setVoucherId(voucherVO.getVoucher_id());
        voucher.setDate(Timestamp.valueOf(voucherVO.getDate()));
        //TODO 时间戳的转换可能会出问题
        voucher.setRemark(voucherVO.getRemark());
        voucher.setVoucherMaker(voucherVO.getVoucher_maker());
        //凭证赋值完成

        ArrayList<VoucherItemVo> itemVoList=voucherVO.getItemList();
        ArrayList<VoucherItem> itemList=new ArrayList<>();

        HashMap<String,String> subjectIdToNameMap=new HashMap<>();
        List<Subjects> allSubjectList=subjectsRepository.findAll();
        for(int count=0;count<allSubjectList.size();count++){
            Subjects oneSubject=allSubjectList.get(count);
            subjectIdToNameMap.put(oneSubject.getSubjectsId(),oneSubject.getSubjectsName());
        }

        if(itemVoList.size()!=0){
            for(int count=0;count<itemVoList.size();count++){
                VoucherItemVo oneItemVo=itemVoList.get(count);
                VoucherItem oneItem=new VoucherItem();
                //TODO 修改余额 根据主键


                String subjectId=oneItemVo.getSubjectId();
                SubjectsBalance beforeBalance=subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(voucherVO.getCompany_id(),subjectId,"");

                SubjectsRecord newRecord=new SubjectsRecord();
                double debitAmount=oneItem.getDebitAmount();
                double creditAmount=oneItem.getCreditAmount();
                double newBalance=beforeBalance.getBalance()+SubjectBalanceHelper.getDirection(subjectId)*(debitAmount-creditAmount);

                newRecord.setCompanyId(voucherVO.getCompany_id());
                newRecord.setVoucherId(voucherVO.getVoucher_id());
                newRecord.setSubjectsId(subjectId);
                newRecord.setDate(Timestamp.valueOf(voucherVO.getDate()));
                newRecord.setDebitAmount(debitAmount);
                newRecord.setCreditAmount(creditAmount);

                subjectsRecordRepository.save(newRecord);

//                itemList.add()

            }

        }


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
