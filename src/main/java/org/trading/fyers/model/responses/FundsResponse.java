package org.trading.fyers.model.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundsResponse extends AbstractResponse{

    private List<FundItem> fund_limit = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FundItem{
        private int id;
        private String title;
        private double equityAmount;
        private double commodityAmount;
    }
}
