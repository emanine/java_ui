import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.demo.DateChooserPanel;

import net.proteanit.sql.DbUtils;

import javax.swing.DefaultComboBoxModel;

public class Absence extends JFrame {

	private JPanel contentPane;
	private JTextField Raisonfield;
	private JTable tableAbsence;
	private JTextField txtTableDesUtilisateurs;
	JComboBox NomEtudBox;
	JComboBox NomEtudBox2;
	
	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	

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
					Absence frame = new Absence();
					frame.setVisible(true);
					frame.setTitle("Gestion des Abscence");
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
	public Absence() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 869, 444);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//ntconecta l base donne
				cnx= ConnectionMysql.connexiondb();
				
				JLabel nom_etudiant = new JLabel("Nom Etudiant");
				nom_etudiant.setBounds(10, 167, 86, 14);
				contentPane.add(nom_etudiant);
				
				JLabel date_nais = new JLabel("date naissance");
				date_nais.setBounds(10, 209, 86, 14);
				contentPane.add(date_nais);
				
				JLabel raison = new JLabel("Raison");
				raison.setBounds(10, 250, 86, 14);
				contentPane.add(raison);
				
				
				NomEtudBox = new JComboBox();
				NomEtudBox.setModel(new DefaultComboBoxModel(new String[] {"Selectionez"}));
				NomEtudBox.setBounds(106, 164, 131, 20);
				contentPane.add(NomEtudBox);
				
				remplicombobox();
				
				
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setDateFormatString("yyyy-mm-dd");
				dateChooser.setBounds(109, 200, 128, 35);
				contentPane.add(dateChooser);
				
				
				NomEtudBox2 = new JComboBox();
				NomEtudBox2.setModel(new DefaultComboBoxModel(new String[] {"Selectionez","malade ", "retard"}));
				NomEtudBox2.setBounds(106, 250, 131, 20);
				contentPane.add(NomEtudBox2);
				
				
				
				
				
				JButton btnNewButton = new JButton("Ajouter");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					
						String nomEtu=NomEtudBox.getSelectedItem().toString();
						String date=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
						String raison=NomEtudBox2.getSelectedItem().toString();
						
						
						String sql="insert into absence(nometud,DateAbsence,raison) values ( ? , ? , ? )";
						
						try {
							if(NomEtudBox.getSelectedItem().equals("Selectionnez") ||date.equals("") || NomEtudBox2.getSelectedItem().equals("Selectionnez"))
								JOptionPane.showMessageDialog(null,"Remplissez les champs");
						    else {
							prepared=cnx.prepareStatement(sql);
							prepared.setString(1, nomEtu);
							prepared.setString(2, date);
					        prepared.setString(3, raison);
					    
					        prepared.execute();
					        
					        NomEtudBox.setSelectedItem("Selectionnez");
					        NomEtudBox2.setSelectedItem("Selectionnez");
					        dateChooser.setDateFormatString("");
					        
					        JOptionPane.showMessageDialog(null,"Abscence ajouter");
					        
					        updatetable();
						
					     }}catch (SQLException e) {
					    	 JOptionPane.showMessageDialog(null,e);
						e.printStackTrace();
					}
					}
				});
				btnNewButton.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\dateADD.jpg"));
				btnNewButton.setBounds(59, 320, 73, 83);
				contentPane.add(btnNewButton);
				
				
				JButton btnNewButton_1 = new JButton("Supprimer");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						int ligne=tableAbsence.getSelectedRow();
						
						if(ligne == -1) {
							JOptionPane.showMessageDialog(null,"selection une absence ");
							
						}
						else {
							int a=JOptionPane.showConfirmDialog(null, "voullez-vous vraimment supprimer filire?", "supprimer filiere", JOptionPane.YES_NO_OPTION);
						
							String id=tableAbsence.getModel().getValueAt(ligne, 0).toString();
						
						    String sql="delete from absence where id_absence='"+id+"'  ";
						try {
							prepared=cnx.prepareStatement(sql);
							
							if(a==0) {
						      prepared.execute();
							 JOptionPane.showMessageDialog(null,"absence supprimer");

							}
						
							updatetable();
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				});
				btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\deleteDate.png"));
				btnNewButton_1.setBounds(164, 320, 73, 83);
				contentPane.add(btnNewButton_1);
	
				
				
				JButton btnNewButton_2 = new JButton("Modifier");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						int ligne=tableAbsence.getSelectedRow();
						
						String nomEtu=NomEtudBox.getSelectedItem().toString();
						String date=((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
						String raison=NomEtudBox2.getSelectedItem().toString();
						
						if(ligne == -1) {
							JOptionPane.showMessageDialog(null,"selection une absence");
						}
						else {
						String id=tableAbsence.getModel().getValueAt(ligne, 0).toString();
						String sql="update absence set nometud=?,DateAbsence=?,raison=? where id_absence= '"+id+"' ";
						try {
							prepared=cnx.prepareStatement(sql);
							prepared.setString(1, nomEtu);
							prepared.setString(2, date);
					        prepared.setString(3, raison);
					        prepared.execute();
							JOptionPane.showMessageDialog(null, "absence updated :D");
							
							updatetable();}
							 catch (SQLException e) {
								 JOptionPane.showMessageDialog(null, e);
							e.printStackTrace();
						}}
					}
				});
				btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\updatedate.jpg"));
				btnNewButton_2.setBounds(266, 320, 73, 83);
				contentPane.add(btnNewButton_2);
				
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(427, 141, 319, 218);
				contentPane.add(scrollPane_1);
				
				tableAbsence = new JTable();
				tableAbsence.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						int ligne=tableAbsence.getSelectedRow();
						   String id=tableAbsence.getModel().getValueAt(ligne, 0).toString();
						   String sql="select * from absence where id_absence='"+id+"'";
						   
						   try {
								prepared=cnx.prepareStatement(sql);
								resultat=prepared.executeQuery();
								
								if(resultat.next()) {
									
									NomEtudBox.setSelectedItem(resultat.getString("nometud"));
									dateChooser.setDateFormatString(resultat.getString("DateAbsence"));
									
			  
							        NomEtudBox2.setSelectedItem(resultat.getString("raison"));
								}
								
							} catch (SQLException e) {
								JOptionPane.showMessageDialog(null,e);
								e.printStackTrace();
							}
							
					}
				});
				scrollPane_1.setViewportView(tableAbsence);
				
				
				
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
						
						fermer();
					}
					
				});
				btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\back.png"));
				btnNewButton_3.setBounds(20, 105, 29, 23);
				contentPane.add(btnNewButton_3);
				
				
				
				JLabel lblNewLabel = new JLabel("");
				lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\backgroundFiliere.png"));
				lblNewLabel.setBounds(0, 61, 800, 354);
				contentPane.add(lblNewLabel);
				
				
				
				
				
				
				
	}
	
	public void updatetable() {
		String sql="select * from absence";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			tableAbsence.setModel(DbUtils.resultSetToTableModel(resultat));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public void remplicombobox() {
		String sql="select * from etudiants";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			while(resultat.next()) {
				String nom=resultat.getString("nom").toString();
				String prenom=resultat.getString("prenom").toString();
				NomEtudBox.addItem(prenom+" "+nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
