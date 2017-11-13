package cn.edu.nju.polaris.service;

import cn.edu.nju.polaris.vo.InventoryItemVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class InventoryItemServiceTest {

    @Autowired
    InventoryItemService inventoryItemService;

    @Test
    public void testAddOne() throws Exception {
        InventoryItemVO vo = new InventoryItemVO("原材料","木头");
        inventoryItemService.saveOneInventoryItem(vo);
    }

    @Test
    public void testAddMore() throws Exception {
        List<InventoryItemVO> list = new ArrayList<>();
        list.add(new InventoryItemVO("原材料","棉花"));
        list.add(new InventoryItemVO("原材料","钢铁"));
        inventoryItemService.saveMoreInventoryItem(list);
    }

    @Test
    public void testDeleteOne() throws Exception {
        InventoryItemVO vo = new InventoryItemVO("原材料","棉花");
        inventoryItemService.deleteOneInventoryItem(vo);
    }
}
