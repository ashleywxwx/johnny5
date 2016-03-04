package com.recursivechaos.johnny5.config;

import com.recursivechaos.johnny5.listener.HelloListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Startup implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(Startup.class);

    @Value("${slack.apikey}")
    public String apiKey;

    @Value("${spring.config.location:NONE_PASSED}")
    public String properties;

    @Autowired
    SlackSession slackSession;

    @Autowired
    HelloListener helloListener;

    @Override
    public void run(String... args) throws Exception {
        log.info("Properties provided: " + properties);
        log.info("Slack service started with key: ..." + apiKey.substring(apiKey.length() - 4));
        slackSession.addMessagePostedListener(helloListener);
    }
}
