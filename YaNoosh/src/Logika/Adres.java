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

public class Adres implements Serializable {
	
	private int id;
	private String kraj;
	private String miasto;
	private String ulica;
	private int numerDomu;
	private String kodPocztowy;
	//ekstencje
	private static ArrayList<Adres> adresy = new ArrayList<>();
	private static HashMap<Integer,Adres> adresyMapa = new HashMap<Integer,Adres>();
	//piwiązania
	private Osoba osoba;
	private ArrayList<Dostawa> dostawy = new ArrayList<>();

	//construktor
	public Adres (String kraj, String miasto, String ulica, int numerDomu, String kodPocztowy) {
		if (kraj != null && kraj != " "){
			this.kraj = kraj;
		}
		if (miasto != null && miasto != " "){
			this.miasto = miasto;
		}
		if (ulica != null && ulica != " "){
			this.ulica = ulica;
		}
		if (numerDomu >0){
			this.numerDomu = numerDomu;
		}
		if (kodPocztowy != null && kodPocztowy != " "){
			this.kodPocztowy = kodPocztowy;
		}
		adresy.add(this);
	}
	
	//gettery settery
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getKraj() {
		return kraj;
	}
	public void setKraj(String kraj) {
		if (kraj != null && kraj != " "){
			this.kraj = kraj;
		}
	}
	public String getMiasto() {
		return miasto;
	}
	public void setMiasto(String miasto) {
		if (miasto != null && miasto != " "){
			this.miasto = miasto;
		}
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		if (ulica != null && ulica != " "){
			this.ulica = ulica;
		}
	}
	public int getNumerDomu() {
		return numerDomu;
	}
	public void setNumerDomu(int numerDomu) {
		if (numerDomu >0){
			this.numerDomu = numerDomu;
		}
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		if (kodPocztowy != null && kodPocztowy != " "){
			this.kodPocztowy = kodPocztowy;
		}
	}
	public static void setAdresyMapa(Integer id, Adres adres) {
		Adres.adresyMapa.put(id, adres);
	}
	
	//zwracanie kopii list
	public static ArrayList<Adres> getAdresy() {
		ArrayList<Adres> adresyKopia = new ArrayList<>(adresy);
		return adresyKopia;		
	}
	public ArrayList<Dostawa> getDostawy() {
		ArrayList<Dostawa> dostawyKopia = new ArrayList<>(dostawy);
		return dostawyKopia;
	}
	
	public void addDostawa(Dostawa dostawa) {
		this.dostawy.add(dostawa);
	}

	public static HashMap<Integer, Adres> getAdresyMapa() {
		HashMap<Integer, Adres> kopia = new HashMap <Integer,Adres>(adresyMapa);
		return kopia;
	}
	
    //Trwałość
	public void dodajAdresDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO adresy(kraj, miasto, ulica, numer_domu, kod_pocztowy, id_osoby) VALUES (?,?,?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1,this.kraj);
	        statement.setString(2,this.miasto);
	        statement.setString(3,this.ulica);
	        statement.setInt(4,this.numerDomu);
	        statement.setString(5,this.kodPocztowy);
	        if (osoba == null){
	        	statement.setInt(6,0);
	        } else {
	        statement.setInt(6,this.osoba.getId());
	        }
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
    
	// Asozjacja wiele do wiele z dostawą
	public void dodajDostaweDoAdresu(Dostawa dostawa) {
		if(dostawa != null) {
			this.dostawy.add(dostawa);
			dostawa.addAdresyDostawy(this);
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO adres_dostawa(id_adres, id_dostawa) VALUES (?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,this.getId());
		        statement.setInt(2,dostawa.getId());
		        statement.executeUpdate();
		        System.out.println("relacja zapisana w bazie");
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
	}
	
	
	public void usunDostawe(Dostawa dostawaDoSkasowania){
		if(dostawy.contains(dostawaDoSkasowania)) {
			dostawy.remove(dostawaDoSkasowania);
            // Usun informacje zwrotna
            dostawaDoSkasowania.usunAdres(this);
            try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "DELETE FROM adres_dostawa WHERE id_adres=? and id_dostawa=?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,this.id);
		        statement.setInt(2, dostawaDoSkasowania.getId());
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
        }
	}
    
    // Asocjacje jeden do jeden
	public void dodajOsobe(Osoba nowaOsoba) {
    	if(nowaOsoba != null) {
    		this.osoba = nowaOsoba;
	        //informacja zwrotna
    		nowaOsoba.dodajAdres(this);
    		try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "UPDATE adresy SET id_osoby=? WHERE id_adres =?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1, nowaOsoba.getId());
		        statement.setInt(2, this.id);
		        //statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
    	}
    }   
    public void usunOsobe(Osoba osobaDoSkasowania) {
        if(osoba.equals(osobaDoSkasowania)) {
            osoba = null;
            // Usun informacje zwrotna
            osobaDoSkasowania.usunAdres(this);
            try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "UPDATE adresy SET id_osoby=null WHERE id_adres =?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,this.id);
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
        }
    }

	@Override
	public String toString() {
		return "Adres [kraj=" + kraj + ", miasto=" + miasto + ", ulica="
				+ ulica + ", numerDomu=" + numerDomu + ", kodPocztowy="
				+ kodPocztowy + "]";
	}
    
}

