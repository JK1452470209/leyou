package com.leyou.search.client;

import com.leyou.item.pojo.Category;
import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void queryGategoryByIds(){
        List<Category> categories = categoryClient.queryGategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3,categories.size());
        for (Category category : categories) {
            System.out.println("category = " + category);
        }
    }
}
