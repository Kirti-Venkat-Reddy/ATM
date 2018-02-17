package com.ttu.bank.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsData {
	//Creating Map interface with key,Value pairs and giving generic type as Integer(Key) and Float(Value) 
	static Map<Integer, Float> checkingAccounts = new HashMap<Integer, Float>(){
		{
			put(1001, 200f);
			put(1002, 200f);
			put(1003, 300f);
			put(1004, 400f);
			put(1005, 500f);
		}
	};
	
	
	static Map<Integer, List<Integer>> debitCards = new HashMap<Integer, List<Integer>>(){
		{
			put(3001, new ArrayList<>(Arrays.asList(3001, 3001, 1001, 200)));
			put(3002, new ArrayList<>(Arrays.asList(3002, 3002, 1002, 250)));
			put(3003, new ArrayList<>(Arrays.asList(3003, 3003, 1003, 0)));
			put(3004, new ArrayList<>(Arrays.asList(3004, 3004, 1004, 100)));
			put(3005, new ArrayList<>(Arrays.asList(3005, 3005, 1005, 150)));
		}
	};
	
	static Map<Integer, Float> dailyDebitLimit = new HashMap<Integer, Float>(){
		{
			put(3001, 200f);
			put(3002, 200f);
			put(3003, 200f);
			put(3004, 200f);
			put(3005, 200f);
		}
	};
}
