package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.entity.InventoryItem;
import cn.edu.nju.polaris.exception.ResourceConflictException;
import cn.edu.nju.polaris.exception.ResourceNotFoundException;
import cn.edu.nju.polaris.repository.InventoryItemRepository;
import cn.edu.nju.polaris.service.InventoryItemService;
import cn.edu.nju.polaris.vo.InventoryItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryItemServiceImpl implements InventoryItemService{

    private final InventoryItemRepository inventoryItemrepository;

    @Autowired
    public InventoryItemServiceImpl(InventoryItemRepository inventoryItemrepository) {
        this.inventoryItemrepository = inventoryItemrepository;
    }


    @Override
    public void saveOneInventoryItem(InventoryItemVO vo) {
        if (vo == null){
            throw new ResourceNotFoundException("原材料/产品信息为空");
        }

        InventoryItem item = inventoryItemrepository.findByName(vo.name);
        if (item != null){
            throw new ResourceConflictException("该原材料/产品已存在");
        }
        item = new InventoryItem();
        item.setCategory(vo.category);
        item.setName(vo.name);
        inventoryItemrepository.save(item);
    }

    @Override
    public void deleteOneInventoryItem(InventoryItemVO vo) {
        if (vo == null){
            throw new ResourceNotFoundException("原材料/产品信息为空");
        }

        InventoryItem item = inventoryItemrepository.findByName(vo.name);
        if (item == null){
            throw new ResourceNotFoundException("不存在该原材料/产品");
        }
        inventoryItemrepository.delete(item.getId());
    }

    @Override
    public void saveMoreInventoryItem(List<InventoryItemVO> list) {
        for (InventoryItemVO vo : list){
            saveOneInventoryItem(vo);
        }
    }
}
