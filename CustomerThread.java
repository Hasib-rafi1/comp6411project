
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerThread extends Thread {
	Money money = new Money();
	String customerName;
	List<String> bankNames = new ArrayList<>(money.bank.keySet());
	public CustomerThread(String threadName){
		super(threadName);
		customerName = threadName;
    }
	
	@Override
    public void run() {
        synchronized (this) {
            try {
                sleep(new Random().nextInt(100) + 1);
            } catch (InterruptedException e) { }
            
            
            while(money.customer.get(customerName)>0) {
            	int randomNumber = 0;
            	Random random = new Random();
            	if (bankNames != null && !bankNames.isEmpty()) {
	            	String bankname = bankNames.get(random.nextInt(bankNames.size()));
	            	if(money.customer.get(customerName)>50) {
	            		
	            		randomNumber = random.nextInt(50 - 1 + 1) + 1;
	            		money.printRequest(customerName, randomNumber, bankname);
	            	}else if(money.customer.get(customerName)==0) {
	            		randomNumber = 0;
	            	}else {
	            		randomNumber = random.nextInt(money.customer.get(customerName) - 1 + 1) + 1;
	            		money.printRequest(customerName, randomNumber, bankname);
	            	}
	            	
	            	try {
						sleep(new Random().nextInt(100 - 10+1) + 1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	if(randomNumber != 0) {
	            		if(money.bank.get(bankname) - randomNumber >= 0) {
	            			money.customer.put(customerName,money.customer.get(customerName)-randomNumber);
	            			money.bank.put(bankname,money.bank.get(bankname) - randomNumber);
	            			money.printApproval(customerName, randomNumber, bankname);
	            		}else {
	            			money.printDenial(customerName, randomNumber, bankname);
	            			bankNames.remove(bankname);
	            		}
	            	}
	            }else {
	            	break;
	            }
           }
        }
        money.flagFinish = money.flagFinish -1;
    }
}
