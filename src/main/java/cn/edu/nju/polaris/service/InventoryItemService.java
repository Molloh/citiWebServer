package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.entity.InventoryItem;
import cn.edu.nju.polaris.vo.InventoryItemVO;

import java.util.List;

public interface InventoryItemService {

    void saveOneInventoryItem(InventoryItemVO vo);

    void deleteOneInventoryItem(InventoryItemVO vo);

    void saveMoreInventoryItem(List<InventoryItemVO> list);
}
