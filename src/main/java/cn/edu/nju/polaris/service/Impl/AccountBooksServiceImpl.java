package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.*;
import cn.edu.nju.polaris.repository.*;
import cn.edu.nju.polaris.service.AccountBooksBlService;
import cn.edu.nju.polaris.util.DateConvert;
import cn.edu.nju.polaris.util.SubjectBalanceHelper;
import cn.edu.nju.polaris.vo.accountBook.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * Created by zhangzy on 2017/11/15 上午11:17
 */
@Service
public class AccountBooksServiceImpl implements AccountBooksBlService{

    private final VoucherRepository voucherRepository;
    private final SubjectsRepository subjectsRepository;
    private final SubjectsRecordRepository subjectsRecordRepository;
    private final SupportItem1Repository supportItem1Repository;
    private final SupportItem2Repository supportItem2Repository;

    @Autowired
    public AccountBooksServiceImpl(VoucherRepository voucherRepository, SubjectsRepository subjectsRepository, SubjectsRecordRepository subjectsRecordRepository, SupportItem1Repository supportItem1Repository, SupportItem2Repository supportItem2Repository) {
        this.voucherRepository=voucherRepository;
        this.subjectsRepository=subjectsRepository;
        this.subjectsRecordRepository=subjectsRecordRepository;
        this.supportItem1Repository=supportItem1Repository;
        this.supportItem2Repository=supportItem2Repository;
    }


    @Override
    public ArrayList<String> getAllExistedSubjectId(long factoryId) {
        ArrayList<String> resultList=new ArrayList<>();

        List<SubjectsRecord> allRecordList=subjectsRecordRepository.findAllByCompanyId(factoryId);
        if(allRecordList.size()==0||allRecordList==null){
            return resultList;

        }else{

            for(int count=0;count<allRecordList.size();count++){
                String oneSubjectId=allRecordList.get(count).getSubjectsId();
                if(resultList.contains(oneSubjectId)){
                    continue;
                }else{
                    resultList.add(oneSubjectId);

                }


            }



        }
        return resultList;

    }

