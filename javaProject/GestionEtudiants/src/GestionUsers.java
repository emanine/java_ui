import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GestionUsers extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	String userOld=null;
	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	private JTable table;
	private JTextField txtTableDesUtilisateurs;

	public void fermer() {
		dispose();
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestionUsers frame = new GestionUsers();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestionUsers() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 200, 814, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	

				cnx= ConnectionMysql.connexiondb();
				
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		});
		textField.setBounds(70, 80, 146, 34);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(70, 135, 146, 30);
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("username");
		lblNewLabel_1.setBounds(13, 90, 85, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("password");
		lblNewLabel_2.setBounds(13, 143, 102, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\add.jpg"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sql="insert into utilisateurs(username,password) values ( ? , ? )";
				try {
					prepared=cnx.prepareStatement(sql);
					
			prepared.setString(1, textField.getText().toString());
			prepared.setString(2, textField_1.getText().toString());
			
			if(!textField.getText().equals("") && !textField_1.getText().equals("") ) {
			prepared.execute();
			JOptionPane.showMessageDialog(null,"utilisateur ajouter avec succes");
			
			updatetable();
			
			textField.setText("");
			textField_1.setText("");
			}
			
			else
				JOptionPane.showMessageDialog(null,"please full all the field");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(56, 186, 73, 83);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Supprimer");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\delete.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql="delete from utilisateurs where username=? and password=? ";
				try {
					if(!textField.getText().equals("")&& !textField_1.getText().equals("")) {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1, textField.getText().toString());
					prepared.setString(2, textField_1.getText().toString());
					prepared.execute();
				
					JOptionPane.showMessageDialog(null,"utilisateur supprimer");
				
					textField.setText("");
					textField_1.setText("");
					
					updatetable();
					
					}else
						JOptionPane.showMessageDialog(null,"choisissez un user");
					} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(165, 186, 73, 83);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Modifier");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\update.png"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String sql="update utilisateurs set username=?,password=? where username= '"+userOld+"' ";
				try {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1, textField.getText().toString());
					prepared.setString(2, textField_1.getText().toString());
					
					if(!textField.getText().equals("") && !textField_1.getText().equals("") ) {
					prepared.execute();
					JOptionPane.showMessageDialog(null, "user est updated :D");
					
					updatetable();
					textField.setText("");
					textField_1.setText("");
					}else
						JOptionPane.showMessageDialog(null,"please full all the field");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(259, 186, 73, 83);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(427, 51, 319, 218);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
				int ligne=table.getSelectedRow();
				
				userOld=table.getModel().getValueAt(ligne, 0).toString();
				String password=table.getModel().getValueAt(ligne, 1).toString();
				
				textField.setText(userOld);
				textField_1.setText(password);
			}
		});
		scrollPane_1.setViewportView(table);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updatetable();
			}
		});
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\refresh.jpg"));
		lblNewLabel_3.setBounds(722, 21, 36, 30);
		contentPane.add(lblNewLabel_3);
		
		txtTableDesUtilisateurs = new JTextField();
		txtTableDesUtilisateurs.setText("Table Des Utilisateurs");
		txtTableDesUtilisateurs.setBounds(427, 26, 160, 20);
		contentPane.add(txtTableDesUtilisateurs);
		txtTableDesUtilisateurs.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuAdministrateur obj=new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				fermer();
			}
			
		});
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\back.png"));
		btnNewButton_3.setBounds(20, 11, 29, 23);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\picBackground.png"));
		lblNewLabel.setBounds(-229, -53, 1017, 512);
		contentPane.add(lblNewLabel);
	}
	public void updatetable() {
		String sql="select username,password from utilisateurs";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
