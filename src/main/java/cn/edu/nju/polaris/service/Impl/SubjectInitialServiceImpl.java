package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.SubjectInitial;
import cn.edu.nju.polaris.repository.SubjectInitialRepository;
import cn.edu.nju.polaris.service.SubjectInitialService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectInitialServiceImpl implements SubjectInitialService{

    private final SubjectInitialRepository subjectInitialRepository;

    public SubjectInitialServiceImpl(SubjectInitialRepository subjectInitialRepository) {
        this.subjectInitialRepository = subjectInitialRepository;
    }

    @Override
    public void saveSubjectInitials(List<SubjectInitial> list) {
        for (SubjectInitial initial : list){
            saveOne(initial);
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
