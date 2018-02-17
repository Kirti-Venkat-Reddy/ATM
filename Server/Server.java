package com.ttu.bank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ttu.bank.connector.Connector;

public class Server implements Runnable {
	private Connector connector;

	public Server(Connector connector) {
		this.connector = connector;
	}

	public String readData() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void run() {
		while (true) {
			if (connector.isServerSignal()) {
				if (connector.isMessage()) {
					String message = connector.receive();
					if (message.trim().equals("1")) {
						handleCheckBalance();
						System.out.println("Would you like to do other transaction (press 1) or exit client (press 2)");
						String msg = readData();
						connector.reply(msg.trim());
						connector.setServerSignal(false);
						connector.setProcessDone(true);
					} else if (message.trim().equals("2")) {
						handleTransfer();
						System.out.println("Would you like to do other transaction (press 1) or exit client (press 2)");
						String msg = readData();
						connector.reply(msg.trim());
						connector.setServerSignal(false);
						connector.setProcessDone(true);

					} else if (message.trim().equals("3")) {
						handleWithDrawal();
						System.out.println("Would you like to do other transaction (press 1) or exit client (press 2)");
						String msg = readData();
						connector.reply(msg.trim());
						connector.setServerSignal(false);
						connector.setProcessDone(true);
					} else if (message.trim().equals("4")) {
						handleDeposit();
						System.out.println("Would you like to do other transaction (press 1) or exit client (press 2)");
						String msg = readData();
						connector.reply(msg.trim());
						connector.setServerSignal(false);
						connector.setProcessDone(true);

					} else {
						System.out.println("Unknown entry.");
						System.out.println("Would you like to do other transaction (press 1) or exit client (press 2)");
						String msg = readData();
						connector.reply(msg.trim());
						connector.setServerSignal(false);
						connector.setProcessDone(true);
					}

				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	//To deposit the balance by getting the card ID and pin
	private void handleDeposit() {
		CheckingAccount account = new CheckingAccount();
		DebitCard db = new DebitCard();
		System.out.println("CardId:");
		int cardId = Integer.valueOf(readData().trim());
		if (AccountsData.debitCards.containsKey(Integer.valueOf(cardId))) {
			System.out.println("Pin:");
			int pin = Integer.valueOf(readData().trim());
			if (db.validatePin(cardId, pin) != 0) {
				System.out.println("Enter the amount you wish to Deposit.");
				Float amountToDeposit = Float.valueOf(readData().trim());
				if (amountToDeposit < 0) {
					System.out.println("You cannot deposit less than zero.");
				} else {
					account.deposit(account.getAccountNumber(cardId), amountToDeposit);
				}

			} else {
				System.out.println("Invalid Pin.");
			}

		} else {
			System.out.println("Invalid cardId.");
		}

	}
	//To transfer the balance by getting the card ID and pin
	private void handleTransfer() {
		CheckingAccount account = new CheckingAccount();
		DebitCard db = new DebitCard();
		System.out.println("CardId:");
		int cardId = Integer.valueOf(readData().trim());
		if (AccountsData.debitCards.containsKey(Integer.valueOf(cardId))) {
			System.out.println("Pin:");
			int pin = Integer.valueOf(readData().trim());
			if (db.validatePin(cardId, pin) != 0) {
				System.out.println("Enter the Checking account number to which you wish to transfer.");
				int accountNumber = Integer.valueOf(readData().trim());
				if (AccountsData.checkingAccounts.containsKey(accountNumber)) {
					System.out.println("Enter the amount to transfer.");
					Float transferAmount = Float.valueOf(readData().trim());
					if (transferAmount < 0) {
						System.out.println("You cannot transfer less than zero.");
					} else {
						if (account.readBalance(cardId) >= transferAmount) {
							account.deposit(accountNumber, transferAmount);
							account.debit(account.getAccountNumber(cardId),
									(account.readBalance(cardId) - transferAmount));
							System.out.println("Available balance for this card is " + account.readBalance(cardId));
						} else {
							System.out.println("Insufficient amount in your account.");
						}
					}

				} else {
					System.out.println("The Account number you wish to transfer does not exist.");
				}
			} else {
				System.out.println("Invalid Pin.");
			}
		} else {
			System.out.println("Invalid cardId.");
		}

	}
	//To withdraw from the balance by getting the card ID and pin
	private void handleWithDrawal() {
		CheckingAccount account = new CheckingAccount();
		DebitCard db = new DebitCard();
		System.out.println("CardId:");
		int cardId = Integer.valueOf(readData().trim());
		if (AccountsData.debitCards.containsKey(Integer.valueOf(cardId))) {
			System.out.println("Pin:");
			int pin = Integer.valueOf(readData().trim());
			if (db.validatePin(cardId, pin) != 0) {
				System.out.println("WithDrawal amount: ");
				Float WithdrawalAmount = Float.valueOf(readData());
				if (WithdrawalAmount < 0) {
					System.out.println("You cannot withdraw less than zero.");
				} else {
					if (db.checkDailyLimit(WithdrawalAmount, cardId)) {
						System.out.println("Transaction is successful. Please collect the cash.");
						db.updateDailyDebitTotal(WithdrawalAmount, cardId);
						account.debit(account.getAccountNumber(cardId),
								(account.readBalance(cardId) - WithdrawalAmount));
						System.out.println("Available balance for this card is " + account.readBalance(cardId));
					} else {
						System.out.println("Exceeded daily limit. The daily limit on this card is "
								+ AccountsData.dailyDebitLimit.get(cardId));
					}
				}

			} else {
				System.out.println("Invalid Pin.");
			}
		}

	}
	//To check the balance by getting the card ID and pin
	private void handleCheckBalance() {
		CheckBalanceTransaction checkBalanceTransaction = new CheckBalanceTransaction();
		System.out.println("CardId:");
		int cardId = Integer.valueOf(readData().trim());
		if (AccountsData.debitCards.containsKey(Integer.valueOf(cardId))) {
			System.out.println("Pin:");
			int pin = Integer.valueOf(readData().trim());
			Float bal = checkBalanceTransaction.checkBalance(cardId, pin, AccountsData.checkingAccounts,
					AccountsData.debitCards);
			if (bal != null) {
				System.out.println("Balance is : " + bal);
			}

		} else {
			System.out.println("Invalid cardId.");
		}
	}

}
