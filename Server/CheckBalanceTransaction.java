package com.ttu.bank.server;

import java.util.List;
import java.util.Map;

public class CheckBalanceTransaction {
	
	//To Check the account balance by validating user pin with cardID and pin
	public Float checkBalance(int cardId, int pin, Map<Integer, Float> checkingAccounts, Map<Integer, List<Integer>> debitCards){
		
		//Creating Object of DebitCard class for validating the pin
		DebitCard dc = new DebitCard();
		
		if(dc.validatePin(cardId, pin) == 0){
		
			System.out.println("Invalid pin.");
			
			return null;
		}
		
		else
		
		{
			//If the pin is valid, read the balance from checkingAccounts class
			return checkingAccounts.get(debitCards.get(cardId).get(2));
		}
	}
}
