/**
 * Created by Andrew Bell 12/13/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Johnny5Application.class)
@ActiveProfiles("local")
public class ITConfig {

    private static final Logger logger = getLogger(TestConfig.class);

    @Value("${jenkins.server}")
    String server;

    @Before
    public void setUp() throws Exception {
        logger.error("server: " + server);
    }

    @Test
    public void testTest() throws Exception {
        assertNotNull(server);
    }

}
