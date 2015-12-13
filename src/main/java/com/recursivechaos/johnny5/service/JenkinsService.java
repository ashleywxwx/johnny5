/**
 * Created by Andrew Bell 12/13/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class JenkinsService {

    private static final Logger logger = getLogger(JenkinsService.class);

    @Autowired
    JenkinsServer jenkinsServer;

    public Map<String, String> getJobStatuses() {
        Map<String, String> statuses = new HashMap<>();
        Map<String, Job> jobs = null;
        try {
            jobs = jenkinsServer.getJobs();
        } catch (IOException e) {
            logger.error("Failed to get Jenkins Jobs status", e);
        }

        if (jobs != null) {
            for (Job job : jobs.values()) {
                logger.debug("Checking job name : {}", job.getName());
                // TODO: We're still failing on parsing some jobs, refine this
                try {
                    if (null != job.details().getLastBuild()) {
                        statuses.put(job.details().getDisplayName(), job.details().getLastBuild().details().getResult().name());
                    } else {
                        logger.info("Job {} has not been built yet", job.getName());
                    }
                } catch (Exception e) {
                    logger.error("Failed to parse job data.", e);
                }
            }
        } else {
            logger.error("No jobs found.");
        }

        return statuses;
    }

}
