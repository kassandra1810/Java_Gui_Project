package Logika;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LaczenieZBaza {

	public static void main(String[] args) throws Exception {
		LaczenieZBaza lb = new LaczenieZBaza();
		lb.pobranieKlientow();
 /* 
		lb.pobranieDostawcow();
		lb.pobranieProduktow2D();
		lb.pobranieCen();
		lb.pobranieWycen();
		lb.pobranieOfert();*/
		//lb.pobranieProduktow3D();
		//lb.pobranieGadzetow();
		lb.pobranieZamowien();

	}
	public void pobranieKlientow(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from osoba where typ_osoby = 'klient' ");
			while(rs.next()){
				Klient klient = new Klient(rs.getString("imie"),rs.getString("nazwisko"),rs.getString("telefon"),rs.getString("nazwa_firmy"), rs.getDouble("marza"),Adres.getAdresyMapa().get(rs.getInt("id_adresu")));
				Klient.setKlienciMapa(rs.getInt("id_osoba"), klient);
				klient.setId(rs.getInt("id_osoba"));
				//System.out.println(klient);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobraniePracownikow(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from osoba where typ_osoby = 'pracownik' ");
			while(rs.next()){
				Pracownik pracownik = new Pracownik(rs.getString("imie"),rs.getString("nazwisko"),rs.getString("telefon"),rs.getString("stanowisko"),Adres.getAdresyMapa().get(rs.getInt("id_adresu")));
				pracownik.setId(rs.getInt("id_osoba"));
				Pracownik.setpracownicyMapa(rs.getInt("id_osoba"), pracownik);
				//System.out.println(pracownik);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieDostawcow(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from osoba where typ_osoby = 'dostawca' ");
			while(rs.next()){
				Dostawca dostawca = new Dostawca(rs.getString("imie"),rs.getString("nazwisko"),rs.getString("telefon"),rs.getString("nazwa_firmy"), rs.getDate("poczatek_wspolpracy"),Adres.getAdresyMapa().get(rs.getInt("id_adresu")));
				Dostawca.setDostawcyMapa(rs.getInt("id_osoba"), dostawca);
				dostawca.setId(rs.getInt("id_osoba"));
				//System.out.println(dostawca);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieProduktow2D(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM produkty WHERE typ_specki = 'Produkt2D' ");
			while(rs.next()){
				Produkt2D prod = new Produkt2D(rs.getString("typ_produktu"),rs.getString("typ_papieru"),rs.getBoolean("zadruk"),
						rs.getInt("ilosc_kolorow"),rs.getBoolean("lakier"),Powtarzalnosc.ProduktNowy,
						rs.getBoolean("prototyp_z_nadrukiem"),rs.getDouble("szerokosc"),rs.getDouble("wysokosc"));
				prod.setId(rs.getInt("id_produktu"));
				Produkt2D.setProduktyMapa(rs.getInt("id_produktu"), prod);
				//System.out.println(prod);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieProduktow3D(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM produkty WHERE typ_specki = 'Produkt3D' ");
			while(rs.next()){
				Produkt3D prod = new Produkt3D(rs.getString("typ_produktu"),rs.getString("typ_papieru"),rs.getBoolean("zadruk"),
						rs.getInt("ilosc_kolorow"),rs.getBoolean("lakier"),Powtarzalnosc.ProduktNowy,
						rs.getBoolean("prototyp_z_nadrukiem"),rs.getDouble("szerokosc"),rs.getDouble("wysokosc"),rs.getDouble("glebokosc"));
				prod.setId(rs.getInt("id_produktu"));
				Produkt3D.setProduktyMapa(rs.getInt("id_produktu"), prod);
				//System.out.println(prod);
			}
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieGadzetow(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM produkty WHERE typ_specki = 'Gadzet' ");
			while(rs.next()){
				Gadzet prod = new Gadzet(rs.getString("typ_produktu"),rs.getString("typ_papieru"),rs.getBoolean("zadruk"),
						rs.getInt("ilosc_kolorow"),rs.getBoolean("lakier"),Powtarzalnosc.ProduktNowy,
						rs.getBoolean("prototyp_z_nadrukiem"),rs.getDouble("szerokosc"),rs.getDouble("wysokosc"),rs.getDouble("glebokosc"),rs.getString("typ_materialu"));
				prod.setId(rs.getInt("id_produktu"));
				Gadzet.setProduktyMapa(rs.getInt("id_produktu"), prod);
				//System.out.println(prod);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	
	public void pobranieCen(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM cenydostawcow");
			while(rs.next()){
				CenaDostawcy cena = new CenaDostawcy(rs.getDouble("cena"),Dostawca.getDostawcyMapa().get(new Integer(rs.getInt("id_dostawcy"))),rs.getString("opis"));
				cena.setId(rs.getInt("id_cena"));
				CenaDostawcy.setCenaDostawcyMapa(new Integer(rs.getInt("id_cena")), cena);
				//System.out.println(cena);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieWycen() throws Exception{
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM wyceny");
			while(rs.next()){
				Wycena wycena = new Wycena(
					CenaDostawcy.getCenaDostawcyMapa().get(rs.getInt("id_cena_d1")),
					CenaDostawcy.getCenaDostawcyMapa().get(rs.getInt("id_cena_d2")),
					CenaDostawcy.getCenaDostawcyMapa().get(rs.getInt("id_cena_d3")),
					SpecyfikacjaProduktu.getProduktyMap().get(rs.getInt("id_produktu"))
				);
				Wycena.setWycenyMapa(rs.getInt("id_wyceny"), wycena);
				wycena.setId(rs.getInt("id_wyceny"));
				//System.out.println(wycena);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieOfert(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM oferta");
			while(rs.next()){
				OfertaDoKlienta oferta = new OfertaDoKlienta(
					rs.getInt("dni"),
					rs.getInt("ilosc_miejsc_dostawy"),
					Klient.getKlienciMapa().get(rs.getInt("id_klienta")),
					Wycena.getWycenyMapa().get(rs.getInt("id_wyceny"))
				);
				OfertaDoKlienta.setOfertyMapa(rs.getInt("id_oferty"), oferta);
				oferta.setId(rs.getInt("id_oferty"));
				//System.out.println(oferta);
			}
			//System.out.println("Polączenie do bazy powiodło się");
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	public void pobranieAdresow(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM adresy");
			while(rs.next()){
				Adres a1 = new Adres(rs.getString("kraj"),rs.getString("miasto"), rs.getString("ulica"), rs.getInt("numer_domu"),rs.getString("kod_pocztowy"));
				if (rs.getInt("id_osoby")!=0){
					a1.dodajOsobe(Osoba.getOsobyMapa().get(rs.getInt("id_osoby")));
				}
				Adres.setAdresyMapa(rs.getInt("id_adres"), a1);
				a1.setId(rs.getInt("id_adres"));
				//System.out.println(a1);
			}
			//System.out.println("Polączenie do bazy powiodło się");
			//System.out.println(Wycena.getWycenyMapa());
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	public void pobranieDostaw(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM dostawy");
			while(rs.next()){
				Dostawa dw = new Dostawa(rs.getString("numer_awizo"));
				Dostawa.setDostawyMapa(rs.getInt("id_dostawa"), dw);
				dw.setId(rs.getInt("id_dostawa"));
				//System.out.println(dw);
			}
			//System.out.println("Polączenie do bazy powiodło się");
			//System.out.println(Wycena.getWycenyMapa());
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void pobranieZamowien(){
		try {	
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM zamowienia");
			while(rs.next()){
				Zamowienie zam = new Zamowienie(rs.getInt("ilosc"), rs.getDouble("cena_netto"), rs.getDate("data_dostawy"));
				Zamowienie.setZamowieniaMapa(rs.getInt("id_zamowienia"), zam);
				zam.setId(rs.getInt("id_zamowienia"));
				//System.out.println(zam);
			}
			//System.out.println("Polączenie do bazy powiodło się");
			//System.out.println(Wycena.getWycenyMapa());
		} catch (SQLException e){
				Logger.getLogger(LaczenieZBaza.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