    @Override
    public DetailAccountVo getOneSubjectDetail(String subjectId, BookSearchVo searchVo, long factoryId) {
        DetailAccountVo resultvo=new DetailAccountVo();
        ArrayList<DetailAccountAmountVo> resultAmountVo=new ArrayList<>();

        HashMap<String,String> subjectIdToNameMap=new HashMap<>();

        List<Subjects> allSubjectList=subjectsRepository.findAll();
        for(int count=0;count<allSubjectList.size();count++){
            Subjects oneSubject=allSubjectList.get(count);
            subjectIdToNameMap.put(oneSubject.getSubjectsId(),oneSubject.getSubjectsName());
        }
        //subjectIdToNameMap赋值完成

        resultvo.setSubjectId(subjectId);
        resultvo.setSubkectName(subjectIdToNameMap.get(subjectId));


        String startMonth=DateConvert.periodToMonth(searchVo.getStartPeriod());
        String endMonth=DateConvert.periodToMonth(searchVo.getEndPeriod());
        ArrayList<String> betweenMonthList= null;
        try {
            betweenMonthList = DateConvert.getBetweenMonthList(startMonth,endMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        HashSet<String> betweenMonthSet=new HashSet<>();
        for(int count=0;count<betweenMonthList.size();count++){
            betweenMonthSet.add(betweenMonthList.get(count));
        }

        if(!(searchVo.getStartSubjectId()==null||searchVo.getEndSubjectId()==null)){
            int startSubjectId=Integer.valueOf(searchVo.getStartSubjectId());
            int endSubjectId=Integer.valueOf(searchVo.getEndSubjectId());
            int currentSubjectId=Integer.valueOf(String.valueOf(subjectId));

            if(currentSubjectId<startSubjectId||endSubjectId<currentSubjectId){
                resultvo.setAmountVoArrayList(new ArrayList<DetailAccountAmountVo>());
                return resultvo;
            }

        }

        if(!(searchVo.getLowLevel()==0||searchVo.getHighLevel()==0)){
            if(searchVo.getLowLevel()>searchVo.getHighLevel()){
                return null;
            }else{
                int subjectLevel= SubjectBalanceHelper.getSubjectLevel(subjectId);
                if(subjectLevel<searchVo.getLowLevel()||searchVo.getHighLevel()<subjectLevel){
                    return null;
                }
            }
        }


        //如果需要得到一个期间的期初的金额 先把第一天到最后一个搜索月的信息取出来 然后进行遍历筛选


        //需要对subjectsPOArrayList按照时间来进行排序
        List<SubjectsRecord> allRecordList=subjectsRecordRepository.findAllByCompanyId(factoryId);

        List<SubjectsRecord> subjectsPOArrayList=new ArrayList<>();
        for(int count=0;count<allRecordList.size();count++){
            if(allRecordList.get(count).getSubjectsId().equals(subjectId)){
                subjectsPOArrayList.add(allRecordList.get(count));
            }
        }

        //处理betweenMonthList


        //用来先把结果进行分类
        HashMap<String,ArrayList<DetailAccountAmountVo>> monthToDetailMap=new HashMap<>();

        for(int count=betweenMonthList.indexOf(startMonth);count<=betweenMonthList.indexOf(endMonth);count++){
            String month=betweenMonthList.get(count);
            //对当前的月份进行处理 期初的金额是上个月的余额 期末的金额是这个月的余额


            ArrayList<DetailAccountAmountVo> newList=new ArrayList<>();

            //本期合计=本期发生额
            DetailAccountAmountVo periodTotal=new DetailAccountAmountVo();
            //本年累计
            DetailAccountAmountVo yearTotal=new DetailAccountAmountVo();



            periodTotal.setDate(DateConvert.getMonthLastDate(month));
            periodTotal.setSubject(subjectId);

            periodTotal.setAbstracts("本期合计");
            periodTotal.setDebitAmount(0.0);
            periodTotal.setCreditAmount(0.0);
            periodTotal.setDirection(SubjectBalanceHelper.getDirectionString(subjectId));
            periodTotal.setBalance(0.0);

            yearTotal.setDate(DateConvert.getMonthLastDate(month));
            yearTotal.setSubject(subjectId);
            yearTotal.setAbstracts("本年累计");
            yearTotal.setDebitAmount(0.0);
            yearTotal.setCreditAmount(0.0);
            yearTotal.setDirection(SubjectBalanceHelper.getDirectionString(subjectId));
            yearTotal.setBalance(0.0);

            newList.add(periodTotal);
            newList.add(yearTotal);


            monthToDetailMap.put(month,newList);
        }

        DetailAccountAmountVo beginNumber=new DetailAccountAmountVo();
        beginNumber.setDate(startMonth+"-01");
        beginNumber.setSubject(subjectId);
        beginNumber.setAbstracts("期初余额");
        beginNumber.setDebitAmount(0.0);
        beginNumber.setCreditAmount(0.0);
        beginNumber.setDirection("平");
        beginNumber.setBalance(0.0);

        //已经完成期初金额的计算
        for(int count=0;count<subjectsPOArrayList.size();count++){
            SubjectsRecord oneRecord=subjectsPOArrayList.get(count);
            String currentMonth=String.valueOf(oneRecord.getDate()).substring(0,7);
            String firstMonth=betweenMonthList.get(0);
            //需要保证currentMonth在第一个月之前
            if((!betweenMonthSet.contains(currentMonth))&&DateConvert.isCurrentMonthBeforeMonth(currentMonth,firstMonth)){
                beginNumber.setDebitAmount(beginNumber.getDebitAmount()+oneRecord.getDebitAmount());
                beginNumber.setCreditAmount(beginNumber.getCreditAmount()+oneRecord.getCreditAmount());
                beginNumber.setBalance(SubjectBalanceHelper.getDirection(subjectId)*(beginNumber.getDebitAmount()-beginNumber.getCreditAmount()));
                if(SubjectBalanceHelper.getDirection(subjectId)==1){
                    beginNumber.setDirection("借");
                }else{
                    beginNumber.setDirection("贷");
                }

            }else if(betweenMonthSet.contains(currentMonth)){
                //每一期的本期合计要随之发生变化   在所有的计算完成之后需要对本年累计进行计算
                DetailAccountAmountVo oneAmountVo=new DetailAccountAmountVo();
                oneAmountVo.setDate(String.valueOf(oneRecord.getDate()).substring(0,7));
                oneAmountVo.setVoucherId(oneRecord.getVoucherId());
                oneAmountVo.setSubject(oneRecord.getSubjectsId());
                oneAmountVo.setAbstracts(oneRecord.getAbstracts());
                oneAmountVo.setDebitAmount(oneRecord.getDebitAmount());
                oneAmountVo.setCreditAmount(oneRecord.getCreditAmount());
                oneAmountVo.setDirection(SubjectBalanceHelper.getDirectionString(subjectId));
                oneAmountVo.setBalance(SubjectBalanceHelper.getDirection(subjectId)*(oneRecord.getDebitAmount()-oneRecord.getCreditAmount()));

                monthToDetailMap.get(currentMonth).add(oneAmountVo);

                DetailAccountAmountVo oneVo=monthToDetailMap.get(currentMonth).get(0);
                oneVo.setDebitAmount(oneVo.getDebitAmount()+oneRecord.getDebitAmount());
                oneVo.setCreditAmount(oneVo.getCreditAmount()+oneRecord.getCreditAmount());
                if(oneVo.getDirection().equals("平")){
                    oneVo.setDirection(SubjectBalanceHelper.getDirectionString(subjectId));
                }
                oneVo.setBalance(SubjectBalanceHelper.getDirection(subjectId)*(oneVo.getDebitAmount())-oneVo.getCreditAmount());
                monthToDetailMap.get(currentMonth).set(0,oneVo);
            }
        }
        //monthToDetailMap的列表中 0是本期合计 1是本年累计 然后后面的是每一个凭证金额对应的明细账

        resultAmountVo.add(beginNumber);

        //对其中的全部月份的本年累计进行计算
        for(int count=betweenMonthList.indexOf(startMonth);count<=betweenMonthList.indexOf(endMonth);count++){
            String currentMonth=betweenMonthList.get(count);
            ArrayList<String> beforeMonthList=new ArrayList<>();

            try {
                beforeMonthList=DateConvert.getThisYearBeforeMonths(currentMonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DetailAccountAmountVo oneVo=monthToDetailMap.get(currentMonth).get(1);
            double oneDebit=0.0;
            double oneCredit=0.0;
            double oneBalance=0.0;

            for(int index=0;index<beforeMonthList.size();index++){
                String oneMonth=beforeMonthList.get(index);
                if(!monthToDetailMap.containsKey(oneMonth)){
                    continue;
                }else{
                    oneDebit=oneDebit+monthToDetailMap.get(oneMonth).get(0).getDebitAmount();
                    oneCredit=oneCredit+monthToDetailMap.get(oneMonth).get(0).getCreditAmount();
                }
            }
            oneBalance=SubjectBalanceHelper.getDirection(subjectId)*(oneDebit-oneCredit);

            oneVo.setDebitAmount(oneDebit);
            oneVo.setCreditAmount(oneCredit);
            oneVo.setBalance(oneBalance);

            monthToDetailMap.get(currentMonth).set(1,oneVo);

            resultAmountVo.addAll(monthToDetailMap.get(currentMonth));

        }
        resultvo.setAmountVoArrayList(resultAmountVo);

        return resultvo;
    }

    @Override
    public ArrayList<TotalAccountVo> getAllSubjectTotal(BookSearchVo searchVo, long factoryId) {
        ArrayList<TotalAccountVo> resultVoList=new ArrayList<>();

        ArrayList<String> allSubjectIdList=new ArrayList<>();

        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        for(int count=0;count<allVoucherList.size();count++){
            allSubjectIdList.add(allVoucherList.get(count).getVoucherId());
        }


        List<SubjectsRecord> allSubjectRecordList=subjectsRecordRepository.findAllByCompanyId(factoryId);

        HashMap<String,ArrayList<SubjectsRecord>> subjectIdToRecordMap=new HashMap<>();

        for(int count=0;count<allSubjectRecordList.size();count++){
            String oneSubjectId=allSubjectRecordList.get(count).getSubjectsId();
            if(subjectIdToRecordMap.containsKey(oneSubjectId)){
                subjectIdToRecordMap.get(oneSubjectId).add(allSubjectRecordList.get(count));

            }else{
                ArrayList<SubjectsRecord> subjectsPOArrayList=new ArrayList<>();
                subjectsPOArrayList.add(allSubjectRecordList.get(count));
                subjectIdToRecordMap.put(oneSubjectId,subjectsPOArrayList);

            }
        }

        for(int count=0;count<allSubjectIdList.size();count++){
            String oneSubjectId=allSubjectIdList.get(count);

            if(subjectIdToRecordMap.containsKey(oneSubjectId)){
//                if(subjectIdToRecordMap.get(oneSubjectId).size()>1){
//
//
//                }
                resultVoList.add(getOneSubjectTotal(oneSubjectId,searchVo,factoryId));
            }
        }
        return resultVoList;
    }

    @Override
    public TotalAccountVo getOneSubjectTotal(String subjectId, BookSearchVo searchVo, long factoryId) {
        TotalAccountVo resultVo=new TotalAccountVo();
        ArrayList<TotalAccountAmountVo> totalAccountAmountVoList=new ArrayList<>();

        HashMap<String,String> subjectIdToNameMap=new HashMap<>();

        List<Subjects> allSubjectList=subjectsRepository.findAll();
        for(int count=0;count<allSubjectList.size();count++){
            Subjects oneSubject=allSubjectList.get(count);
            subjectIdToNameMap.put(oneSubject.getSubjectsId(),oneSubject.getSubjectsName());
        }


        DetailAccountVo detailVo=getOneSubjectDetail(subjectId,searchVo,factoryId);

        ArrayList<DetailAccountAmountVo> detailAmountList=detailVo.getAmountVoArrayList();

        for(int count=0;count<detailAmountList.size();count++){
            DetailAccountAmountVo oneAmountVo=detailAmountList.get(count);

            String oneAbstract=oneAmountVo.getAbstracts();

            if(oneAbstract==null){
                continue;
            }else if(oneAbstract.equals("期初余额")||oneAbstract.equals("本期合计")||oneAbstract.equals("本年累计")){
                TotalAccountAmountVo totalAccountAmountVo=new TotalAccountAmountVo();
                totalAccountAmountVo.setSubjectId(subjectId);
                totalAccountAmountVo.setSubjectName(subjectIdToNameMap.get(subjectId));
                totalAccountAmountVo.setPeriod(DateConvert.monthToPeriod(oneAmountVo.getDate().substring(0,7)));
                totalAccountAmountVo.setAbstracts(oneAmountVo.getAbstracts());
                totalAccountAmountVo.setDebitAmount(oneAmountVo.getDebitAmount());
                totalAccountAmountVo.setCreditAmount(oneAmountVo.getCreditAmount());
                totalAccountAmountVo.setDirection(oneAmountVo.getDirection());
                totalAccountAmountVo.setBalance(oneAmountVo.getBalance());

                totalAccountAmountVoList.add(totalAccountAmountVo);



            }else{
                continue;
            }


        }

        resultVo.setSubjectId(subjectId);
        resultVo.setSubjectName(subjectIdToNameMap.get(subjectId));
        resultVo.setAmountVoArrayList(totalAccountAmountVoList);

        return resultVo;
    }

    @Override
    public ArrayList<BalanceTableOneClause> getBalanceTableAllClauses(BookSearchVo searchVo, long factoryId) {
        ArrayList<BalanceTableOneClause> resultList=new ArrayList<>();

        ArrayList<TotalAccountVo> allSubjectTotal=getAllSubjectTotal(searchVo,factoryId);

        for(int count=0;count<allSubjectTotal.size();count++){
            TotalAccountVo oneAccountVo=allSubjectTotal.get(count);
            String oneSubjectId=oneAccountVo.getSubjectId();

            ArrayList<TotalAccountAmountVo> amountVoList=oneAccountVo.getAmountVoArrayList();

            BalanceTableOneClause oneResultClause=new BalanceTableOneClause();
            oneResultClause.setSubjectId(oneAccountVo.getSubjectId());
            oneResultClause.setSubjectName(oneAccountVo.getSubjectName());

            if(amountVoList.size()==0){
                continue;
            }

            TotalAccountAmountVo periodBeginVo=amountVoList.get(0);
            oneResultClause.setBeginDebit(periodBeginVo.getDebitAmount());
            oneResultClause.setBeginCredit(periodBeginVo.getCreditAmount());

            double debit=0.0;
            double credit=0.0;

            if(amountVoList.size()!=0){
                for(int index=0;index<amountVoList.size();index++){
                    TotalAccountAmountVo oneAmountVo=amountVoList.get(index);
                    if(oneAmountVo.getAbstracts().equals("本期合计")){
                        debit=debit+oneAmountVo.getDebitAmount();
                        credit=credit+oneAmountVo.getCreditAmount();
                    }
                }
            }

            oneResultClause.setCurrentDebit(debit);
            oneResultClause.setCurrentCredit(credit);

            if(SubjectBalanceHelper.getDirection(oneSubjectId)==1){
                oneResultClause.setEndDebit(oneResultClause.getBeginDebit()+oneResultClause.getCurrentDebit()-oneResultClause.getBeginCredit()-oneResultClause.getCurrentCredit());
                oneResultClause.setEndDebit(0.0);

            }else{
                oneResultClause.setEndDebit(0.0);
                oneResultClause.setEndCredit(oneResultClause.getBeginCredit()+oneResultClause.getCurrentCredit()-oneResultClause.getBeginDebit()-oneResultClause.getCurrentDebit());

            }

            resultList.add(oneResultClause);

        }

        BalanceTableOneClause totalClause=new BalanceTableOneClause();
        totalClause.setSubjectId("合计");
        double beginDebit=0.0;
        double beginCredit=0.0;
        double currentDebit=0.0;
        double currentCredit=0.0;
        double endDebit=0.0;
        double endCredit=0.0;

        for(int count=0;count<resultList.size();count++){
            BalanceTableOneClause oneClause=resultList.get(count);

            beginDebit=beginDebit+oneClause.getBeginDebit();
            beginCredit=beginCredit+oneClause.getBeginCredit();
            currentDebit=currentDebit+oneClause.getCurrentDebit();
            currentCredit=currentCredit+oneClause.getCurrentCredit();
            endDebit=endDebit+oneClause.getEndDebit();
            endCredit=endCredit+oneClause.getEndCredit();
        }
        totalClause.setBeginDebit(beginDebit);
        totalClause.setBeginCredit(beginCredit);
        totalClause.setCurrentDebit(currentDebit);
        totalClause.setCurrentCredit(currentCredit);
        totalClause.setEndDebit(endDebit);
        totalClause.setEndCredit(endCredit);

        resultList.add(totalClause);

        return resultList;
    }

    @Override
    public ArrayList<GatherTableOneClause> getGatherTableAllClauses(BookSearchVo searchVo, long factoryId) {
        ArrayList<GatherTableOneClause> resultList=new ArrayList<>();

        ArrayList<BalanceTableOneClause> balanceList=getBalanceTableAllClauses(searchVo,factoryId);

        for(int count=0;count<balanceList.size();count++){
            BalanceTableOneClause oneBalance=balanceList.get(count);

            GatherTableOneClause oneGather=new GatherTableOneClause();
            oneGather.setSubjectId(oneBalance.getSubjectId());
            oneGather.setSubjectName(oneBalance.getSubjectName());
            oneGather.setDebitTotal(oneBalance.getCurrentDebit());
            oneGather.setCreditTotal(oneBalance.getCurrentCredit());

            resultList.add(oneGather);

        }

        return resultList;
    }


    @Override
    public ArrayList<SubjectIdAndNameVo> getBetweenSubject(String startSubject, String endSubject, long factoryId) {
        ArrayList<String> allSubjectIdList=new ArrayList<>();
        List<Subjects> allSubjectsList=subjectsRepository.findAll();
        for(int count=0;count<allSubjectsList.size();count++){
            allSubjectIdList.add(allSubjectsList.get(count).getSubjectsId());
        }

        Collections.sort(allSubjectIdList);

        int startIndex=allSubjectIdList.indexOf(startSubject);
        int endIndex=allSubjectIdList.indexOf(endSubject);

        System.out.println(startIndex+" "+endIndex);

        ArrayList<SubjectIdAndNameVo> resultList=new ArrayList<>();

        HashMap<String,String> subjectIdToNameMap=new HashMap<>();

        List<Subjects> allSubjectList=subjectsRepository.findAll();
        for(int count=0;count<allSubjectList.size();count++){
            Subjects oneSubject=allSubjectList.get(count);
            subjectIdToNameMap.put(oneSubject.getSubjectsId(),oneSubject.getSubjectsName());
        }
        for(int count=0;count<allSubjectIdList.size();count++){
            if(startIndex<=count&&count<=endIndex){
                String currentSubjectId=allSubjectIdList.get(count);
                resultList.add(new SubjectIdAndNameVo(currentSubjectId,subjectIdToNameMap.get(currentSubjectId)));

            }


        }
        return resultList;
    }

    @Override
    public double netAccountReceivable(long factoryId, String startMonth, String endMonth) throws ParseException {
        double result=0.0;

        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        HashMap<String,String> voucherIdToMonthMap=new HashMap<>();
        for(int count=0;count<allVoucherList.size();count++){
            Voucher oneVoucher=allVoucherList.get(count);
            voucherIdToMonthMap.put(oneVoucher.getVoucherId(),String.valueOf(oneVoucher.getDate()).substring(0,7));
        }


        List<SupportItem2> item2List=supportItem2Repository.findAllByCompanyId(factoryId);
        HashSet<String> betweenMonthSet=DateConvert.getBetweenMonth(startMonth,endMonth);

        for(int count=0;count<item2List.size();count++){
            SupportItem2 oneItem2=item2List.get(count);
            if(betweenMonthSet.contains(voucherIdToMonthMap.get(oneItem2.getVoucherId()))){
                if(oneItem2.getSubjects().equals("1122")){
                    result=result+oneItem2.getAmount();
                }else{
                    result=result-oneItem2.getAmount();
                }


            }
        }
        return result;
    }

    @Override
    public double netAccountInventory(long factoryId, String startMonth, String endMonth) throws ParseException {
        double result=0.0;

        List<Voucher> allVoucherList=voucherRepository.findByCompanyId(factoryId);
        HashMap<String,String> voucherIdToMonthMap=new HashMap<>();
        for(int count=0;count<allVoucherList.size();count++){
            Voucher oneVoucher=allVoucherList.get(count);
            voucherIdToMonthMap.put(oneVoucher.getVoucherId(),String.valueOf(oneVoucher.getDate()).substring(0,7));
        }

        List<SupportItem1> item1List=supportItem1Repository.findAllByCompanyId(factoryId);
        HashSet<String> betweenMonthSet=DateConvert.getBetweenMonth(startMonth,endMonth);

        for(int count=0;count<item1List.size();count++){
            SupportItem1 oneItem1=item1List.get(count);

            if(betweenMonthSet.contains(voucherIdToMonthMap.get(oneItem1.getVoucherId()))){
                if(oneItem1.getInputAmount()!=null){
                    result=result+oneItem1.getInputAmount();
                }

                if(oneItem1.getOutputAmount()!=null){
                    result=result-oneItem1.getOutputAmount();
                }


            }


        }
        return result;
    }


}
