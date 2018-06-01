package loanDepotAudit;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.Date;
 
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
import org.bson.Document;

import java.util.*;
import org.apache.commons.collections4.*;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTableStyle;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import au.com.bytecode.opencsv.CSVReader;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
import javax.swing.SwingWorker;

import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;

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
import javax.swing.event.ChangeEvent;
import javax.swing.JTextPane;
import javax.swing.DropMode;
import javax.swing.JSeparator;
import javax.swing.JScrollBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JLayeredPane;

public class LoanDepotAppv1 {
	private JFrame frame;
	private JPanel panel_4;
	private JProgressBar progressBar = new JProgressBar();
	private JLabel lblNewLabel_13 = new JLabel("");
	private JLabel label_1 = new JLabel("");
	private JLabel label_2 = new JLabel("");
	private JLabel label_3 = new JLabel("");
	private JLabel lblNewLabel_14 = new JLabel("Converted to Xlsx");
	private JLabel lblTemplateCreated = new JLabel("Template Created");
	private JLabel lblWeeklyFileUpdated = new JLabel("Weekly File Updated ");
	private JLabel lblDuplicatesExtracted = new JLabel("Duplicates Extracted");

	private JLabel lblTotal = new JLabel("");
	private JLabel label_4 = new JLabel("");
	private JLabel label_5 = new JLabel("");
	private JLabel label_6 = new JLabel("");
	private JLabel label_7 = new JLabel("");
	private JLabel label_8 = new JLabel("");
	public String benefitstxt = "";
	public String employeestxt = "";
	private JTable table;
	private JTable table_1 = new JTable();
	public String cost = "";
	public int premium = 0;
	public int CB= 0;
	public int numEmploy = 0; 
	public FinanceManager Ptotal = null;
	public FinanceManager Ltotal = null;
	MongoDBConnection Mongo = new MongoDBConnection();
	MongoDatabase database  = Mongo.connectToMongoDB("nitro5", "SASuke1!");
	String Collection = "EOIDatabase";
	MongoCollection<Document> EOICollection = database.getCollection(Collection);
	NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	NumberFormat currencyFormat = NumberFormat.getNumberInstance();
	int prep;
	int prel;
	int prei;
	int prem;
	int borp;
	int borl;
	int bori;
	int borm;
	int loanp;
	int loanl;
	int loani;
	int loanm;
	
	
	
