/**
 * Develop Bank application with muli-threading features
 * 	- perform deposit() and withdraw() operations 
 *    for 3 accounts concurrently
 *    
 *    on acc1 and acc2 perform deposit() operations
 *    on acc3 perform withdraw() operation 
 *    concurrently from 3 threads
 *    
 */

//NegativeAmountException.java
class NegativeAmountException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NegativeAmountException() {

	}

	public NegativeAmountException(String msg) {
		super(msg);
	}
}

//InsufficientFundsException.java
class InsufficientFundsException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public InsufficientFundsException() {
		
	}
	public InsufficientFundsException(String msg) {
		super(msg);
	}
}
interface BankAccount{
	void deposit(double amt) throws NegativeAmountException;
	void withdraw(double amt) throws NegativeAmountException,InsufficientFundsException;
	void currentbalance();
	
}
class HDFCBankAccount implements BankAccount{
	private long AccNum;
	private String AccHname;
	private double balance;
	public HDFCBankAccount(long AccNum,String AccHname,double balance){
	   this.AccNum=AccNum;
	   this.AccHname=AccHname;
	   this.balance=balance;
	}
	   @Override
		   public void deposit(double amt) throws NegativeAmountException{
		   if(amt<=0) 
			   throw new NegativeAmountException("amount cannt be nagitive or zero");
		   this.balance=this.balance+amt;
		   System.out.println(amt+"is deposited in the acct"+AccNum);
	   }
	   @Override
		   public void withdraw(double amt) throws NegativeAmountException,InsufficientFundsException{
		   if(amt<=0)
			   throw new NegativeAmountException("amount cannt be nagitive or zero");
		   if(amt > balance)
			   throw new InsufficientFundsException("insuffient balance");
		   this.balance=this.balance-amt;
		   System.out.println(amt+"is withdrwan from account"+AccNum);
	   }
	   @Override
		   public void currentbalance(){
		   System.out.println("current balance:"+balance);
	   }
		@Override
			public String toString(){
			return "HDFCBankAccount(accNum=" + AccNum+")";
   }
	   }
	   //depositethread.java
	   class DepositThread extends Thread{
		   private BankAccount acc;
		   private double amt;
		   public DepositThread(BankAccount acc,double amt){
		   this.acc=acc;
		   this.amt=amt;
		   }
		   @Override
			   public void run(){
			   try{
			   acc.deposit(amt);
			   }catch(NegativeAmountException e){
				   System.out.println(e.getMessage());
			   }
		   }
		   
	   }
	    //withdrawthread.java
	   class withdrawThread extends Thread{
		   private BankAccount acc;
		   private double amt;
		   public withdrawThread(BankAccount acc,double amt){
		   this.acc=acc;
		   this.amt=amt;
		   }
		   @Override
			   public void run(){
			   try{
			   acc.withdraw(amt);
			   }catch(NegativeAmountException|InsufficientFundsException e){
				   System.out.println(e.getMessage());
			   }
		   }
		   
	   }
public class Bank_Account{
	public static void main(String [] args) throws InterruptedException{
		HDFCBankAccount ac1=new HDFCBankAccount(1234,"hk",1000000);
		HDFCBankAccount ac2=new HDFCBankAccount(4567,"hk",2000000);
		HDFCBankAccount ac3=new HDFCBankAccount(8910,"hk",3000000);
		DepositThread dt1=new DepositThread(ac1,50000);
		DepositThread dt2=new DepositThread(ac2,60000);
		withdrawThread wt3=new withdrawThread(ac3,70000);
		dt1.start();
		dt2.start();
		wt3.start();
		Thread.sleep(10000);
		System.out.println("acc1"+ac1+" ");ac1.currentbalance();
		System.out.println("acc1"+ac2+" ");ac2.currentbalance();
		System.out.println("acc1"+ac3+" ");ac3.currentbalance();
}
}
	   