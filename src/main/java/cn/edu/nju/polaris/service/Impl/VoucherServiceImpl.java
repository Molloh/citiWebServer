package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.*;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherItemMultiKeysClass;
import cn.edu.nju.polaris.entity.MultiKeysClass.VoucherMultiKeysClass;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.vo.voucher.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.nju.polaris.util.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService{

    private final VoucherRepository voucherRepository;
    private final VoucherItemRepository voucherItemRepository;
    private final SubjectsRepository subjectsRepository;
    private final SubjectsBalanceRepository subjectsBalanceRepository;
    private final SubjectsRecordRepository subjectsRecordRepository;
    private final SupportItem1Repository supportItem1Repository;
    private final SupportItem2Repository supportItem2Repository;

    @Autowired
    public VoucherServiceImpl(VoucherRepository voucherRepository, VoucherItemRepository voucherItemRepository, SubjectsRepository subjectsRepository, SubjectsBalanceRepository subjectsBalanceRepository, SubjectsRecordRepository subjectsRecordRepository, SupportItem1Repository supportItem1Repository, SupportItem2Repository supportItem2Repository) {
        this.voucherRepository = voucherRepository;
        this.voucherItemRepository = voucherItemRepository;
        this.subjectsRepository=subjectsRepository;
        this.subjectsBalanceRepository=subjectsBalanceRepository;
        this.subjectsRecordRepository=subjectsRecordRepository;
        this.supportItem1Repository=supportItem1Repository;
        this.supportItem2Repository=supportItem2Repository;
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
     * 需要对凭证 凭证条目 余额 辅助信息！！！进行修改
     * 这边传入的date的格式需要是时间戳的格式 "yyyy-mm-dd hh:mm:ss"
     * @param voucherVO
     * @return
     */
    @Override
    public boolean saveOneVoucher(VoucherVO voucherVO) {
        //对于voucher和voucherItem需要进行分别处理
        Voucher voucher=new Voucher();
        voucher.setCompanyId(voucherVO.getCompany_id());
        voucher.setVoucherId(voucherVO.getVoucher_id());
        voucher.setDate(DateHelper.DateToTimeStamp(voucherVO.getDate()));
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
                VoucherItem oneItem=ItemVoToItem(oneItemVo);

                String subjectId=oneItemVo.getSubjectId();
                SubjectsBalance beforeBalance=subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(voucherVO.getCompany_id(),subjectId,DateHelper.DateToMonth(voucherVO.getDate()));
                SubjectsBalance afterBalance=new SubjectsBalance();


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

                afterBalance.setId(beforeBalance.getId());
                afterBalance.setCompanyId(voucherVO.getCompany_id());
                afterBalance.setSubjectsId(subjectId);
                afterBalance.setDate(DateHelper.DateToMonth(voucherVO.getDate()));
                afterBalance.setDebitAmount(beforeBalance.getDebitAmount()+oneItem.getDebitAmount());
                afterBalance.setCreditAmount(beforeBalance.getCreditAmount()+oneItem.getCreditAmount());
                afterBalance.setBalance(newBalance);

                subjectsBalanceRepository.save(afterBalance);

                itemList.add(oneItem);
            }
        }
        //先添加凭证再添加凭证条目
        boolean result=true;
        boolean result1=addVoucher(voucher);
        boolean result2=addSeveralItems(itemList);

        result=result&result1&result2;

        return result;

    }

    private VoucherItem ItemVoToItem(VoucherItemVo itemVo){
        VoucherItem voucherItem=new VoucherItem();
        try{
            voucherItem.setCompanyId(itemVo.getCompany_id());
            voucherItem.setVoucherId(itemVo.getSubjectId());
            voucherItem.setLines(itemVo.getLines());
            voucherItem.setDescription(itemVo.getAbstracts());
            voucherItem.setSubjects(itemVo.getSubjectId());
            voucherItem.setDebitAmount(itemVo.getDebitAmount());
            voucherItem.setCreditAmount(itemVo.getCreditAmount());

        }catch (Exception e){
            e.printStackTrace();
        }

        return voucherItem;
    }
    @Override
    public ItemTotalVo getVoucherTotal(ArrayList<VoucherItem> itemList) {
        ItemTotalVo totalVo=new ItemTotalVo();

        String chineseTotal;
        double debitTotal=0.0;
        double creditToal=0.0;

        if(itemList.size()!=0){
            for(int count=0;count<itemList.size();count++){
                VoucherItem oneItem=itemList.get(count);
                debitTotal=debitTotal+oneItem.getDebitAmount();
                creditToal=creditToal+oneItem.getCreditAmount();
            }
        }

        if(debitTotal==creditToal){
            chineseTotal=NumberToCN.number2CNMontrayUnit(creditToal);
        }else{
            chineseTotal="借款金额和贷款金额不相同";
        }
        totalVo.setChineseTotal(chineseTotal);
        totalVo.setDebitAmount(debitTotal);
        totalVo.setCreditAmount(creditToal);

        return totalVo;
    }

    @Override
    public double getOneSubjectBalance(String subjectId, long factoryId,String month) {
        SubjectsBalance oneBalance=subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(factoryId,subjectId,month);
        if(oneBalance==null){
            return 0;
        }else{
            return oneBalance.getBalance();
        }
    }

    @Override
    public ArrayList<VoucherVO> getCurrentPeriodAllVoucher(long factoryId) {
        String currentMonth=DateHelper.getCurrentMonth();
        //TODO

        return null;
    }

    @Override
    public VoucherVO getOneVoucher(String voucherId, long factoryId) {
        VoucherVO voucherVO=new VoucherVO();
        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        HashSet<String> allVoucherIdSet=new HashSet<>();

        for(int count=0;count<allVoucherList.size();count++){
            allVoucherIdSet.add(allVoucherList.get(count).getVoucherId());
        }

        if(!allVoucherIdSet.contains(voucherId)){
            return null;
        }else{

            Voucher oneVoucher=voucherRepository.findByVoucherIdAndCompanyId(voucherId,factoryId);

            voucherVO=voucherToVoucherVo(oneVoucher);

            List<VoucherItem> itemList=voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,voucherId);

            ArrayList<VoucherItemVo> itemVoList=new ArrayList<>();
            ItemTotalVo itemTotalVo=new ItemTotalVo();

            double debitAmount=0.0;
            double creditAmount=0.0;
            for(int count=0;count<itemList.size();count++){
                VoucherItemVo oneItemVo=voucherItemToItemVo(itemList.get(count));

                List<SupportItem1> supportOneList=supportItem1Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,itemList.get(count).getLines());
                oneItemVo.setSupportOneList(supportOneListToVoList(supportOneList));

                List<SupportItem2> supportTwoList=supportItem2Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,itemList.get(count).getLines());
                oneItemVo.setSupportTwoList(supportTwoListToVoList(supportTwoList));

                itemVoList.add(oneItemVo);
                debitAmount=debitAmount+itemList.get(count).getDebitAmount();
                creditAmount=creditAmount+itemList.get(count).getCreditAmount();

            }
            itemTotalVo.setChineseTotal(NumberToCN.number2CNMontrayUnit(creditAmount));
            itemTotalVo.setCreditAmount(creditAmount);
            itemTotalVo.setDebitAmount(debitAmount);

            voucherVO.setItemList(itemVoList);
            voucherVO.setTotalVo(itemTotalVo);

        }

        return voucherVO;
    }

    private VoucherVO voucherToVoucherVo(Voucher voucher){
        VoucherVO resultVo=new VoucherVO();

        try{
            resultVo.setCompany_id(voucher.getCompanyId());
            resultVo.setVoucher_id(voucher.getVoucherId());
            resultVo.setDate(String.valueOf(voucher.getDate()).substring(0,10));
            resultVo.setRemark(voucher.getRemark());
            resultVo.setVoucher_maker(voucher.getVoucherMaker());



        }catch (Exception e){
            e.printStackTrace();
        }
        return resultVo;
    }


    /**
     * 把voucherItem转换为voucherItemVo的方法
     * @param voucherItem
     * @return
     */
    private VoucherItemVo voucherItemToItemVo(VoucherItem voucherItem){
        VoucherItemVo resultVo=new VoucherItemVo();
        try{
            resultVo.setVoucher_id(voucherItem.getVoucherId());
            resultVo.setLines(voucherItem.getLines());
            resultVo.setAbstracts(voucherItem.getDescription());
            resultVo.setSubjectId(voucherItem.getSubjects());
            resultVo.setDebitAmount(voucherItem.getDebitAmount());
            resultVo.setCreditAmount(voucherItem.getCreditAmount());

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultVo;
    }

    /**
     * 把辅助信息一的实体类列表转换为vo列表
     * @param supportOneList
     * @return
     */
    private List<SupportItemOneVo> supportOneListToVoList(List<SupportItem1> supportOneList){
        List<SupportItemOneVo> resultVo=new ArrayList<>();
        if(supportOneList.size()==0||supportOneList==null){
            return resultVo;
        }else{
            for(int count=0;count<supportOneList.size();count++){
                SupportItemOneVo newVo=supportOneToVo(supportOneList.get(count));
                resultVo.add(newVo);
            }

        }
        return resultVo;
    }

    /**
     * 把辅助信息二的实体类列表转换为vo列表
     * @param supportTwoList
     * @return
     */
    private List<SupportItemTwoVo> supportTwoListToVoList(List<SupportItem2> supportTwoList){
        List<SupportItemTwoVo> resultVo=new ArrayList<>();
        if(supportTwoList.size()==0||supportTwoList==null){
            return resultVo;
        }else{
            for(int count=0;count<supportTwoList.size();count++){
                SupportItemTwoVo newVo=supportTwoToVo(supportTwoList.get(count));
                resultVo.add(newVo);
            }

        }
        return resultVo;
    }

    /**
     * 把辅助信息二的实体类转换为vo
     * @param item2
     * @return
     */
    private SupportItemTwoVo supportTwoToVo(SupportItem2 item2) {
        SupportItemTwoVo resultVo=new SupportItemTwoVo();

        try{
            resultVo.setCompanyId(item2.getCompanyId());
            resultVo.setVoucherId(item2.getVoucherId());
            resultVo.setVoucherLines(item2.getVoucherLines());
            resultVo.setSupportLines(item2.getSupportLines());
            resultVo.setSubjectId(item2.getSubjects());
            resultVo.setCompanyName(item2.getCompanyName());
            resultVo.setDebitDate(String.valueOf(item2.getDebitDate()).substring(0,10));
            resultVo.setRepayLimit(String.valueOf(item2.getRepaymentDDL()).substring(0,10));
            resultVo.setAmount(item2.getAmount());
            resultVo.setDiscountPolicy(item2.getDiscountPolicy());
            resultVo.setDiscountLimit(String.valueOf(item2.getDiscountDDL()).substring(0,10));
            resultVo.setRemark(item2.getRemark());
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultVo;
    }

    /**
     * 把辅助信息一的实体类转换为vo
     * @param item1
     * @return
     */
    private SupportItemOneVo supportOneToVo(SupportItem1 item1){
        SupportItemOneVo resultVo=new SupportItemOneVo();
        try{
            resultVo.setCompanyId(item1.getCompanyId());
            resultVo.setVoucherId(item1.getVoucherId());
            resultVo.setVoucherLines(item1.getVoucherLines());
            resultVo.setSupportLines(item1.getSupportLines());
            resultVo.setEndSide(item1.getEndSide());
            resultVo.setSubjectId(item1.getSubjects());
            resultVo.setVariety(item1.getVariety());
            resultVo.setDate(String.valueOf(item1.getDate()).substring(0,10));
            resultVo.setNew(item1.getNew());
            resultVo.setDispatchOnTime(item1.getDispatchOntime());
            resultVo.setReturnedPurchase(item1.getReturnedPurchase());
            resultVo.setInputNum(item1.getInputNum());
            resultVo.setInputUnitPrice(item1.getInputUnitPrice());
            resultVo.setInputAmount(item1.getInputAmount());
            resultVo.setOutputNum(item1.getOutputNum());
            resultVo.setOutputUnitPrice(item1.getOutputUnitPrice());
            resultVo.setOutputAmount(item1.getOutputAmount());
            resultVo.setEndingStocks(item1.getEndingStocks());

        }catch (Exception e){
            e.printStackTrace();
        }

        return resultVo;
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
