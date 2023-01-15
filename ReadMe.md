# Trading module for Fyers Users with Java

## Requirements
 - maven
 - java ( 1.8 or more)
 - fyers client id 
 - this module is designed for NIFTY / BANKNIFTY for now.

## usage
add `trading-core` dependency as follows

```xml
<dependency>
    <groupId>io.github.iamgollapalli</groupId>
    <artifactId>trading-core</artifactId>
    <version>1.0.0</version> 
</dependency>
```

## Initialization

Create `Trader` Object exposes following operations

- Profile viewing
- fetching open positions
- fetching available funds
- place order
- place multiple orders
- convert a position
- exit all positions
- exit a position base on a position id

### creating fyers configuration 

create an object of `FyersProperties` by passing necessary details as follows
```java
        FyersProperties fyersProperties = new FyersProperties();
        fyersProperties.setUsername("REPLACE_WITH_FYERS_ID");
        fyersProperties.setPan("REPLACE_WITH_PAN");
        fyersProperties.setPassword("REPLACE_WITH_PAN");
        fyersProperties.setPin("REPLACE_WITH_PIN");
        fyersProperties.setClientId("REPLACE_WITH_CLIENT_ID");
        fyersProperties.setClientSecret("REPLACE_WITH_CLIENT_SECRET");
```

Create `Trader` object by passing above properties object and accesstoken file path. Access Token fiel path is for caching the token fetched once to minimize the number of calls to fyers for fetching token.

````java

    Trader trader = new Trader(fyersProperties, "REPLACE_WITH_ACCESS_TOKEN_PATH");
    
````

Above created Trader object can be used to automatically place or exit orders.
