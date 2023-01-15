package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MultiOrderResponse extends AbstractResponse{

    private List<PlaceOrderResponse> data;

    @Getter
    @Setter
    public static class MultiOrderItem{
        private int statusCode;
        private PlaceOrderResponse body;
        private String statusDescription;
    }
}
