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
import java.sql.Date;
import java.util.HashMap;

public class Dostawca extends Osoba implements Serializable{
	private int id;
	private String nazwaFirmy;
	private Date poczatekWspolpracy;
	
	private static ArrayList<Dostawca> dostawcy = new ArrayList<>();
	private static HashMap<Integer,Dostawca> dostawcyMapa = new HashMap<Integer,Dostawca>();	
	
	private ArrayList<CenaDostawcy> cena = new ArrayList<>();
	
	public Dostawca(String imie, String nazwisko, String telefon, String nazwaFirmy, Date poczatekWspolpracy, Adres adres) {
		super(imie, nazwisko, telefon, adres);
		if(nazwaFirmy != " "&& nazwaFirmy != null){
			this.nazwaFirmy = nazwaFirmy;
		}
		if(poczatekWspolpracy != null) {
			this.poczatekWspolpracy = poczatekWspolpracy;
		}
		dostawcy.add(this);
	}

	public String getNazwaFirmy() {
		return nazwaFirmy;
	}

	public void setNazwaFirmy(String nazwaFirmy) {
		if (nazwaFirmy != null && nazwaFirmy != " "){
			this.nazwaFirmy = nazwaFirmy;
		}
	}
	
	public Date getPoczatekWspolpracy() {
			return poczatekWspolpracy;
	}

	public void setPoczatekWspolpracy(Date poczatekWspolpracy) {
		if (poczatekWspolpracy != null) {
			this.poczatekWspolpracy = poczatekWspolpracy;
		}
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static HashMap<Integer, Dostawca> getDostawcyMapa() {
		return dostawcyMapa;
	}

	public static void setDostawcyMapa(Integer id, Dostawca dostawca) {
		Dostawca.dostawcyMapa.put(id, dostawca);
	}

	public static ArrayList<Dostawca> getDostawcy() {
		ArrayList<Dostawca> dostawcyKopia = new ArrayList<>(dostawcy);
		return dostawcyKopia;		
	}
	
	public static void setDostawcy(Dostawca dostawca) {
		Dostawca.dostawcy.add(dostawca);
			
	}
	
	public static void writeExtents(ObjectOutputStream stream) throws IOException {
        stream.writeObject(dostawcy);
    }
 
    public static void readExtents(ObjectInputStream stream) throws IOException, ClassNotFoundException {
    	dostawcy = (ArrayList<Dostawca>) stream.readObject();
    }
    
	//Trwałość    
	public void dodajDostawceDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO osoba(imie, nazwisko, telefon, id_adresu, typ_osoby, nazwa_firmy, poczatek_wspolpracy) VALUES (?,?,?,?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1,this.getImie());
	        statement.setString(2,this.getNazwisko());
	        statement.setString(3,this.getTelefon());
	        statement.setInt(4,this.getAdres().getId());
	        statement.setString(5,"dostawca");
	        statement.setString(6,this.nazwaFirmy);
	        statement.setDate(7, (java.sql.Date) this.poczatekWspolpracy);
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
	
 //// Asocjacje
    public void dodajCene(CenaDostawcy nowaCena) {
    	if(!cena.contains(nowaCena)) {
	    	cena.add(nowaCena);
	        //informacja zwrotna
	    	nowaCena.dodajDostawce(this);
    	}
    }
    
    public void usunCene(CenaDostawcy cenaDoSkasowania) {
        if(cena.contains(cenaDoSkasowania)) {
            cena.remove(cenaDoSkasowania);
            // Usun informacje zwrotna
            cenaDoSkasowania.usunDostawce(this);
        }
    }
    public static Dostawca znajdzDostawce(String name){
    	ArrayList<Dostawca> list = new ArrayList<>();
		list = Dostawca.getDostawcy();
		Dostawca znaleziony = null;
		for (int i=0; i<list.size(); i++) {
			if (list.get(i).getNazwaFirmy().equals(name)) {
				znaleziony = list.get(i);
			}
		}
		return znaleziony;
    }

	@Override
	public String toString() {
		return "Dostawca [id=" + id + ", nazwaFirmy=" + nazwaFirmy
				+ ", poczatekWspolpracy=" + poczatekWspolpracy + "]";
	}
    
    
}

