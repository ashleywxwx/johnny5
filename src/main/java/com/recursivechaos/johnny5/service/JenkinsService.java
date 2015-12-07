/**
 * Created by Andrew Bell 12/6/2015
 * www.recursivechaos.com
 * andrew@recursivechaos.com
 * Licensed under MIT License 2015. See license.txt for details.
 */

package com.recursivechaos.johnny5.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JenkinsService {

    @Autowired
    JenkinsServer jenkinsServer;

    public Map<String, String> getJobStatuses() throws IOException {
        Map<String, String> statuses = new HashMap<>();
        Map<String, Job> jobs = jenkinsServer.getJobs();

        for (Job job : jobs.values()){
            statuses.put(job.getName(), job.details().getLastBuild().details().getResult().name());
        }

        return statuses;
    }

}
