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
import java.util.Vector;

// TODO Dodać metodę usuń cenę dostawcy (usuń część)

public class Wycena implements Serializable {
	private static int ilosc;
	private int id;
	private CenaDostawcy cenaD1;
	private CenaDostawcy cenaD2;
	private CenaDostawcy cenaD3;
	private double najnizszaCena;
	private SpecyfikacjaProduktu specka;
	// 
	private static ArrayList<Wycena> wyceny = new ArrayList<>();
	private static HashMap<Integer,Wycena> wycenyMapa = new HashMap<Integer,Wycena>();	
	//
	private ArrayList<Pracownik> pracownik = new ArrayList<>();
	private Vector<CenaDostawcy> ceny = new Vector<CenaDostawcy>();
	private static ArrayList<CenaDostawcy> wszystkieCeny = new ArrayList<>();

	public Wycena (CenaDostawcy c1, CenaDostawcy c2, CenaDostawcy c3, SpecyfikacjaProduktu specka) throws Exception{
		if(c1 != null) {
			this.setCenaD1(c1);
		}
		if(c2 != null) {
			this.setCenaD2(c2);
		}
		if(c3 != null) {
			this.setCenaD3(c3);
		}
		this.setNajnizszaCena();
		this.specka = specka;
		specka.dodajWycene(this);
		ceny.setSize(3);
		wyceny.add(this);
		//wycenyMapa.put(new Integer(this.id), this);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CenaDostawcy getCenaD1() {
		return cenaD1;
	}

	public void setCenaD1(CenaDostawcy cenaD1) throws Exception {
		if(!this.ceny.contains(cenaD1)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieCeny.contains(cenaD1)) {
                throw new Exception("Ta cena jest już powiązana z tę wyceną!");
            } 
            this.ceny.add(cenaD1);
    		if(cenaD1 != null) {
    			this.cenaD1 = cenaD1;
    		}
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieCeny.add(cenaD1);
        }
	}

	public CenaDostawcy getCenaD2() {
		return cenaD2;
	}

	public void setCenaD2(CenaDostawcy cenaD2) throws Exception {
		if(!this.ceny.contains(cenaD2)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieCeny.contains(cenaD2)) {
                throw new Exception("Ta cena jest już powiązana z tę wyceną!");
            } 
            this.ceny.add(cenaD2);
    		if(cenaD2 != null) {
    			this.cenaD2 = cenaD2;
    		}
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieCeny.add(cenaD2);
		}
	}

	public CenaDostawcy getCenaD3() {
		return cenaD3;
	}

	public void setCenaD3(CenaDostawcy cenaD3) throws Exception {
		if(!this.ceny.contains(cenaD3)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieCeny.contains(cenaD3)) {
                throw new Exception("Ta cena jest już powiązana z tę wyceną!");
            } 
            this.ceny.add(cenaD3);
    		if(cenaD3 != null) {
    			this.cenaD3 = cenaD3;
    		}
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieCeny.add(cenaD3);
		}
	}

	public double getNajnizszaCena() {
		return najnizszaCena;
	}

	public void setNajnizszaCena() {
		double min = this.cenaD1.getCena();
		if (this.cenaD2.getCena() < this.cenaD1.getCena()) {
			min=this.cenaD2.getCena();
		} else if (this.cenaD3.getCena() < this.cenaD1.getCena()) {
			min = this.cenaD3.getCena();
		}
		this.najnizszaCena = min;
	}
	
	public SpecyfikacjaProduktu getSpecka() {
		return specka;
	}

	public void setSpecka(SpecyfikacjaProduktu specka) {
		this.specka = specka;
		specka.dodajWycene(this);
	}
	public static HashMap<Integer, Wycena> getWycenyMapa() {
		HashMap<Integer, Wycena> kopiaWycen = new HashMap<Integer, Wycena>(wycenyMapa);
		return kopiaWycen;
	}
	
	
	
	public Vector<CenaDostawcy> getCeny() {
		return ceny;
	}

	public void addCeny(CenaDostawcy cena) {
		this.ceny.add(cena);
	}

	public static void setWycenyMapa(Integer id, Wycena wycena) {
		Wycena.wycenyMapa.put(id, wycena);
	}

	//// kopia listy
	public static ArrayList<Wycena> getWyceny() {
		ArrayList<Wycena> wycenyKopia = new ArrayList<>(wyceny);
		return wycenyKopia;		
	}
	
	//Trwałość    
	public void dodajWyceneDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO wyceny(id_cena_d1, id_cena_d2, id_cena_d3, id_produktu) VALUES (?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setInt(1,this.cenaD1.getId());
	        statement.setInt(2,this.cenaD2.getId());
	        statement.setInt(3,this.cenaD3.getId());
	        statement.setInt(4,this.specka.getId());
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
    
    //// Asocjacje    
    public void dodajPracownika(Pracownik nowyPracownik) {
    	ceny.setSize(3);
    	if(!pracownik.contains(nowyPracownik)) {
	    	pracownik.add(nowyPracownik);
	        // informacja zwrotna
	    	nowyPracownik.dodajWycene(this);
    	}
    }
    
    public void usunPracownika(Pracownik pracownikDoSkasowania) {
        if(pracownik.contains(pracownikDoSkasowania)) {
            pracownik.remove(pracownikDoSkasowania);
            // Usun informacje zwrotna
            pracownikDoSkasowania.usunWycene(this);
        }
    }
	
    //// Kompozycja    
	public void dodajCene(CenaDostawcy cena) throws Exception {
        if(!this.ceny.contains(cena)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieCeny.contains(cena)) {
                throw new Exception("Ta cena jest już powiązana z wyceną!");
            } 
            this.ceny.add(cena);
            
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieCeny.add(cena);
        }
	}

	public static void usunWycene(Wycena wycenaDoSkasowania) {
        if(wyceny.contains(wycenaDoSkasowania)) {
            wyceny.remove(wycenaDoSkasowania); 
            // Usuwa tomy prypisane do tej ksiązki
            for(int i=0;i<wycenaDoSkasowania.ceny.size();i++) {
            	if(wszystkieCeny.contains(wycenaDoSkasowania.ceny.get(i))) {
                    wszystkieCeny.remove(wycenaDoSkasowania.ceny.get(i));
                }
            }
        }
        wycenaDoSkasowania.ceny.clear();
	}
	
	@Override
	public String toString() {
		return "Wycena [cena D1: " +cenaD1.getCena()+ ", cena D2: " + cenaD2.getCena() + ", cena D3:"
				+ cenaD3.getCena() + ", najnizsza cena: " + najnizszaCena + ", specka: "+ specka.getTypProduktu()+ "\n";
	}
}

