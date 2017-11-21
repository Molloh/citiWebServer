package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.SubjectInitial;
import cn.edu.nju.polaris.repository.SubjectInitialRepository;
import cn.edu.nju.polaris.service.SubjectInitialService;
import cn.edu.nju.polaris.service.VoucherService;
import cn.edu.nju.polaris.sheet.CalculateBalanceSheet;
import cn.edu.nju.polaris.sheet.CashFlowTableSheetCal;
import cn.edu.nju.polaris.sheet.ProfitTableSheetCal;
import cn.edu.nju.polaris.util.DateConvert;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SubjectInitialServiceImpl implements SubjectInitialService{

    private final SubjectInitialRepository subjectInitialRepository;
    private final CalculateBalanceSheet calculateBalanceSheet;
    private final CashFlowTableSheetCal cashFlowTableSheetCal;
    private final ProfitTableSheetCal profitTableSheetCal;
    private final VoucherService voucherService;

    public SubjectInitialServiceImpl(SubjectInitialRepository subjectInitialRepository, CalculateBalanceSheet calculateBalanceSheet, CashFlowTableSheetCal cashFlowTableSheetCal, ProfitTableSheetCal profitTableSheetCal, VoucherService voucherService) {
        this.subjectInitialRepository = subjectInitialRepository;
        this.calculateBalanceSheet = calculateBalanceSheet;
        this.cashFlowTableSheetCal = cashFlowTableSheetCal;
        this.profitTableSheetCal = profitTableSheetCal;
        this.voucherService = voucherService;
    }

    @Override
    public void saveSubjectInitials(List<SubjectInitial> list) {
        Long companyId = list.get(0).getCompanyId();
        for (SubjectInitial initial : list){
            saveOne(initial);
        }
        List<String> periods = voucherService.getAllExistedPeriod(companyId);
        for (String period : periods){
            calculateBalanceSheet.UpdateBalanceSheet(companyId, DateConvert.periodToMonth(period));
            profitTableSheetCal.UpdateProfitTable(DateConvert.periodToMonth(period),companyId);
            cashFlowTableSheetCal.UpdateCashFlowTable(DateConvert.periodToMonth(period),companyId);
        }
    }

    @Override
    public List<SubjectInitial> getAllSubjectInitialByCompanyId(Long id) {
        return subjectInitialRepository.findAllByCompanyId(id);
    }

    private void saveOne(SubjectInitial initial){
        SubjectInitial temp = subjectInitialRepository.findByCompanyIdAndSubjectsId(initial.getCompanyId(),initial.getSubjectsId());
        if (temp != null){
            temp.setBalance(initial.getBalance());
            temp.setCreditAmount(initial.getCreditAmount());
            temp.setDebitAmount(initial.getDebitAmount());
            temp.setYearBalance(initial.getYearBalance());
            subjectInitialRepository.save(temp);
        }else {
            temp = new SubjectInitial();
            temp.setCompanyId(initial.getCompanyId());
            temp.setSubjectsId(initial.getSubjectsId());
            temp.setBalance(initial.getBalance());
            temp.setCreditAmount(initial.getCreditAmount());
            temp.setDebitAmount(initial.getDebitAmount());
            temp.setYearBalance(initial.getYearBalance());
            subjectInitialRepository.save(temp);
        }
    }
}
