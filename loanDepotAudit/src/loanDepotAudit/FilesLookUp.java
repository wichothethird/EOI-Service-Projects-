package loanDepotAudit;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
 
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.*;
import java.util.*;
import org.apache.commons.collections4.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyle;
import java.text.SimpleDateFormat;

import au.com.bytecode.opencsv.CSVReader;


/*
 * This class is in charge of looking up the files in C:\Users\EOI Support\Desktop\LoanDepot Audit\CSV Files 
 * Directory. This is to get all the files that will be input into the Template folder for converter. 
 * 
 */
public class FilesLookUp {
	String username = System.getProperty("user.name");
	public static String AFLACForConverterName;
	private String directory = "C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\";
	private String loandepotFileName;
	public static List<List<String>> allrecords = new ArrayList<List<String>>();
	public static List<String> listOfDates = new ArrayList<String>();
	public File[] getCSVFiles() {
		File CSVFolder = new File("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit\\CSV Files");
		File[] CSVFiles = CSVFolder.listFiles();
		return CSVFiles;
	}
	
	public File[] getFiles() {
		File CSVFolder = new File("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit");
		File[] CSVFiles = CSVFolder.listFiles();
		return CSVFiles;
	}
	public File getCBFile() {
		File auditFolder = new File("C:\\Users\\"+username+"\\Desktop\\LoanDepot Audit");
		File[] allFiles = auditFolder.listFiles();
		File cbFile = null;

		for(File currentFile: allFiles ) {
			if (currentFile.getName().contains("Current Benefits")&&!currentFile.getName().contains("$")) {
				System.out.println(currentFile);
				cbFile= currentFile;
			}
			
		}
		return cbFile;
	}
	public String showCSVfiles(File[] Files) {

		String filesString = "";
		for (int i = 0; i < Files.length; i++) {
			
			if (Files[i].isFile()) { 
				filesString = filesString+Files[i].getName()+"\r\n";}
				//System.out.println(Files[i].getName());}
			else 
				System.out.println("That's not a file");
		}
		return filesString;
	}
	
	public void convertCSVtoXLSX(File[] CSVFiles) throws IOException {
		for (int i = 0; i < CSVFiles.length; i++) {
			System.out.println(CSVFiles[i].getName());
			File CurrentCSV = CSVFiles[i];
			
			XSSFWorkbook workBook = new XSSFWorkbook();
			
	        DataTypeChecker ch = new DataTypeChecker();
	        ch.XLSXBuilder(workBook, CurrentCSV);

		}

		
	}
	
	public void XLSXMerger(File[] CSVFiles) throws IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet("Import");
		XSSFRow FirstRow = sheet.createRow(0);
		int RowNum =1;
		String str = "LoanNumber OrigDate FundingDate BorrowerFirstName BorrowerLastName BorrowerStatus DOB SSN MaritalStatus HomePhoneNumber WorkPhoneNumber CellPhoneNumber EmailAddress LoanType LoanAmount HouseNumber Address1 Address2 City State ZipCode Gender Income Title Occupation Employer Location" ;
		String[] s = str.split(" ");
		
		
		for(int j = 0 ; j < 27;j++ ) {
			FirstRow.createCell(j).setCellValue(s[j]);
		}
		
		System.out.println("Total Number of CSV Files: "+String.valueOf(CSVFiles.length));
		for (int i = 0; i < CSVFiles.length; i++) {
			
			File CurrentCSV = CSVFiles[i];
			CSVReader reader;
			reader = new CSVReader(new FileReader(CurrentCSV));
	        DataTypeChecker ch = new DataTypeChecker();
	        RowNum = ch.merger(sheet, CurrentCSV,reader,RowNum);

		}

		AFLACForConverterName = this.directory+"AFLAC Opt ln For Converter.xlsx";
		FileOutputStream fileOutputStream =  new FileOutputStream(this.directory+"AFLAC Opt ln For Converter.xlsx");
		workBook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("Template for Converter has been Created!!!");
        RowNum = RowNum -1; //minus 1 because the title doesnt count as a benefit.
        System.out.println("Total Number of benefits: "+String.valueOf(RowNum));

        
        
	}
	

	@SuppressWarnings("deprecation")
	public void openWeekly() throws EncryptedDocumentException, InvalidFormatException, IOException {
		//Check Directory for Weekly file 
		System.out.print("12344");
		File Xlsx = new File(directory+"loanDepot- Weekly File Total-2018-02-07.xlsx");
		System.out.print("12344");
		@SuppressWarnings("resource")
		XSSFWorkbook weeklyFile = new XSSFWorkbook(Xlsx);
		System.out.print("12344");
		XSSFSheet sheet= weeklyFile.getSheet("For Dup Check");
		
		int RowNum = sheet.getLastRowNum();
		System.out.println(RowNum);
		XSSFRow row = sheet.getRow(0);
		DataTypeChecker D = new DataTypeChecker();
		
		
		//XSSFCell cell = row.getCell(0);
		short firstCell = row.getFirstCellNum();
		short lastCell = row.getLastCellNum();
		//System.out.println(cell.toString());
		for (int i = 1; i < RowNum+1; i++) {
			List<String> currentrecord = new ArrayList<String>();
	    	for (int r =0 ; r<4; r++) {
	    		currentrecord.add("      ");
	    	}
			row = sheet.getRow(i);
			SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
			for(short colIndex = firstCell; colIndex <4; colIndex++) { 
				XSSFCell cell = row.getCell(colIndex); 
				CellType type = cell.getCellTypeEnum();
				
				if (colIndex == 0) {
					String social = getCellValueAsString(cell);
					currentrecord.set(0, social);
				}
				if (colIndex == 1) {
					String number = getCellValueAsString(cell);
					currentrecord.set(1, number);
				}
				if (colIndex == 2) {
					
					String date = getCellValueAsString(cell);
					currentrecord.set(2, date);
				}
				if (colIndex == 3) {
					String location = getCellValueAsString(cell);
					currentrecord.set(3, location);
				}

			}
			
			//System.out.println(currentrecord.toString());
			
			allrecords.add(currentrecord);
	
		}
		System.out.println(allrecords.size());
		System.out.println(D.uniqueList.size());
		for (int j =0 ; j < D.uniqueList.size();j++) {
			List<String> lista = D.uniqueList.get(j);
			allrecords.add(lista);
		}
		System.out.println(allrecords.get(0));
		System.out.println("After Addition: "+allrecords.size());
		
		
	}
	
	
	
    public static String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                strCellValue = cell.toString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "MM/dd/yyyy");
                    strCellValue = dateFormat.format(cell.getDateCellValue());
                } else {
                    Double value = cell.getNumericCellValue();
                    Long longValue = value.longValue();
                    strCellValue = new String(longValue.toString());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                strCellValue = new String(new Boolean(
                        cell.getBooleanCellValue()).toString());
                break;
            case Cell.CELL_TYPE_BLANK:
                strCellValue = "";
                break;
            }
        }
        return strCellValue;
    }

}
