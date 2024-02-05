package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.execution.ExecutionClient.ExecutionException;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class LimitOrderAgentBrute implements PriceListener {
	private final ExecutionClient executionClient;
	List<Order> buyList = new ArrayList<Order>();
	List<Order> sellList = new ArrayList<Order>();
	public LimitOrderAgentBrute(final ExecutionClient ec) {
		this.executionClient = ec;
    }
    
    @Override
    public void priceTick(String productId, BigDecimal price) {
    	executeOrders();   		
    }
    
    void addOrder(Trade trade,String productId, BigInteger quantity,BigDecimal price,BigDecimal limit) {
		
		switch(trade) {
		case Buy:
			//System.out.println("Buy order for product "+productId + " with price " + price + " when the limit is " + limit);
			Order buy = new Order(productId,trade,price,limit,quantity);
			System.out.println(buy.toString());
			buyList.add(buy);
			break;
		case Sell:
			//System.out.println("Sell order for product "+productId + " with price " + price + " when the limit is " + limit);
			Order sell = new Order(productId,trade,price,limit,quantity);
			System.out.println(sell.toString());
			sellList.add(sell);
			break;
		default:
			System.out.println("Invalid choice");
		}	
	}
    void executeOrders() {
    	//System.out.println("Executed Orders based on limit:");
		for(Order buy:buyList) {
			if(buy.getPrice().compareTo(buy.getLimit())<=0) {
			//Invoke ExecutionClient buy in realtime
			try {
				executionClient.buy(buy.getProductId(),buy.getPrice().intValue());
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			}
		}
		for(Order sell:sellList) {
			if(sell.getPrice().compareTo(sell.getLimit())>=0) {
			//Invoke ExecutionClient sell in realtime 
			try {
				executionClient.sell(sell.getProductId(),sell.getPrice().intValue());
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
    }
    

}
