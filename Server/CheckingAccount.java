package com.ttu.bank.server;

public class CheckingAccount implements Account{

	@Override
	public void open(int accountNumber) {
		
	}

	//To read the balance by using cardID
	@Override
	public Float readBalance(int cardId) {
		return AccountsData.checkingAccounts.get(getAccountNumber(cardId));
	}

	@Override
	public void credit(Float amount) {
		
	}

	//To debit from the account
	@Override
	public void debit(int accountNum, Float amount) {
		AccountsData.checkingAccounts.put(accountNum, amount);
	}
	//To get the AccountNumber by getting card ID from debitCards Class
	@Override
	public int getAccountNumber(int cardId) {
		return AccountsData.debitCards.get(cardId).get(2);
	}
	
	//To deposit into account 
	@Override
	public void deposit(int accountNum, Float amount){
		AccountsData.checkingAccounts.put(accountNum, (AccountsData.checkingAccounts.get(accountNum) + amount));
	}

}
