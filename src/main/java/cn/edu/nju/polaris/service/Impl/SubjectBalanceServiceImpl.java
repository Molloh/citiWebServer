package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.entity.SubjectsBalance;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.SubjectsBalanceRepository;
import cn.edu.nju.polaris.repository.SubjectsRepository;
import cn.edu.nju.polaris.service.SubjectBalanceService;
import cn.edu.nju.polaris.vo.SubjectBalanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectBalanceServiceImpl implements SubjectBalanceService {

    private final SubjectsBalanceRepository subjectsBalanceRepository;
    private final SubjectsRepository subjectsRepository;

    @Autowired
    public SubjectBalanceServiceImpl(SubjectsBalanceRepository subjectsBalanceRepository, SubjectsRepository subjectsRepository) {
        this.subjectsBalanceRepository = subjectsBalanceRepository;
        this.subjectsRepository = subjectsRepository;
    }


    @Override
    public void initialSubjectBalance(Long companyId, String phase) {
        List<Subjects> subjectsList = subjectsRepository.findAll();
        for (Subjects subject : subjectsList){
            SubjectsBalance balance = subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(companyId,subject.getSubjectsId(),phase);
            if (balance != null){
                throw new ResourceConflictException("已进行过期初设置");
            }
            balance = new SubjectsBalance();
            balance.setSubjectsId(subject.getSubjectsId());
            balance.setCompanyId(companyId);
            balance.setDate(phase);
            balance.setBalance(0.0);
            balance.setDebitAmount(0.0);
            balance.setCreditAmount(0.0);
            subjectsBalanceRepository.save(balance);
        }
    }

    @Override
    public void saveOneSubjectBalance(SubjectBalanceVO vo) {
        if (vo == null){
            throw new ResourceNotFoundException("该科目余额信息为空");
        }
        SubjectsBalance balance = subjectsBalanceRepository.findByCompanyIdAndSubjectsIdAndDate(vo.companyId,vo.subjectId,vo.phase);
        if (balance != null){
            balance.setBalance(vo.balance);
            balance.setCreditAmount(vo.creditAmount);
            balance.setDebitAmount(vo.debitAmount);
            subjectsBalanceRepository.save(balance);
        }else {
            balance = new SubjectsBalance();
            balance.setSubjectsId(vo.subjectId);
            balance.setCompanyId(vo.companyId);
            balance.setDate(vo.phase);
            balance.setBalance(vo.balance);
            balance.setDebitAmount(vo.debitAmount);
            balance.setCreditAmount(vo.creditAmount);
            subjectsBalanceRepository.save(balance);
        }
    }
}
