/**
 * Created by Andrew Bell 11/27/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.schedule;

import com.recursivechaos.johnny5.service.JenkinsService;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service
@Transactional
public class StandupJob extends QuartzJobBean {

    @Autowired
    SlackSession slackSession;

    @Autowired
    SlackChannel slackChannel;

    @Autowired
    JenkinsService jenkinsService;

    private String message;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        sendMessage(message);
        sendJobStatus();
    }

    private void sendJobStatus() {
        try {
            Map<String, String> jobStatuses = jenkinsService.getJobStatuses();
            for (Map.Entry<String, String> job : jobStatuses.entrySet()) {
                sendMessage(job.getKey() + ": " + job.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
            sendMessage("Malfunction! Could not fetch job statuses from Jenkins.");
        }
    }

    private void sendMessage(String myMessage) {
        slackSession.sendMessage(slackChannel, myMessage, null);
    }

    public void setMessage(String messsage) {
        this.message = messsage;
    }

}
