package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.Voucher;
import cn.edu.nju.polaris.entity.VoucherItem;
import cn.edu.nju.polaris.vo.voucher.ItemTotalVo;
import cn.edu.nju.polaris.vo.voucher.VoucherSearchVo;
import cn.edu.nju.polaris.vo.voucher.VoucherVO;

import java.util.ArrayList;

public interface VoucherService {

//    void saveVoucher(Voucher voucher);
//
//    //voucher相关的操作
//    boolean addVoucher(Voucher voucher);
//
//    boolean deleteOneVoucher(String voucherId,long factoryId);
//
//    boolean deleteOneFactoryAllVoucher(long factoryId);
//
//    //只能修改 个公司id下的凭证  能改成其他公司的id号 然后需要判断修改后的voucherId是否已经被使
//    boolean modifyOneVoucher(Voucher voucher,String beforeVoucherId);
//
//    //voucherItem相关的操作
//    boolean addOneItem(VoucherItem voucherItem);
//
//    boolean addSeveralItems(ArrayList<VoucherItem> itemList);
//
//    boolean deleteOneVoucherItems(String voucherId,long factoryId);
//
//    boolean deleteOneItem(String voucherId,long factoryId,int line);
//
//
//    // 更改一个凭证的全部的item
//    boolean modifyOneAmountAllItems(String voucherId,long factoryId,ArrayList<VoucherItem> itemList);

    //下面是逻辑层的接口

    /**
     * 添加一个新的凭证
     * @param voucherVO
     * @return
     */
    public boolean saveOneVoucher(VoucherVO voucherVO);

    /**
     * 获得当前凭证所有条目的合计
     * @param itemList
     * @return
     */
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
    public ArrayList<VoucherVO> getCurrentPeriodAllVoucher(long factoryId);

    /**
     * 根据凭证编号和公司编号获得相对应的一条凭证信息
     * @param voucherId
     * @param factoryId
     * @return
     */
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
     * 删除所选择的凭证信息
     * @param voucherIdList
     * @param factoryId
     * @return
     */
    public boolean deleteSelectedVoucher(ArrayList<String> voucherIdList,long factoryId);

    //TODO excel的环节交给前端处理

    /**
     * 修改一条凭证信息 需要判断凭证编号是否发生改变
     * @param voucherVO
     * @param beforeVoucherId
     * @return
     */
    public boolean amendOneVoucher(VoucherVO voucherVO,String beforeVoucherId);

    /**
     * 根据输入的凭证字获得当前最新的凭证号
     * @param voucherCharacter
     * @param factoryId
     * @return
     */
    public int getCurrentNumber(String voucherCharacter,long factoryId);

    /**
     * 获得数据库中全部的会计期间
     * @param factoryId
     * @return
     */
    public ArrayList<String> getAllPeriod(long factoryId);

    /**
     * 获得数据库中全部存在的制单人
     * @param factoryId
     * @return
     */
    public ArrayList<String> getAllVoucherMaker(long factoryId);

}
