package com.excilys.burleon.computerdatabase.spring.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.excilys.burleon.computerdatabase.persistence.idao.IComputerDao;
import com.excilys.burleon.computerdatabase.persistence.model.Computer;
import com.excilys.burleon.computerdatabase.service.iservice.IComputerService;
import com.excilys.burleon.computerdatabase.service.iservice.IPageService;
import com.excilys.burleon.computerdatabase.spring.config.MainConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = MainConfig.class) })
@ActiveProfiles("test")
public class SpringConfigTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    IPageService<Computer> pageService;

    @Autowired
    IComputerService computerService;

    @Autowired
    IComputerDao computerDao;

    @Test
    public void springConfiguration() {
        Assert.assertNotNull(this.wac);
        Assert.assertNotNull(this.pageService);
        Assert.assertNotNull(this.computerService);
        Assert.assertNotNull(this.computerDao);
    }
}