/**
 * Created by Andrew Bell 11/27/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.schedule;

import com.recursivechaos.johnny5.service.JenkinsService;
import com.ullink.slack.simpleslackapi.SlackChannel;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StandupJob extends QuartzJobBean {

    @Autowired
    JenkinsService jenkinsService;

    @Autowired
    SlackChannel defaultChannel;

    private String message;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        jenkinsService.sendMessage(message, defaultChannel);
        jenkinsService.sendJobStatus(defaultChannel);
    }


    public void setMessage(String messsage) {
        this.message = messsage;
    }

}
