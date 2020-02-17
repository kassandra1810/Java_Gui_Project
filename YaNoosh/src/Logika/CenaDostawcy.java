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

//TODO Dodać usuwanie Ceny z wyceny

public class CenaDostawcy implements Serializable {
	
	private int id;
	private double cena;
	private Dostawca dostawca;
	private String opis;
	
	private static ArrayList<CenaDostawcy> cenaDostawcy = new ArrayList<>();
	private static HashMap<Integer,CenaDostawcy> cenaDostawcyMapa = new HashMap<Integer,CenaDostawcy>();
	
	private ArrayList<Dostawca> dostawcy = new ArrayList<>();
	
	public CenaDostawcy (double cena, Dostawca dostawca, String opis) {
		if (cena > 0){
			this.cena = cena;
		}
		if (dostawca != null) {
			this.dostawca = dostawca;
		}
		if (opis != null) {
			this.opis = opis;
		}

		cenaDostawcy.add(this);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public double getCena() {
		return cena;
	}
	public void setCena(double cena) {
		if(cena >0){
			this.cena = cena;
		}
	}
	public Dostawca getDostawca() {
		return dostawca;
	}
	public void setDostawca(Dostawca dostawca) {
		if(dostawca != null) {
			this.dostawca = dostawca;
		}
	}
	public static HashMap<Integer, CenaDostawcy> getCenaDostawcyMapa() {
		HashMap<Integer, CenaDostawcy> kopia = new HashMap<Integer, CenaDostawcy>(cenaDostawcyMapa);
		return kopia;
	}

	public static void setCenaDostawcyMapa(Integer id, CenaDostawcy cenaDostawcy) {
		CenaDostawcy.cenaDostawcyMapa.put(id, cenaDostawcy);
	}

	//// Zwracanie kopii listy	
	public static ArrayList<CenaDostawcy> getCenaDostawcy() {
		ArrayList<CenaDostawcy> cenaDostawcyKopia = new ArrayList<>(cenaDostawcy);
		return cenaDostawcyKopia;		
	}
	
	//Trwałość
		public void dodajCeneDoBazy(){		
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO cenydostawcow(cena, id_dostawcy, opis) VALUES (?,?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setDouble(1,this.cena);
		        statement.setInt(2,this.dostawca.getId());
		        statement.setString(3,this.opis);
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
    
    //// Asocjacje
    public void dodajDostawce(Dostawca nowyDostawca) {
    	if(!dostawcy.contains(nowyDostawca)) {
	    	dostawcy.add(nowyDostawca);
	        //informacja zwrotna
	    	nowyDostawca.dodajCene(this);
    	}
    }
    
    public void usunDostawce(Dostawca dostawcaDoSkasowania) {
        if(dostawcy.contains(dostawcaDoSkasowania)) {
            dostawcy.remove(dostawcaDoSkasowania);
            // Usun informacje zwrotna
            dostawcaDoSkasowania.usunCene(this);
        }
    }
    
    //// Kompozycja
    
    public static CenaDostawcy utworzCene(Wycena wycena, Double cena, Dostawca dostawca, String opis) throws Exception {
        if(wycena == null) {
            throw new Exception("Wycena nie istnieje!");
        }
        // Utwocz nowa czesc
        CenaDostawcy cenad = new CenaDostawcy(cena, dostawca, opis);
        // Dodaj do calosci
        wycena.dodajCene(cenad);
        return cenad;
    }

	@Override
	public String toString() {
		return "CenaDostawcy [cena=" + this.id +" "+ cena + ", dostawca=" + dostawca
				+ ", opis=" + opis + "]";
	}
    
    
}

