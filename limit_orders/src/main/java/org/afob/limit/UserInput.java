package org.afob.limit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;


public class UserInput {


    public static String validateProductAndPriceInput(String s){
        StringBuilder validationResults = new StringBuilder();
        if(s.split("\\s+").length == 5){
        	
            String trade = s.split("\\s+")[0];
            validationResults.append(validateTrade(trade));
            
            String productId = s.split("\\s+")[1];
        	validationResults.append(validateProductId(productId));
        	
            String price =  s.split("\\s+")[2];
            validationResults.append(validatePrice(price));
            
            String limit =  s.split("\\s+")[3];
            validationResults.append(validateLimit(limit));
            
            String quantity = s.split("\\s+")[4];
            validationResults.append(validateQuantity(quantity));
            
            
        }else{
            validationResults.append("Invalid Input. Input should be in the format of [Side (B or S)] [Product ID] [Price (Number greater than 0 to 3 decimal places] [Limit (Number greater than 0 to 3 decimal places] for example: B IBM 10 10 or S IBM 20 10.1\n");
        }
        return validationResults.toString();
    }
    public static String validateProductId(String productId) {
    	if(productId==null) {
    		return "Invalid Product ID";
    	}
    	return "";
    }
    public static String validateTrade(String trade){
        if(!trade.matches("[BS]")){
            return "Invalid order side : " + trade + ". Trade should either B or S\n";
        }
        return "";
    }

    public static String validatePrice(String price){
        if(!price.matches("[0-9]*\\.?[0-9]?[0-9]?[0-9]?") || new BigDecimal(price).setScale(3, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO.setScale(3, BigDecimal.ROUND_DOWN)) <= 0){
            return "Invalid price : " + price + ". Price should be any value greater than 0 to 3 decimal places e.g. 10 or 0.225\n";
        }
        return "";
    }
    public static String validateLimit(String limit){
        if(!limit.matches("[0-9]*\\.?[0-9]?[0-9]?[0-9]?") || new BigDecimal(limit).setScale(3, BigDecimal.ROUND_DOWN).compareTo(BigDecimal.ZERO.setScale(3, BigDecimal.ROUND_DOWN)) <= 0){
            return "Invalid limit : " + limit + ". limit should be any value greater than 0 to 3 decimal places e.g. 10 or 0.225\n";
        }
        return "";
    }
    public static String validateQuantity(String quantity){
        if(!quantity.matches("[0-9]*") || new BigInteger(quantity).compareTo(BigInteger.ZERO) <= 0){
            return "Invalid quantity : " + quantity + ". Quantity should be a number greater than 0 e.g. 100 or 9995\n";
        }
        return "";
    }
    public static Trade extractTrade(String input){
        return input.split("\\s+")[0].equals("B") ? Trade.Buy : Trade.Sell;
    }
    public static String extractProductId(String input) {
    	return input.split("\\s+")[1];
    }

    public static BigDecimal extractPrice(String input){
        return new BigDecimal(input.split("\\s+")[2]).setScale(3, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal extractLimit(String input){
        return new BigDecimal(input.split("\\s+")[3]).setScale(3, RoundingMode.HALF_DOWN);
    }
    public static BigInteger extractQuantity(String input){
        return new BigInteger(input.split("\\s+")[4]);
    }
}
