
import javax.swing.*;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;

/**
 * A class that creates a GUI that can display information about the computer
 * that the program is running on.
 * 
 * @author Matthew Carney (i7218006)
 * @version 1.0
 * 
 */
public class JSysInfo {
	// *GUI Variables*
	private JFrame mainFrame;
	private JPanel mainPanel;
	private JLabel lblInfoLogger, lblOptionsTab, lblInfoOption, lblMemFormatOption, lblNotesArea;
	private JCheckBox chkBoxBasicInfo, chkBoxOSInfo, chkBoxDriveInfo, chkBoxNetworkInfo;
	private JRadioButton radioBtnKB, radioBtnMB, radioBtnGB;
	private JButton btnCreateLog, btnSaveLog, btnClearLog, btnAddNotes, btnExit;
	private ButtonGroup radioBtnGroup;
	private JTextArea txtAreaInfoLogger, txtAreaNotes;
	private JScrollPane infoLoggerAreaScroll, notesAreaScroll;
	private JFileChooser fileSaveLoc;
	
	// *EVENT VARIABLES*
	private SystemInfo SysInfo;
	private int chkBoxBasicInfoEvent = 1;
	private int chkBoxOSInfoEvent = 2;
	private int chkBoxDriveInfoEvent = 2;
	private int chkBoxNetworkInfoEvent = 2;
	private int memoryFormat = 0;
	
	
	public static void main(String[] args) {
		new JSysInfo();
	}
	
	/**
	 * Constructs the GUI from all the required methods.
	 */
	public JSysInfo() {
		// *CONSTUCTOR CLASS*
		createForm();
		createPanel();
		addLabels();
		
		addCheckBoxes();
		addRadioButtons();
		addButtons();
		addTextArea();
		
		addFileChooser();
		
		SysInfo = new SystemInfo(); // Creating an object from SystemInfo, SystemInfo methods can now be called in the event handlers
		
		mainFrame.add(mainPanel);
		mainFrame.setVisible(true);
	}
	
