package com.recursivechaos.johnny5.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jenkins")
public class JenkinsProperties {

    public String server;
    public String username;
    public String password;

}
