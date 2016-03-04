/**
 * Created by Andrew Bell 12/6/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.service;

import com.offbytwo.jenkins.model.BuildResult;
import com.recursivechaos.johnny5.properties.SlackProperties;
import com.ullink.slack.simpleslackapi.SlackBot;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPersona;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SlackService {

    private static final Logger logger = LoggerFactory.getLogger(SlackService.class);

    @Autowired
    SlackSession slackSession;

    @Autowired
    JenkinsService jenkinsService;

    @Autowired
    SlackProperties slackProperties;

    /**
     * Sends a message to the provided channel
     *
     * @param myMessage    your message
     * @param slackChannel channel to send
     */
    public void sendMessage(String myMessage, SlackChannel slackChannel) {
        slackSession.sendMessage(slackChannel, myMessage, null);
    }

    /**
     * Queries Jenkins for job status, and sends a message to the provided channel
     *
     * @param slackChannel channel to send
     */
    public void sendJobStatus(SlackChannel slackChannel) {
        logger.debug("Getting jenkins status for " + slackChannel.getName());
        String failedJobsMessage =
            jenkinsService.getJobStatuses().entrySet().stream()
                .filter(getFailedJobs())
                .map(getJobStatusMessage())
                .collect(Collectors.joining("\n", "Sure thing partner\n", ""));
        sendMessage(failedJobsMessage, slackChannel);
    }

    /**
     * Returns the bot that matches the property provided in configuration
     *
     * @return SlackPersona of bot
     */
    public SlackPersona getBot() {
        SlackPersona foundBot = null;
        String botName = slackProperties.getName();
        Collection<SlackBot> bots = slackSession.getBots();
        for (SlackBot bot : bots) {
            if (bot.getUserName().equalsIgnoreCase(botName)) {
                foundBot = bot;
            }
        }
        if (null == foundBot) {
            throw new IllegalStateException("Unable to find bot " + botName);
        }
        return foundBot;
    }

    /**
     * Formats each job's status message
     *
     * @return String of job status
     */
    private Function<Map.Entry<String, String>, String> getJobStatusMessage() {
        return job -> {
            StringBuilder sb = new StringBuilder();
            sb.append(getBuildResultEmoticon(job.getValue()));
            sb.append(" ");
            sb.append(job.getKey());
            sb.append(": ");
            sb.append(job.getValue());
            return sb.toString();
        };
    }

    /**
     * Filters out successful jobs
     *
     * @return Failed jobs
     */
    private Predicate<Map.Entry<String, String>> getFailedJobs() {
        return job -> !job.getValue().equals("SUCCESS");
    }

    /**
     * Gets a matching emoticon for each BuildResult
     *
     * @param buildResult String of BuildResult
     * @return emoticon as String
     */
    private String getBuildResultEmoticon(String buildResult) {
        String emoticon = ":question:";
        BuildResult result = BuildResult.valueOf(buildResult);
        switch (result) {
            case FAILURE:
                emoticon = ":rage:";
                break;
            case ABORTED:
                emoticon = ":skull:";
                break;
            case UNSTABLE:
                emoticon = ":scream:";
                break;
            default:
                break;
        }
        return emoticon;
    }

}
