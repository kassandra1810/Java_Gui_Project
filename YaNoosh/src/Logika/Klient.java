package Logika;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Klient extends Osoba implements Serializable {
	
	//private static int ilosc = 0;
	private int id;
	private String nazwaFirmy;
	private double marza;
	
	// ekstencje
	private static ArrayList<Klient> klienci = new ArrayList<>();	
	private static HashMap<Integer,Klient> klienciMapa = new HashMap<Integer,Klient>();	
	
	// powiązania
	private ArrayList<OfertaDoKlienta> oferta = new ArrayList<>();
	private ArrayList<Zamowienie> zamowienia = new ArrayList<>();
	
	// konstruktor
	public Klient(String imie, String nazwisko, String telefon, String nazwaFirmy, double marza, Adres adres) {
		super(imie, nazwisko, telefon, adres);
		if(nazwaFirmy != " "&& nazwaFirmy != null){
			this.nazwaFirmy = nazwaFirmy;
		}
		if (marza >=0) {
			this.marza = marza;
		}
		klienci.add(this);
	}
	
	public ArrayList<Zamowienie> getZamowienie() {
		return zamowienia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static void setKlienci(Klient klient) {
		Klient.klienci.add(klient);
	}

	public static void setKlienciMapa(Integer id,Klient klient) {
		Klient.klienciMapa.put(id, klient);
	}
	
	// Zwraca wszystkie oferty dla poszczególnych klientów
	public ArrayList<OfertaDoKlienta> getOferty() {
		ArrayList<OfertaDoKlienta> kopia = new ArrayList<OfertaDoKlienta>(oferta);
		return kopia;
	}

	public String getNazwaFirmy() {
		return nazwaFirmy;
	}

	public void setNazwaFirmy(String nazwaFirmy) {
		if(nazwaFirmy != " "&& nazwaFirmy != null){
			this.nazwaFirmy = nazwaFirmy;
		}
	}

	public double getMarza() {
		return marza;
	}

	public void setMarza(double marza) {
		if (marza >=0) {
			this.marza = marza;
		}
	}
	public static HashMap<Integer, Klient> getKlienciMapa() {
		HashMap<Integer, Klient> klienciKopia = new HashMap<Integer, Klient>(klienciMapa);
		return klienciKopia;
	}
	
	public ArrayList<OfertaDoKlienta> getOfertay() {
		return oferta;
	}

	public void addOferta(OfertaDoKlienta oferta) {
		this.oferta.add(oferta);
	}

	public ArrayList<Zamowienie> getZamowienia() {
		return zamowienia;
	}

	public void addZamowienia(Zamowienie zamowienie) {
		this.zamowienia.add(zamowienie);
	}

	
	//// Zwracanie kopii listy
	public static ArrayList<Klient> getKlienci() {
		ArrayList<Klient> klienciKopia = new ArrayList<>(klienci);
		return klienciKopia;		
	}
	
	//Trwałość    
	public void dodajKlientaDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO osoba(imie, nazwisko, telefon, id_adresu, typ_osoby, nazwa_firmy, marza) VALUES (?,?,?,?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1,this.getImie());
	        statement.setString(2,this.getNazwisko());
	        statement.setString(3,this.getTelefon());
	        statement.setInt(4,this.getAdres().getId());
	        statement.setString(5,"klient");
	        statement.setString(6,this.nazwaFirmy);
	        statement.setDouble(7, this.marza);
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
    
    //// Asocjacje 
    public void dodajOferte(OfertaDoKlienta nowaOferta) {
    	if(!oferta.contains(nowaOferta)) {
	    	oferta.add(nowaOferta);
	        //informacja zwrotna
	    	nowaOferta.dodajKlienta(this);
    	}
    }
    
    public void usunOferte(OfertaDoKlienta ofertaDoSkasowania) {
        if(oferta.contains(ofertaDoSkasowania)) {
            oferta.remove(ofertaDoSkasowania);
            // Usun informacje zwrotna
            ofertaDoSkasowania.usunKlienta(this);
        }
    }
    
    public void dodajZamowienie(Zamowienie noweZamowienie) {
    	if(!zamowienia.contains(noweZamowienie)) {
	    	zamowienia.add(noweZamowienie);
	        //informacja zwrotna
	    	noweZamowienie.setKlient(this);
	    
    	}
    }
    
    public void deleteZamowienie(Zamowienie zamowienieoSkasowania){
    	if(zamowienia != null) {
            this.zamowienia=null;
    	}
    }
    
    public void usunZamowienie(Zamowienie zamowienieoSkasowania) {
        if(zamowienia.contains(zamowienieoSkasowania)) {
            zamowienia.remove(zamowienieoSkasowania);
            // Usun informacje zwrotna
            zamowienieoSkasowania.deleteKlient(this);
        }
    }

	@Override
	public String toString() {
		return "id= " + id + " " + nazwaFirmy + ", marza: "+ marza;
	}
	
	public static ArrayList <OfertaDoKlienta> pobranieOfert (Klient klient){
		ArrayList <OfertaDoKlienta> pobraneOferty = null;
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM oferta where id_klienta="+ klient.getId());
			while(rs.next()){
				OfertaDoKlienta oferta = new OfertaDoKlienta(
					rs.getInt("dni"),
					rs.getInt("ilosc_miejsc_dostawy"),
					Klient.getKlienciMapa().get(rs.getInt("id_klienta")),
					Wycena.getWycenyMapa().get(rs.getInt("id_wyceny"))
				);
				OfertaDoKlienta.setOfertyMapa(rs.getInt("id_oferty"), oferta);
				oferta.setId(rs.getInt("id_oferty"));
				pobraneOferty.add(oferta);
				//System.out.println(oferta);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}	
		return pobraneOferty;
	}

}

