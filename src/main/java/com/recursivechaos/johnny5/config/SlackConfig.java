package com.recursivechaos.johnny5.config;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConfigurationProperties
public class SlackConfig {

    private final Logger log = LoggerFactory.getLogger(SlackConfig.class);

    @Value("${slack.channel}")
    String channel;

    @Value("${slack.api.key}")
    String key;

    SlackSession slackSession;

    @Bean
    SlackSession slackSession() throws IOException {
        if (null == slackSession) {
            slackSession = SlackSessionFactory.createWebSocketSlackSession(key);
            slackSession.connect();
            log.debug("New session created: {}", slackSession);
        }
        return slackSession;
    }

    @Bean
    SlackChannel slackChannel() throws IOException {
        return slackSession.findChannelByName(channel);
    }

}
