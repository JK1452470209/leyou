package com.leyou.page.service.impl;


import com.leyou.page.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.beans.Transient;

/**
 * @author Mr.JK
 * @create 2020-05-24  11:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceImplTest {

    @Autowired
    private PageService pageService;

    @Test
    public void createHtml() {
        pageService.createHtml(141L);
    }
}