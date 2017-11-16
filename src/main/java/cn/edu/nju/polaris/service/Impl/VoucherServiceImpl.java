package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.*;
import cn.edu.nju.polaris.entity.MultiKeysClass.SupportItemMultiKeysClass;
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
import java.text.ParseException;
import java.util.*;

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
                throw new ResourceConflictException("该凭证已存在");
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
            throw new ResourceNotFoundException("该凭证不存在");
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
            throw new ResourceNotFoundException("该凭证不存在");
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
                throw new ResourceConflictException("该凭证条目已存在");
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
                throw new ResourceConflictException("该凭证条目已存在");
            }
        }
        voucherItemRepository.save(itemList);
        return true;
    }

    private boolean deleteOneVoucherItems(String voucherId, long factoryId) {
        Voucher voucher = voucherRepository.findByVoucherIdAndCompanyId(voucherId,factoryId);
        if (voucher == null){
            throw new ResourceNotFoundException("该凭证不存在");
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
            throw new ResourceConflictException("该凭证条目已存在");
        }
        voucherItemRepository.delete(key);
        return true;
    }

    private boolean modifyOneAmountAllItems(String voucherId, long factoryId, ArrayList<VoucherItem> itemList) {
        List<VoucherItem> list = voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,voucherId);
        if (list == null) {
            throw new ResourceNotFoundException("该凭证不存在");
        }
        voucherItemRepository.delete(list);
        voucherItemRepository.save(itemList);
        return true;
    }
    //需要添加对辅助信息一、二的操作！！！

    /**
     * 删除一个凭证中全部的辅助信息一
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @return
     */
    private boolean deleteOneVoucherAllSupportOne(String voucherId,long factoryId){
        supportItem1Repository.deleteByCompanyIdAndVoucherId(factoryId,voucherId);
        return true;
    }

    /**
     * 删除一个凭证条目对应的全部辅助信息一
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @return
     */
    private boolean deleteOneVoucherItemAllSupportOne(String voucherId,long factoryId,int voucherLine){
        supportItem1Repository.deleteAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,voucherLine);
        return true;
    }

    /**
     * 删除一条辅助信息一
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @param supportLine 辅助信息行数
     * @return
     */
    private boolean deleteOneSupportOne(String voucherId,long factoryId,int voucherLine,int supportLine){
        SupportItemMultiKeysClass key = new SupportItemMultiKeysClass();
        key.setCompanyId(factoryId);
        key.setVoucherId(voucherId);
        key.setVoucherLines(voucherLine);
        key.setSupportLines(supportLine);
        SupportItem1 item1 = supportItem1Repository.findOne(key);
        if (item1 == null){
            throw new ResourceNotFoundException("该辅助信息条目不存在");
        }
        supportItem1Repository.delete(key);
        return true;
    }

    /**
     * 删除一个凭证中全部的辅助信息二
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @return
     */
    private boolean deleteOneVoucherAllSupportTwo(String voucherId,long factoryId){
        supportItem2Repository.deleteByCompanyIdAndVoucherId(factoryId,voucherId);
        return true;
    }

    /**
     * 删除一个凭证条目对应的全部辅助信息二
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @return
     */
    private boolean deleteOneVoucherItemAllSupportTwo(String voucherId,long factoryId,int voucherLine){
        supportItem2Repository.deleteAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,voucherLine);
        return true;
    }

    /**
     * 删除一条辅助信息一
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @param supportLine 辅助信息行数
     * @return
     */
    private boolean deleteOneSupportTwo(String voucherId,long factoryId,int voucherLine,int supportLine){
        SupportItemMultiKeysClass key = new SupportItemMultiKeysClass();
        key.setCompanyId(factoryId);
        key.setVoucherId(voucherId);
        key.setVoucherLines(voucherLine);
        key.setSupportLines(supportLine);
        SupportItem2 item2 = supportItem2Repository.findOne(key);
        if (item2 == null){
            throw new ResourceNotFoundException("该辅助信息条目不存在");
        }
        supportItem2Repository.delete(key);
        return true;
    }

    /**
     * 修改一条凭证条目对应的全部的辅助信息一
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @param item1List 辅助信息一列表
     * @return
     */
    private boolean modifyOneItemAllSupportOne(String voucherId,long factoryId,int voucherLine,List<SupportItem1> item1List){
        List<SupportItem1> item1s = supportItem1Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,voucherLine);
        if (item1s == null){
            throw new ResourceNotFoundException("该凭证条目不存在辅助信息");
        }
        supportItem1Repository.delete(item1s);
        supportItem1Repository.save(item1List);
        return true;
    }

    /**
     * 修改一条凭证条目对应的全部的辅助信息二
     * @param voucherId 凭证编号
     * @param factoryId 公司编号
     * @param voucherLine 凭证行数
     * @param item2List 辅助信息二列表
     * @return
     */
    private boolean modifyOneItemAllSupportTwo(String voucherId,long factoryId,int voucherLine,List<SupportItem2> item2List){
        List<SupportItem2> item2s = supportItem2Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,voucherId,voucherLine);
        if (item2s == null){
            throw new ResourceNotFoundException("该凭证条目不存在辅助信息");
        }
        supportItem2Repository.delete(item2s);
        supportItem2Repository.save(item2List);
        return true;
    }


    /**
     * 需要对凭证 凭证条目 科目余额  科目记录 辅助信息！！！这五项属性进行修改
     * 这边传入的date的格式需要是日期的格式 "yyyy-mm-dd" 在进行凭证属性设置的时候会把转换为时间戳的格式
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
                newRecord.setDate(DateHelper.DateToTimeStamp(voucherVO.getDate()));
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

                int voucherLine=count+1;
                if(oneItemVo.getSupportOneList().size()!=0&&oneItemVo.getSupportOneList()!=null){
                    List<SupportItemOneVo> itemOneVoList=oneItemVo.getSupportOneList();
                    for(int i=0;i<itemOneVoList.size();i++){
                        int supportLine=i+1;
                        SupportItemOneVo itemOneVo=itemOneVoList.get(i);
                        SupportItem1 item1=new SupportItem1();

                        try{
                            item1.setCompanyId(itemOneVo.getCompanyId());
                            item1.setVoucherId(itemOneVo.getVoucherId());
                            item1.setVoucherLines(itemOneVo.getVoucherLines());
                            item1.setSupportLines(itemOneVo.getSupportLines());
                            item1.setEndSide(itemOneVo.getEndSide());
                            item1.setSubjects(itemOneVo.getSubjectId());
                            item1.setVariety(itemOneVo.getVariety());
                            item1.setDate(DateHelper.DateToTimeStamp(itemOneVo.getDate()));
                            item1.setNew(itemOneVo.getNew());
                            item1.setDispatchOntime(itemOneVo.getDispatchOnTime());
                            item1.setReturnedPurchase(itemOneVo.getReturnedPurchase());
                            item1.setInputNum(itemOneVo.getInputNum());
                            item1.setInputUnitPrice(itemOneVo.getInputUnitPrice());
                            item1.setInputAmount(itemOneVo.getInputAmount());
                            item1.setOutputNum(itemOneVo.getOutputNum());
                            item1.setOutputUnitPrice(itemOneVo.getOutputUnitPrice());
                            item1.setOutputAmount(itemOneVo.getOutputAmount());
                            item1.setEndingStocks(itemOneVo.getEndingStocks());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        supportItem1Repository.save(item1);
                    }

                }
                if(oneItemVo.getSupportTwoList().size()!=0&&oneItemVo.getSupportTwoList()!=null){
                    List<SupportItemTwoVo> itemTwoVoList=oneItemVo.getSupportTwoList();
                    for(int i=0;i<itemTwoVoList.size();i++){
                        int supportLine=i+1;
                        SupportItemTwoVo itemTwoVo=itemTwoVoList.get(i);
                        SupportItem2 item2=new SupportItem2();

                        try{
                            item2.setCompanyId(itemTwoVo.getCompanyId());
                            item2.setVoucherId(itemTwoVo.getVoucherId());
                            item2.setVoucherLines(itemTwoVo.getVoucherLines());
                            item2.setSupportLines(itemTwoVo.getSupportLines());
                            item2.setSubjects(itemTwoVo.getSubjectId());
                            item2.setCompanyName(itemTwoVo.getCompanyName());
                            item2.setDebitDate(DateHelper.DateToTimeStamp(itemTwoVo.getDebitDate()));
                            item2.setRepaymentDDL(DateHelper.DateToTimeStamp(itemTwoVo.getRepayLimit()));
                            item2.setAmount(itemTwoVo.getAmount());
                            item2.setDiscountPolicy(itemTwoVo.getDiscountPolicy());
                            item2.setDiscountDDL(DateHelper.DateToTimeStamp(itemTwoVo.getDiscountLimit()));
                            item2.setRemark(itemTwoVo.getRemark());

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        supportItem2Repository.save(item2);
                    }
                }
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
            voucherItem.setVoucherId(itemVo.getVoucher_id());
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

    /**
     * 需要取出voucher voucherItem SupportOne SupportTwo
     * TODO 效率可能会很慢
     * @param factoryId
     * @return
     */
    @Override
    public ArrayList<VoucherVO> getCurrentPeriodAllVoucher(long factoryId) {
        String currentMonth=DateHelper.getCurrentMonth();

        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        ArrayList<VoucherVO> resultVoList=new ArrayList<>();

        for(int count=0;count<allVoucherList.size();count++){
            String currentDate=String.valueOf(allVoucherList.get(count).getDate()).substring(0,10);

            if(!currentDate.contains(currentMonth)){
                continue;
            }else{
                VoucherVO oneVoucherVo=voucherToVoucherVo(allVoucherList.get(count));
                //条目 辅助信息一 辅助信息二
                List<VoucherItem> itemList=voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,allVoucherList.get(count).getVoucherId());
                List<VoucherItemVo> itemVoList=new ArrayList<>();
                for(int i=0;i<itemList.size();i++){
                    itemVoList.add(voucherItemToItemVo(itemList.get(i)));
                }

                for(int i=0;i<itemVoList.size();i++){
                    List<SupportItem1> item1List=supportItem1Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,allVoucherList.get(count).getVoucherId(),itemVoList.get(i).getLines());
                    List<SupportItemOneVo> itemOneVoList=supportOneListToVoList(item1List);
                    List<SupportItem2> item2List=supportItem2Repository.findAllByCompanyIdAndVoucherIdAndVoucherLines(factoryId,allVoucherList.get(count).getVoucherId(),itemVoList.get(i).getLines());
                    List<SupportItemTwoVo> itemTwoVoList=supportTwoListToVoList(item2List);

                    itemVoList.get(i).setSupportOneList(itemOneVoList);
                    itemVoList.get(i).setSupportTwoList(itemTwoVoList);
                }

                oneVoucherVo.setItemList((ArrayList<VoucherItemVo>) itemVoList);
                oneVoucherVo.setTotalVo(getVoucherTotal((ArrayList<VoucherItem>) itemList));

                resultVoList.add(oneVoucherVo);
            }

        }


        return resultVoList;
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
        //存储最终结果
        ArrayList<VoucherVO> resultList=new ArrayList<>();
        //存储最终结果的凭证编号
        ArrayList<String> resultVoucherIdList=new ArrayList<>();

        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        List<VoucherVO> allVoucherVoList=new ArrayList<>();


        for(int count=0;count<allVoucherList.size();count++){
            VoucherVO oneVoucherVo=voucherToVoucherVo(allVoucherList.get(count));
            List<VoucherItem> itemList=voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,allVoucherList.get(count).getVoucherId());
            List<VoucherItemVo> itemVoList=new ArrayList<>();
            for(int i=0;i<itemList.size();i++){
                itemVoList.add(voucherItemToItemVo(itemList.get(i)));
            }
            oneVoucherVo.setItemList((ArrayList<VoucherItemVo>) itemVoList);
            oneVoucherVo.setTotalVo(getVoucherTotal((ArrayList<VoucherItem>) itemList));

            allVoucherVoList.add(oneVoucherVo);
        }


        for(int count=0;count<allVoucherVoList.size();count++){

            if(isOneVoucherVoSearched(searchVo,allVoucherVoList.get(count))){
                resultVoucherIdList.add(allVoucherVoList.get(count).getVoucher_id());

            }

        }


        if(resultVoucherIdList.size()==0){
            return null;
        }else{
            for(int count=0;count<resultVoucherIdList.size();count++){
                resultList.add(getOneVoucher(resultVoucherIdList.get(count),factoryId));


            }
            return resultList;
        }


    }



    /**
     * 用来判断一个VoucherVo是否符合搜索条件
     * @param searchVo
     * @param vo
     * @return
     */
    private boolean isOneVoucherVoSearched(VoucherSearchVo searchVo,VoucherVO vo){
        boolean result=true;

        //有一个条件不满足就返回false 全部满足才能返回true

        //处理会计期间
        if(searchVo.getStartPeriod().equals(searchVo.getEndPeriod())){
            String period=searchVo.getStartPeriod();
            String oneMonth= DateConvert.periodToMonth(period);

            //月份不包含搜索的月就直接返回false
            if(!vo.getDate().contains(oneMonth)){
                return false;
            }
        }else{
            String startMonth=DateConvert.periodToMonth(searchVo.getStartPeriod());
            String endMonth=DateConvert.periodToMonth(searchVo.getEndPeriod());
            HashSet<String> betweenMonthSet= null;
            try {
                betweenMonthSet = DateConvert.getBetweenMonth(startMonth,endMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String currentMonth=vo.getDate().substring(0,vo.getDate().lastIndexOf("-"));

            if(!betweenMonthSet.contains(currentMonth)){
                return false;
            }
        }

        //处理凭证字
        String searchCharacter=searchVo.getCharacter();
        if(!searchCharacter.equals("全部")){
            if(!searchCharacter.equals(vo.getVoucher_id().split("-")[0])){
                return false;
            }
        }

        //处理制单人
        String searchMaker=searchVo.getMaker();
        if(!searchMaker.equals("全部")){
            if(!searchMaker.equals(vo.getVoucher_maker())){
                return false;
            }
        }

        //处理摘要和科目id 摘要和科目id都是包含的关系
        String searchAbstract=searchVo.getAbstracts();
        String searchSubject=searchVo.getSubjectId();

        ArrayList<VoucherItemVo> itemVoArrayList=vo.getItemList();

        //每一行都没有摘要 每一行都没有科目id才返回false

        //需要分别处理摘要和科目id

        boolean abstractResult=false;
        if(!(searchAbstract==null||searchAbstract.length()<=0)){
            for(int count=0;count<itemVoArrayList.size();count++){
                VoucherItemVo oneItemVo=itemVoArrayList.get(count);
                boolean result1;
                String oneAbstract=oneItemVo.getAbstracts();

                if(searchAbstract.indexOf(oneAbstract)!=-1||oneAbstract.indexOf(searchAbstract)!=-1){
                    result1=true;
                }else{
                    result1=false;
                }

                abstractResult=abstractResult||result1;
            }

            if(abstractResult==false){
                return false;
            }
        }

        boolean subjectResult=false;
        if(!(searchSubject==null||searchSubject.length()<=0)){
            for(int count=0;count<itemVoArrayList.size();count++){
                VoucherItemVo oneItemVo=itemVoArrayList.get(count);
                boolean result2;
                String oneSubject=oneItemVo.getSubjectId();

                if(searchSubject.indexOf(oneSubject)!=-1||oneSubject.indexOf(searchSubject)!=-1){
                    result2=true;
                }else{
                    result2=false;
                }

                subjectResult=subjectResult||result2;
            }

            if(subjectResult==false){
                return false;
            }
        }

        //处理金额
        double searchLowPrice=searchVo.getLowPrice();
        double searchHighPrice=searchVo.getHighPrice();

        if(!(searchLowPrice==-1.0&&searchHighPrice==-1.0)){
            double currentPrice=vo.getTotalVo().getCreditAmount();
            if(searchLowPrice==-1.0){
                if(currentPrice>searchHighPrice){
                    return false;
                }
            }else if(searchHighPrice==-1.0){
                if(currentPrice<searchLowPrice){
                    return false;
                }


            }else{
                if(currentPrice<searchLowPrice||currentPrice>searchHighPrice){
                    return false;
                }
            }
        }

        //处理凭证号
        int searchLowNumber=searchVo.getLowVoucherNumber();
        int searchHighNumber=searchVo.getHighVoucherNumber();

        if(!(searchLowNumber==-1&&searchHighNumber==-1)){
            int currentNumber=Integer.valueOf(vo.getVoucher_id().split("-")[1]);
            if(searchLowNumber==-1){
                if(currentNumber>searchHighNumber){
                    return false;
                }
            }else if(searchHighNumber==-1){

                if(currentNumber<searchLowNumber){
                    return false;
                }

            }else{
                if(currentNumber<searchLowNumber||currentNumber>searchHighNumber){
                    return false;
                }
            }
        }


        return result;
    }

    /**
     * 需要删除voucher voucherItem supportItem1 supportItem2 subjectRecord需要修改subjectBalance
     * @param voucherId
     * @param factoryId
     * @return
     */
    @Override
    public boolean deleteOneVoucherVo(String voucherId, long factoryId) {
        Voucher voucher=voucherRepository.findByVoucherIdAndCompanyId(voucherId,factoryId);
        List<VoucherItem> itemList=voucherItemRepository.findByCompanyIdAndVoucherId(factoryId,voucherId);

        //先对每一行凭证条目进行处理 把当前期间的科目余额进行修改
        for(int count=0;count<itemList.size();count++){
            VoucherItem oneItem=itemList.get(count);
            String subjectId=oneItem.getSubjects();
            String month=String.valueOf(voucher.getDate()).substring(0,7);
            double debitAmount=oneItem.getDebitAmount();
            double creditAmount=oneItem.getCreditAmount();

            SubjectsBalance beforeBalance=subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(factoryId,subjectId,month);
            SubjectsBalance newBalance=new SubjectsBalance();
            newBalance.setId(beforeBalance.getId());
            newBalance.setCompanyId(beforeBalance.getCompanyId());
            newBalance.setSubjectsId(beforeBalance.getSubjectsId());
            newBalance.setDate(beforeBalance.getDate());
            newBalance.setDebitAmount(beforeBalance.getDebitAmount()-debitAmount);
            newBalance.setCreditAmount(beforeBalance.getCreditAmount()-creditAmount);
            newBalance.setBalance(beforeBalance.getBalance()-SubjectBalanceHelper.getDirection(subjectId)*(debitAmount-creditAmount));

            subjectsBalanceRepository.save(newBalance);
        }

        boolean result3=deleteOneVoucherAllSupportOne(voucherId,factoryId);
        boolean result4=deleteOneVoucherAllSupportTwo(voucherId,factoryId);
        boolean result2=deleteOneVoucherItems(voucherId,factoryId);
        boolean result1=deleteOneVoucher(voucherId,factoryId);
        subjectsRecordRepository.deleteAllByCompanyIdAndVoucherId(factoryId,voucherId);

        boolean result=result1&&result2&&result3&&result4;


        return result;
    }



    @Override
    public boolean deleteSelectedVoucher(ArrayList<String> voucherIdList, long factoryId) {
        boolean result=true;

        if(voucherIdList.size()==0||voucherIdList==null){
            return false;
        }else{
            for(int count=0;count<voucherIdList.size();count++){
                result=deleteOneVoucherVo(voucherIdList.get(count),factoryId)&&result;
            }
        }
        return result;
    }

    @Override
    public boolean amendOneVoucher(VoucherVO voucherVO, String beforeVoucherId) {
        long factoryId=voucherVO.getCompany_id();
        String voucherId=voucherVO.getVoucher_id();
        //对voucherId,factoryId,beforeVoucherId进行操作

        //修改：先进行删除再进行添加
        boolean result=true;
        boolean result1=deleteOneVoucherVo(beforeVoucherId,factoryId);
        boolean result2=saveOneVoucher(voucherVO);
        result=result&result1&result2;

        return result;
    }

    @Override
    public int getCurrentNumber(String voucherCharacter, long factoryId) {
        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        int theMaxNumber=0;


        for(int count=0;count<allVoucherList.size();count++){
            String voucherId=allVoucherList.get(count).getVoucherId();
            String character=voucherId.split("-")[0];
            int number=Integer.valueOf(voucherId.split("-")[1]);

            if(character.equals(voucherCharacter)){
                if(number>theMaxNumber){
                    theMaxNumber=number;
                }
            }

        }
        return theMaxNumber+1;
    }

    @Override
    public double getCurrentVarietyEndingStocks(long factoryId, String variety) {
        double total=0.0;

        List<SupportItem1> allItemOneList=supportItem1Repository.findAllByCompanyId(factoryId);

        for(int count=0;count<allItemOneList.size();count++){
            SupportItem1 oneItem1=allItemOneList.get(count);
            if(oneItem1.getCompanyId()==factoryId&&oneItem1.getVariety().equals(variety)){
                if(oneItem1.getOutputNum()!=null){
                    total=total+oneItem1.getEndingStocks();

                }else if(oneItem1.getInputNum()!=null){
                    total=total-oneItem1.getEndingStocks();
                }


            }
        }
        return total;
    }

    @Override
    public ArrayList<String> getAllExistedPeriod(long factoryId) {
        ArrayList<String> resultList=new ArrayList<>();
        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);

        for(int count=0;count<allVoucherList.size();count++){

            String oneMonth=String.valueOf(allVoucherList.get(count).getDate()).substring(0,7);
            String onePeriod=DateConvert.monthToPeriod(oneMonth);
            if(!resultList.contains(onePeriod)){
                resultList.add(onePeriod);
            }

        }
        return resultList;
    }

    @Override
    public ArrayList<String> getAllExistedMaker(long factoryId) {
        ArrayList<String> resultList=new ArrayList<>();
        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);

        for(int count=0;count<allVoucherList.size();count++){
            String oneMaker=allVoucherList.get(count).getVoucherMaker();
            if(!resultList.contains(oneMaker)){
                resultList.add(oneMaker);
            }
        }
        return resultList;
    }

    @Override
    public ArrayList<String> getAllSubjectId() {
        ArrayList<String> resultList=new ArrayList<>();
        List<Subjects> allSubjectList=subjectsRepository.findAll();

        for(int count=0;count<allSubjectList.size();count++){
            String oneSubjectId=allSubjectList.get(count).getSubjectsId();
            if(!resultList.contains(oneSubjectId)){
                resultList.add(oneSubjectId);

            }

        }
        return resultList;
    }


}
