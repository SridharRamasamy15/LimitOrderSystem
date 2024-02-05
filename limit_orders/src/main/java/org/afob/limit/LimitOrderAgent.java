package org.afob.limit;

import org.afob.execution.ExecutionClient;
import org.afob.prices.PriceListener;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LimitOrderAgent implements PriceListener {

	private ExecutionClient executionClient;
	private List<Order> pendingOrders;

	public LimitOrderAgent(final ExecutionClient ec) {
		this.executionClient=ec;
		this.pendingOrders= new ArrayList<>();
	}

	@Override
	public void priceTick(String productId, BigDecimal marketPrice) {

		List<Order> executedOrders = new ArrayList<>();
		try {
			for (Order order : pendingOrders) {
				if (order.getProductId().equals(productId)
						&& isPriceAtOrBetter(marketPrice, order.getLimit(),order.getTrade())) {
					if (order.getTrade().equals(Trade.Buy)) {
						executionClient.buy(order.getProductId(), order.getPrice().intValue());
					}else{
						executionClient.sell(order.getProductId(), order.getPrice().intValue());
					}
					executedOrders.add(order);
					System.out.println
					(String.format("Order executed successfully for " +
							"ProductId- %s ,OrderType- %s ,Limit- %s ,Quantity- %s"
							,order.getProductId(), order.getTrade(),
							String.valueOf(order.getLimit()),order.getQuantity()));
				}
				else{
					System.out.println(String.format(
							"Market Data Processed Successfully " +
									"for ProductId - %s ,MarketPrice - %s",
									productId,String.valueOf(marketPrice)));
				}
			}
		}catch (Exception ex){
			System.out.println("Exception while executing orders"+ex);
		}
		// Remove executed orders from the pendingOrders list
		pendingOrders.removeAll(executedOrders);
	}

	public void addOrder(Trade trade, String productId,
			BigDecimal price,BigDecimal limit, BigInteger quantity) {
		Order order = new Order(productId,trade,price,limit,quantity);
		pendingOrders.add(order);
	}

	private boolean isPriceAtOrBetter(BigDecimal marketPrice,
			BigDecimal limit,Trade trade ) {

		if(trade.equals(Trade.Buy)){
			return marketPrice.doubleValue() <= limit.doubleValue();
		}else if(trade.equals(Trade.Sell)){
			return marketPrice.doubleValue() >= limit.doubleValue();
		}else{
			return false;
		}

	}

	public static void main(String[] args) {
		LimitOrderAgent loa = new LimitOrderAgent(new ExecutionClient());
		//Limit Amount is 100
		loa.addOrder(Trade.Buy,"IBM",BigDecimal.valueOf(100.0),BigDecimal.valueOf(100.0),new BigInteger("1000"));
		//Market Price is 99.5
		loa.priceTick("IBM", BigDecimal.valueOf(99.5));
		
		//Limit Amount is 210
		loa.addOrder(Trade.Sell,"INFOSYS",BigDecimal.valueOf(210),BigDecimal.valueOf(210.0),new BigInteger("200"));
		//Market Price is
		loa.priceTick("INFOSYS", BigDecimal.valueOf(250.0));

		loa.addOrder(Trade.Sell,"ACN",BigDecimal.valueOf(250),BigDecimal.valueOf(200.0),new BigInteger("2210"));
		loa.priceTick("ACN", BigDecimal.valueOf(100));
	}
}