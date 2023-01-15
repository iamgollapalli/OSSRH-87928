package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateAuthCodeResponse extends AbstractResponse{

    private String access_token;
    private String refresh_token;
}
