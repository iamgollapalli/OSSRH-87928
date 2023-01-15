package org.trading.fyers.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenerateAuthCodeRequest {

    private String fyers_id;
    private String app_id;
    private final String redirect_uri = "http://localhost:5000/";
    private final String appType ="100";
    private final String code_challenge = "";
    private final String state = "abcdefg";
    private final String scope = "";
    private final String nonce = "";
    private final String response_type = "code";
    private final boolean create_cookie = true;
}
