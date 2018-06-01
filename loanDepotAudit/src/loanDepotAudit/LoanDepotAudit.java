package loanDepotAudit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.*;
import java.util.*;
import org.apache.commons.collections4.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyle;

import au.com.bytecode.opencsv.CSVReader;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeEvent;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import java.util.Date;

public class LoanDepotAudit {
	
	public static int bornum = 0;
	
	public static int loansnum = 0;
	
	public static String toFile = "";
	
	public static int BorrowersForWeek=0;
	
	public static int loansForWeek = 0;
	
	public static int lborrowers;
	public static int iborrowers;
	public static int mborrowers;
	public static int pborrowers;
	public static int ploans;
	public static int lloans;
	public static int iloans;
	public static int mloans;
	public static String lpre;
	public static String mpre;
	public static String ipre;
	public static String ppre;
	public static int curbenn;
	public static int borr;
	

	
	@SuppressWarnings("deprecation")
	public static void loanDepotRunner(JProgressBar progressBar,JLabel la ,JLabel i1 ,JLabel lb ,JLabel i2,JLabel l3,JLabel i3, JLabel l4 ,JLabel i4,JLabel r1,JLabel r2,JLabel r3,JLabel r4,JLabel r5,JLabel r6, String cb, JTable t1,JTable t2,int currentEmploy, FinanceManager Pt, FinanceManager Lt)  {
		
		String username = System.getProperty("user.name");
		FilesLookUp Files = new FilesLookUp();
		File[] CSVFiles = Files.getCSVFiles();
		DataTypeChecker D = new DataTypeChecker(); 
		List<List<String>> sortedRecords = new ArrayList<List<String>>();
		List<List<String>> dupsInWeekly = new ArrayList<List<String>>();
		Set<List<String>> seto = new HashSet<List<String>>();
		List<List<String>> dups = new ArrayList<List<String>>();
		DupsInFileExtractor DIFE = new DupsInFileExtractor();
		
		

		
		
		try {
			
            String report = "";
            int progress = 0;           
            progress = setProgressBar(progress, 25, progressBar);

			Files.convertCSVtoXLSX(CSVFiles);
			System.out.println("Converter to XLSX and Merger Work!!!!!!");
			Files.XLSXMerger(CSVFiles); 
			la.setEnabled(true);
			i1.setEnabled(true);
			
			System.out.println("Records for Dups in File : "+D.allRecords.size());
			int numInFile = D.allRecords.size();
			
			int numBenefits = Integer.valueOf(cb);
			bornum = Integer.valueOf(cb);
			report = "Total Number of Benefits: "+D.allRecords.size()+"\r\n"+"\r\n";
			
			r6.setText(Integer.toString(numBenefits+numInFile));//this has to be written in the file to update the number 
			
			
//STEPS TO FINDING THE DUPLICATES IN FILE /////////////////////////////////////////////////////////////////////////////////////////////////////////
			
	//Step 1: sort all the records for this week. allrecords is a list of all this records
			sortedRecords = DupsInFileExtractor.sorter(D.allRecords);//takes in a list
			
	//Step 2: Once all the records are sorted the method sorter returns a sorted list of list. This then is sent to dupsFinder to find the dups.
			seto = DupsInFileExtractor.dupsFinder(sortedRecords);
			
	//Step 3: dupsFinder returns a set of all the duplicates in the sorted list we gave to dupsFinder.
			
			r1.setText(String.valueOf(D.allRecords.size()));
	//Step 4: We add this to a list 		
			for (List<String> temp: seto) {
				dups.add(temp);	
			}		
	//Steps 5: We sort the duplicates and create a table for the report. We also print the dups and its size for checking correctness.	
            progress = setProgressBar(progress, 50, progressBar);
			dups = DupsInFileExtractor.sorter(dups);
			lb.setEnabled(true);
			i2.setEnabled(true);
			createTableForInFile(t1,dups);

            progress = setProgressBar(progress, 70, progressBar);
            l3.setEnabled(true);
			i3.setEnabled(true);
			
			//Prints all the Duplicates in FIle 
			for(List<String> H: dups) {
				System.out.println(H);
			}

			progressBar.setValue(70);
			
			//Prints the Number of Duplicates in File 
			System.out.println(dups.size());
			r4.setText(String.valueOf(dups.size()));
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			
			
			
			
			
/////////STEPS TO FINDING THE DUPLICATES IN WEEKLY //////////////////////////////////////////////////////////////////////////////////////////////////	
			
			System.out.println("Number of Borrowers For loanDepot: "+D.uniquessn.size());
			lborrowers = D.uniquessn.size();
			System.out.println("Number of Borrowers For Imortgage: "+D.IMRuniquessn.size());
			iborrowers = D.IMRuniquessn.size();
			System.out.println("Number of Borrowers For MM       : "+D.MMRuniquessn.size());
			mborrowers = D.MMRuniquessn.size();
			BorrowersForWeek = D.uniquessn.size();
			
			//Total number of borrowers
			
			borr = D.uniquessn.size()+D.MMRuniquessn.size()+D.IMRuniquessn.size();
			r3.setText(String.valueOf(D.uniquessn.size()+D.MMRuniquessn.size()+D.IMRuniquessn.size()));
			int totalBorrowers  = D.uniquessn.size()+D.MMRuniquessn.size()+D.IMRuniquessn.size();
			pborrowers = totalBorrowers;
			report = report + "Total Number of Borrowers: " + totalBorrowers+"\r\n";
			report = report + "Number of Borrowers For loanDepot: "+ D.uniquessn.size() + "\r\n" + "Number of Borrowers For Imortgage: "+ D.IMRuniquessn.size() + "\r\n" + "Number of Borrowers For MortgageM: "+ D.MMRuniquessn.size() + "\r\n\r\n"; 
			
			
			System.out.println("Date loanDepot: "+D.loanDepotDate);
			System.out.println("Date Imortgage: "+D.imortgageDate);
			System.out.println("Date MM       : "+D.mortgageMasterDate);
			System.out.println("SI");
			System.out.println(D.listOfDates);
			report = report + "Dates in Files "+"\r\n"+"Date loanDepot: " + D.loanDepotDate+"\r\n "+ "Date Imortgage: "+D.imortgageDate + "\r\n"+"Date MM       : "+D.mortgageMasterDate + "\r\n\r\n";
			
			System.out.println("SI");
	//Step 1 : allrecords list Contains all the records on the weekly and PLUS all the Unique SSN for each file of the week. 
			//we are sorting them by SSN and Date. 
			Files.openWeekly();
			Comparator<List<String>> order = (l1,l2)-> l1.get(0).compareTo(l2.get(0));
			order.thenComparing((l1,l2)->l1.get(2).compareTo(l2.get(2)));
			Files.allrecords.sort(order);
			progressBar.setValue(80);

			System.out.println("SI");
			List<String> prevrecord = new ArrayList<String>();
			List<String> currentrecord = new ArrayList<String>();
			int cpos = 1;
			System.out.println(D.listOfDates);
	//Step 2: Once sorted, This is the algorithm will perform the formula 
			Files.allrecords.get(0).set(1, "1");
			System.out.println("SI");
			for (int i = 0 ; i< Files.allrecords.size();i++) {
				if (cpos == Files.allrecords.size()-1) {
					prevrecord = Files.allrecords.get(i);
					currentrecord = Files.allrecords.get(cpos);
					if(prevrecord.get(0).equals(currentrecord.get(0))) {
						int count;
						count = Integer.valueOf(prevrecord.get(1))+1;
						Files.allrecords.get(cpos).set(1, String.valueOf(count));
						if (count>1 && D.listOfDates.contains(Files.allrecords.get(cpos).get(2)) ) {
							dupsInWeekly.add(Files.allrecords.get(cpos));
						}
					}
					break;//Files.allrecords.get(cpos).get(2).equals(D.loanDepotDate)
				}
				prevrecord = Files.allrecords.get(i);
				currentrecord = Files.allrecords.get(cpos);

				if(prevrecord.get(0).equals(currentrecord.get(0))){
					int count;
					count = Integer.valueOf(prevrecord.get(1))+1;
					Files.allrecords.get(cpos).set(1, String.valueOf(count));
					if (count>1&&D.listOfDates.contains(Files.allrecords.get(cpos).get(2))) {
						dupsInWeekly.add(Files.allrecords.get(cpos));
					}
				}
				else {
					Files.allrecords.get(cpos).set(1, "1");
				}
				progressBar.setValue(90);
				cpos++;
				
			}
			System.out.println("SI");
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			r2.setText(String.valueOf(String.valueOf(D.Stageone.size()+D.MMRStageone.size()+D.IMRStageone.size())));
			loansnum = D.Stageone.size();
			loansForWeek = D.Stageone.size();
			System.out.println("Number Of loans for LoanDepot: "+ D.Stageone.size());
			lloans = D.Stageone.size();
			System.out.println("Number Of loans for Imortgage: "+ D.IMRStageone.size());
			iloans = D.IMRStageone.size();
			System.out.println("Number Of loans for MortgageM: "+ D.MMRStageone.size());
			mloans = D.MMRStageone.size();
			int loans =  D.Stageone.size() +  D.MMRStageone.size()+D.IMRStageone.size();
			ploans = loans;
			report = report + "Total Number of Loans: "+ loans + "\r\n"+ "Number Of loans for LoanDepot: "+ D.Stageone.size()+"\r\n"+"Number Of loans for Imortgage: "+ D.IMRStageone.size()+"\r\n"+"Number Of loans for MortgageM: "+ D.MMRStageone.size()+"\r\n\r\n"; 
			dupsInWeekly.forEach(System.out::println);
			System.out.println(dupsInWeekly.size());
			r5.setText(String.valueOf(dupsInWeekly.size()));
			
			createTableForInWeekly(t2,dupsInWeekly);

            DIFE.copyWeekly(Files.allrecords);
            DIFE.copyIntoTemplate(dups, dupsInWeekly);
            report = report + "Dups in File: " + dups.size()+"\r\n\r\n"+"Dups in Weekly: "+dupsInWeekly.size()+"\r\n\r\n";

            
            
            l4.setEnabled(true);
			i4.setEnabled(true);
			progress = setProgressBar(progress, 99, progressBar);
			progressBar.setValue(100);
            PrintWriter fw = new PrintWriter("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\"+"Report.txt");
			fw.print(report);
			fw.close();

			
		}catch (IOException e) {
			System.out.println("Converter or Merger are not Working!!!!!");
		} catch (EncryptedDocumentException e) {
			System.out.print("Error with weekly File !!!!");
		} catch (InvalidFormatException e) {
			System.out.print("Error with weekly File !!!!");
		}
        

	}

	
	private static int setProgressBar(int start, int end, JProgressBar progressBar) {
        Random random = new Random();
        int progress = start;
        while (progress < end) {
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ignore) {}
            progress += random.nextInt(10);
            progressBar.setValue(Math.min(progress, start));
        }
        return progress;
	}
	
