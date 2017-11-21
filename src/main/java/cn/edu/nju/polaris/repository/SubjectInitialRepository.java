package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SubjectInitial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectInitialRepository extends JpaRepository<SubjectInitial,Long>{

    List<SubjectInitial> findAllByCompanyId(Long id);

    SubjectInitial findByCompanyIdAndSubjectsId(Long companyId,String subjectId);
}
