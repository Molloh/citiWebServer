package cn.edu.nju.polaris.service;

import java.util.List;

public interface IndustryService {

    List<String> findAllFirstIndustry();

    List<String> findAllSecondIndustryByFirstIndustry(String firstIndustry);
}
