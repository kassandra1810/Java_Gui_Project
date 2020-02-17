package Logika;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Osoba implements Serializable {
	
	private int id;
	private String imie;
	private String nazwisko;
	private String telefon;
	private Adres adres;

	//ekstencje
	private static HashMap<Integer,Osoba> osobyMapa = new HashMap<Integer,Osoba>();
	
	// konstruktor
	public Osoba (String imie, String nazwisko, String telefon, Adres adres) {
		this.adres = adres;
		if(imie != null && imie!= " "){
			this.imie = imie;			
		}
		if (nazwisko != null && nazwisko != " ") {
			this.nazwisko = nazwisko;
		}
		if(telefon != null && telefon != " ") {
			this.telefon = telefon;
		}
	}
	
	//gettery i settery
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImie() {
		return imie;
	}
	
	public void setImie(String imie) {
		if(imie != null && imie!= " "){
			this.imie = imie;			
		}
	}
	public Adres getAdres() {
		return adres;
	}
	
	public String getNazwisko() {
		return nazwisko;
	}
	
	public void setNazwisko(String nazwisko) {
		if (nazwisko != null && nazwisko != " ") {
			this.nazwisko = nazwisko;
		}
	}
	
	public String getTelefon() {
		return telefon;
	}
	
	public void setTelefon(String telefon) {
		if(telefon != null && telefon != " ") {
			this.telefon = telefon;
		}
	}

	public static HashMap<Integer, Osoba> getOsobyMapa() {
		HashMap<Integer, Osoba> kopia = new HashMap<Integer, Osoba>(osobyMapa);
		return kopia;
	}

	public static void setOsobyMapa(Integer id, Osoba osoba) {
		Osoba.osobyMapa.put(id, osoba);
	}
	
	// Asocjacja jeden do jeden z Adresem
	public void dodajAdres(Adres nowyAdres) {
    	if(nowyAdres != null) {
    		this.adres = nowyAdres;
	        //informacja zwrotna
    		nowyAdres.dodajOsobe(this);
    	}
    }
    
    public void usunAdres(Adres adresDoSkasowania) {
        if(adres.equals(adresDoSkasowania)) {
            adres = null;
            // Usun informacje zwrotna
            adresDoSkasowania.usunOsobe(this);
        }
    }
}

