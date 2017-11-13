package cn.edu.nju.polaris.service.Impl;


import cn.edu.nju.polaris.entity.Subjects;
import cn.edu.nju.polaris.repository.SubjectsRepository;
import cn.edu.nju.polaris.service.SubjectService;
import cn.edu.nju.polaris.vo.SubjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{


    private final SubjectsRepository subjectsRepository;

    @Autowired
    public SubjectServiceImpl(SubjectsRepository subjectsRepository) {
        this.subjectsRepository = subjectsRepository;
    }

    @Override
    public List<SubjectVO> findAllSubject() {
        List<Subjects> list = subjectsRepository.findAll();
        List<SubjectVO> voList = new ArrayList<>();
        for (Subjects subjects : list){
            voList.add(new SubjectVO(subjects.getSubjectsId(),subjects.getSubjectsName(),subjects.getDirection(),subjects.getType()));
        }
        return voList;
    }
}
