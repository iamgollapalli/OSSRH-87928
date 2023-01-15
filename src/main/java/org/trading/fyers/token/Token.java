package org.trading.fyers.token;

public interface Token {

    public boolean isTokenExpired();
    public String getToken();
    public boolean updateToken(String token);
}
