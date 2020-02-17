package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.SystemColor;

public class OfertyDoKlientow extends JFrame {

	//private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OfertyDoKlientow frame = new OfertyDoKlientow();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OfertyDoKlientow() {
		setForeground(SystemColor.inactiveCaption);
		setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JList list = new JList();
		getContentPane().add(list, "cell 0 0,grow");
		/*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1021, 857);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 69, 953, 696);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 953, 696);
		panel.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel = new JLabel("Wszystkie oferty");
		lblNewLabel.setBounds(419, 22, 158, 26);
		contentPane.add(lblNewLabel);*/
	}

}
