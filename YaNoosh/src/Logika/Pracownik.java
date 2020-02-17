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

public class Pracownik extends Osoba implements Serializable{
	
	private String stanowisko;
	private static ArrayList<Pracownik> pracownicy = new ArrayList<>();
	private ArrayList<OfertaDoKlienta> oferta = new ArrayList<>();
	private ArrayList<Wycena> wycena = new ArrayList<>();
	
	private static HashMap<Integer,Pracownik> pracownicyMapa = new HashMap<Integer,Pracownik>();	

	public Pracownik(String imie, String nazwisko, String telefon, String stanowisko, Adres adres) {
		super(imie, nazwisko, telefon, adres);
		if (stanowisko != " " && stanowisko != null) {
			this.stanowisko = stanowisko;
		}
		pracownicy.add(this);
	}

	public String getStanowisko() {
		return stanowisko;
	}

	public void setStanowisko(String stanowisko) {
		if (stanowisko != " " && stanowisko != null) {
			this.stanowisko = stanowisko;
		}
	}
	public static ArrayList<Pracownik> getPracownicy() {
		ArrayList<Pracownik> pracKopia = new ArrayList<>(pracownicy);
		return pracKopia;		
	}
	
	public static void setpracownicyMapa(Integer id,Pracownik pracownik) {
		Pracownik.pracownicyMapa.put(id, pracownik);
	}
	
	public static HashMap<Integer, Pracownik> getPracownicyMapa() {
		HashMap<Integer, Pracownik> pracownicyKopia = new HashMap<Integer, Pracownik>(pracownicyMapa);
		return pracownicyKopia;
	}
	
	//Trwałość    
		public void dodajPracownikaDoBazy(){		
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO osoba(imie, nazwisko, telefon, id_adresu, typ_osoby, stanowisko) VALUES (?,?,?,?,?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setString(1,this.getImie());
		        statement.setString(2,this.getNazwisko());
		        statement.setString(3,this.getTelefon());
		        statement.setInt(4,this.getAdres().getId());
		        statement.setString(5,"pracownik");
		        statement.setString(6,this.stanowisko);
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
 
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        pracownicy = (ArrayList<Pracownik>) stream.readObject();
    }
    
    //////Asocjacje
    public void dodajOferte(OfertaDoKlienta nowaOferta) {
    	if(!oferta.contains(nowaOferta)) {
	    	oferta.add(nowaOferta);
	        //informacja zwrotna
	    	nowaOferta.dodajPracownika(this);
    	}
    }
    
    public void usunOferte(OfertaDoKlienta ofertaDoSkasowania) {
        if(oferta.contains(ofertaDoSkasowania)) {
            oferta.remove(ofertaDoSkasowania);
 
            // Usun informacje zwrotna
            ofertaDoSkasowania.usunPracownika(this);
        }
    }
    
    public void dodajWycene(Wycena nowaWycena) {
    	if(!wycena.contains(nowaWycena)) {
	    	wycena.add(nowaWycena);
	        //informacja zwrotna
	    	nowaWycena.dodajPracownika(this);
    	}
    }
    
    public void usunWycene(Wycena wycenaDoSkasowania) {
        if(wycena.contains(wycenaDoSkasowania)) {
            wycena.remove(wycenaDoSkasowania);
 
            // Usun informacje zwrotna
            wycenaDoSkasowania.usunPracownika(this);
        }
    } 
}

