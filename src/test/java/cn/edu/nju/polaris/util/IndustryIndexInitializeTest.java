package cn.edu.nju.polaris.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class IndustryIndexInitializeTest {

    @Autowired
    private IndustryIndexInitialize industryIndexInitialize;

    @Test
    public void test() throws IOException {
        industryIndexInitialize.initialize();
    }
}
