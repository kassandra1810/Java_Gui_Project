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

public class Produkt3D extends SpecyfikacjaProduktu implements Serializable {
	private int id;
	private double szerokosc;
	private double wysokosc;
	private double glebokosc;
	
	// wszystkie obiekty
	private static ArrayList<Produkt3D> produkty3d = new ArrayList<>();
	private static HashMap<Integer,Produkt3D> produkty3DMapa = new HashMap<Integer,Produkt3D>();

	public Produkt3D(String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp, double szerokosc, double wysokosc, double glebokosc) {
		super(typProduktu, typPapieru, zadruk, iloscKolorow, lakier,powtarzalnosc, prototyp);
		if (szerokosc >0){
			this.szerokosc = szerokosc;
		}
		if (wysokosc >0){
			this.wysokosc = wysokosc;
		}
		if (glebokosc >0){
			this.setGlebokosc(glebokosc);
		}
		produkty3d.add(this);
	}
	
	public Produkt3D(String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, int poprzedniaIlosc, double szerokosc, double wysokosc, double glebokosc) {
		super(typProduktu, typPapieru, zadruk, iloscKolorow, lakier,powtarzalnosc, poprzedniaIlosc);
		if (szerokosc >0){
			this.szerokosc = szerokosc;
		}
		if (wysokosc >0){
			this.wysokosc = wysokosc;
		}
		if (glebokosc >0){
			this.setGlebokosc(glebokosc);
		}
		produkty3d.add(this);
	}

	public double getSzerokosc() {
		return szerokosc;
	}

	public void setSzerokosc(double szerokosc) {
		this.szerokosc = szerokosc;
	}

	public double getWysokosc() {
		return wysokosc;
	}

	public void setWysokosc(double wysokosc) {
		this.wysokosc = wysokosc;
	}

	public static ArrayList<Produkt3D> getProdukty() {
		ArrayList<Produkt3D> produkty3dKopia = new ArrayList<>(produkty3d);
		return produkty3dKopia;
	}

	public double getGlebokosc() {
		return glebokosc;
	}

	public void setGlebokosc(double glebokosc) {
		if (glebokosc >0){
			this.glebokosc = glebokosc;
		}
	}
	
	public static void setKlienciMapa(Integer id,Produkt3D produkt) {
		Produkt3D.produkty3DMapa.put(id, produkt);
	}
	
	public static HashMap<Integer, Produkt3D> getKlienciMapa() {
		HashMap<Integer, Produkt3D> produkryKopia = new HashMap<Integer, Produkt3D>(produkty3DMapa);
		return produkryKopia;
	}
	//Trwałość
		public void dodajProdukt3dDoBazy(){		
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO produkty(typ_produktu, typ_papieru, zadruk, ilosc_kolorow, lakier, powtarzalnosc, wysokosc, szerokosc, glebokosc, typ_specki) VALUES (?,?,?,?,?,?,?,?,?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setString(1,this.getTypProduktu());
		        statement.setString(2,this.getTypPapieru());
		        statement.setBoolean(3, this.isZadtuk());
		        statement.setInt(4,this.getIloscKolorow());
		        statement.setBoolean(5, this.isLakier());
		        statement.setBoolean(6, false);
		        statement.setDouble(7, this.getWysokosc());
		        statement.setDouble(8, this.getSzerokosc());
		        statement.setDouble(9, this.getGlebokosc());
		        statement.setString(10,"Produkt3D");
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
}
