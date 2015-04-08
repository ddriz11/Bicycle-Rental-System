// specify the package
package userinterface;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.GridLayout;
import java.util.Properties;
import java.util.EventObject;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;
import model.*;

public class WorkerView extends JPanel implements ActionListener
{
	private Peon peon;
	
	private JLabel firstNameLabel;
	private JTextField firstNameTextField;
	
	private JLabel lastNameLabel;
	private JTextField lastNameTextField;
	
	private JLabel bannerLabel;
	private JTextField bannerTextField;
	
	private JLabel emailLabel;
	private JTextField emailTextField;
	
	private JLabel phoneLabel;
	private JTextField phoneTextField;
	
	private JLabel passwordLabel;
	private JTextField passwordTextField;
	
	private JButton submitButton;
	private JButton backButton;
	
	private JComboBox adminComboBox;
	
	private MessageView statusLog;

    public ResourceBundle localizedBundle;
	
	//-----------------------------------------------------------------
	public WorkerView(Peon otherPeon)
	{
		peon = otherPeon;
		Locale currentLocale = LocaleConfig.currentLocale();
		localizedBundle = ResourceBundle.getBundle("BicycleStringsBundle", currentLocale);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JLabel lbl = new JLabel(localizedBundle.getString("addWorker"));
		Font myFont = new Font("Helvetica", Font.BOLD, 20);
		lbl.setFont(myFont);
		titlePanel.add(lbl);

		add(titlePanel);
		add(dataEntryFields());
		add(choiceBox());
		add(navigationButtons());
		
		add(statusLog("         "));
	}

	// Create the main data entry fields
	//-------------------------------------------------------------
	private JPanel dataEntryFields()
	{
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new GridLayout(6,2));

		// data entry fields		
		JLabel bannerLabel = new JLabel(localizedBundle.getString("bannerID") + ": ");
		temp.add(bannerLabel);

		bannerTextField = new JTextField(20);
		bannerTextField.addActionListener(this);
		temp.add(bannerTextField);
		//
		JLabel passwordLabel = new JLabel(localizedBundle.getString("password") + ": ");
		temp.add(passwordLabel);

		passwordTextField = new JTextField(20);
		passwordTextField.addActionListener(this);
		temp.add(passwordTextField);
		//
		JLabel firstNameLabel = new JLabel(localizedBundle.getString("firstName") + ": ");
		temp.add(firstNameLabel);

		firstNameTextField = new JTextField(20);
		firstNameTextField.addActionListener(this);
		temp.add(firstNameTextField);
		//
		JLabel lastNameLabel = new JLabel(localizedBundle.getString("lastName") + ": ");
		temp.add(lastNameLabel);

		lastNameTextField = new JTextField(20);
		lastNameTextField.addActionListener(this);
		temp.add(lastNameTextField);
		//
		JLabel phoneLabel = new JLabel(localizedBundle.getString("phoneNumber") + ": ");
		temp.add(phoneLabel);

		phoneTextField = new JTextField(20);
		phoneTextField.addActionListener(this);
		temp.add(phoneTextField);
		//
		JLabel emailLabel = new JLabel(localizedBundle.getString("email") + ": ");
		temp.add(emailLabel);

		emailTextField = new JTextField(20);
		emailTextField.addActionListener(this);
		temp.add(emailTextField);

		return temp;
	}

	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel navigationButtons()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(50);
		f1.setHgap(25);
		temp.setLayout(f1);

		// create the buttons, listen for events, add them to the panel
		
		
		backButton = new JButton(localizedBundle.getString("back"));
		backButton.addActionListener(this);
		temp.add(backButton);

		submitButton = new JButton(localizedBundle.getString("submit"));
		submitButton.addActionListener(this);
		temp.add(submitButton);

		return temp;
	}
	
	// Create the combo box
	private JPanel choiceBox()
	{
		JPanel temp = new JPanel();		// default FlowLayout is fine
		FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
		f1.setVgap(1);
		f1.setHgap(25);
		temp.setLayout(f1);
		
		JLabel adminLabel = new JLabel(localizedBundle.getString("administrator") + "?");
		temp.add(adminLabel);
		
		String [] adminPossibilites = { localizedBundle.getString("yes"), localizedBundle.getString("no") };
		adminComboBox = new JComboBox(adminPossibilites);
		adminComboBox.addActionListener(this);
		temp.add(adminComboBox);
		
		return temp;
	}
	
	// Create the status log field
	//-------------------------------------------------------------
	private JPanel statusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}
	
	//-----------------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}
	
	//------------------------------------------------------------------
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == submitButton)
		{
			
			if((bannerTextField.getText() == null || passwordTextField.getText() == null || firstNameTextField.getText() == null) ||
				(lastNameTextField.getText() == null || phoneTextField.getText().length() < 7 || emailTextField.getText() == null))
			{
				displayErrorMessage("Error: Book fields incorrect");
			}
			else
			{
					Properties workerProperties = new Properties();
					workerProperties.setProperty("bannerId",bannerTextField.getText());
					workerProperties.setProperty("password",passwordTextField.getText());
					workerProperties.setProperty("adminLevel",(String)adminComboBox.getSelectedItem());
					workerProperties.setProperty("firstName",firstNameTextField.getText());
					workerProperties.setProperty("lastName",lastNameTextField.getText());
					workerProperties.setProperty("phoneNumber",phoneTextField.getText());
					workerProperties.setProperty("email",emailTextField.getText());

					peon.processWorkerData(workerProperties);
					
					bannerTextField.setText("");
					passwordTextField.setText("");
					firstNameTextField.setText("");
					lastNameTextField.setText("");
					phoneTextField.setText("");
					emailTextField.setText("");
				
			}
			
		}
		else if(event.getSource() == backButton)
		{
			peon.workerDataDone();
		}
	}
}
