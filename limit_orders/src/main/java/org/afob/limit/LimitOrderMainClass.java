package org.afob.limit;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.afob.execution.ExecutionClient;

public class LimitOrderMainClass {

	public static void main(String[] args) {
		
	    ExecutionClient executionClient = new ExecutionClient();
	    LimitOrderAgent limitOrderAgent = new LimitOrderAgent(executionClient);

	    // Add a buy order for 1000 shares of IBM when the price drops below $100
	    limitOrderAgent.addOrder(Trade.Buy, "IBM", BigDecimal.valueOf(100.0),BigDecimal.valueOf(100.0),new BigInteger("1000"));

	    // Simulate market changes
	    BigDecimal currentMarketPrice = BigDecimal.valueOf(99.5);

	    // Execute orders based on current market price
	    limitOrderAgent.priceTick("IBM", currentMarketPrice);
		 }

	
}
