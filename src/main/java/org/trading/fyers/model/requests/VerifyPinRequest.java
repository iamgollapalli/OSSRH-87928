package org.trading.fyers.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyPinRequest {

    private String request_key;
    private final String identity_type = "pin";
    private String identifier;
    private final String recaptcha_token = "";
}
