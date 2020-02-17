package Logika;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Status {Zaakceptowana, Odrzucona, Brak};

public class OfertaDoKlienta implements Serializable{
	
	private static int id;
	private int dniProdukcji;
	private int iloscMiejscDostawy;
	private double cenaDoKlienta;
	private Status status = Status.Brak;
	private Klient klient;
	private Wycena wycena;
	// ekstencja
	private static ArrayList<OfertaDoKlienta> oferty = new ArrayList<>();
	private static HashMap<Integer,OfertaDoKlienta> ofertyMapa = new HashMap<Integer,OfertaDoKlienta>();
	// powiązania
	private ArrayList<Pracownik> pracownik = new ArrayList<>();
	private ArrayList<Klient> klienci = new ArrayList<>();
	//konstruktor
	public OfertaDoKlienta (int dniProdukcji, int iloscMiejscDostawy, Klient klient, Wycena wycena) {
		this.dniProdukcji = dniProdukcji;
		this.iloscMiejscDostawy = iloscMiejscDostawy;
		this.klient = klient;
		klient.dodajOferte(this);
		if (klient != null) {
			this.cenaDoKlienta = ((klient.getMarza()/100)*wycena.getNajnizszaCena()+wycena.getNajnizszaCena());
		}
		this.wycena = wycena;
		oferty.add(this);
	}
		

	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		OfertaDoKlienta.id = id;
	}
	public int getDniProdukcji() {
		return dniProdukcji;
	}
	public void setDniProdukcji(int dniProdukcji) {
		this.dniProdukcji = dniProdukcji;
	}
	public int getIloscMiejscDostawy() {
		return iloscMiejscDostawy;
	}
	public void setIloscMiejscDostawy(int iloscMiejscDostawy) {
		this.iloscMiejscDostawy = iloscMiejscDostawy;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public double getCenaDoKlienta() {
		return cenaDoKlienta;
	}

	public void setCenaDoKlienta(Wycena wycena) {
		this.cenaDoKlienta = (klient.getMarza()/100)*wycena.getNajnizszaCena()+wycena.getNajnizszaCena();
	}
	
	public static HashMap<Integer, OfertaDoKlienta> getOfertyMapa() {
		return ofertyMapa;
	}

	public static void setOfertyMapa(Integer id, OfertaDoKlienta oferta) {
		OfertaDoKlienta.ofertyMapa.put(id, oferta);
	}
	
	////Zwracania kopii listy	
	public ArrayList<OfertaDoKlienta> getOferty() {
		ArrayList<OfertaDoKlienta> ofertyKopia = new ArrayList<>(oferty);
		return ofertyKopia;		
	}
	
	//// Trwałość
	public void dodajOferteDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO oferta(dni, ilosc_miejsc_dostawy, id_klienta, id_wyceny) VALUES (?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setInt(1,this.dniProdukcji);
	        statement.setInt(2,this.iloscMiejscDostawy);
	        statement.setInt(3, this.klient.getId());
	        statement.setInt(4, this.wycena.getId());
	        statement.executeUpdate();
	     }catch(SQLException e){

	     }
	}
	
	public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(oferty);
    }
 
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    	oferty = (ArrayList<OfertaDoKlienta>) stream.readObject();
    }
    
    //// Asocjacje
    public void dodajPracownika(Pracownik nowyPracownik) {
    	if(!pracownik.contains(nowyPracownik)) {
	    	pracownik.add(nowyPracownik);
	        ////informacja zwrotna
	    	nowyPracownik.dodajOferte(this);
    	}
    }
    
    public void usunPracownika(Pracownik pracownikDoSkasowania) {
        if(pracownik.contains(pracownikDoSkasowania)) {
            pracownik.remove(pracownikDoSkasowania);
            // Usun informacje zwrotna
            pracownikDoSkasowania.usunOferte(this);
        }
    }
    
    public void dodajKlienta(Klient nowyKlient) {
    	if(this.klient == null) {
	    	this.klient = nowyKlient;                  
	    	double najnizszaCena = this.wycena.getNajnizszaCena();
	    	this.cenaDoKlienta = ((klient.getMarza()/100)*najnizszaCena+najnizszaCena);
	        ////informacja zwrotna
	    	nowyKlient.dodajOferte(this);
    	}
    }
    
    public void usunKlienta(Klient klientDoSkasowania) {
        if(this.klient.equals(klientDoSkasowania)) {
            klienci.remove(klientDoSkasowania);
            // Usun informacje zwrotna
            klientDoSkasowania.usunOferte(this);
        }
    }
    
    
	@Override
	public String toString() {
		return "OfertaDoKlienta [dniProdukcji=" + dniProdukcji
				+ ", iloscMiejscDostawy=" + iloscMiejscDostawy
				+ ", cenaDoKlienta=" + cenaDoKlienta + ", status=" + status
				+ ", klient=" + klient + ", wycena=" + wycena + ", pracownik="
				+ pracownik + ", klienci=" + klienci + "]";
	}
}

