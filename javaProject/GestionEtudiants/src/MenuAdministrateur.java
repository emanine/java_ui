import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuAdministrateur extends JFrame {

	private JPanel contentPane;
	
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
					MenuAdministrateur frame = new MenuAdministrateur();
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
	public MenuAdministrateur() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 200, 814, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionUsers obj=new GestionUsers();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				fermer();
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\images.png"));
		btnNewButton.setBounds(163, 40, 120, 131);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Gestion Des Utilisateurs");
		lblNewLabel_1.setForeground(new Color(0, 0, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(143, 168, 140, 21);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GestionEtudiants obj=new GestionEtudiants();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				fermer();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\content_etudiant-universitaires.png"));
		btnNewButton_1.setBounds(324, 43, 134, 125);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GestionFilieres obj=new GestionFilieres();
				obj.setVisible(true);
				obj.setLocationRelativeTo(null);
				
				fermer();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\22009337-3d-\u00E9tudiant-de-l-homme-et-livre-sur-fond-blanc.jpg"));
		btnNewButton_2.setBounds(495, 48, 101, 123);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showInternalMessageDialog(null, "mallllll jd booooooooook");
			}
		});
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\Comment-obtenir-une-bourse-d\u00E9tude-au-Royaume-Uni.jpg"));
		btnNewButton_3.setBounds(155, 259, 128, 126);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\images.jpg"));
		btnNewButton_4.setBounds(324, 259, 134, 126);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		btnNewButton_5.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\pngtree-student-notes-note-education-flat-color-icon-vector-icon-png-image_1650322.jpg"));
		btnNewButton_5.setBounds(495, 259, 107,126);

		contentPane.add(btnNewButton_5);
		
		JLabel lblNewLabel_2 = new JLabel("Gestion Des Etudiants");
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(323, 171, 162, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Gestion Des Filieres");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setBounds(495, 171, 144, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Gestion Des Absence");
		lblNewLabel_4.setForeground(Color.BLUE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(127, 396, 176, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Gestion Des Matieres");
		lblNewLabel_5.setForeground(Color.BLUE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(319, 396, 152, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Gestion Des Notes");
		lblNewLabel_6.setForeground(Color.BLUE);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_6.setBounds(495, 401, 144, 14);
		contentPane.add(lblNewLabel_6);
		
	
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\imane\\OneDrive\\Bureau\\all\\javaProject\\picBackground.png"));
		lblNewLabel.setBounds(-140, -53, 955, 512);
		contentPane.add(lblNewLabel);
	}
}
