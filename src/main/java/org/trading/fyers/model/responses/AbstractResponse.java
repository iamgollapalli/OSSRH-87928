package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractResponse {

    private String s;
    private String code;
    private String message;
}
