/**
 * Created by Andrew Bell 12/6/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.service;

import com.offbytwo.jenkins.model.BuildResult;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
