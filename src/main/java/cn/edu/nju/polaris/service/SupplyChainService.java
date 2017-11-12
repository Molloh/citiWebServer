package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.SupplyChain;
import cn.edu.nju.polaris.vo.SupplyChainVO;

import java.util.List;

/**
 * 
 * @author hyf
 *
 */
public interface SupplyChainService {

    List<SupplyChainVO> findChainByUpstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByMidstreamCompany(Long companyId);

    List<SupplyChainVO> findChainByDownstreamCompany(Long companyId);

    List<SupplyChainVO> findAll();

    void addOneChainForUpstream(SupplyChainVO vo);

    void addOneChainForMidStream(SupplyChainVO vo);

    void addOneChainForDownStream(SupplyChainVO vo);
}
