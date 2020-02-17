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

public class Dostawa  {
	//extends Adres implements Serializable
	private int id;
	private String numerAwizo;
	private ArrayList<Adres> adresyDostawy = new ArrayList();
	
	private static ArrayList<Dostawa> dostawy = new ArrayList<>();
	private static HashMap<Integer,Dostawa> dostawyMapa = new HashMap<Integer,Dostawa>();
	
	public Dostawa(String numerAwizo) {
		//super(kraj, miasto, ulica, numerDomu, kodPocztowy);
		
		if (numerAwizo != null && numerAwizo != " "){
			this.numerAwizo=numerAwizo;
		}
		dostawy.add(this);
	}
	public Dostawa() {		
		numerAwizo="";
		dostawy.add(this);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumerAwizo() {
		return numerAwizo;
	}

	public void setNumerAwizo(String numerAwizo) {
		if (numerAwizo != null && numerAwizo != " "){
			this.numerAwizo=numerAwizo;
		}
	}
	
	public ArrayList<Dostawa> getDostawy() {
		ArrayList<Dostawa> dostawyKopia = new ArrayList<>(dostawy);
		return dostawyKopia;		
	}
    public ArrayList<Adres> getAdresyDostawy() {
    	ArrayList<Adres> adresyKopia = new ArrayList<>(adresyDostawy);
		return adresyKopia;
	}
	public void addAdresyDostawy(Adres adres) {
		this.adresyDostawy.add(adres);
	}
	public static HashMap<Integer, Dostawa> getDostawyMapa() {
    	HashMap<Integer, Dostawa> kopia = new HashMap<Integer, Dostawa>(dostawyMapa);
		return kopia;
	}
	public static void setDostawyMapa(Integer id, Dostawa dostawa) {
		Dostawa.dostawyMapa.put(id, dostawa);
	}
	//Trwałość
	public void dodajDostaweDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO dostawy(numer_awizo ) VALUES (?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        if (this.numerAwizo == null){
	        	statement.setString(1,null);
	        } else {
	        statement.setString(1,this.numerAwizo);
	        }
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
	
    //Asocjacja wiele do wiele z adresem
	public void dodajAdresDoDostawy(Adres adres) {
		if (adres != null){
			this.adresyDostawy.add(adres);
			adres.addDostawa(this);
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO adres_dostawa(id_adres, id_dostawa) VALUES (?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,adres.getId());
		        statement.setInt(2,this.getId());
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
	}
	
	public void usunAdres(Adres adresDoSkasowania){
		if(adresyDostawy.contains(adresDoSkasowania)) {
			dostawy.remove(adresDoSkasowania);
			adresDoSkasowania.usunDostawe(this);
        }
	}

	//Kompozycja  
    public static Dostawa utworzDostawe(Zamowienie zamowienie, String kraj, String miasto, String ulica, int numerDomu, String kodPocztowy, String numerAwizo) throws Exception {
        if(zamowienie == null) {
            throw new Exception("Zamówienie nie istnieje!");
        }
        // Utwocz nowa czesc
        Dostawa dostawa = new Dostawa(numerAwizo);
        // Dodaj do calosci
        zamowienie.dodajDostawe(dostawa);
        return dostawa;
    }
	@Override
	public String toString() {
		return "Dostawa "+ "numerAwizo=" + numerAwizo
				+ ", adresyDostawy=" + adresyDostawy + "]";
	}
    
    

}

