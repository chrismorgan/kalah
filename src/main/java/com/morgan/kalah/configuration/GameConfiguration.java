package com.morgan.kalah.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    private int pits;

    @Min(4)
    @Max(6)
    private int seeds;

    @NotNull
    @Min(1)
    private int northPlayerKalah;

    @NotNull
    @Min(1)
    private int southPlayerKalah;

}
