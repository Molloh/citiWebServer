package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.BalanceSheet;
import cn.edu.nju.polaris.repository.BalanceSheetRepository;
import cn.edu.nju.polaris.service.BalanceSheetService;
import cn.edu.nju.polaris.vo.BalanceSheetItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 费慧通 on 2017/8/7.
 *
 * 资产负债表
 */
@Service
public class BalanceSheetImpl implements BalanceSheetService {

    private final BalanceSheetRepository balanceSheetRepository;

    @Autowired
    public BalanceSheetImpl(BalanceSheetRepository balanceSheetRepository) {
        this.balanceSheetRepository = balanceSheetRepository;
    }

    @Override
    public ArrayList<BalanceSheetItemVo> getBalanceSheet(long company_id, String phase) {
        List<BalanceSheet> list1 = balanceSheetRepository.findByCompanyIdAndPeriod(company_id, phase);
        List<BalanceSheet> list2 = balanceSheetRepository.findByCompanyIdAndPeriod(company_id, getLastYear(phase));

        ArrayList<BalanceSheetItemVo> result = new ArrayList<>();

        boolean has_last_year = true;
        if(list2.size()==0){ //如果该企业没有上一年的数据
            has_last_year = false;
        }
        for(int i=0;i<list1.size();i++){
            BalanceSheet current1 = list1.get(i);
            if(has_last_year){
                BalanceSheet current2 = list2.get(i);
                result.add(new BalanceSheetItemVo(current1.getName(),i+1,current1.getBalance(),current2.getBalance()));
            }else{
                result.add(new BalanceSheetItemVo(current1.getName(),i+1,current1.getBalance(),0.0));
            }
        }
        return result;
    }

    @Override
    //得到上一期期末的总资产、本期期末总资产、总负债、流动资产、流动负债、上一期期末应收帐款、本期期末应收帐款、上期期末存货、本期期末存货、本期所有者权益、上一期所有者权益
    public double[] getValue(long company_id, String phase) {
        double[] value = new double[11];
        String last = getLastPhase(phase);
        BalanceSheet temp1 = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,last,"资产合计");
        if(temp1==null){
            value[0] = 0;
            value[5] = 0;
            value[7] = 0;
            value[10] = 0;
        }else{
            value[0] = temp1.getBalance();
            value[5] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,last,"应收账款").getBalance();
            value[7] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,last,"存货").getBalance();
            value[10] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,last,"所有者权益").getBalance();
        }
        value[1] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"资产合计").getBalance();
        value[2] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"负债合计").getBalance();
        value[3] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"流动资产合计").getBalance();
        value[4] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"流动负债合计").getBalance();
        value[6] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"应收账款").getBalance();
        value[8] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"存货").getBalance();
        value[9] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"所有者权益").getBalance();
        return value;
    }

    @Override
    //得到本期期末总资产、流动资产、流动负债、本期期末应收帐款、本期期末存货
    public double[] getValue2(long company_id, String phase) {
        double[] value = new double[5];
        value[0] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"资产合计").getBalance();
        value[1] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"流动资产合计").getBalance();
        value[2] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"流动负债合计").getBalance();
        value[3] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"应收账款").getBalance();
        value[4] = balanceSheetRepository.findByCompanyIdAndPeriodAndName(company_id,phase,"存货").getBalance();
        return value;
    }

    @Override
    public double getTotalAsset(long comany_id, String phase) {
        return balanceSheetRepository.findByCompanyIdAndPeriodAndName(comany_id,phase,"资产合计").getBalance();
    }

    /**
     * 得到上一年期末时间
     * @param phase
     * @return
     */
    private String getLastYear(String phase){
        int year = Integer.valueOf(phase.substring(0,4));
        return year+"-12";
    }

    /**
     * 得到上一期
     * @param phase
     * @return
     */
    private String getLastPhase(String phase){
        String[] time = phase.split("-");
        int year = Integer.valueOf(time[0]);
        int month = Integer.valueOf(time[1]);
        if(month==1){
            year = year-1;
            month = 12;
        }else {
            month--;
        }
        if(month<10){
            return year+"-0"+month;
        }
        return year+"-"+month;
    }
}
