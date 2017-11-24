package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.vo.voucher.ItemTotalVo;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.List;

@CacheConfig(cacheNames = "vouchers")
public interface VoucherService {


    //下面是逻辑层的接口

    /**
     * 添加一个新的凭证
     * @param voucherVO
     * @return
     */
    @CachePut
    public boolean saveOneVoucher(VoucherVO voucherVO);

    @CachePut
    public boolean saveSomeVoucher(List<VoucherVO> list);

    /**
     * 获得当前凭证所有条目的合计
     * @param itemList
     * @return
     */
    @Cacheable
    public ItemTotalVo getVoucherTotal(ArrayList<VoucherItem> itemList);

    /**
     * 获得当前月份的会计科目的余额
     * @param subjectId
     * @param factoryId
     * @param month "2010-10"
     * @return
     */
    public double getOneSubjectBalance(String subjectId,long factoryId,String month);

    /**
     * 获得当前时期的全部凭证信息
     * @param factoryId
     * @return
     */
    @Cacheable
    public ArrayList<VoucherVO> getCurrentPeriodAllVoucher(long factoryId);

    /**
     * 根据凭证编号和公司编号获得相对应的一条凭证信息
     * @param voucherId
     * @param factoryId
     * @return
     */
    @Cacheable
    public VoucherVO getOneVoucher(String voucherId,long factoryId);

    /**
     * 获得按条件筛选后的全部的凭证信息
     * ps:加入搜索的vo中的一个字符用户没有填写
     * String传入的是""
     * double传入的是-1.0
     * int传入的是-1
     * @param searchVo
     * @param factoryId
     * @return
     */
    public ArrayList<VoucherVO> getSearchedVoucher(VoucherSearchVo searchVo,long factoryId);

    /**
     * 删除一条凭证
     * @param voucherId
     * @param factoryId
     * @return
     */
    @CachePut
    public boolean deleteOneVoucherVo(String voucherId,long factoryId);

    /**
     * 删除所选择的凭证信息
     * @param voucherIdList
     * @param factoryId
     * @return
     */
    @CachePut
    public boolean deleteSelectedVoucher(ArrayList<String> voucherIdList,long factoryId);

    //TODO excel的环节交给前端处理

    /**
     * 修改一条凭证信息 需要判断凭证编号是否发生改变
     * @param voucherVO
     * @param beforeVoucherId
     * @return
     */
    @CachePut
    public boolean amendOneVoucher(VoucherVO voucherVO,String beforeVoucherId);

    /**
     * 根据输入的凭证字获得当前最新的凭证号
     * @param voucherCharacter
     * @param factoryId
     * @return
     */
    public int getCurrentNumber(String voucherCharacter,long factoryId);


    /**
     * 获得当前的辅助信息一种的某种类的结存量
     * @param factoryId
     * @param variety
     * @return
     */
    public double getCurrentVarietyEndingStocks(long factoryId,String variety);

    /**
     * 获得全部的会计期间
     * @param factoryId
     * @return
     */
    public ArrayList<String> getAllExistedPeriod(long factoryId);

    /**
     * 获得全部的制单人信息
     * @param factoryId
     * @return
     */
    public ArrayList<String> getAllExistedMaker(long factoryId);

    /**
     * 获得全部的科目编号
     * @return
     */
    public ArrayList<String> getAllSubjectId();

}
