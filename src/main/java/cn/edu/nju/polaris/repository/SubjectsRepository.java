package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.Subjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectsRepository extends JpaRepository<Subjects,String>{

    Subjects findBySubjectsId(String subjectsId);

    List<Subjects> findAll();
}