	/**
	 * Creates the main GUI form and its properties.
	 */
	public void createForm() {
		// *FORM CREATION*
		mainFrame = new JFrame();
		mainFrame.setTitle("JSysInfo");
		mainFrame.setSize(475,550);
		mainFrame.setLocation(650,200);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Creates the main GUI panel and its layout properties.
	 */
	public void createPanel() {
		// *PANEL CREATION*
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
	}
	
	/**
	 * Adds labels to the main form.
	 */
	public void addLabels() {
		// *LABEL CREATION*
		lblInfoLogger = new JLabel();
		lblInfoLogger.setText("System Information Log");
		lblInfoLogger.setBounds(20, 30, 150, 20);
		mainPanel.add(lblInfoLogger);
		
		lblOptionsTab = new JLabel(); //Reposition
		lblOptionsTab.setText("Options");
		lblOptionsTab.setBounds(325, 30, 100, 20);
		mainPanel.add(lblOptionsTab);
		
		lblInfoOption = new JLabel();
		lblInfoOption.setText("Information to Include:");
		lblInfoOption.setBounds(325, 60, 150, 20);
		mainPanel.add(lblInfoOption);
		
		lblMemFormatOption = new JLabel();
		lblMemFormatOption.setText("Memory Format:");
		lblMemFormatOption.setBounds(325, 156, 150, 20);
		mainPanel.add(lblMemFormatOption);
		
		lblNotesArea = new JLabel();
		lblNotesArea.setText("User Notes");
		lblNotesArea.setBounds(20, 410, 100, 20);
		mainPanel.add(lblNotesArea);
	}
	
	/**
	 * Adds check boxes to the main form.
	 */
	public void addCheckBoxes() {
		// *CHECK BOX CREATION*
		chkBoxBasicInfo = new JCheckBox("Basic Information");
		chkBoxBasicInfo.setBounds(325, 75, 150, 20);
		chkBoxBasicInfo.setMnemonic(KeyEvent.VK_B);
		chkBoxBasicInfo.setSelected(true);
		mainPanel.add(chkBoxBasicInfo);
		
		chkBoxOSInfo = new JCheckBox("Operating System");
		chkBoxOSInfo.setBounds(325, 92, 150, 20);
		chkBoxOSInfo.setMnemonic(KeyEvent.VK_O);
		chkBoxOSInfo.setSelected(false);
		mainPanel.add(chkBoxOSInfo);
		
		chkBoxDriveInfo = new JCheckBox("Drive Information");
		chkBoxDriveInfo.setBounds(325, 109, 150, 20);
		chkBoxDriveInfo.setMnemonic(KeyEvent.VK_D);
		chkBoxDriveInfo.setSelected(false);
		mainPanel.add(chkBoxDriveInfo);
		
		chkBoxNetworkInfo = new JCheckBox("Network Information");
		chkBoxNetworkInfo.setBounds(325, 126, 150, 20);
		chkBoxNetworkInfo.setMnemonic(KeyEvent.VK_N);
		chkBoxNetworkInfo.setSelected(false);
		mainPanel.add(chkBoxNetworkInfo);
		
		// *CHECK BOX ACTION LISTENERS*
		chkBoxBasicInfo.addItemListener(new ChkBoxBasicInfoHandler());
		chkBoxOSInfo.addItemListener(new ChkBoxOSInfoHandler());
		chkBoxDriveInfo.addItemListener(new ChkBoxDriveInfoHandler());
		chkBoxNetworkInfo.addItemListener(new ChkBoxNetworkInfoHandler());
	}
	
	/**
	 * Adds radio buttons to the forms.
	 */
	public void addRadioButtons() {
		// *RADIO BUTTON CREATION*
		radioBtnKB = new JRadioButton("KiloBytes");
		radioBtnKB.setMnemonic(KeyEvent.VK_K);
		radioBtnKB.setBounds(325, 171, 150, 20);
		mainPanel.add(radioBtnKB);
		
		radioBtnMB = new JRadioButton("MegaBytes");
		radioBtnMB.setMnemonic(KeyEvent.VK_M);
		radioBtnMB.setBounds(325, 188, 150, 20);
		mainPanel.add(radioBtnMB);
		
		radioBtnGB = new JRadioButton("GigaBytes");
		radioBtnGB.setMnemonic(KeyEvent.VK_G);
		radioBtnGB.setBounds(325, 205, 150, 20);
		mainPanel.add(radioBtnGB);
		
		// *GROUP RADIO BUTTONS*
		radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(radioBtnKB);
		radioBtnGroup.add(radioBtnMB);
		radioBtnGroup.add(radioBtnGB);
		
		// *RADIO BUTTON ACTION LISTENERS*
		radioBtnKB.addActionListener(new RadioBtnKBHandler());
		radioBtnMB.addActionListener(new RadioBtnMBHandler());
		radioBtnGB.addActionListener(new RadioBtnGBHandler());
	}
	
	/**
	 * Adds buttons to the main form.
	 */
	public void addButtons() {
		// *BUTTON CREATION*
		btnCreateLog = new JButton();
		btnCreateLog.setText("Create Log");
		btnCreateLog.setBounds(325, 235, 135, 50);
		btnCreateLog.setMnemonic(KeyEvent.VK_C);
		mainPanel.add(btnCreateLog);
		
		btnAddNotes = new JButton();
		btnAddNotes.setText("Add Notes to Log");
		btnAddNotes.setBounds(325, 290, 135, 50);
		mainPanel.add(btnAddNotes);
		
		btnSaveLog = new JButton();
		btnSaveLog.setText("Save Log");
		btnSaveLog.setBounds(325, 345, 135, 50);
		mainPanel.add(btnSaveLog);
		
		btnClearLog = new JButton();
		btnClearLog.setText("Clear Log");
		btnClearLog.setBounds(325, 400, 135, 50);
		mainPanel.add(btnClearLog);
		
		btnExit = new JButton();
		btnExit.setText("Exit");
		btnExit.setBounds(325, 455, 135, 50);
		btnExit.setMnemonic(KeyEvent.VK_E);
		mainPanel.add(btnExit);
		
		// *BUTTON ACTION LISTENERS*
		btnCreateLog.addActionListener(new CreateLogBtnHandler());
		btnAddNotes.addActionListener(new AddNotesBtnHandler());
		btnClearLog.addActionListener(new ClearLogBtnHandler());
		btnSaveLog.addActionListener(new SaveBtnHandler());
		btnExit.addActionListener(new ExitBtnHandler());
	}
	
	/**
	 * Adds text area to the forms.
	 */
	public void addTextArea() {
		// *TEXT AREA CREATION*
		txtAreaInfoLogger = new JTextArea();
		txtAreaInfoLogger.setFont(new Font("Consolas", Font.PLAIN, 11));
		txtAreaInfoLogger.setText("Welcome " + SysInfo.getUserName()  + " . . . \n");
		txtAreaInfoLogger.setEditable(false);
		txtAreaInfoLogger.setLineWrap(true);
		
		txtAreaNotes = new JTextArea();
		txtAreaNotes.setText("Add Your Notes Here. . . ");
		txtAreaNotes.setFont(new Font("lucida grande", Font.ITALIC, 12));
		txtAreaNotes.setEditable(true);
		txtAreaNotes.setLineWrap(true);
		
		// *TEXT AREA SCROLL BAR CREATION*
		infoLoggerAreaScroll = new JScrollPane(txtAreaInfoLogger);
		infoLoggerAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		infoLoggerAreaScroll.setBounds(20, 50, 300, 350);

		notesAreaScroll = new JScrollPane(txtAreaNotes);
		notesAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		notesAreaScroll.setBounds(20, 430, 300, 80);
		
		mainPanel.add(infoLoggerAreaScroll);
		mainPanel.add(notesAreaScroll);
	}
	
	/**
	 * Adds the FileChooser to the main form.
	 */
	public void addFileChooser() {
		fileSaveLoc = new JFileChooser();
		fileSaveLoc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	}
	
	// *EVENT HANDLERS*
	
	/**
	 * Handles the events on the Basic Information checkbox button.
	 * @author Matthew Carney (i7218006)
	 */
	class ChkBoxBasicInfoHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			chkBoxBasicInfoEvent = event.getStateChange();
		}
	}
	
	/**
	 * Handles the events on the OS Information checkbox button.
	 * @author Matthew Carney (i7218006)
	 */
	class ChkBoxOSInfoHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			chkBoxOSInfoEvent = event.getStateChange();
		}
	}
	
	/**
	 * Handles the events on the Drive Information checkbox button.
	 * @author Matthew Carney (i7218006)
	 */
	class ChkBoxDriveInfoHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			chkBoxDriveInfoEvent = event.getStateChange();
		}
	}
	
	/**
	 * Handles the events on the Network Information checkbox button.
	 * @author Matthew Carney (i7218006)
	 */
	class ChkBoxNetworkInfoHandler implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			chkBoxNetworkInfoEvent = event.getStateChange();
		}
	}
	
	/**
	 * Handles the events on the KB radio button.
	 * @author Matthew Carney (i7218006)
	 */
	class RadioBtnKBHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			memoryFormat = 1;
		}
	}
	
	/**
	 * Handles the events on the MB radio button.
	 * @author Matthew Carney (i7218006)
	 */
	class RadioBtnMBHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			memoryFormat = 2;
		}
	}
	
	/**
	 * Handles the events on the GB radio button.
	 * @author Matthew Carney (i7218006)
	 */
	class RadioBtnGBHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			memoryFormat = 3;
		}
	}
	
	/**
	 * Handles the events on the Create Log button.
	 * @author Matthew Carney (i7218006)
	 */
	class CreateLogBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (memoryFormat == 0) {
				JOptionPane.showMessageDialog(mainFrame, "Please select a memory format before creating a system log.", "Warning", JOptionPane.WARNING_MESSAGE);
			} else if (chkBoxBasicInfoEvent == 2 && chkBoxOSInfoEvent == 2 && chkBoxDriveInfoEvent == 2 && chkBoxNetworkInfoEvent == 2) {
				JOptionPane.showMessageDialog(mainFrame, "Please select at least one information field to include in the system log.", "Warning", JOptionPane.WARNING_MESSAGE);
			} else {
				txtAreaInfoLogger.setText("");
				if (chkBoxBasicInfoEvent == 1) {txtAreaInfoLogger.append(SysInfo.getBasicSystemInformation(memoryFormat));}
				if (chkBoxOSInfoEvent == 1) {txtAreaInfoLogger.append(SysInfo.getOSInformation());}
				if (chkBoxDriveInfoEvent == 1) {txtAreaInfoLogger.append(SysInfo.getDriveInformation(memoryFormat));}
				if (chkBoxNetworkInfoEvent == 1) {txtAreaInfoLogger.append(SysInfo.getNetworkInformation());}
			}
		}
	}
	
	/**
	 * Handles the events on the Add Notes button.
	 * @author Matthew Carney (i7218006)
	 */
	class AddNotesBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String userNotesTemp;
			userNotesTemp = txtAreaNotes.getText();
			txtAreaInfoLogger.append("\r\n########################\r\nNOTES:\r\n########################\r\n" + userNotesTemp);
		}
	}
	
	/**
	 * Handles the events on the Save button.
	 * @author Matthew Carney (i7218006)
	 */
	class SaveBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int returnValue = fileSaveLoc.showSaveDialog(mainFrame);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File logFile = fileSaveLoc.getSelectedFile();
				try {
					PrintWriter outFile = new PrintWriter (new BufferedWriter (new FileWriter(logFile)));
					outFile.print(txtAreaInfoLogger.getText());
					outFile.close();
				}
				catch (IOException exception) { // add warning box if save works
					JOptionPane.showMessageDialog(mainFrame, "An error has occured when saving the log. Please try again with elevated rights.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				finally {
					JOptionPane.showMessageDialog(mainFrame, "The log has been saved.");
				}
			}
		}
	}
	
	/**
	 * Handles the events on the Clear Log button.
	 * @author Matthew Carney (i7218006)
	 */
	class ClearLogBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			txtAreaInfoLogger.setText("");
		}
	}
	
	/**
	 * Handles the events on the Clear Log button.
	 * @author Matthew Carney (i7218006)
	 */
	class ExitBtnHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int userChoice = JOptionPane.showConfirmDialog(mainFrame, "Do you really want to exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (userChoice == 0) {
				System.exit(0);
			}
		}
	}
}
