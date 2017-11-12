package cn.edu.nju.polaris.repository;

import cn.edu.nju.polaris.entity.SupplyChain;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SupplyChainRepository extends JpaRepository<SupplyChain,Long>{

    List<SupplyChain> findAllByUpstreamCompany(String companyName);

    List<SupplyChain> findAllByMidstreamCompany(String companyName);

    List<SupplyChain> findAllByDownstreamCompany(String companyName);

    List<SupplyChain> findAll();

    SupplyChain findByUpstreamCompanyAndMidstreamCompanyAndDownstreamCompany(String upstreamCompany, String midstreamCompany, String downstreamCompany);

    @Query(value = "select upper_name from supply_chain_up2mid where middle_name=?1",nativeQuery = true)
    List<String> findAllUpByMid(String midCompanyName);

    @Query(value = "select down_name from supply_chain_mid2down where middle_name=?1",nativeQuery = true)
    List<String> findAllDownByMid(String midCompanyName);

    @Modifying
    @Transactional
    @Query(value = "insert into supply_chain_up2mid(upper_name, middle_name) values(?1,?2) ",nativeQuery = true)
    void insertUp2Mid(String upperName, String midName);

    @Modifying
    @Transactional
    @Query(value = "insert into supply_chain_mid2down(middle_name, down_name) values(?1,?2)",nativeQuery = true)
    void insertMid2Down(String midName, String downName);
}
