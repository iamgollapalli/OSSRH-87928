package org.trading.fyers.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.trading.fyers.model.ProductType;
import org.trading.fyers.model.Side;

@Getter
@Setter
@Builder
public class ConvertPositionRequest {

    private String symbol;
    private Side positionSide;
    private int convertQty;
    private ProductType convertFrom;
    private ProductType convertTo;
}
