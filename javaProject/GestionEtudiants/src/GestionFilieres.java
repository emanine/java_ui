import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GestionFilieres extends JFrame {

	private JPanel contentPane;
	private JTextField nomfilierefield;
	
	String NomFiliereold=null;
	
	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	
	private JTable tablefiliere;
	private JTextField txtTableDesUtilisateurs;
	private JComboBox<String> typecombobox;
	
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
					GestionFilieres frame = new GestionFilieres();
					frame.setVisible(true);
					frame.setTitle("Gestion des Etudiants");
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
	public GestionFilieres() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 200, 814, 498);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Fichier");
		menuBar.add(mnNewMenu);
		
		JMenu mnNewMenu_2 = new JMenu("Ouvrir");
		mnNewMenu.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Gestion Des Etudiants");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionEtudiants j=new GestionEtudiants();
				j.setVisible(true);
				j.setLocationRelativeTo(null);
				fermer();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Gestion Des users");
		mnNewMenu_2.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Fermer");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fermer();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("Edition");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Copier");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		cnx= ConnectionMysql.connexiondb();
		
		JLabel Filiere = new JLabel("Nom Filiere");
		Filiere.setBounds(10, 167, 86, 14);
		contentPane.add(Filiere);
		
		JLabel Type = new JLabel("Type");
		Type.setBounds(10, 209, 86, 14);
		contentPane.add(Type);
		
		nomfilierefield = new JTextField();
		nomfilierefield.setBounds(109, 157, 99, 35);
		contentPane.add(nomfilierefield);
		nomfilierefield.setColumns(10);
		
		 typecombobox = new JComboBox();
		 
		typecombobox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		typecombobox.setBounds(109, 203, 99, 26);
		contentPane.add(typecombobox);
		
		remplicombobox();
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\add.jpg"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String nomfil=nomfilierefield.getText().toString();
				String type=typecombobox.getSelectedItem().toString();
				
				
				String sql="insert into filiere(nom,type) values ( ? , ? )";
				try {

					if(nomfilierefield.getText().equals("") && typecombobox.getSelectedItem().equals("Selectionnez"))
					JOptionPane.showMessageDialog(null,"Remplissez les champs");
					
					if(nomfilierefield.getText().equals("") || typecombobox.getSelectedItem().equals("Selectionnez")){
						
						if(nomfilierefield.getText().equals("") && !typecombobox.getSelectedItem().equals("Selectionnez"))
						JOptionPane.showMessageDialog(null,"tapez le nom de la filier");
						if(!nomfilierefield.getText().equals("") && typecombobox.getSelectedItem().equals("Selectionnez"))
							JOptionPane.showMessageDialog(null,"tapez le type de la filier");
					}
					else {
					prepared=cnx.prepareStatement(sql);
					
		           	prepared.setString(1, nomfil);
			        prepared.setString(2, type);
			        prepared.execute();
			        
			        nomfilierefield.setText("");
			        typecombobox.setSelectedItem("Selectionnez");
			        JOptionPane.showMessageDialog(null,"filiere ajouter");
			        updatetable();
			        
					}
					}	catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(59, 276, 73, 83);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Supprimer");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\delete.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				int ligne=tablefiliere.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null,"selection une filier");
					
				}
				else {
					int a=JOptionPane.showConfirmDialog(null, "voullez-vous vraimment supprimer filire?", "supprimer filiere", JOptionPane.YES_NO_OPTION);
				
					String id=tablefiliere.getModel().getValueAt(ligne, 0).toString();
				
				    String sql="delete from filiere where id_filiere='"+id+"'  ";
				try {
					prepared=cnx.prepareStatement(sql);
					
					if(a==0) {
				      prepared.execute();
					 JOptionPane.showMessageDialog(null,"filiere supprimer");

					}
				
					updatetable();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}}
		});
		btnNewButton_1.setBounds(164, 276, 73, 83);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Modifier");
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\update.png"));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int ligne=tablefiliere.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null,"selection une filier");
				}
				else {
				String id=tablefiliere.getModel().getValueAt(ligne, 0).toString();
				String sql="update filiere set nom=?,type=? where id_filiere= '"+id+"' ";
				try {
					prepared=cnx.prepareStatement(sql);
					prepared.setString(1, nomfilierefield.getText().toString());
					prepared.setString(2, typecombobox.getSelectedItem().toString());
					prepared.execute();
					JOptionPane.showMessageDialog(null, "user est updated :D");
					
					updatetable();}
					 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
			}
		});
		btnNewButton_2.setBounds(266, 276, 73, 83);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(427, 141, 319, 218);
		contentPane.add(scrollPane_1);
		
		tablefiliere = new JTable();
		tablefiliere.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ligne=tablefiliere.getSelectedRow();
				   String id=tablefiliere.getModel().getValueAt(ligne, 0).toString();
				   String sql="select * from filiere where id_filiere='"+id+"'";
				   
				   try {
						prepared=cnx.prepareStatement(sql);
						resultat=prepared.executeQuery();
						
						if(resultat.next()) {
							nomfilierefield.setText(resultat.getString("nom"));
							typecombobox.setSelectedItem(resultat.getString("type"));
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			}
		});
		scrollPane_1.setViewportView(tablefiliere);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				updatetable();
			}
		});
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\refresh.jpg"));
		lblNewLabel_3.setBounds(710, 100, 36, 30);
		contentPane.add(lblNewLabel_3);
		
		txtTableDesUtilisateurs = new JTextField();
		txtTableDesUtilisateurs.setText("Table Des Utilisateurs");
		txtTableDesUtilisateurs.setBounds(430, 110, 160, 20);
		contentPane.add(txtTableDesUtilisateurs);
		txtTableDesUtilisateurs.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuAdministrateur obj=new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				fermer();}
			
		});
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\back.png"));
		btnNewButton_3.setBounds(20, 105, 29, 23);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\backgroundFiliere.png"));
		lblNewLabel.setBounds(0, 61, 833, 445);
		contentPane.add(lblNewLabel);
	}
	
	public void updatetable() {
		String sql="select * from filiere";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			tablefiliere.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void remplicombobox() {
		String sql="select * from filiere";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			while(resultat.next()) {
				String nom=resultat.getString("type").toString();
				typecombobox.addItem(nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
