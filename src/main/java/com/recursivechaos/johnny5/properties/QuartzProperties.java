package com.recursivechaos.johnny5.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jobdetails.standup")
public class QuartzProperties {

    public String greeting;
    public String time;

}