	private static void createTableForInFile(JTable t1, List<List<String>> dups) {
        DefaultTableModel model = (DefaultTableModel) t1.getModel();
        t1.setModel(model);
        t1.setRowHeight(20);
        //model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
		
        String[] columnNames = {"SSN",
                "First Name",
                "Last Name",
                "Birth Date",
                "Funding Date",
                "Loan Number"};
        model.setColumnIdentifiers(columnNames);
        
        for(List<String> cr: dups) {
        	Object[] row = new Object[6];
        	row[0] = cr.get(0);
        	row[1] = cr.get(1);
        	row[2] = cr.get(2);
        	row[3] = cr.get(3);
        	row[4] = cr.get(4);
        	row[5] = cr.get(5);
        	model.addRow(row);
        }
       
		
	}
	private static void createTableForInWeekly(JTable t2, List<List<String>> dupsInWeekly) {
        DefaultTableModel model1 = (DefaultTableModel) t2.getModel();
        t2.setModel(model1);
        t2.setRowHeight(20);
        //model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
		
        String[] columnNames1 = {"SSN",
                "Formula",
                "Date",
                "Location"};
        model1.setColumnIdentifiers(columnNames1);
        
        for(List<String> cr: dupsInWeekly) {
        	Object[] row = new Object[4];
        	row[0] = cr.get(0);
        	row[1] = cr.get(1);
        	row[2] = cr.get(2);
        	row[3] = cr.get(3);

        	model1.addRow(row);
        }
       
       
		
	}

}
