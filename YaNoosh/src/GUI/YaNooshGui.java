package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLayeredPane;

import net.miginfocom.swing.MigLayout;

import java.awt.CardLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;

import Logika.CenaDostawcy;
import Logika.Dostawca;
import Logika.Klient;
import Logika.LaczenieZBaza;
import Logika.OfertaDoKlienta;
import Logika.Powtarzalnosc;
import Logika.SpecyfikacjaProduktu;
import Logika.Pracownik;
import Logika.Produkt2D;
import Logika.Wycena;
import Logika.Powtarzalnosc.*;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.SystemColor;

import javax.swing.SwingConstants;
public class YaNooshGui extends JFrame {

	private JPanel contentPane;
	private JPanel panel4;
	private JPanel panel3;
	private JPanel panel2;
	private JPanel panel1;
	private JLayeredPane layeredPane;
	private JLabel lblPanel;
	private JLabel lblPanel_1;
	private JLabel lblPanel_2;
	private JLabel lblPanel_3;
	private JButton btnP;
	private JButton btnNewButton_4;
	private JButton btnP_1;
	private JButton btnZakadanieSpesyfikacjiProduktu;
	private JButton btnNewButton_5;
	private JButton btnRobienieWyceny;
	private JButton btnZakadanieNowegoKlienta;
	private JList list;
	private JList list_1;
	private JLabel lblPodajIlocDni;
	private JLabel lblPodajIloMiejsc;
	private JTextField textField_dni;
	private JTextField textField_miejsca;
	private JList list_2;
	private JPanel panel5;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblProdukt;
	private JLabel lblWycena;
	private JLabel lblOfertaDoKlienta;
	private JTextField tfKlient;
	private JTextField tfProdukt;
	private JButton btnNewButton_6;
	private JButton btnNewButton_7;
	private JButton btnNewButton_8;
	private JButton btnNewButton_9;
	private JButton btnAnuluj;
	private JButton btnNewButton_10;
	private JButton btnNewButton;
	private JTextField tfWycena;
	private JPanel panel6;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_3;
	private JButton btnNewButton_11;
	private JList list_3;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JButton btnKlienci;
	private JScrollPane scrollPane_4;
	private JScrollPane scrollPane_5;
	private JScrollPane scrollPane_6;
	private JLabel komunikat;
	private JLabel komunkat;
	private JLabel kom;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YaNooshGui frame = new YaNooshGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
	// PEZWŁACZANIE PANELI
	public void swichPanels(JPanel panel){
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	// SZUKANIE WYCEN PO ASOCJACJI
	public ArrayList<Wycena> szukajWyceny(int produktId){
		ArrayList<Wycena>wycenyProduktu = new ArrayList<>();
		wycenyProduktu = SpecyfikacjaProduktu.getProduktyMap().get(new Integer(produktId)).getWyceny();
		return wycenyProduktu;
	}
	
	public Klient szukajKlienta(int klientId){
		return Klient.getKlienciMapa().get(new Integer(klientId));
	}
	public Wycena szukajWycene(int wycenaId){
		return Wycena.getWycenyMapa().get(new Integer(wycenaId));
	}
	
	public SpecyfikacjaProduktu szukajProduktu(int produktId){
		return SpecyfikacjaProduktu.getProduktyMap().get(new Integer(produktId));
	}
	
	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public YaNooshGui() throws Exception {
		setForeground(Color.LIGHT_GRAY);
		setBackground(Color.LIGHT_GRAY);
		// Łązenie z baza danch
		LaczenieZBaza lb = new LaczenieZBaza();
		//lb.createConnection();
		lb.pobranieKlientow();
		lb.pobranieDostawcow();
		lb.pobranieProduktow2D();
		lb.pobranieGadzetow();
		lb.pobranieProduktow3D();
		lb.pobranieCen();
		lb.pobranieWycen();
		lb.pobranieOfert();	
		
		// USTAWIENIA OKNA
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 656);
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[105px][][][595px]", "[28px][503px]"));
		contentPane.setBackground(Color.gray);
		
		ImageIcon image = new ImageIcon("C:\\Users\\Lilia\\Desktop\\ph.png");
		JLabel jLabelIcon = new JLabel(image);
		contentPane.add(jLabelIcon,"cell 2 0,alignx center");
		
		layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, "cell 0 1 4 1,grow");
		layeredPane.setLayout(new CardLayout(0, 0));
		
		// PANEL 1
		panel1 = new JPanel();
		layeredPane.add(panel1, "name_116262821145600");
		panel1.setLayout(new MigLayout("", "[][][][][][227.00][][][][][][-28.00][352.00][][][][][][][][][][]", "[35px][][36.00][][][][15.00][][15.00][][15.00][][15.00][][15.00][][][]"));
		
