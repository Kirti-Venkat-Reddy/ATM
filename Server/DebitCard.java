package com.ttu.bank.server;

public class DebitCard {

	//Checking the given pin and cardID
	public int validatePin(int cardId, int pin) {
		if(AccountsData.debitCards.get(cardId).get(1) == pin){
			return AccountsData.debitCards.get(cardId).get(2);
		}else{
			return 0;
		}
	}
	
	//Check for daily limit whether it is exceeded the fixed amount
	public boolean checkDailyLimit(float amount, int cardId){
		if(AccountsData.dailyDebitLimit.get(cardId) >= amount){
			return true;
		}
		else{
			return false;
		}
	}
	
	//Updating the daily Debit total by deducting amount
	public void updateDailyDebitTotal(float amount, int cardId){
		AccountsData.dailyDebitLimit.put(cardId, AccountsData.dailyDebitLimit.get(cardId) - amount);
	}

}
