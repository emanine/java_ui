import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;

public class GestionEtudiants extends JFrame {

	private JPanel contentPane;
	private JTextField prenomfield;
	private JTextField nomfield;
	private JTextField cinfield;
	private JTextField nomtelfield;
	private JTextField adressefield;
	private JTextField datenaisfield;
	private JTextField txtTableauxDesEtudiants;
	
	JComboBox filierecombox;
	
	Connection cnx=null;
	PreparedStatement prepared=null;
	ResultSet resultat=null;
	
	private JTable tableUser;

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
					GestionEtudiants frame = new GestionEtudiants();
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
	public GestionEtudiants() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 200, 814, 498);;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		cnx= ConnectionMysql.connexiondb();
		
		
		
		prenomfield = new JTextField();
		prenomfield.setBounds(110, 42, 97, 17);
		contentPane.add(prenomfield);
		prenomfield.setColumns(10);
		
		nomfield = new JTextField();
		nomfield.setBounds(110, 70, 97, 20);
		contentPane.add(nomfield);
		nomfield.setColumns(10);
		
		cinfield = new JTextField();
		cinfield.setBounds(110, 101, 97, 20);
		contentPane.add(cinfield);
		cinfield.setColumns(10);
		
		nomtelfield = new JTextField();
		nomtelfield.setBounds(110, 132, 97, 20);
		contentPane.add(nomtelfield);
		nomtelfield.setColumns(10);
		
		adressefield = new JTextField();
		adressefield.setBounds(110, 169, 171, 20);
		contentPane.add(adressefield);
		adressefield.setColumns(10);
		
		datenaisfield = new JTextField();
		datenaisfield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		datenaisfield.setBounds(110, 200, 171, 30);
		contentPane.add(datenaisfield);
		datenaisfield.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Prenom:");
		lblNewLabel_1.setBounds(22, 42, 78, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nom:");
		lblNewLabel_2.setBounds(22, 73, 78, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Cin:");
		lblNewLabel_3.setBounds(22, 104, 78, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Nom Tel:");
		lblNewLabel_4.setBounds(22, 135, 78, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Adresse:");
		lblNewLabel_5.setBounds(22, 172, 78, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Date Naissance:");
		lblNewLabel_6.setBounds(22, 210, 91, 14);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Filiere:");
		lblNewLabel_7.setBounds(22, 247, 78, 14);
		contentPane.add(lblNewLabel_7);
		
		 filierecombox = new JComboBox();
		filierecombox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		filierecombox.setBounds(110, 241, 171, 27);
		contentPane.add(filierecombox);
		
		remplicombobox();
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String prenom=prenomfield.getText().toString();
				String nom=nomfield.getText().toString();
				String cin=cinfield.getText().toString();
				String nomtele=nomtelfield.getText().toString();
				String datenaiss=datenaisfield.getText().toString();
				String adresse=adressefield.getText().toString();
				
				String sql="insert into etudiants(prenom,nom,cin,tel,datenaissance,adresse,filiere) values(?,?,?,?,?,?,?)";
				try {
					prepared=cnx.prepareStatement(sql);
					
					if(!prenom.equals("")&& !nom.equals("")&& !cin.equals("")&& !nomtele.equals("")&& !datenaiss.equals("")&& !adresse.equals(""))
					{
					prepared.setString(1, prenom);
					prepared.setString(2, nom);
					prepared.setString(3, cin);
					prepared.setString(4, nomtele);
					prepared.setString(5, datenaiss);
					prepared.setString(6, adresse);
					prepared.setString(7, filierecombox.getSelectedItem().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "etudiant tzaaaad");
					
					prenomfield.setText("");
					nomfield.setText("");
					cinfield.setText("");
					nomtelfield.setText("");
					datenaisfield.setText("");
					adressefield.setText("");
					
					
					updatetable();
					}
					else
						JOptionPane.showMessageDialog(null, "remplissez tous les champs");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\add.jpg"));
		btnNewButton.setBounds(22, 297, 68, 102);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int ligne=tableUser.getSelectedRow();
				
				if(ligne== -1) {
					JOptionPane.showMessageDialog(null, "selectionner un etudiant");
				}
				else {
				   String id=tableUser.getModel().getValueAt(ligne, 0).toString();
				   String sql="delete from etudiants where id_etudiant='"+id+	"'";
				   try {
					prepared=cnx.prepareStatement(sql);
					prepared.execute();
					JOptionPane.showMessageDialog(null, "etudiant supprimer");
					updatetable();
					
					prenomfield.setText("");
					nomfield.setText("");
					cinfield.setText("");
					nomtelfield.setText("");
					datenaisfield.setText("");
					adressefield.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}}
		});
		
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\delete.png"));
		btnNewButton_1.setBounds(100, 297, 68, 102);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int ligne=tableUser.getSelectedRow();
				
				if(ligne== -1) {
					JOptionPane.showMessageDialog(null, "selectionner un etudiant");
				}
				else {
				   String id=tableUser.getModel().getValueAt(ligne, 0).toString();
				   String sql="update etudiants set prenom=? ,nom=?, cin=?,tel=?,datenaissance=?,adresse=?,filiere=? where id_etudiant='"+id+	"'";
				   
				   try {
					prepared=cnx.prepareStatement(sql);
					
					
					prepared.setString(1, prenomfield.getText().toString());
					prepared.setString(2, nomfield.getText().toString());
					prepared.setString(3, cinfield.getText().toString());
					prepared.setString(4, nomtelfield.getText().toString());
					prepared.setString(5, datenaisfield.getText().toString());
					prepared.setString(6, adressefield.getText().toString());
					prepared.setString(7, filierecombox.getSelectedItem().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "etudiant modifier");
					updatetable();
					
					prenomfield.setText("");
					nomfield.setText("");
					cinfield.setText("");
					nomtelfield.setText("");
					datenaisfield.setText("");
					adressefield.setText("");
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
			}
		});
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\update.png"));
		btnNewButton_2.setBounds(178, 297, 78, 102);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Document doc=new Document();
				
				String sql="select *from etudiants";
				
				try {
					
					prepared=cnx.prepareStatement(sql);
					resultat=prepared.executeQuery();
					
					PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\Etudiants.pdf") );
				
					
					doc.open();
					doc.add(new Paragraph("Gestion etudiants"));
					doc.add(new Paragraph("--------------------------------------"));
					Image img=Image.getInstance("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\background.jpg");
					img.scaleAbsoluteWidth(600);
					img.scaleAbsoluteHeight(92);
					img.setAlignment(Image.ALIGN_CENTER);
					doc.add(img);
					
					doc.add(new Paragraph(""));
					doc.add(new Paragraph("Liste des Etudiants"));
					doc.add(new Paragraph(""));
					
					
					
					
					PdfPTable table=new PdfPTable(7);
					table.setWidthPercentage(100);
					
					PdfPCell cell;

					///////////////////////////////////////////////////////////////////////
					                //ligne 1
					
					cell=new PdfPCell(new Phrase("Prenom",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);
					

					cell=new PdfPCell(new Phrase("Nom",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase("Cin",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase("Num tele",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);


					cell=new PdfPCell(new Phrase("Date Naissance",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase("Adress",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);
					
					cell=new PdfPCell(new Phrase("filiere",FontFactory.getFont("Comic Sans MS",12)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setBackgroundColor(BaseColor.GRAY);
					table.addCell(cell);
					
					/////////////////////////////////////////////////////////////////
					
					
					while(resultat.next()) {
						
					cell=new PdfPCell(new Phrase(resultat.getString("prenom").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					

					cell=new PdfPCell(new Phrase(resultat.getString("nom").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase(resultat.getString("cin").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase(resultat.getString("tel").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase(resultat.getString("datenaissance").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);

					cell=new PdfPCell(new Phrase(resultat.getString("adresse").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					cell=new PdfPCell(new Phrase(resultat.getString("filiere").toString(),FontFactory.getFont("Arial",11)));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					
					}
					/////////////////////////////////////////////////////////////////
					
					doc.add(table);
					
					doc.close();
					Desktop.getDesktop().open(new File("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProjectc\\Etudiants.pdf"));
				
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (DocumentException e) {
					
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\printer.jpg"));
		btnNewButton_3.setBounds(266, 297, 78, 102);
		contentPane.add(btnNewButton_3);
		
		txtTableauxDesEtudiants = new JTextField();
		txtTableauxDesEtudiants.setText("tableaux Des Etudiants");
		txtTableauxDesEtudiants.setBounds(379, 25, 150, 20);
		contentPane.add(txtTableauxDesEtudiants);
		txtTableauxDesEtudiants.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(379, 51, 390, 218);
		contentPane.add(scrollPane_1);
		
		tableUser = new JTable();
		tableUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int ligne=tableUser.getSelectedRow();
			   String id=tableUser.getModel().getValueAt(ligne, 0).toString();
			   String sql="select * from etudiants where id_etudiant='"+id+	"'";
			   
			   try {
				prepared=cnx.prepareStatement(sql);
				resultat=prepared.executeQuery();
				
				if(resultat.next()) {
				prenomfield.setText(resultat.getString("prenom"));
				nomfield.setText(resultat.getString("nom"));
				cinfield.setText(resultat.getString("cin"));
				nomtelfield.setText(resultat.getString("tel"));
				datenaisfield.setText(resultat.getString("datenaissance"));
				adressefield.setText(resultat.getString("adresse"));
				filierecombox.setSelectedItem(resultat.getString("filiere"));
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		scrollPane_1.setViewportView(tableUser);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuAdministrateur obj=new MenuAdministrateur();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				fermer();
			}
			
		});
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\back.png"));
		btnNewButton_4.setBounds(20, 11, 29, 23);
		contentPane.add(btnNewButton_4);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			
				updatetable();
			}
		});
		lblNewLabel_8.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\refresh.jpg"));
		lblNewLabel_8.setBounds(722, 21, 36, 30);
		contentPane.add(lblNewLabel_8);
		
		JPanel panel = new JPanel();
		panel.setBounds(217, 42, 97, 78);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 1));
		
		JLabel lblNewLabel_9 = new JLabel("");
		panel.add(lblNewLabel_9);
		
		JButton btnNewButton_5 = new JButton("Parcourir");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser=new JFileChooser();
				FileNameExtensionFilter filter=new FileNameExtensionFilter("IMAGE", "jpg","png","gif");
				fileChooser.addChoosableFileFilter(filter);
				int result=fileChooser.showSaveDialog(null);
			}
		});
		btnNewButton_5.setBounds(217, 131, 89, 23);
		contentPane.add(btnNewButton_5);
		
		JLabel lblNewLabel = new JLabel("tableau des Etudiant");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\photo-1579546929518-9e396f3cc809.jpg"));
		lblNewLabel.setBounds(-30, -14, 838, 500);
		contentPane.add(lblNewLabel);
	}
	
	public void updatetable() {
		String sql="select * from etudiants";
		try {
			prepared=cnx.prepareStatement(sql);
			resultat=prepared.executeQuery();
			tableUser.setModel(DbUtils.resultSetToTableModel(resultat));
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
				String nom=resultat.getString("nom").toString();
				filierecombox.addItem(nom);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
