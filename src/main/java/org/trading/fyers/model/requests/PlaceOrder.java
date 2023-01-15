package org.trading.fyers.model.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrder {

    public String symbol;
    public int qty;
    public int type;
    public int side;
    public String productType;
    public float limitPrice;
    public float stopPrice;
    public String validity;
    public int disclosedQty;
    public String offlineOrder;
    public float stopLoss;
    public float takeProfit;
}
