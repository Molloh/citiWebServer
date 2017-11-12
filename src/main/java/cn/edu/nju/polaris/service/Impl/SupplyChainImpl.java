package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.Account;
import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.AccountRepository;
import cn.edu.nju.polaris.repository.SupplyChainRepository;
import cn.edu.nju.polaris.service.SupplyChainService;
import cn.edu.nju.polaris.vo.SupplyChainVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hyf
 *
 */
@Service
public class SupplyChainImpl implements SupplyChainService{

    private final SupplyChainRepository supplyChainRepository;
    private final AccountRepository accountRepository;


    @Autowired
    public SupplyChainImpl(SupplyChainRepository supplyChainRepository, AccountRepository accountRepository) {
        this.supplyChainRepository = supplyChainRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public List<SupplyChainVO> findChainByUpstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByUpstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findChainByMidstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByMidstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findChainByDownstreamCompany(Long companyId) {
        Account account = accountRepository.findById(companyId);
        if (account == null){
            throw new ResourceNotFoundException("该公司不存在");
        }
        String name = account.getCompanyName();
        List<SupplyChain> entityList = supplyChainRepository.findAllByDownstreamCompany(name);
        List<SupplyChainVO> voList = new ArrayList<>();
        for (SupplyChain entity : entityList){
            voList.add(entity2VO(entity));
        }
        return voList;
    }

    @Override
    public List<SupplyChainVO> findAll() {
        List<SupplyChainVO> voList = new ArrayList<>();
        List<SupplyChain> list = supplyChainRepository.findAll();
        for (SupplyChain chain : list){
            voList.add(entity2VO(chain));
        }
        return voList;
    }

    @Override
    public void addOneChainForUpstream(SupplyChainVO vo) {
        supplyChainRepository.insertUp2Mid(vo.upstreamCompany,vo.midstreamCompany);
    }

    @Override
    public void addOneChainForMidStream(SupplyChainVO vo) {
        SupplyChain temp = supplyChainRepository.findByUpstreamCompanyAndMidstreamCompanyAndDownstreamCompany(vo.upstreamCompany,vo.midstreamCompany,vo.downstreamCompany);
        if (temp != null){
            throw new ResourceConflictException("该条供应链已存在");
        }
        List<String> upstreamList = supplyChainRepository.findAllUpByMid(vo.midstreamCompany);
        List<String> downstreamList = supplyChainRepository.findAllDownByMid(vo.midstreamCompany);
        if (upstreamList.contains(vo.upstreamCompany) && downstreamList.contains(vo.downstreamCompany)){
            SupplyChain chain = new SupplyChain();
            chain.setUpstreamCompany(vo.upstreamCompany);
            chain.setMidstreamCompany(vo.midstreamCompany);
            chain.setDownstreamCompany(vo.downstreamCompany);
            supplyChainRepository.save(chain);
        }else if (!upstreamList.contains(vo.upstreamCompany)){
            throw new ResourceNotFoundException("该企业不是您的上游企业");
        }else if (!downstreamList.contains(vo.downstreamCompany)){
            throw new ResourceNotFoundException("该企业不是您的下游企业");
        }
    }

    @Override
    public void addOneChainForDownStream(SupplyChainVO vo) {
        supplyChainRepository.insertMid2Down(vo.midstreamCompany,vo.downstreamCompany);
    }


    private SupplyChainVO entity2VO(SupplyChain chain){
        SupplyChainVO vo = new SupplyChainVO();
        vo.upstreamCompany = chain.getUpstreamCompany();
        vo.midstreamCompany = chain.getMidstreamCompany();
        vo.downstreamCompany = chain.getDownstreamCompany();
        return vo;
    }
}
