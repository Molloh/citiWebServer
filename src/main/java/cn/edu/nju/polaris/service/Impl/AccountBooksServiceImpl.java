package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.service.AccountBooksBlService;
import cn.edu.nju.polaris.vo.accountBook.*;

import java.util.ArrayList;

/**
 * Created by zhangzy on 2017/11/15 上午11:17
 */
public class AccountBooksServiceImpl implements AccountBooksBlService{


    @Override
    public ArrayList<String> getAllExistedSubjectId(String factoryId) {
        return null;
    }

    @Override
    public DetailAccountVo getOneSubjectDetail(String subjectId, BookSearchVo searchVo, String factoryId) {
        return null;
    }

    @Override
    public ArrayList<TotalAccountVo> getAllSubjectTotal(BookSearchVo searchVo, String factoryId) {
        return null;
    }

    @Override
    public TotalAccountVo getOneSubjectTotal(String subjectId, BookSearchVo searchVo, String factoryId) {
        return null;
    }

    @Override
    public ArrayList<BalanceTableOneClause> getBalanceTableAllClauses(BookSearchVo searchVo, String factoryId) {
        return null;
    }

    @Override
    public ArrayList<GatherTableOneClause> getGatherTableAllClauses(BookSearchVo searchVo, String factoryId) {
        return null;
    }

    @Override
    public ArrayList<Subjects> getAllSubjectPeriodEndPrice(String month, String factoryId) {
        return null;
    }

    @Override
    public ArrayList<SubjectIdAndNameVo> getBetweenSubject(String startSubject, String endSubject, String factoryId) {
        return null;
    }

    @Override
    public double netAccountReceivable(long factoryId, String startMonth, String endMonth) {
        return 0;
    }

    @Override
    public double netAccountInventory(long factoryId, String startMonth, String endMonth) {
        return 0;
    }
}
