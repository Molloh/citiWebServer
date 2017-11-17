package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.SubjectBalanceVO;

public interface SubjectBalanceService {

    void initialSubjectBalance(Long companyId,String phase);

    void saveOneSubjectBalance(SubjectBalanceVO vo);
}
