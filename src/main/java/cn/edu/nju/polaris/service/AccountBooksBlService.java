package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.vo.accountBook.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 处理和账目相关功能的逻辑层接口
 * Created by zhangzy on 2017/11/15 上午11:14
 */
@Service
public interface AccountBooksBlService {

/**
 * searchVo中的startPeriod endPeriod highLevel lowLevel为必填选项
 */

    /**
     * 明细账
     */

    /**
     * 获得全部有数据记录的科目编号
     * @param factoryId
     * @return
     */
    public ArrayList<String> getAllExistedSubjectId(String factoryId);

    /**
     * 获得一个科目的明细账
     * 默认状态是会计期间是当前的期间 科目级别是1到1
     * @param subjectId
     * @param searchVo
     * @param factoryId
     * @return
     */
    public DetailAccountVo getOneSubjectDetail(String subjectId, BookSearchVo searchVo, String factoryId);

    /**
     * 总账
     */

    /**
     * 获得全部科目的总账
     * @param searchVo
     * @param factoryId
     * @return
     */
    public ArrayList<TotalAccountVo> getAllSubjectTotal(BookSearchVo searchVo, String factoryId);

    /**
     * 获得单个科目的总账
     * @param subjectId
     * @param searchVo
     * @param factoryId
     * @return
     */
    public TotalAccountVo getOneSubjectTotal(String subjectId,BookSearchVo searchVo,String factoryId);

    /**
     * 科目余额表
     */

    /**
     * 获取部分科目的科目余额表
     * @param searchVo
     * @param factoryId
     * @return
     */
    public ArrayList<BalanceTableOneClause> getBalanceTableAllClauses(BookSearchVo searchVo, String factoryId);


    /**
     * 科目汇总表
     */

    /**
     * 获取部分科目的科目汇总表
     * @param searchVo
     * @param factoryId
     * @return
     */
    public ArrayList<GatherTableOneClause> getGatherTableAllClauses(BookSearchVo searchVo, String factoryId);

    /**
     * 获得全部科目某一个期间的期末金额信息
     * @param month    "2017-08"
     * @param factoryId
     * @return
     */
    public ArrayList<Subjects> getAllSubjectPeriodEndPrice(String month, String factoryId);

    /**
     * 获得开始科目和结束科目之间所有有改变记录的科目的id和name
     * @param startSubject
     * @param endSubject
     * @return
     */
    public ArrayList<SubjectIdAndNameVo> getBetweenSubject(String startSubject,String endSubject,String factoryId);

    /**
     * 附加的方法
     */

    /**
     * 获得应收账款净额
     * @param factoryId
     * @param startMonth "2010-10"
     * @param endMonth "2011-03"
     * @return
     */
    public double netAccountReceivable(long factoryId,String startMonth,String endMonth);

    /**
     * 获得库存净额
     * @param factoryId
     * @param startMonth "2010-03"
     * @param endMonth "2011-06"
     * @return
     */
    public double netAccountInventory(long factoryId,String startMonth,String endMonth);


}
