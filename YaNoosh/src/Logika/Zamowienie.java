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
import java.util.Vector;

public class Zamowienie implements Serializable {
	
	//private static int numerZamowienia;
	private int id;
	private Klient klient;
	//private Dostawca dostawca;
	private int ilosc;
	private double cenaNetto;
	private double cenaBrutto;
	private Date dataDostawy;
	
	private static ArrayList<Zamowienie> zamowienia = new ArrayList<>();
	private static HashMap<Integer,Zamowienie> zamowieniaMapa = new HashMap<Integer,Zamowienie>();	
	
	private Vector<Dostawa> dostawy = new Vector<>();
	private static ArrayList<Dostawa> wszystkieDostawy = new ArrayList<>();
	
	private Vector<SpecyfikacjaProduktu> specyfikacje = new Vector<>();
	private static ArrayList<SpecyfikacjaProduktu> wszystkieSpecyfikacje = new ArrayList<>();
	
	public Zamowienie (int ilosc, double cenaNetto, Date dataDostawy){
		this.dodajKlienta(klient);
		if (ilosc >0){
			this.setIlosc(ilosc);
		}
		if(cenaNetto >0){
			this.cenaNetto = cenaNetto;
		}
		if(dataDostawy != null){
			this.setDataDostawy(dataDostawy);
		}
		this.cenaBrutto =((23.00/100.00)*this.cenaNetto+this.cenaNetto);

		zamowienia.add(this);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Klient getKlient() {
		return klient;
	}

	public void setKlient(Klient klient) {
		if(klient != null){
			this.klient = klient;
		}
	}
	public int getIlosc() {
		return ilosc;
	}

	public void setIlosc(int ilosc) {
		if(ilosc > 0){
			this.ilosc = ilosc;
		}	
	}

	public double getCenaBrutto() {
		return cenaBrutto;
	}

	public Date getDataDostawy() {
		return dataDostawy;
	}

	public void setDataDostawy(java.sql.Date dataDostawy) {
		if(dataDostawy != null){
			this.dataDostawy = dataDostawy;
		}
	}
	
	
	public static HashMap<Integer, Zamowienie> getZamowieniaMapa() {
		HashMap<Integer, Zamowienie> kopia = new HashMap<Integer, Zamowienie>(zamowieniaMapa);
		return kopia;
	}

	public static void setZamowieniaMapa(Integer id, Zamowienie zamowienie) {
		Zamowienie.zamowieniaMapa.put(id, zamowienie);
	}

	////Zwracania kopii listy
	public ArrayList<Zamowienie> getZamowienia() {
		ArrayList<Zamowienie> zamowieniaKopia = new ArrayList<>(zamowienia);
		return zamowieniaKopia;		
	}
	
	//Trwałość    
		public void dodajZamówienieDoBazy(){		
			try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO zamowienia(id_klienta, ilosc, cena_netto, data_dostawy) VALUES (?,?,?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,this.klient.getId());
		        statement.setInt(2,this.ilosc);
		        statement.setDouble(3,this.cenaNetto);
		        statement.setDate(4,this.dataDostawy);
		        statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
		}
    
	//// Asocjacje 
    public void dodajKlienta(Klient nowyKlient) {
    	if(nowyKlient != null) {
	    	this.klient = nowyKlient;
	        ////informacja zwrotna
	    	nowyKlient.addZamowienia(this);
	    	try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "INSERT INTO zamowienia_klienci(id_klienta, id_zamowienia) VALUES (?,?)";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1,nowyKlient.getId());
		        statement.setInt(2,this.getId());
		        statement.executeUpdate();
		        System.out.println("relacja zapisana w bazie");
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
    	}
    }
    
    public void deleteKlient(Klient klientDoSkasowania){
    	if(klientDoSkasowania != null) {
            this.klient=null;
    	}
    }
    
    public void usunKlienta(Klient klientDoSkasowania) {
    	if(klientDoSkasowania != null) {
            this.klient=null;
            // Usun informacje zwrotna
            klientDoSkasowania.deleteZamowienie(this);
            try{
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
		        String sql = "DELETE FROM zamowienia_klienci WHERE id_klienta=? and id_zamowienia=?";
		        PreparedStatement statement = con.prepareStatement(sql);
		        statement.setInt(1, klientDoSkasowania.getId());
		        statement.setInt(2,this.id);
		        //statement.executeUpdate();
		     }catch(SQLException e){
		    	 e.printStackTrace();
		     }
        }
    }
    

    //// Kompozycja Dostawa  
    public void dodajDostawe(Dostawa dostawa) throws Exception {
        if(!dostawy.contains(dostawa)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieDostawy.contains(dostawa)) {
                throw new Exception("Ta dostawa jest już powiązana z zamówieniem!");
            } 
            dostawy.add(dostawa);
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieDostawy.add(dostawa);
        }
	}

	public static void usunZamowienieDost(Zamowienie zamowienieDoSkasowania) {
        if(zamowienia.contains(zamowienieDoSkasowania)) {
            zamowienia.remove(zamowienieDoSkasowania); 
            // Usuwa tomy prypisane do tej ksiązki
            for(int i=0;i<zamowienieDoSkasowania.dostawy.size();i++) {
            	if(wszystkieDostawy.contains(zamowienieDoSkasowania.dostawy.get(i))) {
                    wszystkieDostawy.remove(zamowienieDoSkasowania.dostawy.get(i));
                }
            }
        }
        zamowienieDoSkasowania.dostawy.clear();
	}
	
	//// Kompozycja Specyfikacja Produktu
	public void dodajSpecyfikacje(SpecyfikacjaProduktu specyfikacja) throws Exception {
        if(!specyfikacje.contains(specyfikacja)) {
            //czy ta czesc nie zostala dodana do jakiejs calosci
            if(wszystkieSpecyfikacje.contains(specyfikacja)) {
                throw new Exception("Ta Specyfikacja jest już powiązana z zamówieniem!");
            } 
            specyfikacje.add(specyfikacja);
            // Zapisz na liscie wszystkich czesci (przeciwdziala wspoldzielniu czesci)
            wszystkieSpecyfikacje.add(specyfikacja);
        }
	}

	public static void usunZamowienieSpec(Zamowienie zamowienieDoSkasowania) {
        if(zamowienia.contains(zamowienieDoSkasowania)) {
            zamowienia.remove(zamowienieDoSkasowania); 
            // Usuwa tomy prypisane do tej ksiązki
            for(int i=0;i<zamowienieDoSkasowania.specyfikacje.size();i++) {
            	if(wszystkieSpecyfikacje.contains(zamowienieDoSkasowania.specyfikacje.get(i))) {
                    wszystkieSpecyfikacje.remove(zamowienieDoSkasowania.specyfikacje.get(i));
                }
            }
        }
        zamowienieDoSkasowania.specyfikacje.clear();
	}

	@Override
	public String toString() {
		return "Zamowienie [\n klient=" + klient + ", \n ilosc=" + ilosc
				+ ", \n cenaNetto=" + cenaNetto + ", cenaBrutto=" + cenaBrutto
				+ ", \n dataDostawy=" + dataDostawy + ", \n dostawy=" + dostawy
				+ ", \n specyfikacje=" + specyfikacje + "]";
	}
	
	
}

