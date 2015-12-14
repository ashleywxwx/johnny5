package com.recursivechaos.johnny5.config;

import com.recursivechaos.johnny5.listener.HelloListener;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Data
@Configuration
@ConfigurationProperties(prefix = "slack.bot")
public class SlackConfig {

    private final Logger log = LoggerFactory.getLogger(SlackConfig.class);

    private static String channel;
    private static String apikey;
    private static String name;

    private SlackSession slackSession;
    private SlackPersona slackBot;

    @Bean
    SlackSession slackSession() throws IOException {
        if (null == slackSession) {
            slackSession = SlackSessionFactory.createWebSocketSlackSession(apikey);
            slackSession.connect();
            log.debug("New session created: {}", slackSession);
        }
        return slackSession;
    }

    @Bean
    SlackChannel slackChannel() throws IOException {
        return slackSession.findChannelByName(channel);
    }

    @Bean
    HelloListener helloListener() {
        return new HelloListener();
    }

}