		lblPanel = new JLabel("Wybierz działanie");
		lblPanel.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel1.add(lblPanel, "cell 12 1,alignx center");
		
		btnZakadanieSpesyfikacjiProduktu = new JButton("Zakładanie spesyfikacji produktu");
		btnZakadanieSpesyfikacjiProduktu.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnZakadanieSpesyfikacjiProduktu.setForeground(Color.BLACK);
		btnZakadanieSpesyfikacjiProduktu.setBackground(SystemColor.inactiveCaption);
		panel1.add(btnZakadanieSpesyfikacjiProduktu, "cell 12 5,growx");
		
		btnNewButton_5 = new JButton("Zakładanie nowego dostawcy");
		btnNewButton_5.setForeground(Color.BLACK);
		btnNewButton_5.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnNewButton_5.setBackground(SystemColor.inactiveCaption);
		panel1.add(btnNewButton_5, "cell 12 7,growx");
		
		btnRobienieWyceny = new JButton("Robienie wyceny");
		btnRobienieWyceny.setForeground(Color.BLACK);
		btnRobienieWyceny.setBackground(SystemColor.inactiveCaption);
		btnRobienieWyceny.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		panel1.add(btnRobienieWyceny, "cell 12 9,growx");
		
		btnZakadanieNowegoKlienta = new JButton("Zakładanie nowego klienta");
		btnZakadanieNowegoKlienta.setForeground(Color.BLACK);
		btnZakadanieNowegoKlienta.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnZakadanieNowegoKlienta.setBackground(SystemColor.inactiveCaption);
		panel1.add(btnZakadanieNowegoKlienta, "cell 12 11,growx");
		
		JButton btnNewButton_1 = new JButton("Sporządzanie oferty dla klienta");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		panel1.add(btnNewButton_1, "cell 12 13,growx");
		
