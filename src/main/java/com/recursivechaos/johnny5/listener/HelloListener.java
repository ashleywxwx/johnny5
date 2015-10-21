package com.recursivechaos.johnny5.listener;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloListener implements SlackMessagePostedListener {

    private final Logger log = LoggerFactory.getLogger(HelloListener.class);

    @Override
    public void onEvent(SlackMessagePosted event, SlackSession session) {
        log.debug("Message Posted: {}", event.getMessageContent());

        if (event.getMessageContent().trim().toUpperCase().contains("HELLO <@U0CSFUZHB>")){
            session.sendMessage(event.getChannel(),"Hello " + event.getSender().getUserName(), null);
        }
    }

}
