package org.trading.fyers.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FyersProperties {

    private String username;
    private String password;
    private String pin;
    private String pan;
    private String app_id = "";
    private String imei = "";
    private String recaptcha_token = "";
    private String clientId;
    private String clientSecret;

    private String host = "https://api.fyers.in/";
}
