package org.trading.fyers.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ValidateAuthCodeRequest {

    private final String grant_type = "authorization_code";
    private String appIdHash;
    private String code;
}
