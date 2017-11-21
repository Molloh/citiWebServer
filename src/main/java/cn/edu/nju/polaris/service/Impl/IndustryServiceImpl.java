package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.repository.IndustryRepository;
import cn.edu.nju.polaris.service.IndustryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryServiceImpl implements IndustryService{

    private final IndustryRepository industryRepository;

    public IndustryServiceImpl(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public List<String> findAllFirstIndustry() {
        return industryRepository.findAllFirstIndustry();
    }

    @Override
    public List<String> findAllSecondIndustryByFirstIndustry(String firstIndustry) {
        return industryRepository.findAllSecondIndustryByFirstIndustry(firstIndustry);
    }
}
