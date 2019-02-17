package com.morgan.kalah.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Configuration
@ConfigurationProperties("game")
@Data
public class GameConfiguration {

    @NotBlank
    private String host;

    @NotBlank
    private String port;

    @Min(2)
    @Max(10)
    private int pitsize;

}
