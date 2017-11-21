package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.SubjectInitial;

import java.util.List;

public interface SubjectInitialService {

    void saveSubjectInitials(List<SubjectInitial> list);

    List<SubjectInitial> getAllSubjectInitialByCompanyId(Long id);
}
