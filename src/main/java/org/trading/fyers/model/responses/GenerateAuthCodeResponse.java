package org.trading.fyers.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class GenerateAuthCodeResponse extends AbstractResponse{
    private String cookie;
    private String url;
    private String Url;
}
