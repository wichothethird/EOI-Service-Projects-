package loanDepotAudit;
import java.io.BufferedReader;
import java.io.IOException;

public class FinanceManager {
	
	LoanDepotAudit loandepotnum = new LoanDepotAudit();
	//LoanDepotApp pre = new LoanDepotApp();
	public  String borrowers = "";
	public  String loans = "";
	public  String premium = "";
	public int numOfBorrowers = loandepotnum.bornum;
	public int numOfLoans = loandepotnum.loansnum;
	public int newCurrentBenefits;
	public int newEmployees;
	public int newProjectTotalBor;
	public int newProjectTotalPre;
	public int newtotalloan;
	//public float Prem = pre.premium;
	
	
	public FinanceManager(BufferedReader B) {
		try {
			this.borrowers = B.readLine();
			this.loans = B.readLine();
			this.premium = B.readLine();
		} catch (IOException e) {
			System.out.println("The Loans/Borrowers/premium attributes were not assigned correctly check the file ");
		}
		
	}
	
	public FinanceManager(int newCB, int newEmploy) {
		
		this.newCurrentBenefits = newCB;
		this.newEmployees = newEmploy;
	}

	public String textForFile() {
		String sCB = String.valueOf(newCurrentBenefits);
		String se = String.valueOf(newEmployees);
		String toFile = sCB+"\r\n"+se;
		return toFile;
	}
	
	public String computeNewTotal(int borrower1, int loan1) {
		System.out.println("We are instide Compute New total ");
		
		this.borrowers.replaceAll(",", "");
		System.out.print(this.borrowers);
		this.loans.replaceAll(",", "");
		newProjectTotalBor = Integer.valueOf(this.borrowers) + borrower1;
		
		newtotalloan = Integer.valueOf(this.loans) + loan1;
		
		String toFile = String.valueOf(newProjectTotalBor);
		String toFile1 = String.valueOf(newtotalloan);
		
		return toFile+"\r\n"+toFile1+"\r\n";
		
	}
	public String computeNewPremiumTotal(int pre ) {
		this.premium.replaceAll(",", "");
		this.premium.replaceAll("$", "");
		System.out.println("Entered Compute Premium");
		System.out.println(this.premium);
		System.out.println(pre);
		newProjectTotalPre = Integer.valueOf(this.premium) + pre;
		System.out.println(newProjectTotalPre);
		
		
		String toFile3 = String.valueOf(newProjectTotalPre);
		
		
		return toFile3;
		
	}
	
	public String getBorrowersString() {
		return this.borrowers;
	}
	
	public String getloansString() {
		return this.loans;
	}
	public String getpremiumString() {
		return this.premium;
	}
	
	public String computeBorrowers() {
		int f = numOfBorrowers + Integer.valueOf(borrowers);
		this.borrowers = String.valueOf(f);
		return this.borrowers;
	}
	
	public String computeloans() {
		int l = numOfLoans + Integer.valueOf(loans);
		String j = String.valueOf(l);
		return j;
	}
	


}
