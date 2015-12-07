package com.recursivechaos.johnny5.listener;

import com.recursivechaos.johnny5.service.JenkinsService;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloListener implements SlackMessagePostedListener {

    private final Logger log = LoggerFactory.getLogger(HelloListener.class);

    @Autowired
    JenkinsService jenkinsService;

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        log.debug("Message Posted: '{}'", event.getMessageContent().toUpperCase());

        if (event.getMessageContent().trim().toUpperCase().contains("HELLO <@U0CSFUZHB>")) {
            session.sendMessage(event.getChannel(), " Hello " + event.getSender().getUserName() + ". Number 5 is alive.", null);
        }

        if (event.getMessageContent().toUpperCase().contains("<@U0CSFUZHB>: JENKINS STATUS")) {
            log.info("Fetching Jenkins status...");
            jenkinsService.sendMessage("Sure thing partner.");
            jenkinsService.sendJobStatus();
        }
    }

}
