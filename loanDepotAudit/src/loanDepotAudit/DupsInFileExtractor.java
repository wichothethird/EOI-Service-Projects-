package loanDepotAudit;

import java.util.HashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

import org.apache.commons.collections4.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyle;

import au.com.bytecode.opencsv.CSVReader;

public class DupsInFileExtractor {
	
	static String username = System.getProperty("user.name");
	public static List<List<String>> sorter(List allRecords) {
		//This function will be used in the main Function, so in he future make sure you pass the hashmap as a parameter and return a srted hashmap.
		List<String> current = new ArrayList<String>();
		List<String> prev = new ArrayList<String>(); 
		List<String> next = new ArrayList<String>();
		
		allRecords.sort((l1,l2)->  ((List<String>) l1).get(0).compareTo(((List<String>) l2).get(0)));
		
		return allRecords;
		
		
	}
	
	public static Set<List<String>> dupsFinder(List allRecords) {
		List<String> current = new ArrayList<String>();
		List<String> prev = new ArrayList<String>(); 
		Set<List<String>> seto = new HashSet<List<String>>();
		List<List<String>> DupsInFile = new ArrayList<List<String>>();
		if (allRecords.size()==1) {
			return seto;
		}
		int num =1;
		current = (List<String>) allRecords.get(num);
		

		for (int i = 0; i < allRecords.size(); i++) {
			//System.out.println(allRecords.get(i));
			current = (List<String>) allRecords.get(num);
			prev = (List<String>) allRecords.get(i);
			
			//Checking that the current is not out of bounce.
			if (allRecords.indexOf(current)==allRecords.size()-1) {
				
				String CurrentSSNString = current.get(0).replaceAll("-", "");
				String PrevSSNString = prev.get(0).replaceAll("-", "");
				
				//System.out.println(PrevSSNString);
				//System.out.println(CurrentSSNString);
				Double CurrentSSN = Double.valueOf(CurrentSSNString);
				Double PrevSSN = Double.valueOf(PrevSSNString);
				//System.out.println(allRecords.get(num));
				if (CurrentSSN.equals(PrevSSN) ) {
					//System.out.println("__________________________________________________________________________________");
					seto.add(current);
					seto.add(prev);
				}
				break;
			}
			
			String CurrentSSNString = current.get(0).replaceAll("-", "");
			String PrevSSNString = prev.get(0).replaceAll("-", "");
			
			//System.out.println(PrevSSNString);
			//System.out.println(CurrentSSNString);
			Double CurrentSSN = Double.valueOf(CurrentSSNString);
			Double PrevSSN = Double.valueOf(PrevSSNString);
			
			if (CurrentSSN.equals(PrevSSN) ) {
				//System.out.println("__________________________________________________________________________________");
				seto.add(current);
				seto.add(prev);
			}
			num++;

			
			
		}
		
		return seto;
		
	}
	
	public static void copyIntoTemplate(List dups,List dupsInWeekly)  {

		
		FilesLookUp FileDir = new FilesLookUp();
		XSSFWorkbook workBook1;
		try {
			System.out.println(FileDir.AFLACForConverterName);
			workBook1 = new XSSFWorkbook(new FileInputStream(FileDir.AFLACForConverterName));
			
			XSSFSheet sheet1 = workBook1.createSheet("DUPS IN FILE");
			XSSFSheet sheet2 = workBook1.createSheet("DUPS IN Wkly");
			XSSFRow FirstRow = sheet1.createRow(0);
			String headers = "SSN FirstName LastName OriginDate FundingDate LoanNumber";
			String[] listOfHeaders = headers.split(" ");
			

			
			for(int j = 0 ; j < 6;j++ ) {
				FirstRow.createCell(j).setCellValue(listOfHeaders[j]);
			}
			
			
			int c =1;
			for(int j = 0 ; j < dups.size() ;j++ ) {
				
				XSSFRow nextRow = sheet1.createRow(c);
				List<String> currentContent = (List<String>) dups.get(j);
				for(int i = 0 ; i < 6;i++ ) {
					System.out.println(currentContent.get(i));
					nextRow.createCell(i).setCellValue(currentContent.get(i));
				}
				c++;
			}
			
			
			String headers2 = "SSN Formula Date Location";
			String[] listOfHeaders2 = headers2.split(" ");
			XSSFRow FirstRow2 = sheet2.createRow(0);
			for(int j = 0 ; j < 4;j++ ) {
				FirstRow2.createCell(j).setCellValue(listOfHeaders2[j]);
			}
			
			int d =1;
			for(int j = 0 ; j < dupsInWeekly.size() ;j++ ) {
				
				XSSFRow nextRow = sheet2.createRow(d);
				List<String> currentContent = (List<String>) dupsInWeekly.get(j);
				for(int i = 0 ; i < 4;i++ ) {
					//System.out.println(currentContent.get(i));
					nextRow.createCell(i).setCellValue(currentContent.get(i));
				}
				d++;
			}
			
			
			FileOutputStream fileOutputStream1 =  new FileOutputStream(FileDir.AFLACForConverterName);
			workBook1.write(fileOutputStream1);
	        fileOutputStream1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void copyWeekly(List allrecords) {
		

		
		XSSFWorkbook workBook1;
		try {
			System.out.println("ALmost there!!");
			workBook1 = new XSSFWorkbook(new FileInputStream("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\"+"loanDepot- Weekly File Total-2018-02-07.xlsx"));
			
			System.out.println("It opened ");
			XSSFSheet sheet1 = workBook1.getSheet("For Dup Check");
			

			

			System.out.println(allrecords.size());
			
			String headers2 = "SSN Formula Date Location";
			String[] listOfHeaders2 = headers2.split(" ");
			XSSFRow FirstRow2 = sheet1.createRow(0);
			for(int j = 0 ; j < 4;j++ ) {
				FirstRow2.createCell(j).setCellValue(listOfHeaders2[j]);
			}


			int d =1;
			for(int j = 0 ; j < allrecords.size() ;j++ ) {
				SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
				XSSFRow nextRow = sheet1.createRow(d);
				List<String> currentContent = (List<String>) allrecords.get(j);
				for(int i = 0 ; i < 4;i++ ) {
					if (i==0||i==3) {
						//System.out.println(currentContent.get(i));
						nextRow.createCell(i).setCellValue(currentContent.get(i));
					}
					else if (i==1) {
						//System.out.println(currentContent.get(i));
						Double value = Double.parseDouble(currentContent.get(i));
						nextRow.createCell(i).setCellValue(value);
					}
					else if(i==2) {
						Date date;
						try {
							date = formatter.parse(currentContent.get(i));
							String dateString = formatter.format(date);
							//System.out.println(dateString);
							nextRow.createCell(i).setCellValue(date);
						} catch (ParseException e) {
							System.out.println("Error with copying the date back to the weekly file!!!");
						}
					}
				}
				d++;
			}
			
			System.out.println("Finished");
			FileOutputStream fileOutputStream1 =  new FileOutputStream("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\"+"loanDepot- Weekly File Total-2018-02-07.xlsx");
		
			workBook1.write(fileOutputStream1);
			
	        fileOutputStream1.close();
			
		} catch (IOException e) {
			System.out.println("Not yet ");
		}

		
		
		
	}


}







