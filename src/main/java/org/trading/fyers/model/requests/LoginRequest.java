package org.trading.fyers.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {

    private String fy_id;
    private String password;
    private final String app_id = "2";
    private final String imei = "";
    private final String recaptcha_token = "";
}
