import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;



import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Authentification extends JFrame{

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	
	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null; 
	
	
	public void fermer() {
		frame.dispose();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();  
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 200, 574, 312);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
	
		cnx= ConnectionMysql.connexiondb();
		
		
		
		
		usernameField = new JTextField();
		usernameField.setBounds(203, 73, 148, 35);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("UserName");
		lblNewLabel_1.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setBounds(109, 73, 84, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("password");
		lblNewLabel_2.setFont(new Font("Stencil", Font.PLAIN, 16));
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBounds(109, 139, 84, 35);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("CONNECTER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
				
			
				String username=usernameField.getText().toString();
				String password=passwordField.getText().toString();
				
		
				String sql="select username,password from utilisateurs";
				
				try {
					prepared=cnx.prepareStatement(sql);
					resultat=prepared.executeQuery();
					int i=0;
					
					if(username.equals("") && password.equals("")) {
						JOptionPane.showMessageDialog(null,"remplisseze les champs vide!");
					}
					
					else {
					while(resultat.next()) {
						
						String username1=resultat.getString("username");
						String password1=resultat.getString("password");
						
						if(username1.equals(username)&& !password1.equals(password)) 
							JOptionPane.showMessageDialog(null,"Mot de passe incorrect");
						if(!username1.equals(username)&& password1.equals(password)) 
							JOptionPane.showMessageDialog(null,"username introuvable");
						
						if(username1.equals(username)&&password1.equals(password)) {
							JOptionPane.showMessageDialog(null,"connection reussite :D");
							
							i=1;
							
							MenuAdministrateur obj=new MenuAdministrateur();
							obj.setVisible(true);
							obj.setLocationRelativeTo(null);
							fermer();
						}
						
					}
					
				
						
					}
					
					}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
					}
			
		});
		btnNewButton.setBounds(227, 207, 109, 23);
		frame.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(203, 139, 148, 27);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel_3 = new JLabel("Mot de passe oublier");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IndicationPassword obj=new IndicationPassword();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
			}
		});
		lblNewLabel_3.setForeground(Color.PINK);
		lblNewLabel_3.setBackground(Color.PINK);
		lblNewLabel_3.setBounds(246, 177, 117, 14);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setForeground(Color.RED);
		
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\picBackground.png"));
		lblNewLabel.setBounds(-17, 0, 575, 273);
		frame.getContentPane().add(lblNewLabel);
	}
}
