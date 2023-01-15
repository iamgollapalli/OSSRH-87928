package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyPinResponse extends AbstractResponse{

    private Data data = new Data();

    @Getter
    @Setter
    public static class Data{
        private String refresh_token;
        private String access_token;
    }
}