		btnKlienci = new JButton("Klienci");
		btnKlienci.setForeground(Color.BLACK);
		btnKlienci.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		btnKlienci.setBackground(SystemColor.inactiveCaption);
		btnKlienci.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				swichPanels(panel6);
			}
		});
		panel1.add(btnKlienci, "cell 12 15,growx");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				swichPanels(panel2);
			}
		});
		// PANEL 2
		panel2 = new JPanel();
		layeredPane.add(panel2, "name_116316832825100");
		panel2.setLayout(new MigLayout("", "[][][][][][][726.00,grow][21.00][]", "[35px][][][][grow][grow][][][grow][][][][][][][][]"));
		
		//wybrany klient
		final int[] wybranyKlient = {0};
		
		lblPanel_1 = new JLabel("Wybierz klienta");
		lblPanel_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel2.add(lblPanel_1, "cell 4 1 3 1,alignx center");
		btnP_1 = new JButton("Wstecz");
		btnP_1.setBackground(SystemColor.inactiveCaption);
		btnP_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnP_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		
		scrollPane_1 = new JScrollPane();
		panel2.add(scrollPane_1, "cell 6 3 1 11,grow");
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		list.setListData(Klient.getKlienci().toArray());
		list.addListSelectionListener(new ListSelectionListener () {
			public void valueChanged(ListSelectionEvent arg0) {
				int idKlienta = ((Klient)list.getSelectedValue()).getId();
				wybranyKlient[0]=idKlienta;
				System.out.println(idKlienta+ "id");
				System.out.println(wybranyKlient[0]+ " id 0");
			}
		});
		
		komunikat = new JLabel("");
		komunikat.setForeground(Color.RED);
		komunikat.setFont(new Font("Century Gothic", Font.BOLD, 15));
		panel2.add(komunikat, "cell 6 14");
		panel2.add(btnP_1, "cell 1 16,alignx left,aligny top");
		
		btnNewButton_10 = new JButton("Anuluj");
		btnNewButton_10.setBackground(Color.LIGHT_GRAY);
		btnNewButton_10.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		panel2.add(btnNewButton_10, "cell 4 16,alignx left,aligny top");
		
		JButton btnNewButton_2 = new JButton("Dalej");
		btnNewButton_2.setBackground(SystemColor.inactiveCaption);
		btnNewButton_2.setFont(new Font("Century Gothic", Font.BOLD, 15));
		panel2.add(btnNewButton_2, "cell 8 16,alignx right,aligny top");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wybranyKlient[0] != 0){
					swichPanels(panel3);
				} else {
					komunikat.setText("WYBIERZ ELEMENT Z LISTY");
				}
			}
		});
		
		// PANEL 3
		panel3 = new JPanel();
		layeredPane.add(panel3, "name_116327873032500");
		panel3.setLayout(new MigLayout("", "[][62px][][][][][][][][][][grow][][grow][][][][][][][][][][]", "[35px][][grow][grow][][][][][][][][][][][][]"));
		
		btnNewButton_4 = new JButton("Wstecz");
		btnNewButton_4.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_4.setBackground(SystemColor.inactiveCaption);
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel2);
			}
		});
		
		final int[] wybranyProdukt = {0};
		final int[] dni = {0};
		final int[] mDostawy = {0};
		
		/// Wybrany produkt
		lblPanel_2 = new JLabel("Wybierz produkt z listy");
		lblPanel_2.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel3.add(lblPanel_2, "cell 11 0 3 1,alignx center,aligny center");
		
		scrollPane_2 = new JScrollPane();
		panel3.add(scrollPane_2, "cell 11 2 3 6,grow");
		list_1 = new JList();
		scrollPane_2.setViewportView(list_1);
		///
		list_1.setListData(Produkt2D.getProd().toArray());
		list_1.addListSelectionListener(new ListSelectionListener () {
			public void valueChanged(ListSelectionEvent arg0) {
				int idProduktu = ((SpecyfikacjaProduktu)list_1.getSelectedValue()).getId();
				wybranyProdukt[0]=idProduktu;
				System.out.println(idProduktu+ "id");
				System.out.println(wybranyProdukt[0]+ " id 0");
			}
		});
		
		komunkat = new JLabel("");
		komunkat.setForeground(Color.RED);
		komunkat.setFont(new Font("Century Gothic", Font.BOLD, 15));
		panel3.add(komunkat, "cell 11 8 3 1");
		
		lblPodajIlocDni = new JLabel("Podaj ilośc dni produkcji");
		lblPodajIlocDni.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		panel3.add(lblPodajIlocDni, "cell 11 9,alignx left");
		
		textField_dni = new JTextField();
		panel3.add(textField_dni, "cell 12 9 2 1,grow");
		textField_dni.setColumns(10);
		
		lblPodajIloMiejsc = new JLabel("Podaj ilość miejsc dostawy");
		lblPodajIloMiejsc.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		panel3.add(lblPodajIloMiejsc, "cell 11 11,alignx left");
		
		textField_miejsca = new JTextField();
		panel3.add(textField_miejsca, "cell 12 11 2 1,grow");
		textField_miejsca.setColumns(10);
		panel3.add(btnNewButton_4, "cell 1 15,alignx left,aligny top");
		
		btnAnuluj = new JButton("Anuluj");
		btnAnuluj.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnAnuluj.setBackground(Color.LIGHT_GRAY);
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		panel3.add(btnAnuluj, "cell 4 15,alignx left,aligny top");
		
		JButton btnNewButton_3 = new JButton("Dalej");
		btnNewButton_3.setBackground(SystemColor.inactiveCaption);
		btnNewButton_3.setFont(new Font("Century Gothic", Font.BOLD, 15));
		panel3.add(btnNewButton_3, "cell 22 15,alignx left,aligny top");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wybranyProdukt[0] !=0 && !textField_dni.getText().equals("") && !textField_miejsca.getText().equals("")){
					swichPanels(panel4);
					dni[0]=Integer.parseInt(textField_dni.getText());
					mDostawy[0]=Integer.parseInt(textField_miejsca.getText());
					list_2.setListData(SpecyfikacjaProduktu.getProduktyMap().get(new Integer(wybranyProdukt[0])).getWyceny().toArray());
					System.out.println(wybranyKlient[0]);
					System.out.println(wybranyProdukt[0]);
					System.out.println(dni[0]+ "dni");
					System.out.println(mDostawy[0]+ "miejsca dostawy");
					//System.out.println(SpecyfikacjaProduktu.getProduktyMap().get(new Integer(wybranyProdukt[0])).getWyceny());
				} else {
					komunkat.setText("WYBIERZ ELEMENT Z LISTY i UZUPEŁNIJ POLA DANYMI");
				}
			}
		});
		
		// PANEL 4
		panel4 = new JPanel();
		layeredPane.add(panel4, "name_116330770528600");
		panel4.setLayout(new MigLayout("", "[][][][][][][][][][][][][grow][][][][][][][][][][][][]", "[35px][][grow][][grow][][][][][][][][][][][][]"));
		
		final int[] wybranaWycena = {0};
		lblPanel_3 = new JLabel("Wbierz wycenę");
		lblPanel_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel4.add(lblPanel_3, "cell 12 0,alignx center,aligny bottom");
		
		btnP = new JButton("Wstecz");
		btnP.setBackground(SystemColor.inactiveCaption);
		btnP.setFont(new Font("Century Schoolbook", Font.BOLD, 15));
		btnP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel3);			
			}
		});
		
		scrollPane_3 = new JScrollPane();
		panel4.add(scrollPane_3, "cell 12 2 1 9,grow");
		
		list_2 = new JList();
		scrollPane_3.setViewportView(list_2);
		list_2.addListSelectionListener(new ListSelectionListener () {
			public void valueChanged(ListSelectionEvent arg0) {
				int idWyceny = ((Wycena)list_2.getSelectedValue()).getId();
				wybranaWycena[0]=idWyceny;
				System.out.println(idWyceny+ "id");
				System.out.println(wybranaWycena[0]+ " id wybrana wycena");
				System.out.println(wybranaWycena[0]+ " ggggg");
			}
		});
		
		kom = new JLabel("");
		kom.setForeground(Color.RED);
		kom.setFont(new Font("Century Gothic", Font.BOLD, 15));
		panel4.add(kom, "cell 12 11");
		panel4.add(btnP, "cell 0 16,alignx left,aligny top");

		btnNewButton_9 = new JButton("Anuluj");
		btnNewButton_9.setBackground(Color.LIGHT_GRAY);
		btnNewButton_9.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_9.setForeground(Color.BLACK);
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		panel4.add(btnNewButton_9, "cell 3 16,alignx left,aligny top");
		
		btnNewButton = new JButton("Dalej");
		btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wybranaWycena[0] != 0){
					swichPanels(panel5);
					tfProdukt.setText(szukajProduktu(wybranyProdukt[0]).toString());
					tfWycena.setText(szukajWycene(wybranaWycena[0]).toString());
					tfKlient.setText(szukajKlienta(wybranyKlient[0]).getNazwaFirmy());
				} else {
					kom.setText("WYBIERZ ELEMENT Z LISTY");
				}
			}
		});
		panel4.add(btnNewButton, "cell 24 16,alignx right,aligny top");
		
		// PANEL 5
		panel5 = new JPanel();
		layeredPane.add(panel5, "name_316170581226400");
		panel5.setLayout(new MigLayout("", "[][][][][][][][][][-92.00][264.00,grow][][][][][][][][][]", "[][][][][][][][grow][][grow][][grow][][][][][][][]"));
		
		lblNewLabel_1 = new JLabel("Podsumowanie");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel5.add(lblNewLabel_1, "cell 10 0,alignx center");
		
		lblOfertaDoKlienta = new JLabel("Oferta do klienta");
		lblOfertaDoKlienta.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel5.add(lblOfertaDoKlienta, "cell 10 1,alignx center");
		
		lblNewLabel_2 = new JLabel("Klient");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		panel5.add(lblNewLabel_2, "cell 3 7,alignx left");
		
		scrollPane_4 = new JScrollPane();
		panel5.add(scrollPane_4, "cell 6 7 13 1,growx,aligny center");
		
		//Klient
		tfKlient = new JTextField();
		tfKlient.setBackground(Color.WHITE);
		tfKlient.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		scrollPane_4.setViewportView(tfKlient);
		tfKlient.setEditable(false);
		tfKlient.setColumns(10);
		
		lblProdukt = new JLabel("Produkt");
		lblProdukt.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		panel5.add(lblProdukt, "cell 3 9,alignx left");
		
		scrollPane_5 = new JScrollPane();
		panel5.add(scrollPane_5, "cell 6 9 13 1,growx,aligny center");
		
		//Produkt
		tfProdukt = new JTextField();
		tfProdukt.setBackground(Color.WHITE);
		tfProdukt.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		scrollPane_5.setViewportView(tfProdukt);
		tfProdukt.setEditable(false);
		tfProdukt.setColumns(10);
		
		lblWycena = new JLabel("Wycena");
		lblWycena.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		panel5.add(lblWycena, "cell 3 11,alignx left");
		btnNewButton_6 = new JButton("Wstecz");
		btnNewButton_6.setBackground(SystemColor.inactiveCaption);
		btnNewButton_6.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel4);
			}
		});
		
		scrollPane_6 = new JScrollPane();
		panel5.add(scrollPane_6, "cell 6 11 13 1,growx,aligny center");
		
		//Wycena
		tfWycena = new JTextField();
		tfWycena.setBackground(Color.WHITE);
		tfWycena.setFont(new Font("Century Gothic", Font.PLAIN, 15));
		scrollPane_6.setViewportView(tfWycena);
		tfWycena.setEditable(false);
		tfWycena.setColumns(10);
		panel5.add(btnNewButton_6, "cell 0 18,alignx left,aligny bottom");
		
		btnNewButton_8 = new JButton("Anuluj");
		btnNewButton_8.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_8.setBackground(Color.LIGHT_GRAY);
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		panel5.add(btnNewButton_8, "cell 3 18,alignx left,aligny top");
		
		btnNewButton_7 = new JButton("Utwórz ofertę");
		btnNewButton_7.setFont(new Font("Century Gothic", Font.BOLD, 15));
		btnNewButton_7.setBackground(SystemColor.inactiveCaption);
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(szukajKlienta(wybranyKlient[0])+" klient");
				OfertaDoKlienta ofdk = new OfertaDoKlienta(dni[0],mDostawy[0],szukajKlienta(wybranyKlient[0]),szukajWycene(wybranaWycena[0]));
				ofdk.dodajOferteDoBazy();
				PodwierdzenieUtworzeniaWyceny puw = new PodwierdzenieUtworzeniaWyceny();
				puw.setVisible(true);
				dni[0]=0;
				mDostawy[0]=0;
				wybranyKlient[0]=0;
				wybranaWycena[0]=0;
				wybranyProdukt[0]=0;
				textField_dni.setText("");
				textField_miejsca.setText("");
				swichPanels(panel6);
				System.out.println(ofdk);
			}
		});
		panel5.add(btnNewButton_7, "cell 19 18,alignx right,aligny bottom");
		
		// PANEL 6
		panel6 = new JPanel();
		layeredPane.add(panel6, "name_405403054434700");
		panel6.setLayout(new MigLayout("fillx", "[][][]", "[][][][58.00][][][][][][][][85.00][][][][][]"));
		
		OfertyDoKlientow wdk = new OfertyDoKlientow();
		
		lblNewLabel_3 = new JLabel("Klienci");
		lblNewLabel_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		panel6.add(lblNewLabel_3, "cell 1 2,alignx center,aligny center");
		
		scrollPane = new JScrollPane();
		panel6.add(scrollPane, "cell 1 5 1 6,alignx center");
		list_3 = new JList();
		scrollPane.setViewportView(list_3);
		list_3.setBackground(Color.WHITE);
		list_3.setListData(Klient.getKlienci().toArray());
		list_3.addListSelectionListener(new ListSelectionListener () {
			public void valueChanged(ListSelectionEvent arg0) {
				int idKlienta = ((Klient)list_3.getSelectedValue()).getId();
				JPanel contentPaneWDK;
				wdk.setBounds(100, 100, 1021, 857);
				contentPaneWDK = new JPanel();
				contentPaneWDK.setBorder(new EmptyBorder(5, 5, 5, 5));
				wdk.setContentPane(contentPaneWDK);
				contentPaneWDK.setLayout(null);
				
				JPanel panelWDK = new JPanel();
				panelWDK.setBounds(21, 69, 953, 696);
				contentPaneWDK.add(panelWDK);
				panelWDK.setLayout(new MigLayout("", "[]", "[]"));
				
				JScrollPane scrollPaneWDK = new JScrollPane();
				scrollPaneWDK.setBounds(0, 0, 953, 696);
				panelWDK.add(scrollPaneWDK);
				
				JList listWDK = new JList();
				listWDK.setListData(Klient.getKlienciMapa().get(new Integer(idKlienta)).getOferty().toArray());
				scrollPaneWDK.setViewportView(listWDK);
				
				JLabel lblOferty = new JLabel("Wszystkie oferty");
				lblOferty.setBounds(419, 22, 158, 26);
				contentPaneWDK.add(lblOferty);
				wdk.setVisible(true);
							
			}
		});
		
		btnNewButton_11 = new JButton("Strona Główna");
		btnNewButton_11.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton_11.setBackground(SystemColor.inactiveCaption);
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				swichPanels(panel1);
			}
		});
		panel6.add(btnNewButton_11, "cell 1 16,alignx center,aligny center");
		
		// ETYKIETY DO GŁÓWNEGO OKNA
		JLabel lblNewLabel = new JLabel("Ya-Noosh");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 22));
		lblNewLabel.setForeground(Color.white);
		contentPane.add(lblNewLabel, "cell 0 0,alignx left,growy");
		
		JLabel lblZalogowanyLiliiaBaluk = new JLabel("Zalogowany: Liliia Baluk");
		lblZalogowanyLiliiaBaluk.setForeground(Color.white);
		lblZalogowanyLiliiaBaluk.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 21));
		contentPane.add(lblZalogowanyLiliiaBaluk, "cell 3 0,alignx right,aligny center");
	}
}
