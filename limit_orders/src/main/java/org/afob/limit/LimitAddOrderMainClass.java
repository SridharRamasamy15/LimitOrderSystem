package org.afob.limit;

import java.util.Scanner;

import org.afob.execution.ExecutionClient;

public class LimitAddOrderMainClass {

	public static void main(String[] args) {
		
		 LimitOrderAgent limitOrderAgent = new LimitOrderAgent(new ExecutionClient()); 
		 Scanner scanner = new Scanner(System.in); 
		 startOrder(limitOrderAgent, scanner); 
	}

	private static void startOrder(LimitOrderAgent limitOrderAgent, Scanner scanner) {
		System.out.println("##### Limit Add Order #####\n");
       while(true){
           System.out.println("Enter order or type 'exit' to exit the simulator:");
           String input = scanner.nextLine();
           if(input.equalsIgnoreCase("exit")){
               break;
           }
           String inputValidation = UserInput.validateProductAndPriceInput(input);
           if(!inputValidation.isEmpty()){
               System.out.println(inputValidation);
               continue;
           }
           limitOrderAgent.addOrder(UserInput.extractTrade(input), UserInput.extractProductId(input), UserInput.extractPrice(input), UserInput.extractLimit(input), UserInput.extractQuantity(input));
           limitOrderAgent.priceTick(UserInput.extractProductId(input), UserInput.extractPrice(input));
       }
	}

}
