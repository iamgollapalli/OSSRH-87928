package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends AbstractResponse{

    private String request_key;
    private boolean pin_created;
}
