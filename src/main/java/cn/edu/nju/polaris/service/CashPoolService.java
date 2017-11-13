package cn.edu.nju.polaris.service;

/**
 * Created by 费慧通 on 2017/11/13.
 */
public interface CashPoolService {
    /**
     * 得到现金流量表里的 现金流入、现金流出、现金池内留存的现金
     * @param company_id
     * @param time
     * @return
     */
    public double[] getCashFlow(long company_id, String time);

    /**
     * 得到与现金有关的财务指标
     * @param company_id
     * @param time
     * @return
     */
    public double[] getFinancialIndex(long company_id, String time);
}
