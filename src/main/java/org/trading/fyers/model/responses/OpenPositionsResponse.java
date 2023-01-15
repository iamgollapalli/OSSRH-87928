package org.trading.fyers.model.responses;

import lombok.Getter;
import lombok.Setter;
import org.trading.fyers.model.ProductType;
import org.trading.fyers.model.Side;
import org.trading.fyers.util.TradeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OpenPositionsResponse extends AbstractResponse{

    private List<Position> netPositions = new ArrayList<>();

    public List<Position> openPositions(){
        return this.netPositions.stream().filter(Position::isOpenPosition).collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class Position{

        private int netQty;
        private int qty;
        private double avgPrice;
        private double netAvg;
        private Side side;
        private ProductType productType;
        private double realized_profit;
        private double unrealized_profit;
        private double pl;
        private double ltp;
        private int buyQty;
        private double buyAvg;
        private double buyVal;
        private int sellQty;
        private double sellAvg;
        private double sellVal;
        private int slNo;
        private String fyToken;
        private String dummy;
        private String crossCurrency;
        private double rbiRefRate;
        private double qtyMulti_com;
        private int segment;
        private String symbol;
        private String id;
        private double qtyMultiplier;

        public boolean isOpenPosition(){
            return (this.buyQty - this.sellQty) == 0;
        }

        public int openLots(){
            return isOpenPosition() ? (Math.abs(this.buyQty - this.sellQty)) / lotSize() : 0;
        }

        public int lotSize(){
            return this.symbol.contains("BANKNIFTY") ? TradeConstants.BANKNIFTY_LOT : TradeConstants.NIFTY_LOT;
        }
    }
}
