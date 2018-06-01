package loanDepotAudit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyle;

import au.com.bytecode.opencsv.CSVReader;


 

public class DataTypeChecker {
	String username = System.getProperty("user.name");
	private String directory = "C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\";
	public static HashMap<String, List<String>> Stageone = new HashMap();
	public static HashMap<String, List<String>> IMRStageone = new HashMap();
	public static HashMap<String, List<String>> MMRStageone = new HashMap();
	public static List<List<String>> allRecords = new ArrayList<List<String>>();
	public String LoanNumber;
	public String SSN;
	public static HashMap<String, List<String>> uniquessn = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> IMRuniquessn = new HashMap<String, List<String>>();
	public static HashMap<String, List<String>> MMRuniquessn = new HashMap<String, List<String>>();
	public static List<List<String>> uniqueList = new ArrayList<List<String>>();
	public static String loanDepotDate;
	public static String imortgageDate;
	public static String mortgageMasterDate;
	public static List<String> listOfDates = new ArrayList<String>();
	
	public void XLSXBuilder(XSSFWorkbook workBook, File CurrentCSV ) {
		
		
		try {
			
	        XSSFSheet sheet = workBook.createSheet("Sheet1");
	        XSSFRow FirstRow = sheet.createRow(0);
		    CSVReader reader;
			reader = new CSVReader(new FileReader(CurrentCSV));
		    String [] nextLine;
		    nextLine = reader.readNext();//to remove the title 
		    
		    
		    int numCol;
		    //Here i have to add the checker
		    if (CurrentCSV.getName().contains("MMR") || CurrentCSV.getName().contains("IMR"))
		    	numCol = 25;
		    else
		    	numCol = 26;
		    
		    
		    //this is for copying the column tabs
		    for(int j=0;j<numCol;j++){
		    	
		    	FirstRow.createCell(j).setCellValue(nextLine[j]);
		    	
		    }
		    
		    
		    
	    	if (numCol == 26)
	    		LoanDepotStyle(nextLine, reader,sheet);
	    	if (numCol == 25)
	    		IMRandMMRStyle(nextLine, reader,sheet);
		    

		    
	        String CSVName = CurrentCSV.getName();
	        String XLSXName = CSVName.replace("csv","xlsx");
	        System.out.println(XLSXName);
	        
	        FileOutputStream fileOutputStream =  new FileOutputStream(this.directory+XLSXName);
	        workBook.write(fileOutputStream);
	        fileOutputStream.close();
	        System.out.println("Has Been Created!!!");

			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	private void LoanDepotStyle(String[] nextLine,CSVReader reader,XSSFSheet sheet ) throws NumberFormatException, IOException {
		int RowNum = 1;
		SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
		int c = 0;
		while ((nextLine = reader.readNext()) != null ) {
	    	XSSFRow currentRow = sheet.createRow(RowNum);
	    	if(nextLine[0].isEmpty()) {break;}
	    	
            for(int j=0;j<26;j++){
            	
            	//LoanNumber
            	if (j==0) {
            		//System.out.println(nextLine[j]);
            		if (nextLine[j].isEmpty()) {
            			//System.out.println("Its Empty");
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);

            		}
            	}
            	
            	//Borrower Status
            	if (j==5) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Martial Status
            	if (j==8) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}		            	
        
            	//Gender
            	if (j==21) {
            		
            		if (nextLine[j].isEmpty()) {
            			
            			currentRow.createCell(j).setCellValue(3);
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Origin Date
            	if (j==1) {
            		//System.out.println(nextLine[j]);
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(Dateformat.format(date));
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	//Funding Date
            	if (j==2) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(Dateformat.format(date));
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	
            	//DOB
            	if (j==6) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(Dateformat.format(date));
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}

            	if (j==3||j==4||j==7||j==9||j==10||j==11||j==12||j==13||j==14||j==15||j==16||j==17||j==18||j==19||j==20||j==22||j==23||j==24||j==25) {

            		currentRow.createCell(j).setCellValue(nextLine[j]);
            	}	            	
            }
            
	    	RowNum++;
	    }
		System.out.println(c);
		
	}
	
	private void IMRandMMRStyle(String[] nextLine,CSVReader reader,XSSFSheet sheet) throws NumberFormatException, IOException {
		int RowNum = 1;
		SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
		while ((nextLine = reader.readNext()) != null) {
	    	XSSFRow currentRow = sheet.createRow(RowNum);
   	
	    	
            for(int j=0;j<25;j++){
            	
            	//LoanNumber
            	if (j==0) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Borrower Status
            	if (j==4) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Martial Status
            	if (j==7) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}		            	
        
            	//Gender
            	if (j==20) {
            		
            		if (nextLine[j].isEmpty()) {
            			
            			currentRow.createCell(j).setCellValue(3);
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	

            	//Funding Date
            	if (j==1) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(Dateformat.format(date));
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	
            	//DOB
            	if (j==5) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(Dateformat.format(date));
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	} 
            	
            	if (j==2||j==3||j==6||j==8||j==9||j==10||j==11||j==12||j==13||j==14||j==15||j==16||j==17||j==18||j==19||j==21||j==22||j==23||j==24) {
            		currentRow.createCell(j).setCellValue(nextLine[j]);
            	}	            	
            }
	    	RowNum++;
	    }
		
		

		
		
		
		
		
	}
	
	public int merger(XSSFSheet sheet,File CurrentCSV, CSVReader reader, int RowNum) throws NumberFormatException, IOException {
		String [] nextLine;
	    nextLine = reader.readNext();//to remove the title 
	
		if (CurrentCSV.getName().contains("MMR")) {
			System.out.println("Copying Mortgage Master...");
			RowNum = MortgageMasterinserter(sheet,CurrentCSV, reader,nextLine,RowNum);
			
			
		}
		else if (CurrentCSV.getName().contains("IMR")) {
			System.out.println("Copying Imortgage...");
			RowNum = Imortgageinserter(sheet,CurrentCSV, reader,nextLine,RowNum);
			
		}
		else if (!CurrentCSV.getName().contains("IMR") || !CurrentCSV.getName().contains("MMR") ) {
	    	System.out.println("LoanDepot");
			RowNum = LoanDepotinserter(sheet,CurrentCSV, reader,nextLine,RowNum);
		}
			
		return RowNum;
	}
	
	private int MortgageMasterinserter(XSSFSheet sheet,File CurrentCSV, CSVReader reader, String [] nextLine,int RowNum ) throws NumberFormatException, IOException {
		String MMRDate = dateExtractor(CurrentCSV);
		mortgageMasterDate = MMRDate;
		
		listOfDates.add(MMRDate);
		SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
		int MCounter = 0;
		while ((nextLine = reader.readNext()) != null) {
	    	XSSFRow currentRow = sheet.createRow(RowNum);
	    	List<String> personalInfo = new ArrayList<String>();
	    	for (int i =0 ; i<6; i++) {
	    		personalInfo.add("      ");
	    	}
   	
	    	
            for(int j=0;j<28;j++){
            	
            	//LoanNumber
            	if (j==0) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			LoanNumber= nextLine[j];
            			MMRStageone.put(LoanNumber,null);
            			currentRow.createCell(j).setCellValue(value);
            			personalInfo.set(5, LoanNumber);
            		}
            	}
            	
            	//Borrower Status
            	if (j==4) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}
            	
            	//Martial Status
            	if (j==7) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}		            	
        
            	//Gender
            	if (j==20) {
            		
            		if (nextLine[j].isEmpty()) {
            		
            			currentRow.createCell(j+1).setCellValue(3);
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}
            	

            	//Funding Date
            	if (j==1) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j+1).setCellValue(date);
							personalInfo.set(4, Dateformat.format(date) );
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	
            	//DOB
            	if (j==5) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j+1).setCellValue(date);
							personalInfo.set(3, Dateformat.format(date) );
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	} 
            	if (j==13) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			String loanamount = nextLine[j].replaceAll(",", "");
            			Double value = Double.parseDouble(loanamount);
            			//System.out.println(value);
            			currentRow.createCell(j+1).setCellValue(value);

            		}
            	}
            	if(j==26) {
            		
            		currentRow.createCell(j).setCellValue("Mortgage Master");
            	}
            	
            	if (j==2||j==3||j==6||j==8||j==9||j==10||j==11||j==12||j==14||j==15||j==16||j==17||j==18||j==19||j==22||j==21||j==23||j==24) {
            		currentRow.createCell(j+1).setCellValue(nextLine[j]);
            		//First Name
            		if(j==2) {
            			personalInfo.set(1, nextLine[j]);}
            		//Last Name
            		else if(j==3) {
            			personalInfo.set(2, nextLine[j]);}
            		//SSN
            		else if(j==6) {
            			nextLine[j].toString();
            			MMRuniquessn.put(nextLine[j], null);
            			personalInfo.set(0, nextLine[j]);
            		}
            	}	            	
            }
	    	RowNum++;
	    	MCounter+= 1;
	    	allRecords.add(personalInfo);
	    	
	    }
		
		System.out.println(CurrentCSV.getName()+": "+ MCounter);
		
		for(String key: MMRuniquessn.keySet()) {
			List<String> currentsec = new ArrayList<String>();
			for (int i = 0 ; i < 4;i++) {
				currentsec.add("  ");
			}
			currentsec.set(0, key);
			currentsec.set(1, "0");
			currentsec.set(2, MMRDate);
			currentsec.set(3, "Mortgage Master");
			uniqueList.add(currentsec);
		}
		return RowNum;
		
		
	}
		
	private int Imortgageinserter(XSSFSheet sheet,File CurrentCSV, CSVReader reader, String [] nextLine,int RowNum ) throws NumberFormatException, IOException {
		
		
		String IMRDate = dateExtractor(CurrentCSV);
		imortgageDate = IMRDate;
		listOfDates.add(imortgageDate);
		
		SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
		int ICounter=0;
		while ((nextLine = reader.readNext()) != null) {
			List<String> personalInfo = new ArrayList<String>();
	    	XSSFRow currentRow = sheet.createRow(RowNum);
	    	for (int i =0 ; i<6; i++) {
	    		personalInfo.add("      ");
	    	}
   	
	    	
            for(int j=0;j<28;j++){
            	
            	//LoanNumber
            	if (j==0) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			LoanNumber = nextLine[j];
            			IMRStageone.put(LoanNumber,null);
            			currentRow.createCell(j).setCellValue(value);
            			personalInfo.set(5, LoanNumber);
            		}
            	}
            	
            	//Borrower Status
            	if (j==4) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}
            	
            	//Martial Status
            	if (j==7) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}		            	
        
            	//Gender
            	if (j==20) {
            		
            		if (nextLine[j].isEmpty()) {
            			
            			currentRow.createCell(j+1).setCellValue(3);
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j+1).setCellValue(value);
            		}
            	}
            	

            	//Funding Date
            	if (j==1) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j+1).setCellValue(date);
							personalInfo.set(4, Dateformat.format(date) );
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	
            	//DOB
            	if (j==5) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j+1).setCellValue(date);
							personalInfo.set(3, Dateformat.format(date) );
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	} 
            	if (j==13) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			String loanamount = nextLine[j].replaceAll(",", "");
            			Double value = Double.parseDouble(loanamount);
            			//System.out.println(value);
            			currentRow.createCell(j+1).setCellValue(value);

            		}
            	}
            	if(j==26) {
            		
            		currentRow.createCell(j).setCellValue("imortgage");
            	}
            	
            	if (j==2||j==3||j==6||j==8||j==9||j==10||j==11||j==12||j==14||j==15||j==16||j==17||j==18||j==19||j==22||j==21||j==23||j==24) {
            		currentRow.createCell(j+1).setCellValue(nextLine[j]);
            		//First Name
            		if(j==2) {
            			personalInfo.set(1, nextLine[j]);}
            		//Last Name
            		else if(j==3) {
            			personalInfo.set(2, nextLine[j]);}
            		//SSN
            		else if(j==6) {
            			nextLine[j].toString();
            			IMRuniquessn.put(nextLine[j], null);
            			personalInfo.set(0, nextLine[j]);
            		}

            	}	            	
            }
	    	RowNum++;
	    	ICounter +=1;
	    	allRecords.add(personalInfo);
	    }
		System.out.println(CurrentCSV.getName()+": "+ ICounter);
		//This will get the Unique SSN 
		for(String key: IMRuniquessn.keySet()) {
			List<String> currentsec = new ArrayList<String>();
			for (int i = 0 ; i < 4;i++) {
				currentsec.add("  ");
			}
			currentsec.set(0, key);
			currentsec.set(1, "0");
			currentsec.set(2, IMRDate);
			currentsec.set(3, "imortgage");
			uniqueList.add(currentsec);
		}
		return RowNum;
		
		
	}
		
	private int LoanDepotinserter(XSSFSheet sheet,File CurrentCSV, CSVReader reader, String [] nextLine,int RowNum ) throws NumberFormatException, IOException {
		

		System.out.println("Here we fixing this first");
		System.out.println("     ");

		

		String LDDate = dateExtractor(CurrentCSV);
		loanDepotDate = LDDate;
		listOfDates.add(LDDate);
		
		System.out.println(LDDate);
		
        
       
		
		
		
		SimpleDateFormat Dateformat = new SimpleDateFormat("MM/dd/yyyy");
		int LCounter=0;
		while ((nextLine = reader.readNext()) != null) {
			if(nextLine[0].isEmpty()) {break;}
	    	XSSFRow currentRow = sheet.createRow(RowNum);
	    	List<String> personalInfo = new ArrayList<String>();
	    	for (int i =0 ; i<6; i++) {
	    		personalInfo.add("      ");
	    	}
	    	
	    	
	    	BiFunction<List<String>,List<String>,List<String>> bi = (x,y)->{return y;};
            for(int j=0;j<28;j++){
            	
            	//LoanNumber
            	if (j==0) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            			
            			LoanNumber = nextLine[j].toString();
            			Stageone.put(LoanNumber,null);
            			personalInfo.set(5, LoanNumber);
            		}
            	}
            	
            	//Borrower Status
            	if (j==5) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Martial Status
            	if (j==8) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}		            	
        
            	//Gender
            	if (j==21) {
            		
            		if (nextLine[j].isEmpty()) {
            			
            			currentRow.createCell(j).setCellValue(3);
            		}
            		else {
            			Double value = Double.parseDouble(nextLine[j]);
            			currentRow.createCell(j).setCellValue(value);
            		}
            	}
            	
            	//Origin Date
            	if (j==1) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(date);
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	//Funding Date
            	if (j==2) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(date);
							personalInfo.set(4, Dateformat.format(date) );
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	
            	
            	//DOB
            	if (j==6) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			Date date;
						try {
							date = Dateformat.parse(nextLine[j]);
							currentRow.createCell(j).setCellValue(date);
							personalInfo.set(3, Dateformat.format(date) );
							
						} catch (ParseException e) {
							System.out.println("Error with Origin Date Section ");
						}
            			
            		}
            	}
            	//Loan Amount
            	if (j==14) {
            		
            		if (nextLine[j].isEmpty()) {
            			continue;
            		}
            		else {
            			String loanamount = nextLine[j].replaceAll(",", "");
            			Double value = Double.parseDouble(loanamount);
            			//System.out.println(value);
            			currentRow.createCell(j).setCellValue(value);

            		}
            	}
            	if(j==26) {
            		
            		currentRow.createCell(j).setCellValue("loanDepot");
            	}
            	
            	if (j==3||j==4||j==7||j==9||j==10||j==11||j==12||j==13||j==15||j==16||j==17||j==18||j==19||j==20||j==22||j==23||j==24||j==25) {
            		currentRow.createCell(j).setCellValue(nextLine[j]);
            		if(j==3)
            			
            			personalInfo.set(1, nextLine[j]);
            		else if(j==4)
            			personalInfo.set(2, nextLine[j]);
            		else if(j==7) {
            		nextLine[j].toString();
            			uniquessn.put(nextLine[j], null); 
            			personalInfo.set(0, nextLine[j]);

            		}
            		
            	}
            	
            }
	    	RowNum++;
	    	LCounter+=1;
	    	Stageone.merge(LoanNumber,personalInfo,bi);
	    	allRecords.add(personalInfo);
	    	
	    	
	    }
		
		System.out.println(CurrentCSV.getName()+": "+ LCounter);
		//System.out.println(allRecords);
		
		
		///////this i sfor the weekly file
		for(String key: uniquessn.keySet()) {
			List<String> currentsec = new ArrayList<String>();
			for (int i = 0 ; i < 4;i++) {
				currentsec.add("  ");
			}
			currentsec.set(0, key);
			currentsec.set(1, "0");
			currentsec.set(2, LDDate);
			currentsec.set(3, "loanDepot");
			uniqueList.add(currentsec);
		}
		
		return RowNum;
	}
	
	public String dateExtractor(File file) {
		String csvfilename = file.getName();
		String regex = "(\\d*)_(\\d*)_(\\d\\d\\d\\d)";
		Pattern pattern = Pattern.compile(regex);
		String[] datecom = new String[3];
		Matcher dateInString = pattern.matcher(csvfilename);
		if(dateInString.find()) {
			datecom[0]= dateInString.group(1);
			datecom[1]= dateInString.group(2);
			datecom[2]= dateInString.group(3);
			System.out.println("Date Created by regex: "+datecom[0]+"/"+datecom[1]+"/"+datecom[2]);
			
		}
		
		return datecom[0]+"/"+datecom[1]+"/"+datecom[2];
	}
}
