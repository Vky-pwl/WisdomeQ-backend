package com.icat.quest.admin.service;

import com.icat.quest.admin.service.impl.TestConductorLicenseServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TestConductorLicenseServiceTest {
    @Autowired
    TestConductorLicenseService testConductorLicenseService;

    @Test
    @Ignore
    public void assignExternalLicenseTest(){
        Integer assigned = testConductorLicenseService.assignExternalLicense(1366,11,1);
        Assert.assertNotNull(assigned);
    }
}