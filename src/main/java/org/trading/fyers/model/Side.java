package org.trading.fyers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Side {
    BUY(1), SELL(-1);
    private final int value;
}