	//private JTable table;
	//Abstract classes 
	class LoanDepotLoader extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			setProgress(0);
			LoanDepotAudit loanDepot = new LoanDepotAudit();
			loanDepot.loanDepotRunner( progressBar, lblNewLabel_13, lblNewLabel_14,label_1,lblTemplateCreated,label_2,lblWeeklyFileUpdated,label_3,lblDuplicatesExtracted,lblTotal,label_4,label_5,label_6,label_7,label_8,benefitstxt,table, table_1, numEmploy, Ptotal, Ltotal);
			return null;
		}
		protected void done() {
			System.out.println("Done Running in the background");
		}
		
	}
	
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoanDepotAppv1 window = new LoanDepotAppv1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoanDepotAppv1() {
		initialize();
	}
	private String monthIdentifier(int monthNum) {
		String month = "";
		if (monthNum==1)
			month = "January";
		if (monthNum==2)
			month = "Febuary";
		if (monthNum==3)
			month = "March";
		if (monthNum==4)
			month = "April";
		if (monthNum==5)
			month = "May";
		if (monthNum==6)
			month = "June";
		if (monthNum==7)
			month = "July";
		if (monthNum==8)
			month = "August";
		if (monthNum==9)
			month = "September";
		if (monthNum==10)
			month = "October";
		if (monthNum==11)
			month = "November";
		if (monthNum==12)
			month = "December";
			
		return month;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		LoanDepotAudit Audit = new LoanDepotAudit();
		String FileName = "C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\loanDepotAudit\\Current Benefits.txt";
		String PTName = "C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\loanDepotAudit\\Project Total.txt";
		String LName = "C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\loanDepotAudit\\LoanDepot Total.txt";
		String IMName = "C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\loanDepotAudit\\Imortgage Total.txt";
		String MMName = "C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\loanDepotAudit\\Mortgage Master.txt";

		FinanceManager Itotal = null;
		FinanceManager Mtotal = null;
		
		try {
			FileReader filereader = new FileReader(FileName);
			BufferedReader bufferedReader = new BufferedReader(filereader);
			FileReader filereader1 = new FileReader(PTName);
			BufferedReader PReader = new BufferedReader(filereader1);
			FileReader filereader2 = new FileReader(LName);
			BufferedReader LReader = new BufferedReader(filereader2);
			FileReader filereader3 = new FileReader(IMName);
			BufferedReader IReader = new BufferedReader(filereader3);
			FileReader filereader4 = new FileReader(MMName);
			BufferedReader MReader = new BufferedReader(filereader4);
			//Here we get everything for the files 
			
			Ptotal = new FinanceManager(PReader);
			Ltotal = new FinanceManager(LReader);
			Itotal = new FinanceManager(IReader);
			Mtotal = new FinanceManager(MReader);
			
			
            benefitstxt = bufferedReader.readLine();
            CB = Integer.valueOf(benefitstxt);
            employeestxt = bufferedReader.readLine();
            numEmploy = Integer.valueOf(employeestxt);
            
		} catch (FileNotFoundException e1) {
			System.out.println("Current Benefits txt couldn't open!!!");
		
		} catch (IOException e1) {
			System.out.println("No content on Current Benefits txt!!!");
		}
		/////////////////////////////////////////////////////////////////////////////
		progressBar.setForeground(new Color(153, 102, 153));
		progressBar.setToolTipText("");
		progressBar.setStringPainted(true);
		/////////////////////////////////////////////////////////////////////////////////
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(new Double(10));
		values.add(new Double(20));
		values.add(new Double(30));
		values.add(new Double(15));
		values.add(new Double(15));
		 
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.gray);
		colors.add(Color.green);
		colors.add(Color.blue);
		colors.add(Color.pink);
		colors.add(Color.yellow);
		/////////////////////////////////////////////////////////////////////////////////
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 1440, 926);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(255, 255, 255));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
		);
		
		
		
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(null);
		panel_16.setBackground(new Color(255, 255, 255));

		CardLayout cardsbeast = new CardLayout(0, 0);
		panel_16.setLayout(cardsbeast);
		String BLANKPANEL =  "Card BLANK in BEAST PANEL";
		String BARPANEL =  "BAR Card for Beast Panel";
		JPanel Blank = new JPanel();
		JPanel Bar = new JPanel();

		Blank.setBackground(Color.WHITE);
		Bar.setBackground(Color.WHITE);

		panel_16.add(Blank,BLANKPANEL);
		panel_16.add(Bar,BARPANEL);
		GroupLayout gl_Blank = new GroupLayout(Blank);
		gl_Blank.setHorizontalGroup(
			gl_Blank.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1012, Short.MAX_VALUE)
		);
		gl_Blank.setVerticalGroup(
			gl_Blank.createParallelGroup(Alignment.LEADING)
				.addGap(0, 313, Short.MAX_VALUE)
		);
		Blank.setLayout(gl_Blank);
		
		



		
		JPanel panel_21 = new JPanel();
		panel_21.setBackground(new Color(255, 255, 255));
		
		JLabel lblNewLabel_12 = new JLabel("Progress");
		lblNewLabel_12.setForeground(new Color(153, 102, 153));
		lblNewLabel_12.setFont(new Font("Segoe UI", Font.BOLD, 31));
		GroupLayout gl_panel_21 = new GroupLayout(panel_21);
		gl_panel_21.setHorizontalGroup(
			gl_panel_21.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_21.createSequentialGroup()
					.addComponent(lblNewLabel_12)
					.addContainerGap(215, Short.MAX_VALUE))
		);
		gl_panel_21.setVerticalGroup(
			gl_panel_21.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_21.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_12)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		panel_21.setLayout(gl_panel_21);
		
		JPanel panel_25 = new JPanel();
		panel_25.setBackground(new Color(255, 255, 255));
		
		JPanel panel_26 = new JPanel();
		panel_26.setBackground(new Color(255, 255, 255));
		
		JPanel panel_27 = new JPanel();
		panel_27.setBackground(new Color(255, 255, 255));
		
		JPanel panel_28 = new JPanel();
		panel_28.setBackground(new Color(255, 255, 255));
		GroupLayout gl_Bar = new GroupLayout(Bar);
		gl_Bar.setHorizontalGroup(
			gl_Bar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Bar.createSequentialGroup()
					.addGap(39)
					.addGroup(gl_Bar.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_21, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_Bar.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_Bar.createSequentialGroup()
								.addComponent(panel_25, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_26, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(panel_27, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
								.addGap(28)
								.addComponent(panel_28, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED))
							.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 961, GroupLayout.PREFERRED_SIZE)))
					.addGap(12))
		);
		gl_Bar.setVerticalGroup(
			gl_Bar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Bar.createSequentialGroup()
					.addComponent(panel_21, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addGroup(gl_Bar.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_28, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(panel_27, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(panel_26, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
						.addComponent(panel_25, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
					.addGap(0, 0, Short.MAX_VALUE))
		);
		
		
		label_3.setEnabled(false);
		label_3.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\excel.png"));
		
		
		lblDuplicatesExtracted.setEnabled(false);
		lblDuplicatesExtracted.setForeground(new Color(153, 102, 153));
		lblDuplicatesExtracted.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GroupLayout gl_panel_28 = new GroupLayout(panel_28);
		gl_panel_28.setHorizontalGroup(
			gl_panel_28.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_28.createSequentialGroup()
					.addContainerGap(77, Short.MAX_VALUE)
					.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(73))
				.addGroup(Alignment.LEADING, gl_panel_28.createSequentialGroup()
					.addGap(25)
					.addComponent(lblDuplicatesExtracted, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_panel_28.setVerticalGroup(
			gl_panel_28.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_28.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblDuplicatesExtracted, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		panel_28.setLayout(gl_panel_28);
		
		
		label_2.setEnabled(false);
		label_2.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\excel.png"));
		
		
		lblWeeklyFileUpdated.setEnabled(false);
		lblWeeklyFileUpdated.setForeground(new Color(153, 102, 153));
		lblWeeklyFileUpdated.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GroupLayout gl_panel_27 = new GroupLayout(panel_27);
		gl_panel_27.setHorizontalGroup(
			gl_panel_27.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_27.createSequentialGroup()
					.addGap(72)
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(78, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_27.createSequentialGroup()
					.addContainerGap(27, Short.MAX_VALUE)
					.addComponent(lblWeeklyFileUpdated)
					.addGap(23))
		);
		gl_panel_27.setVerticalGroup(
			gl_panel_27.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_27.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblWeeklyFileUpdated, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		panel_27.setLayout(gl_panel_27);
		
		
		label_1.setEnabled(false);
		label_1.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\excel.png"));
		
		
		lblTemplateCreated.setEnabled(false);
		lblTemplateCreated.setForeground(new Color(153, 102, 153));
		lblTemplateCreated.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GroupLayout gl_panel_26 = new GroupLayout(panel_26);
		gl_panel_26.setHorizontalGroup(
			gl_panel_26.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_26.createSequentialGroup()
					.addGap(67)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(83, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel_26.createSequentialGroup()
					.addContainerGap(37, Short.MAX_VALUE)
					.addComponent(lblTemplateCreated, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		gl_panel_26.setVerticalGroup(
			gl_panel_26.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_26.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTemplateCreated, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(55, Short.MAX_VALUE))
		);
		panel_26.setLayout(gl_panel_26);
		
		
		lblNewLabel_13.setEnabled(false);
		lblNewLabel_13.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\excel.png"));
		
		
		lblNewLabel_14.setEnabled(false);
		lblNewLabel_14.setForeground(new Color(153, 102, 153));
		lblNewLabel_14.setFont(new Font("Segoe UI", Font.BOLD, 17));
		GroupLayout gl_panel_25 = new GroupLayout(panel_25);
		gl_panel_25.setHorizontalGroup(
			gl_panel_25.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_25.createSequentialGroup()
					.addContainerGap(70, Short.MAX_VALUE)
					.addComponent(lblNewLabel_13, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(80))
				.addGroup(gl_panel_25.createSequentialGroup()
					.addGap(32)
					.addComponent(lblNewLabel_14, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_panel_25.setVerticalGroup(
			gl_panel_25.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_25.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_13)
					.addGap(18)
					.addComponent(lblNewLabel_14, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		panel_25.setLayout(gl_panel_25);
		Bar.setLayout(gl_Bar);
		String WAITPANEL =  "Card for WAITING";
		String STATSPANEL =  "Card for STATS";		
		
		CardLayout cards = new CardLayout(0, 0);
		panel_1.setLayout(cards);
		
		String HOMEPANEL =  "Card for Home Panel";
		String BEASTPANEL =  "Card for Beast Panel";
		String RESULTSPANEL =  "Card for Results Panel";
		String FINANCESPANEL =  "Card for Finances Panel";
		
		JPanel Home = new JPanel();
		JPanel Beast = new JPanel();
		JPanel Results = new JPanel();
		Results.setForeground(new Color(0, 102, 102));
		JPanel Finances = new JPanel();
		Finances.setForeground(SystemColor.textHighlight);
		Home.setBackground(Color.WHITE);
		Beast.setBackground(Color.WHITE);
		Results.setBackground(Color.WHITE);
		Finances.setBackground(Color.WHITE);
		
		panel_1.add(Home,HOMEPANEL);
		
		JPanel panel_29 = new JPanel();
		panel_29.setBackground(Color.WHITE);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panel_33 = new JPanel();

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JPanel panel_34 = new JPanel();
		panel_34.setBackground(Color.WHITE);
		GroupLayout gl_Home = new GroupLayout(Home);
		gl_Home.setHorizontalGroup(
			gl_Home.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Home.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_Home.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_29, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
						.addGroup(gl_Home.createSequentialGroup()
							.addComponent(panel_34, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_33, GroupLayout.PREFERRED_SIZE, 537, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_Home.setVerticalGroup(
			gl_Home.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Home.createSequentialGroup()
					.addGroup(gl_Home.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_Home.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_33, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_Home.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_34, GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(panel_29, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JPanel panel_48 = new JPanel();
		panel_48.setBackground(SystemColor.textHighlight);
		panel_48.setForeground(SystemColor.textHighlight);
		GroupLayout gl_panel_33 = new GroupLayout(panel_33);
		gl_panel_33.setHorizontalGroup(
			gl_panel_33.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_33.createSequentialGroup()
					.addComponent(panel_48, GroupLayout.PREFERRED_SIZE, 535, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_33.setVerticalGroup(
			gl_panel_33.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_33.createSequentialGroup()
					.addComponent(panel_48, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(299, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_24 = new JLabel("Upcoming Events");
		lblNewLabel_24.setForeground(SystemColor.text);
		lblNewLabel_24.setFont(new Font("Segoe UI", Font.BOLD, 25));
		GroupLayout gl_panel_48 = new GroupLayout(panel_48);
		gl_panel_48.setHorizontalGroup(
			gl_panel_48.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_48.createSequentialGroup()
					.addContainerGap(162, Short.MAX_VALUE)
					.addComponent(lblNewLabel_24, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
					.addGap(137))
		);
		gl_panel_48.setVerticalGroup(
			gl_panel_48.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_48.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_24, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_48.setLayout(gl_panel_48);
		panel_33.setLayout(gl_panel_33);
		
		JLabel lblNewLabel_10 = new JLabel("Current Benefits");
		lblNewLabel_10.setForeground(SystemColor.textHighlight);
		lblNewLabel_10.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel lblBorrowers_1 = new JLabel("Borrowers ");
		lblBorrowers_1.setForeground(SystemColor.textHighlight);
		lblBorrowers_1.setFont(new Font("Segoe UI", Font.BOLD, 28));
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		JLabel lblNewLabel_11 = new JLabel("");
		/////////////////CURENT BENEFITS
		int CurBen = Mongo.getCurrentBenefit(EOICollection);
		
		String numberString = numberFormat.format(CurBen);
		
		lblNewLabel_11.setText(numberString);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		lblNewLabel_11.setForeground(SystemColor.textHighlight);
		lblNewLabel_11.setFont(new Font("Segoe UI", Font.BOLD, 60));
		
		
		int CurBor = Mongo.getCurrentBorrowers(EOICollection);
		
		String numberStrings = numberFormat.format(CurBor);
		JLabel label_9 = new JLabel(numberStrings);
		///////////////////////////////////////////////////////////////////////////////////////////////////
		label_9.setForeground(SystemColor.textHighlight);
		label_9.setFont(new Font("Segoe UI", Font.BOLD, 60));
		GroupLayout gl_panel_34 = new GroupLayout(panel_34);
		gl_panel_34.setHorizontalGroup(
			gl_panel_34.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_34.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_panel_34.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBorrowers_1, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_10, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(119, Short.MAX_VALUE))
				.addGroup(gl_panel_34.createSequentialGroup()
					.addContainerGap(146, Short.MAX_VALUE)
					.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_34.createSequentialGroup()
					.addContainerGap(146, Short.MAX_VALUE)
					.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_34.setVerticalGroup(
			gl_panel_34.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_34.createSequentialGroup()
					.addGap(33)
					.addComponent(lblNewLabel_10, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblBorrowers_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(32, Short.MAX_VALUE))
		);
		panel_34.setLayout(gl_panel_34);
		
		JPanel panel_5 = new JPanel();
		
		panel_5.setBackground(SystemColor.control);
		panel_5.setBorder(null);
		
		
		JPanel panel_20 = new JPanel();
		
		JPanel panel_22 = new JPanel();
		GroupLayout gl_panel_29 = new GroupLayout(panel_29);
		gl_panel_29.setHorizontalGroup(
			gl_panel_29.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_29.createSequentialGroup()
					.addGap(42)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
					.addGap(61)
					.addComponent(panel_20, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
					.addGap(60)
					.addComponent(panel_22, GroupLayout.PREFERRED_SIZE, 269, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		gl_panel_29.setVerticalGroup(
			gl_panel_29.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_29.createSequentialGroup()
					.addContainerGap(146, Short.MAX_VALUE)
					.addGroup(gl_panel_29.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_22, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel_29.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE))
						.addComponent(panel_20, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(30))
		);
		
		JPanel panel_32 = new JPanel();
		panel_32.setBackground(SystemColor.textHighlight);
		GroupLayout gl_panel_22 = new GroupLayout(panel_22);
		gl_panel_22.setHorizontalGroup(
			gl_panel_22.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_32, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
		);
		gl_panel_22.setVerticalGroup(
			gl_panel_22.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_22.createSequentialGroup()
					.addComponent(panel_32, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(354, Short.MAX_VALUE))
		);
		GroupLayout gl_panel_32 = new GroupLayout(panel_32);
		gl_panel_32.setHorizontalGroup(
			gl_panel_32.createParallelGroup(Alignment.LEADING)
				.addGap(0, 269, Short.MAX_VALUE)
		);
		gl_panel_32.setVerticalGroup(
			gl_panel_32.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
		);
		panel_32.setLayout(gl_panel_32);
		panel_22.setLayout(gl_panel_22);
		JPanel panel_31 = new JPanel();
		panel_31.setBackground(SystemColor.textHighlight);
		GroupLayout gl_panel_20 = new GroupLayout(panel_20);
		gl_panel_20.setHorizontalGroup(
			gl_panel_20.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_20.createSequentialGroup()
					.addComponent(panel_31, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_20.setVerticalGroup(
			gl_panel_20.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_20.createSequentialGroup()
					.addComponent(panel_31, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(354, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_16 = new JLabel("Loans");
		lblNewLabel_16.setForeground(Color.WHITE);
		lblNewLabel_16.setFont(new Font("Segoe UI", Font.BOLD, 24));
		GroupLayout gl_panel_31 = new GroupLayout(panel_31);
		gl_panel_31.setHorizontalGroup(
			gl_panel_31.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_31.createSequentialGroup()
					.addContainerGap(98, Short.MAX_VALUE)
					.addComponent(lblNewLabel_16, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addGap(76))
		);
		gl_panel_31.setVerticalGroup(
			gl_panel_31.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_31.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_16, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		panel_31.setLayout(gl_panel_31);
		panel_20.setLayout(gl_panel_20);
		
		JPanel panel_30 = new JPanel();
		panel_30.setBackground(SystemColor.textHighlight);
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(panel_30, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(panel_30, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(354, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_15 = new JLabel("Profits");
		lblNewLabel_15.setForeground(SystemColor.text);
		lblNewLabel_15.setFont(new Font("Segoe UI", Font.BOLD, 24));
		GroupLayout gl_panel_30 = new GroupLayout(panel_30);
		gl_panel_30.setHorizontalGroup(
			gl_panel_30.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_30.createSequentialGroup()
					.addContainerGap(89, Short.MAX_VALUE)
					.addComponent(lblNewLabel_15, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addGap(74))
		);
		gl_panel_30.setVerticalGroup(
			gl_panel_30.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_30.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_15, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel_30.setLayout(gl_panel_30);
		panel_5.setLayout(gl_panel_5);
		panel_29.setLayout(gl_panel_29);
		Home.setLayout(gl_Home);
		panel_1.add(Beast,BEASTPANEL);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(153, 102, 153));
		
		JPanel panel_14 = new JPanel();
		panel_14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				panel_14.setBackground(SystemColor.activeCaptionBorder);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_14.setBackground(new Color(153, 102, 153));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				cardsbeast.show(panel_16, BARPANEL);
				
				LoanDepotLoader f = new LoanDepotLoader();
				f.execute();

			}
		});
		panel_14.setBackground(new Color(153, 102, 153));
		
		JLabel lblNewLabel_8 = new JLabel("Dropped Files ");
		lblNewLabel_8.setForeground(new Color(153, 102, 153));
		lblNewLabel_8.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		JPanel panel_13 = new JPanel();
		
		JLabel label = new JLabel("Make sure the Weekly, CSV and Current Benefits files are included!! ");
		label.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\warning.png"));
		label.setForeground(new Color(153, 102, 153));
		label.setFont(new Font("Segoe UI", Font.BOLD, 20));
		

		GroupLayout gl_Beast = new GroupLayout(Beast);
		gl_Beast.setHorizontalGroup(
			gl_Beast.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
				.addGroup(gl_Beast.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(621, Short.MAX_VALUE))
				.addGroup(gl_Beast.createSequentialGroup()
					.addGap(20)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 697, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_Beast.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_Beast.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_13, GroupLayout.PREFERRED_SIZE, 1012, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_Beast.setVerticalGroup(
			gl_Beast.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Beast.createSequentialGroup()
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(lblNewLabel_8, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_13, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addGroup(gl_Beast.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_16, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_23 = new JPanel();
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_13.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 989, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_13.setVerticalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_13.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 165, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panel_24 = new JPanel();
		GroupLayout gl_panel_23 = new GroupLayout(panel_23);
		gl_panel_23.setHorizontalGroup(
			gl_panel_23.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_23.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_24, GroupLayout.PREFERRED_SIZE, 965, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_23.setVerticalGroup(
			gl_panel_23.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_23.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_24, GroupLayout.PREFERRED_SIZE, 140, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JTextPane txtpnAflacOptIn = new JTextPane();
		txtpnAflacOptIn.setDropMode(DropMode.INSERT);
		txtpnAflacOptIn.setEditable(false);
		txtpnAflacOptIn.setForeground(new Color(153, 102, 153));
		txtpnAflacOptIn.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		
		
		
		////////////////Here Files lookup\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
		FilesLookUp Files = new FilesLookUp();
		File[] otherFiles = Files.getFiles();
		File[] CSVFiles = Files.getCSVFiles();
		String filesName = "";
		String csvFileName = "";
		csvFileName = Files.showCSVfiles(CSVFiles);
		filesName = Files.showCSVfiles(otherFiles);
		txtpnAflacOptIn.setText(csvFileName+filesName);
		//////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		
		txtpnAflacOptIn.setBackground(SystemColor.menu);
		GroupLayout gl_panel_24 = new GroupLayout(panel_24);
		gl_panel_24.setHorizontalGroup(
			gl_panel_24.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_24.createSequentialGroup()
					.addComponent(txtpnAflacOptIn, GroupLayout.PREFERRED_SIZE, 965, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_24.setVerticalGroup(
			gl_panel_24.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_24.createSequentialGroup()
					.addComponent(txtpnAflacOptIn, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_24.setLayout(gl_panel_24);
		panel_23.setLayout(gl_panel_23);
		panel_13.setLayout(gl_panel_13);
		
		JLabel lblNewLabel_7 = new JLabel("Run");
		lblNewLabel_7.setForeground(new Color(255, 255, 255));
		lblNewLabel_7.setFont(new Font("Segoe UI", Font.BOLD, 21));
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_14.createSequentialGroup()
					.addContainerGap(114, Short.MAX_VALUE)
					.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addGap(96))
		);
		gl_panel_14.setVerticalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_7, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_14.setLayout(gl_panel_14);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(new Color(153, 102, 153));
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGap(54)
					.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 672, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(290, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGap(29)
					.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_6 = new JLabel("LoanDepot Audit");
		lblNewLabel_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_6.setFont(new Font("Segoe UI", Font.BOLD, 45));
		GroupLayout gl_panel_12 = new GroupLayout(panel_12);
		gl_panel_12.setHorizontalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_12.createSequentialGroup()
					.addGap(29)
					.addComponent(lblNewLabel_6, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		gl_panel_12.setVerticalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_12.createSequentialGroup()
					.addGap(27)
					.addComponent(lblNewLabel_6, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		panel_12.setLayout(gl_panel_12);
		panel_9.setLayout(gl_panel_9);
		Beast.setLayout(gl_Beast);
		panel_1.add(Results,RESULTSPANEL);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBackground(new Color(0, 102, 51));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBackground(new Color(0, 102, 51));
		
		JLabel lblStatistics = new JLabel("LoanDepot Report");
		lblStatistics.setForeground(Color.WHITE);
		lblStatistics.setFont(new Font("Segoe UI", Font.BOLD, 45));
		GroupLayout gl_panel_18 = new GroupLayout(panel_18);
		gl_panel_18.setHorizontalGroup(
			gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGap(0, 672, Short.MAX_VALUE)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addGap(29)
					.addComponent(lblStatistics, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
		);
		gl_panel_18.setVerticalGroup(
			gl_panel_18.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 104, Short.MAX_VALUE)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addGap(27)
					.addComponent(lblStatistics, GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
		);
		panel_18.setLayout(gl_panel_18);
		GroupLayout gl_panel_17 = new GroupLayout(panel_17);
		gl_panel_17.setHorizontalGroup(
			gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1016, Short.MAX_VALUE)
				.addGroup(gl_panel_17.createSequentialGroup()
					.addGap(54)
					.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 672, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(290, Short.MAX_VALUE))
		);
		gl_panel_17.setVerticalGroup(
			gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addGap(0, 189, Short.MAX_VALUE)
				.addGroup(gl_panel_17.createSequentialGroup()
					.addGap(29)
					.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		panel_17.setLayout(gl_panel_17);
		
		JPanel panel_19 = new JPanel();
		panel_19.setBackground(new Color(255, 255, 255));
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(51, 102, 102));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		
		JLabel lblNewLabel_9 = new JLabel("Total Number of Benefits: ");
		lblNewLabel_9.setForeground(new Color(102, 153, 153));
		lblNewLabel_9.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel lblBorrowers = new JLabel("Total Number of Loans: ");
		lblBorrowers.setForeground(new Color(102, 153, 153));
		lblBorrowers.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel lblNumberOfBorrowers = new JLabel("Number of Borrowers: ");
		lblNumberOfBorrowers.setForeground(new Color(102, 153, 153));
		lblNumberOfBorrowers.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		
		lblTotal.setForeground(new Color(51, 102, 153));
		lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		
		label_4.setForeground(new Color(51, 102, 153));
		label_4.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		
		label_5.setForeground(new Color(51, 102, 153));
		label_5.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		JLabel lblDuplicatesInFile = new JLabel("Duplicates in File:");
		lblDuplicatesInFile.setForeground(new Color(102, 153, 153));
		lblDuplicatesInFile.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel lblDuplicatesInWeekly = new JLabel("Duplicates in Weekly:");
		lblDuplicatesInWeekly.setForeground(new Color(102, 153, 153));
		lblDuplicatesInWeekly.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel lblCurrentBenefits = new JLabel("New Current Benefits:");
		lblCurrentBenefits.setForeground(new Color(102, 153, 153));
		lblCurrentBenefits.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		
		label_6.setForeground(new Color(51, 102, 153));
		label_6.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		
		label_7.setForeground(new Color(51, 102, 153));
		label_7.setFont(new Font("Segoe UI", Font.BOLD, 31));
		
		
		label_8.setForeground(new Color(51, 102, 153));
		label_8.setFont(new Font("Segoe UI", Font.BOLD, 31));
		GroupLayout gl_panel_19 = new GroupLayout(panel_19);
		gl_panel_19.setHorizontalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_19.createSequentialGroup()
							.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_19.createSequentialGroup()
							.addComponent(lblBorrowers)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_19.createSequentialGroup()
							.addComponent(lblNumberOfBorrowers)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addGap(39)
					.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_19.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDuplicatesInWeekly)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_19.createSequentialGroup()
							.addComponent(lblDuplicatesInFile, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_19.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCurrentBenefits)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_8, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_19.setVerticalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_19.createSequentialGroup()
							.addGroup(gl_panel_19.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
								.addComponent(lblBorrowers, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_4, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNumberOfBorrowers, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_19.createSequentialGroup()
							.addGroup(gl_panel_19.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDuplicatesInFile, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_19.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_7, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDuplicatesInWeekly, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_19.createParallelGroup(Alignment.LEADING)
								.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCurrentBenefits, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_19.setLayout(gl_panel_19);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblNewLabel_17 = new JLabel("In File");
		lblNewLabel_17.setForeground(new Color(0, 102, 0));
		lblNewLabel_17.setFont(new Font("Segoe UI", Font.BOLD, 29));
		
		JLabel lblInWeekly = new JLabel("Weekly");
		lblInWeekly.setForeground(new Color(0, 102, 0));
		lblInWeekly.setFont(new Font("Segoe UI", Font.BOLD, 29));
		GroupLayout gl_Results = new GroupLayout(Results);
		gl_Results.setHorizontalGroup(
			gl_Results.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_17, GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
				.addGroup(gl_Results.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_19, GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_Results.createSequentialGroup()
					.addContainerGap()
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_Results.createSequentialGroup()
					.addGap(29)
					.addGroup(gl_Results.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_17, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInWeekly, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_Results.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 811, Short.MAX_VALUE))
					.addGap(24))
		);
		gl_Results.setVerticalGroup(
			gl_Results.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Results.createSequentialGroup()
					.addComponent(panel_17, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_19, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_Results.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Results.createSequentialGroup()
							.addGap(35)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
							.addGap(45)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_Results.createSequentialGroup()
							.addGap(88)
							.addComponent(lblNewLabel_17, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addGap(195)
							.addComponent(lblInWeekly, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(119, Short.MAX_VALUE))))
		);
		table_1.setForeground(SystemColor.inactiveCaptionText);
		
		
		table_1.setFont(new Font("Segoe UI", Font.BOLD, 16));
		table_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane_1.setViewportView(table_1);
		
		table = new JTable();
		table.setForeground(SystemColor.menuText);
		table.setFont(new Font("Segoe UI", Font.BOLD, 16));
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setViewportView(table);
		Results.setLayout(gl_Results);
		panel_1.add(Finances,FINANCESPANEL);
		
		JPanel panel_35 = new JPanel();
		panel_35.setBackground(new Color(204, 0, 0));
		
		JPanel panel_36 = new JPanel();
		panel_36.setBackground(new Color(204, 0, 0));
		
		JLabel lblAsOfNow = new JLabel("As of Now");
		lblAsOfNow.setForeground(Color.WHITE);
		lblAsOfNow.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblAsOfNow.setBackground(SystemColor.textHighlight);
		GroupLayout gl_panel_36 = new GroupLayout(panel_36);
		gl_panel_36.setHorizontalGroup(
			gl_panel_36.createParallelGroup(Alignment.LEADING)
				.addGap(0, 226, Short.MAX_VALUE)
				.addGroup(gl_panel_36.createSequentialGroup()
					.addGap(42)
					.addComponent(lblAsOfNow, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(53, Short.MAX_VALUE))
		);
		gl_panel_36.setVerticalGroup(
			gl_panel_36.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 70, Short.MAX_VALUE)
				.addGroup(gl_panel_36.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblAsOfNow, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_36.setLayout(gl_panel_36);
		
		JLabel lblNewLabel_18 = new JLabel("Project Total");
		lblNewLabel_18.setForeground(SystemColor.windowBorder);
		lblNewLabel_18.setFont(new Font("Segoe UI", Font.BOLD, 23));
		
		JLabel lblLoandepotTotal = new JLabel("LoanDepot Total");
		lblLoandepotTotal.setForeground(SystemColor.windowBorder);
		lblLoandepotTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
		
		JLabel lblImortgageTotal = new JLabel("Imortgage Total");
		lblImortgageTotal.setForeground(SystemColor.windowBorder);
		lblImortgageTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
		
		JLabel lblMortagemasterTotal = new JLabel("MortageMaster Total\r\n");
		lblMortagemasterTotal.setForeground(SystemColor.textInactiveText);
		lblMortagemasterTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
		
		JLabel lblNewLabel_20 = new JLabel("Borrowers:");
		lblNewLabel_20.setForeground(SystemColor.textHighlight);
		lblNewLabel_20.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_11 = new JLabel("Borrowers:");
		label_11.setForeground(SystemColor.textHighlight);
		label_11.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_12 = new JLabel("Borrowers:");
		label_12.setForeground(SystemColor.textHighlight);
		label_12.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_13 = new JLabel("Borrowers:");
		label_13.setForeground(SystemColor.textHighlight);
		label_13.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel lblLoans = new JLabel("Loans:");
		lblLoans.setForeground(SystemColor.textHighlight);
		lblLoans.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_14 = new JLabel("Loans:");
		label_14.setForeground(SystemColor.textHighlight);
		label_14.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_15 = new JLabel("Loans:");
		label_15.setForeground(SystemColor.textHighlight);
		label_15.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_16 = new JLabel("Loans:");
		label_16.setForeground(SystemColor.textHighlight);
		label_16.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel lblPremium = new JLabel("Premium:");
		lblPremium.setForeground(SystemColor.textHighlight);
		lblPremium.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_17 = new JLabel("Premium:");
		label_17.setForeground(SystemColor.textHighlight);
		label_17.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_18 = new JLabel("Premium:");
		label_18.setForeground(SystemColor.textHighlight);
		label_18.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_19 = new JLabel("Premium:");
		label_19.setForeground(SystemColor.textHighlight);
		label_19.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		
		
		
		
		
		
		int lbor = Mongo.getValue(EOICollection, "Project Total", "Borrowers");
		borp = lbor;
		String lbstring = numberFormat.format(lbor);
		JLabel lblNewLabel_21 = new JLabel(lbstring);
		lblNewLabel_21.setForeground(SystemColor.windowBorder);
		lblNewLabel_21.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		
		int lsbor = Mongo.getValue(EOICollection, "loanDepot Total", "Borrowers");
		borl = lsbor;	
		String labstring = numberFormat.format(lsbor);
		JLabel label_20 = new JLabel(labstring);
		label_20.setForeground(SystemColor.windowBorder);
		label_20.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		
		int ibor = Mongo.getValue(EOICollection, "Imortgage Total", "Borrowers");
		bori = ibor;
		String ibstring = numberFormat.format(ibor);
		JLabel label_21 = new JLabel(ibstring);
		label_21.setForeground(SystemColor.windowBorder);
		label_21.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		int mbor = Mongo.getValue(EOICollection, "Mortgage Master Total", "Borrowers");
		borm = mbor;
		String mbstring = numberFormat.format(mbor);
		
		JLabel label_22 = new JLabel(mbstring);
		label_22.setForeground(SystemColor.windowBorder);
		label_22.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_23 = new JLabel();
		
		
		int Pl = Mongo.getValue(EOICollection, "Project Total", "Loans");
		loanp = Pl;
		String plstring = numberFormat.format(Pl);
		
		label_23.setText(plstring);
		label_23.setForeground(SystemColor.windowBorder);
		label_23.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		int kl = Mongo.getValue(EOICollection, "loanDepot Total", "Loans");
		loanl = kl;
		String lstring = numberFormat.format(kl);
		JLabel label_24 = new JLabel(lstring);
		label_24.setForeground(SystemColor.windowBorder);
		label_24.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		int il = Mongo.getValue(EOICollection, "Imortgage Total", "Loans");
		loani = il;
		String ilstring = numberFormat.format(il);
		JLabel label_25 = new JLabel(ilstring);
		
		label_25.setForeground(SystemColor.windowBorder);
		label_25.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		
		int ml = Mongo.getValue(EOICollection, "Mortgage Master Total", "Loans");
		loanm = ml;
		String mlstring = numberFormat.format(ml);
		JLabel label_26 = new JLabel(mlstring);
		label_26.setForeground(SystemColor.windowBorder);
		label_26.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		
		int Pp = Mongo.getValue(EOICollection, "Project Total", "Premium");
		prep = Pp;
		String ppstring = currencyFormat.format(Pp);
		JLabel lblNewLabel_22 = new JLabel("$"+ppstring);
		lblNewLabel_22.setForeground(SystemColor.windowBorder);
		lblNewLabel_22.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		
		int lp = Mongo.getValue(EOICollection, "loanDepot Total", "Premium");
		prel = lp;
		String lpstring = currencyFormat.format(lp);
		
		JLabel label_27 = new JLabel("$"+lpstring);
		label_27.setForeground(SystemColor.windowBorder);
		label_27.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		
		
		
		int ip = Mongo.getValue(EOICollection, "Imortgage Total", "Premium");
		prei = ip;
		String ipstring = currencyFormat.format(ip);
		JLabel label_28 = new JLabel("$"+ipstring);
		label_28.setForeground(SystemColor.windowBorder);
		label_28.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		
		int mp = Mongo.getValue(EOICollection, "Mortgage Master Total", "Premium");
		prem = mp;
		String mpstring = currencyFormat.format(mp);
		JLabel label_29 = new JLabel("$"+mpstring);
		label_29.setForeground(SystemColor.windowBorder);
		label_29.setFont(new Font("Segoe UI", Font.BOLD, 28));
		
		JLabel label_124 = new JLabel("");
		label_124.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label_124.setEnabled(false);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_124.setEnabled(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			    int reply = JOptionPane.showConfirmDialog(null, "WARNING!! Please Check everything is correct before saving updated information into the Cloud Database. Press Yes if everything is Checked ", "Save", JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION) {
		        	
		        	
		        	//borrowers
		        	Mongo.updateDocument(EOICollection, "Project Total", "Borrowers", borp);
		        	Mongo.updateDocument(EOICollection, "loanDepot Total", "Borrowers", borl);
		        	Mongo.updateDocument(EOICollection, "Imortgage Total", "Borrowers", bori);
		        	Mongo.updateDocument(EOICollection, "Mortgage Master Total", "Borrowers", borm);
		        	
		        	//loans
		        	Mongo.updateDocument(EOICollection, "Project Total", "Loans", loanp);
		        	Mongo.updateDocument(EOICollection, "loanDepot Total", "Loans", loanl);
		        	Mongo.updateDocument(EOICollection, "Imortgage Total", "Loans", loani);
		        	Mongo.updateDocument(EOICollection, "Mortgage Master Total", "Loans", loanm);
		        	
		        	//Premium
		        	Mongo.updateDocument(EOICollection, "Project Total", "Premium", prep);
		        	Mongo.updateDocument(EOICollection, "loanDepot Total", "Premium", prel);
		        	Mongo.updateDocument(EOICollection, "Imortgage Total", "Premium", prei);
		        	Mongo.updateDocument(EOICollection, "Mortgage Master Total", "Premium", prem);
		        	JOptionPane.showMessageDialog(null, "Saved!!");
		        }
		        else {
		           JOptionPane.showMessageDialog(null, "GOODBYE");
		           
		        }
				
				
			}
		});
		label_124.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\save-disk.png"));
		label_124.setVisible(false);
		JLabel label_123 = new JLabel("");
		label_123.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				label_123.setEnabled(false);
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_123.setEnabled(true);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				Audit.lpre = JOptionPane.showInputDialog(null, "Enter Annual Premium for LoanDepot: ");
				Audit.ipre = JOptionPane.showInputDialog(null, "Enter Annual Premium for Imortgage: ");
				Audit.mpre = JOptionPane.showInputDialog(null, "Enter Annual Premium for Mortgage Master: ");
				try {
					
					
					int lprem = Integer.valueOf(Audit.lpre);
					int iprem = Integer.valueOf(Audit.ipre);
					int mprem = Integer.valueOf(Audit.mpre);
					int pprem = lprem+iprem+mprem;
					
					//borowers
					
					borp = borp + Audit.pborrowers;
					borl = borl + Audit.lborrowers;
					bori = bori + Audit.iborrowers;
					borm = borm + Audit.mborrowers;
					
					
					//loans
					loanp = loanp + Audit.ploans;
					loanl = loanl + Audit.lloans;
					loani = loani + Audit.iloans;
					loanm = loanm + Audit.mloans;
					
					//premum
					prep = prep +pprem;
					prel = prel +lprem;
					prei = prei + iprem;
					prem = prem + mprem;
					
					label_124.setVisible(true);// the save button 
					
					
					

				}catch(NumberFormatException ignore ) {
					
					JOptionPane.showMessageDialog(null,"One of you Inputs is not a Number!! Please Try Again!!");
				}catch(NullPointerException neo ) {System.out.println("H");}
				System.out.println(premium);
				
			}
		});
		label_123.setIcon(new ImageIcon("src\\plus.png"));
	
		LocalDate LDate =  LocalDate.now();
		LDate.toString();
		
		String[] dateComponent = LDate.toString().split("-");
		String month = dateComponent[1];
		int monthnum =Integer.valueOf(month);
		String monthString = monthIdentifier(monthnum);
		
		
		
		JLabel lblNewLabel_23 = new JLabel(monthString+ "  " +dateComponent[0]);
		lblNewLabel_23.setForeground(SystemColor.windowBorder);
		lblNewLabel_23.setFont(new Font("Segoe UI", Font.BOLD, 40));
		
		JPanel panel_37 = new JPanel();
		panel_37.setBackground(SystemColor.text);
		
		
		
		
		
		
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		GroupLayout gl_Finances = new GroupLayout(Finances);
		gl_Finances.setHorizontalGroup(
			gl_Finances.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Finances.createSequentialGroup()
					.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_Finances.createSequentialGroup()
							.addGap(31)
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblNewLabel_18, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_Finances.createSequentialGroup()
									.addComponent(lblNewLabel_20, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_21, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_Finances.createSequentialGroup()
									.addComponent(lblLoans, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label_23, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblPremium)
								.addComponent(lblNewLabel_22, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
							.addGap(12)
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
								.addComponent(label_17)
								.addGroup(gl_Finances.createSequentialGroup()
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_11)
										.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_24, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_20, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
								.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblLoandepotTotal, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
							.addGap(35)
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
								.addComponent(lblImortgageTotal, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_18, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_Finances.createSequentialGroup()
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_12)
										.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_21, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
								.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE))
							.addGap(35)
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
								.addComponent(label_29, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_19, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_Finances.createSequentialGroup()
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_13)
										.addComponent(label_16, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_22, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_Finances.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_Finances.createSequentialGroup()
										.addComponent(label_124, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label_123, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblMortagemasterTotal))))
						.addGroup(gl_Finances.createSequentialGroup()
							.addGroup(gl_Finances.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
							.addGap(216)
							.addComponent(lblNewLabel_23, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_Finances.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_37, GroupLayout.PREFERRED_SIZE, 1012, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_Finances.setVerticalGroup(
			gl_Finances.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Finances.createSequentialGroup()
					.addGap(21)
					.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_23, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_37, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(gl_Finances.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_123, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_124, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblImortgageTotal, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMortagemasterTotal, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_18, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblLoandepotTotal, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_Finances.createSequentialGroup()
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_Finances.createSequentialGroup()
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_20, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_21, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
										.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
											.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
											.addComponent(label_21, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
										.addComponent(label_13, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
										.addComponent(label_25, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_23, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblLoans, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
											.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
											.addComponent(label_24, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
										.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_Finances.createParallelGroup(Alignment.BASELINE)
											.addComponent(label_16, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
											.addComponent(label_26, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
								.addComponent(label_22, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
								.addComponent(label_18, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_19, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPremium, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_17, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_20, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_Finances.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_22, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_27, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_28, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_29, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addGap(44))
		);
		panel_37.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_38 = new JPanel();
		panel_38.setVisible(false);
		panel_37.add(panel_38);
		
		JPanel panel_43 = new JPanel();
		panel_43.setBackground(new Color(204, 0, 0));
		
		JLabel label_30 = new JLabel("Week 72");
		label_30.setForeground(Color.WHITE);
		label_30.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_31 = new JLabel("03/14/2019");
		label_31.setForeground(Color.WHITE);
		label_31.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_43 = new GroupLayout(panel_43);
		gl_panel_43.setHorizontalGroup(
			gl_panel_43.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_43.createSequentialGroup()
					.addGap(56)
					.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
				.addGroup(gl_panel_43.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addComponent(label_31, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		gl_panel_43.setVerticalGroup(
			gl_panel_43.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
				.addGroup(gl_panel_43.createSequentialGroup()
					.addGap(6)
					.addComponent(label_30, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_31, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_43.setLayout(gl_panel_43);
		
		JLabel label_32 = new JLabel("LoanDepot ");
		label_32.setForeground(SystemColor.windowBorder);
		label_32.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_33 = new JLabel("Project Total");
		label_33.setForeground(SystemColor.windowBorder);
		label_33.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_34 = new JLabel("Borrowers:");
		label_34.setForeground(SystemColor.textHighlight);
		label_34.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_35 = new JLabel("");
		label_35.setForeground(SystemColor.windowBorder);
		label_35.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_36 = new JLabel("Loans:");
		label_36.setForeground(SystemColor.textHighlight);
		label_36.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_37 = new JLabel("");
		label_37.setForeground(SystemColor.windowBorder);
		label_37.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_38 = new JLabel("Premium:");
		label_38.setForeground(SystemColor.textHighlight);
		label_38.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_39 = new JLabel("");
		label_39.setForeground(SystemColor.windowBorder);
		label_39.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_40 = new JLabel("Borrowers:");
		label_40.setForeground(SystemColor.textHighlight);
		label_40.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_41 = new JLabel("");
		label_41.setForeground(SystemColor.windowBorder);
		label_41.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_42 = new JLabel("Loans:");
		label_42.setForeground(SystemColor.textHighlight);
		label_42.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_107 = new JLabel("");
		label_107.setForeground(SystemColor.windowBorder);
		label_107.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_108 = new JLabel("Premium:");
		label_108.setForeground(SystemColor.textHighlight);
		label_108.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_109 = new JLabel("");
		label_109.setForeground(SystemColor.windowBorder);
		label_109.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_38 = new GroupLayout(panel_38);
		gl_panel_38.setHorizontalGroup(
			gl_panel_38.createParallelGroup(Alignment.LEADING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addComponent(panel_43, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_32, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_33, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_34, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_35, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_36, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_37, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_39, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_40, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_41, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_42, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_107, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_38.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_108, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_109, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_38.setVerticalGroup(
			gl_panel_38.createParallelGroup(Alignment.LEADING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_panel_38.createSequentialGroup()
					.addComponent(panel_43, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_32, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_38.createSequentialGroup()
							.addComponent(label_34)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_38.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_36)
								.addComponent(label_37, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_35, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
						.addComponent(label_38, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_39, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(label_33, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
						.addComponent(label_40, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_41, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
						.addComponent(label_42, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_107, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_38.createParallelGroup(Alignment.LEADING)
						.addComponent(label_109, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_108, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_38.setLayout(gl_panel_38);
		
		JPanel panel_39 = new JPanel();
		panel_39.setVisible(false);
		panel_37.add(panel_39);
		
		JLabel label_43 = new JLabel("");
		label_43.setForeground(SystemColor.windowBorder);
		label_43.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_44 = new JLabel("");
		label_44.setForeground(SystemColor.windowBorder);
		label_44.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_45 = new JLabel("");
		label_45.setForeground(SystemColor.windowBorder);
		label_45.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JPanel panel_40 = new JPanel();
		panel_40.setBackground(new Color(204, 0, 0));
		
		JLabel label_46 = new JLabel("Week 72");
		label_46.setForeground(Color.WHITE);
		label_46.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_47 = new JLabel("03/14/2019");
		label_47.setForeground(Color.WHITE);
		label_47.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_40 = new GroupLayout(panel_40);
		gl_panel_40.setHorizontalGroup(
			gl_panel_40.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_40.createSequentialGroup()
					.addGap(56)
					.addComponent(label_46, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
				.addGroup(gl_panel_40.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addComponent(label_47, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		gl_panel_40.setVerticalGroup(
			gl_panel_40.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
				.addGroup(gl_panel_40.createSequentialGroup()
					.addGap(6)
					.addComponent(label_46, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_47, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_40.setLayout(gl_panel_40);
		
		JLabel label_48 = new JLabel("LoanDepot ");
		label_48.setForeground(SystemColor.windowBorder);
		label_48.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_49 = new JLabel("Project Total");
		label_49.setForeground(SystemColor.windowBorder);
		label_49.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_50 = new JLabel("Borrowers:");
		label_50.setForeground(SystemColor.textHighlight);
		label_50.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_51 = new JLabel("");
		label_51.setForeground(SystemColor.windowBorder);
		label_51.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_52 = new JLabel("Loans:");
		label_52.setForeground(SystemColor.textHighlight);
		label_52.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_53 = new JLabel("");
		label_53.setForeground(SystemColor.windowBorder);
		label_53.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_54 = new JLabel("Premium:");
		label_54.setForeground(SystemColor.textHighlight);
		label_54.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_55 = new JLabel("");
		label_55.setForeground(SystemColor.windowBorder);
		label_55.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_56 = new JLabel("Borrowers:");
		label_56.setForeground(SystemColor.textHighlight);
		label_56.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_57 = new JLabel("Loans:");
		label_57.setForeground(SystemColor.textHighlight);
		label_57.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_58 = new JLabel("Premium:");
		label_58.setForeground(SystemColor.textHighlight);
		label_58.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_39 = new GroupLayout(panel_39);
		gl_panel_39.setHorizontalGroup(
			gl_panel_39.createParallelGroup(Alignment.LEADING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addComponent(panel_40, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_49, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_50, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_51, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_52, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_53, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_54, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_55, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_56, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_43, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_57, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_44, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_39.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_58, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_45, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_39.setVerticalGroup(
			gl_panel_39.createParallelGroup(Alignment.LEADING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_panel_39.createSequentialGroup()
					.addComponent(panel_40, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_48, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_39.createSequentialGroup()
							.addComponent(label_50)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_39.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_52)
								.addComponent(label_53, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_51, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
						.addComponent(label_54, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_55, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(label_49, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
						.addComponent(label_56, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_43, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
						.addComponent(label_57, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_44, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_39.createParallelGroup(Alignment.LEADING)
						.addComponent(label_45, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_58, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_39.setLayout(gl_panel_39);
		
		JPanel panel_41 = new JPanel();
		panel_37.add(panel_41);
		
		JLabel label_59 = new JLabel("");
		label_59.setForeground(SystemColor.windowBorder);
		label_59.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_60 = new JLabel("");
		label_60.setForeground(SystemColor.windowBorder);
		label_60.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_61 = new JLabel("");
		label_61.setForeground(SystemColor.windowBorder);
		label_61.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JPanel panel_42 = new JPanel();
		panel_42.setBackground(new Color(204, 0, 0));
		
		JLabel label_62 = new JLabel("Week 72");
		label_62.setForeground(Color.WHITE);
		label_62.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_63 = new JLabel("03/14/2019");
		label_63.setForeground(Color.WHITE);
		label_63.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_42 = new GroupLayout(panel_42);
		gl_panel_42.setHorizontalGroup(
			gl_panel_42.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_42.createSequentialGroup()
					.addGap(56)
					.addComponent(label_62, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
				.addGroup(gl_panel_42.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addComponent(label_63, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		gl_panel_42.setVerticalGroup(
			gl_panel_42.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
				.addGroup(gl_panel_42.createSequentialGroup()
					.addGap(6)
					.addComponent(label_62, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_63, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_42.setLayout(gl_panel_42);
		
		JLabel label_64 = new JLabel("LoanDepot ");
		label_64.setForeground(SystemColor.windowBorder);
		label_64.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_65 = new JLabel("Project Total");
		label_65.setForeground(SystemColor.windowBorder);
		label_65.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_66 = new JLabel("Borrowers:");
		label_66.setForeground(SystemColor.textHighlight);
		label_66.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_67 = new JLabel("");
		label_67.setForeground(SystemColor.windowBorder);
		label_67.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_68 = new JLabel("Loans:");
		label_68.setForeground(SystemColor.textHighlight);
		label_68.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_69 = new JLabel("");
		label_69.setForeground(SystemColor.windowBorder);
		label_69.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_70 = new JLabel("Premium:");
		label_70.setForeground(SystemColor.textHighlight);
		label_70.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_71 = new JLabel("");
		label_71.setForeground(SystemColor.windowBorder);
		label_71.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_72 = new JLabel("Borrowers:");
		label_72.setForeground(SystemColor.textHighlight);
		label_72.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_73 = new JLabel("Loans:");
		label_73.setForeground(SystemColor.textHighlight);
		label_73.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_74 = new JLabel("Premium:");
		label_74.setForeground(SystemColor.textHighlight);
		label_74.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_41 = new GroupLayout(panel_41);
		gl_panel_41.setHorizontalGroup(
			gl_panel_41.createParallelGroup(Alignment.LEADING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addComponent(panel_42, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_64, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_65, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_66, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_67, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_68, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_69, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_70, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_71, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_72, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_59, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_73, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_60, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_41.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_74, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_61, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_41.setVerticalGroup(
			gl_panel_41.createParallelGroup(Alignment.LEADING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_panel_41.createSequentialGroup()
					.addComponent(panel_42, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_64, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_41.createSequentialGroup()
							.addComponent(label_66)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_41.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_68)
								.addComponent(label_69, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_67, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
						.addComponent(label_70, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_71, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(label_65, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
						.addComponent(label_72, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_59, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
						.addComponent(label_73, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_60, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_41.createParallelGroup(Alignment.LEADING)
						.addComponent(label_61, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_74, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_41.setLayout(gl_panel_41);
		
		JPanel panel_44 = new JPanel();
		panel_44.setVisible(false);
		panel_37.add(panel_44);
		
		JLabel label_75 = new JLabel("");
		label_75.setForeground(SystemColor.windowBorder);
		label_75.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_76 = new JLabel("");
		label_76.setForeground(SystemColor.windowBorder);
		label_76.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_77 = new JLabel("");
		label_77.setForeground(SystemColor.windowBorder);
		label_77.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JPanel panel_45 = new JPanel();
		panel_45.setBackground(new Color(204, 0, 0));
		
		JLabel label_78 = new JLabel("Week 72");
		label_78.setForeground(Color.WHITE);
		label_78.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_79 = new JLabel("03/14/2019");
		label_79.setForeground(Color.WHITE);
		label_79.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_45 = new GroupLayout(panel_45);
		gl_panel_45.setHorizontalGroup(
			gl_panel_45.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_45.createSequentialGroup()
					.addGap(56)
					.addComponent(label_78, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
				.addGroup(gl_panel_45.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addComponent(label_79, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		gl_panel_45.setVerticalGroup(
			gl_panel_45.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
				.addGroup(gl_panel_45.createSequentialGroup()
					.addGap(6)
					.addComponent(label_78, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_79, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_45.setLayout(gl_panel_45);
		
		JLabel label_80 = new JLabel("LoanDepot ");
		label_80.setForeground(SystemColor.windowBorder);
		label_80.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_81 = new JLabel("Project Total");
		label_81.setForeground(SystemColor.windowBorder);
		label_81.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_82 = new JLabel("Borrowers:");
		label_82.setForeground(SystemColor.textHighlight);
		label_82.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_83 = new JLabel("");
		label_83.setForeground(SystemColor.windowBorder);
		label_83.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_84 = new JLabel("Loans:");
		label_84.setForeground(SystemColor.textHighlight);
		label_84.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_85 = new JLabel("");
		label_85.setForeground(SystemColor.windowBorder);
		label_85.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_86 = new JLabel("Premium:");
		label_86.setForeground(SystemColor.textHighlight);
		label_86.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_87 = new JLabel("");
		label_87.setForeground(SystemColor.windowBorder);
		label_87.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_88 = new JLabel("Borrowers:");
		label_88.setForeground(SystemColor.textHighlight);
		label_88.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_89 = new JLabel("Loans:");
		label_89.setForeground(SystemColor.textHighlight);
		label_89.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_90 = new JLabel("Premium:");
		label_90.setForeground(SystemColor.textHighlight);
		label_90.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_44 = new GroupLayout(panel_44);
		gl_panel_44.setHorizontalGroup(
			gl_panel_44.createParallelGroup(Alignment.LEADING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addComponent(panel_45, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_80, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_81, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_82, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_83, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_84, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_85, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_86, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_87, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_88, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_75, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_89, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_76, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_44.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_90, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_77, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_44.setVerticalGroup(
			gl_panel_44.createParallelGroup(Alignment.LEADING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_panel_44.createSequentialGroup()
					.addComponent(panel_45, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_80, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_44.createSequentialGroup()
							.addComponent(label_82)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_44.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_84)
								.addComponent(label_85, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_83, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
						.addComponent(label_86, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_87, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(label_81, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
						.addComponent(label_88, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_75, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
						.addComponent(label_89, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_76, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_44.createParallelGroup(Alignment.LEADING)
						.addComponent(label_77, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_90, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_44.setLayout(gl_panel_44);
		
		JPanel panel_46 = new JPanel();
		panel_46.setVisible(false);
		panel_46.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
		});
		panel_37.add(panel_46);
		
		JLabel label_91 = new JLabel("");
		label_91.setForeground(SystemColor.windowBorder);
		label_91.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_92 = new JLabel("");
		label_92.setForeground(SystemColor.windowBorder);
		label_92.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_93 = new JLabel("");
		label_93.setForeground(SystemColor.windowBorder);
		label_93.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JPanel panel_47 = new JPanel();
		panel_47.setBackground(new Color(204, 0, 0));
		
		JLabel label_94 = new JLabel("Week 72");
		label_94.setForeground(Color.WHITE);
		label_94.setFont(new Font("Segoe UI", Font.BOLD, 18));
		
		JLabel label_95 = new JLabel("03/14/2019");
		label_95.setForeground(Color.WHITE);
		label_95.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_47 = new GroupLayout(panel_47);
		gl_panel_47.setHorizontalGroup(
			gl_panel_47.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_47.createSequentialGroup()
					.addGap(56)
					.addComponent(label_94, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
				.addGroup(gl_panel_47.createSequentialGroup()
					.addContainerGap(49, Short.MAX_VALUE)
					.addComponent(label_95, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addGap(33))
		);
		gl_panel_47.setVerticalGroup(
			gl_panel_47.createParallelGroup(Alignment.LEADING)
				.addGap(0, 65, Short.MAX_VALUE)
				.addGroup(gl_panel_47.createSequentialGroup()
					.addGap(6)
					.addComponent(label_94, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_95, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_47.setLayout(gl_panel_47);
		
		JLabel label_96 = new JLabel("LoanDepot ");
		label_96.setForeground(SystemColor.windowBorder);
		label_96.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_97 = new JLabel("Project Total");
		label_97.setForeground(SystemColor.windowBorder);
		label_97.setFont(new Font("Segoe UI", Font.BOLD, 19));
		
		JLabel label_98 = new JLabel("Borrowers:");
		label_98.setForeground(SystemColor.textHighlight);
		label_98.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_99 = new JLabel("");
		label_99.setForeground(SystemColor.windowBorder);
		label_99.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_100 = new JLabel("Loans:");
		label_100.setForeground(SystemColor.textHighlight);
		label_100.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_101 = new JLabel("");
		label_101.setForeground(SystemColor.windowBorder);
		label_101.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_102 = new JLabel("Premium:");
		label_102.setForeground(SystemColor.textHighlight);
		label_102.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_103 = new JLabel("");
		label_103.setForeground(SystemColor.windowBorder);
		label_103.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_104 = new JLabel("Borrowers:");
		label_104.setForeground(SystemColor.textHighlight);
		label_104.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_105 = new JLabel("Loans:");
		label_105.setForeground(SystemColor.textHighlight);
		label_105.setFont(new Font("Segoe UI", Font.BOLD, 16));
		
		JLabel label_106 = new JLabel("Premium:");
		label_106.setForeground(SystemColor.textHighlight);
		label_106.setFont(new Font("Segoe UI", Font.BOLD, 16));
		GroupLayout gl_panel_46 = new GroupLayout(panel_46);
		gl_panel_46.setHorizontalGroup(
			gl_panel_46.createParallelGroup(Alignment.LEADING)
				.addGap(0, 195, Short.MAX_VALUE)
				.addComponent(panel_47, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_96, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_97, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_98, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_99, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_100, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_101, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_102, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_103, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_104, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_91, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_105, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(label_92, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel_46.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_106, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_93, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_46.setVerticalGroup(
			gl_panel_46.createParallelGroup(Alignment.LEADING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_panel_46.createSequentialGroup()
					.addComponent(panel_47, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_96, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_46.createSequentialGroup()
							.addComponent(label_98)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_46.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_100)
								.addComponent(label_101, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
						.addComponent(label_99, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
						.addComponent(label_102, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_103, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addComponent(label_97, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
						.addComponent(label_104, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_91, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
						.addComponent(label_105, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_92, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_46.createParallelGroup(Alignment.LEADING)
						.addComponent(label_93, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_106, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		panel_46.setLayout(gl_panel_46);
		
		JLabel lblNewLabel_19 = new JLabel("This Month");
		lblNewLabel_19.setBackground(SystemColor.textHighlight);
		lblNewLabel_19.setForeground(SystemColor.textHighlightText);
		lblNewLabel_19.setFont(new Font("Segoe UI", Font.BOLD, 22));
		GroupLayout gl_panel_35 = new GroupLayout(panel_35);
		gl_panel_35.setHorizontalGroup(
			gl_panel_35.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_35.createSequentialGroup()
					.addGap(42)
					.addComponent(lblNewLabel_19, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(53, Short.MAX_VALUE))
		);
		gl_panel_35.setVerticalGroup(
			gl_panel_35.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_35.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel_19, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_35.setLayout(gl_panel_35);
		Finances.setLayout(gl_Finances);
		
		cards.show(panel_1, HOMEPANEL);
		
		
		//Home.setBackground(SystemColor.desktop);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				panel_2.setBackground(SystemColor.activeCaptionBorder);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_2.setBackground(SystemColor.activeCaption);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cards.show(panel_1, BEASTPANEL);
				
			}
		});
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				panel_3.setBackground(SystemColor.activeCaption);
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				panel_3.setBackground(SystemColor.activeCaptionBorder);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cards.show(panel_1, RESULTSPANEL);
				
			}

		});
		
		
		JPanel panel_7 = new JPanel();
		panel_7.setOpaque(false);
		panel_7.setBackground(SystemColor.activeCaption);
		
		JLabel lblLoandepot = new JLabel("Results");
		lblLoandepot.setForeground(Color.WHITE);
		lblLoandepot.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblLoandepot.setBackground(SystemColor.activeCaption);
		panel_7.add(lblLoandepot);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\positive-verified-symbol-of-a-clipboard.png"));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(44)
					.addComponent(lblNewLabel_2)
					.addGap(53)
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(125, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_2)
						.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SystemColor.activeCaption);
		panel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				panel_8.setBackground(SystemColor.activeCaptionBorder);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				panel_8.setBackground(SystemColor.activeCaption);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				cards.show(panel_1, FINANCESPANEL);
				
			}
		});
		
		
		JPanel panel_10 = new JPanel();
		panel_10.setOpaque(false);
		panel_10.setBackground(SystemColor.activeCaption);
		
		JLabel lblImortgage = new JLabel("Finances");
		lblImortgage.setForeground(Color.WHITE);
		lblImortgage.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblImortgage.setBackground(SystemColor.activeCaption);
		panel_10.add(lblImortgage);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\payment-method.png"));
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGap(47)
					.addComponent(lblNewLabel_3)
					.addGap(46)
					.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_8.setLayout(gl_panel_8);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(SystemColor.textHighlight);
		
		JLabel lblNewLabel_5 = new JLabel("Billing Coming Soon");
		lblNewLabel_5.setForeground(SystemColor.control);
		lblNewLabel_5.setFont(new Font("Segoe UI", Font.BOLD, 20));
		GroupLayout gl_panel_11 = new GroupLayout(panel_11);
		gl_panel_11.setHorizontalGroup(
			gl_panel_11.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_11.createSequentialGroup()
					.addGap(83)
					.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(84, Short.MAX_VALUE))
		);
		gl_panel_11.setVerticalGroup(
			gl_panel_11.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_11.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_5)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panel_11.setLayout(gl_panel_11);
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(153, 180, 209));
		panel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent er) {
				panel_4.setBackground(SystemColor.activeCaptionBorder);
				
			}
			@Override
			public void mouseExited(java.awt.event.MouseEvent er) {
				panel_4.setBackground(SystemColor.activeCaption);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				cards.show(panel_1, HOMEPANEL);
				
			}
		});
		
		
		JPanel panel_15 = new JPanel();
		panel_15.setOpaque(false);
		panel_15.setBackground(SystemColor.activeCaption);
		
		JLabel lblHome = new JLabel("Home");
		lblHome.setForeground(Color.WHITE);
		lblHome.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblHome.setBackground(SystemColor.activeCaption);
		panel_15.add(lblHome);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\Homeicon.png"));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(45)
					.addComponent(lblNewLabel)
					.addGap(48)
					.addComponent(panel_15, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 58, Short.MAX_VALUE)
				.addGap(0, 58, Short.MAX_VALUE)
				.addGap(0, 58, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel)
						.addComponent(panel_15, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		
		JLabel lblNewLabel_4 = new JLabel("Created by EOI Service Company Inc 2018.");
		lblNewLabel_4.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lblNewLabel_4.setForeground(SystemColor.control);
		
		JLabel label_10 = new JLabel("");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(48)
							.addComponent(lblNewLabel_4))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(133)
							.addComponent(label_10)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(164)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addGap(146)
					.addComponent(label_10)
					.addPreferredGap(ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
					.addComponent(lblNewLabel_4)
					.addGap(36))
		);
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_6.setBackground(SystemColor.activeCaption);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\jlv\\eclipse-workspace\\loanDepotAudit\\src\\scary-monster.png"));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addComponent(lblNewLabel_1)
					.addGap(53)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(126, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JLabel lblAudits = new JLabel("Beast Killer");
		lblAudits.setForeground(SystemColor.window);
		lblAudits.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblAudits.setBackground(SystemColor.activeCaption);
		panel_6.add(lblAudits);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
		frame.getContentPane().setLayout(groupLayout);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
