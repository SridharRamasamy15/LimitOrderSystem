package org.afob.limit;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
	private String productId;
	private Trade trade;
	private BigInteger quantity;
    private BigDecimal price;
    private BigDecimal limit;
    
    public Order(String productId, Trade trade, BigDecimal price,BigDecimal limit,BigInteger quantity) {
        this.productId = productId;
        this.trade = trade;
        this.price = price;
        this.limit = limit;
        this.quantity = quantity;
    }
    
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Trade getTrade() {
		return trade;
	}
	public void setTrade(Trade trade) {
		this.trade = trade;
	}
	 public BigInteger getQuantity() {
			return quantity;
		}
		public void setQuantity(BigInteger quantity) {
			this.quantity = quantity;
		}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getLimit() {
		return limit;
	}
	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}
    
    @Override
    public String toString() {
        return "Order added for {" +
                "Product Id =" + productId +
                ", Trade='" + trade + '\'' +
                ", Quantity=" + quantity +
                ", Price=" + price +
                ", Limit=" + limit +
                '}';
    }
}
