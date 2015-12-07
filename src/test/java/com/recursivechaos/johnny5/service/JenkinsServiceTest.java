package com.recursivechaos.johnny5.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Andrew Bell 12/6/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({JenkinsService.class})
public class JenkinsServiceTest {

    @Autowired
    JenkinsService jenkinsService;

    @Test
    public void testGetStatus() throws Exception {
        Map<String, String> statuses = jenkinsService.getJobStatuses();

        assertNotNull(statuses);
    }
}