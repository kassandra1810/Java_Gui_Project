package GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class PodwierdzenieUtworzeniaWyceny extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PodwierdzenieUtworzeniaWyceny dialog = new PodwierdzenieUtworzeniaWyceny();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PodwierdzenieUtworzeniaWyceny() {
		setBounds(100, 100, 496, 334);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{424, 0};
		gridBagLayout.rowHeights = new int[]{184, 45, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		{
			JLabel lblOfertaZostaaPomyslnie = new JLabel("Oferta została pomyslnie utworzona i wysłana do klienta.");
			GridBagConstraints gbc_lblOfertaZostaaPomyslnie = new GridBagConstraints();
			gbc_lblOfertaZostaaPomyslnie.insets = new Insets(0, 0, 5, 0);
			gbc_lblOfertaZostaaPomyslnie.gridx = 0;
			gbc_lblOfertaZostaaPomyslnie.gridy = 0;
			getContentPane().add(lblOfertaZostaaPomyslnie, gbc_lblOfertaZostaaPomyslnie);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			GridBagConstraints gbc_buttonPane = new GridBagConstraints();
			gbc_buttonPane.anchor = GridBagConstraints.NORTH;
			gbc_buttonPane.fill = GridBagConstraints.HORIZONTAL;
			gbc_buttonPane.gridx = 0;
			gbc_buttonPane.gridy = 1;
			getContentPane().add(buttonPane, gbc_buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						setVisible(false);
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
