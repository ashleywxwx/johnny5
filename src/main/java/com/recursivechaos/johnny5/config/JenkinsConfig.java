/**
 * Created by Andrew Bell 12/6/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.config;

import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@ConfigurationProperties
public class JenkinsConfig {

    @Value("${jenkins.server}")
    private String server;

    @Value("${jenkins.username}")
    private String username;

    @Value("${jenkins.password}")
    private String password;

    @Bean
    public JenkinsServer jenkinsServer() throws URISyntaxException {
        return new JenkinsServer(new URI(server), username, password);
    }


}
