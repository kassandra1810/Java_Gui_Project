package Logika;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class Produkt2D extends SpecyfikacjaProduktu implements IProdukt2D, Serializable {
	private int id;
	private double szerokosc;
	private double wysokosc;
	private int iloscZadrukowanychStron;

	private static ArrayList<Produkt2D> produkty2d = new ArrayList<>();
	private static HashMap<Integer,Produkt2D> produkty2DMapa = new HashMap<Integer,Produkt2D>();
	
	public Produkt2D(String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp, double szerokosc, double wysokosc) {
		super(typProduktu, typPapieru, zadruk, iloscKolorow, lakier,powtarzalnosc, prototyp);

		if (szerokosc >0){
			this.szerokosc = szerokosc;
		}
		if (wysokosc >0){
			this.wysokosc = wysokosc;
		}
		produkty2d.add(this);
	}
	
	public Produkt2D(String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, int poprzedniaIlosc, double szerokosc, double wysokosc) {
		super(typProduktu, typPapieru, zadruk, iloscKolorow, lakier, powtarzalnosc, poprzedniaIlosc);
		if (szerokosc >0){
			this.szerokosc = szerokosc;
		}
		if (wysokosc >0){
			this.wysokosc = wysokosc;
		}
		produkty2d.add(this);
	}

	public double getSzerokosc() {
		return szerokosc;
	}

	public void setSzerokosc(double szerokosc) {
		this.szerokosc = szerokosc;
	}

	public int getId() {
		return super.getId();
	}

	public double getWysokosc() {
		return wysokosc;
	}

	public void setWysokosc(double wysokosc) {
		this.wysokosc = wysokosc;
	}

	public int getIloscZadrukowanychStron() {
		return iloscZadrukowanychStron;
	}

	public void setIloscZadrukowanychStron(int iloscZadrukowanychStron) {
		this.iloscZadrukowanychStron = iloscZadrukowanychStron;
	}
	public static void setKlienciMapa(Integer id,Produkt2D produkt) {
		Produkt2D.produkty2DMapa.put(id, produkt);
	}
	
	//// Zwracanie kopii listy
	public static HashMap<Integer, Produkt2D> getKlienciMapa() {
		HashMap<Integer, Produkt2D> produkryKopia = new HashMap<Integer, Produkt2D>(produkty2DMapa);
		return produkryKopia;
	}
	
	public static ArrayList<Produkt2D> getProdukty() {
		ArrayList<Produkt2D> produkty2dKopia = new ArrayList<>(produkty2d);
		return produkty2dKopia;
	}

	@Override
	public ArrayList<Produkt2D> znajdzProdukt(String typProduktu) {
		ArrayList<Produkt2D> produkty = new ArrayList<>();
		produkty = getProdukty();
		ArrayList<Produkt2D> pasujaceProdukty = new ArrayList<>();
		for (int i=0; i<produkty.size(); i++) {
			if (produkty.get(i).getTypProduktu().equals(typProduktu)) {
				pasujaceProdukty.add(produkty.get(i));
			}
		}
		return pasujaceProdukty;
	}

	public ArrayList<Produkt2D> znajdzProdukt2(boolean zadruk) {
		ArrayList<Produkt2D> produkty = new ArrayList<>();
		produkty = getProdukty();
		ArrayList<Produkt2D> pasujaceProdukty = new ArrayList<>();
		for (int i=0; i<produkty.size(); i++) {
			if (produkty.get(i).isZadtuk() == zadruk) {
				pasujaceProdukty.add(produkty.get(i));
			}
		}
		if (zadruk == true) {
			System.out.println("Zwracam produkty zadrukowane");
		} else {
			System.out.println("Zwracam produkty bez nadruku");
		}
		return pasujaceProdukty;
	}
	@Override
	public String toString() {
		
		return "Produkt: "+this.getId()+ "\n" + this.getTypProduktu() + "\n" + "Papier: " + this.getTypPapieru() + "\n" + "Zadruk: "+ this.isZadtuk() + "\n"+ "Ilość kolorów: " + this.getIloscKolorow() + "\n" +"Lakier: " + this.isLakier() + "\n"+ "Typ lakieru: " + this.getTypLakieru() + "\n";
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//Trwałość
	public void dodajProduktDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO produkty(typ_produktu, typ_papieru, zadruk, ilosc_kolorow, lakier, powtarzalnosc, wysokosc, szerokosc,typ_specki) VALUES (?,?,?,?,?,?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1,this.getTypProduktu());
	        statement.setString(2,this.getTypPapieru());
	        statement.setBoolean(3, this.isZadtuk());
	        statement.setInt(4,this.getIloscKolorow());
	        statement.setBoolean(5, this.isLakier());
	        statement.setBoolean(6, false);
	        statement.setDouble(7, this.getWysokosc());
	        statement.setDouble(8, this.getSzerokosc());
	        statement.setString(9,"Produkt2D");
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
}

