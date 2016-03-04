package com.recursivechaos.johnny5.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "slack.bot")
public class SlackProperties {

    public String channel;
    public String key;
    public String name;

}
