package com.recursivechaos.johnny5.listener;

import com.recursivechaos.johnny5.service.SlackService;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HelloListener implements SlackMessagePostedListener {

    private final Logger log = LoggerFactory.getLogger(HelloListener.class);

    @Autowired
    SlackService slackService;

    private SlackPersona bot;

    @PostConstruct
    private void setBotName() {
        bot = slackService.getBot();
    }

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        log.debug("Message Posted: '{}'", event.getMessageContent().toUpperCase());
        if (event.getMessageContent().trim().toUpperCase().contains("HELLO <@" + bot.getId() + ">")) {
            session.sendMessage(event.getChannel(), " Hello " + event.getSender().getUserName() + ". Number 5 is alive. :robot_face:", null);
        }

        if (event.getMessageContent().toUpperCase().contains("JENKINS STATUS")) {
            slackService.sendJobStatus(event.getChannel());
        }
    }

}
