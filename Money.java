import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Money {

	public static HashMap<String, Integer> customer = new HashMap<String, Integer>();
	public static HashMap<String, Integer> customerHistory = new HashMap<String, Integer>();
	public static HashMap<String, Integer> bank = new HashMap<String, Integer>();
	public static Integer flagFinish = 0;
	public static void main(String[] args) {
		customer = readData("customers.txt",1);
		customerHistory = readData("customers.txt",2);
		bank = readData("banks.txt",0);
		List<String> customerNames = new ArrayList<>(customer.keySet());
		List<String> listedBanks = new ArrayList<>(bank.keySet());
        for (String customerName : customerNames) {
        	CustomerThread customerThread = new CustomerThread(customerName);
        	customerThread.start();
        	flagFinish++;
        }
        while(flagFinish>0) {
        	System.out.print("");
        }
        for (String customerName : customerNames) {
        	if(customer.get(customerName)==0) {
        		System.out.println(customerName+"  has reached the objective of "+customerHistory.get(customerName)+"  dollar(s). Woo Hoo! ");
        	}else {
        		System.out.println(customerName+" was only able to borrow "+ (customerHistory.get(customerName) - customer.get(customerName)) +"  dollar(s). Boo Hoo! ");
        	}
        	
        }
        for(String bankName : listedBanks) {
        	if(bank.get(bankName)!=0) {
        		System.out.println(bankName+" has "+ bank.get(bankName) +" dollar(s) remaining.");
        	}
        }
        
    }
	
    private static HashMap<String, Integer> readData (String file, Integer customer){
        List<String> dataList = new ArrayList<>();
        HashMap<String, Integer> dataHash = new HashMap<>();
        Scanner input = null;
        try {
            input = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (input.hasNextLine()) {
            String tmpData = input.nextLine();
            tmpData = tmpData.trim();
            if (tmpData != null && !tmpData.isEmpty()){
                dataList.add(tmpData);
            }
        }
        
        if (dataList.size() > 0){
        	if(customer!=2) {
            showCallsList(dataList, customer);
        	}
            for(String data : dataList){

               String dataDetails = data.replaceAll("[.{}]","");
               String[] splitedData = dataDetails.split(",");
               dataHash.put(splitedData[0], Integer.parseInt(splitedData[1]));
            }
        }
        else {
            System.out.println("Empty Data!");
        }
        return dataHash;
    }
    
    private static void showCallsList(List<String> dataList, Integer customer) {
        if(customer==1) {
        	System.out.println("\n** Customers and loan objectives **");
        }else {
        	System.out.println("\n** Banks and financial resources **");
        }
    	
        for (String data : dataList) {
            String dataDetails = data.replaceAll("[.{}]", "");
            dataDetails = dataDetails.replaceAll(",", " : ");
            System.out.println(dataDetails);
        }
        System.out.println("\n");
    }
    
    public static void printRequest(String requesterName, int amount, String requestedBank) {
    	System.out.println(requesterName+" requests a loan of "+amount+" dollar(s) from "+ requestedBank);
    	
    }
    
    public static void printApproval(String requesterName, int amount, String requestedBank) {
    	System.out.println(requestedBank+" approves a loan of "+amount+" dollars from "+ requesterName);
    	
    }
    
    public static void printDenial(String requesterName, int amount, String requestedBank) {
    	System.out.println(requestedBank+" denies a loan of "+amount+" dollars from "+ requesterName);
    	
    }
}
