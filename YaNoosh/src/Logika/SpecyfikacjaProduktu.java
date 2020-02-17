package Logika;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ComboBoxModel;

//enum Powtarzalnosc1 {ProduktNowy, ProduktPowtarzalny, Null};

public abstract class SpecyfikacjaProduktu implements Serializable {
	
	private static int ilosc=0;
	private int id;
	private static final long serialVersionUID = 1L;
	//private static int numerSpec;
	private String typProduktu;
	private String typPapieru;
	private boolean zadtuk;
	private int iloscKolorow;
	private boolean lakier;
	private ArrayList<String> typLakieru = new ArrayList<>();
	private Integer poprzedniaIlosc;
	private boolean prototypZDarukiem;// może byc null
	private Powtarzalnosc powtarzalnosc;
	
	// wszystkie produkty
	private static ArrayList<SpecyfikacjaProduktu> produkty = new ArrayList<>();
	private static HashMap<Integer,SpecyfikacjaProduktu> produktyMapa = new HashMap<Integer,SpecyfikacjaProduktu>();
	
	private ArrayList<Wycena> wyceny = new ArrayList<>();
	
	//konstruktor
	public SpecyfikacjaProduktu (String typProduktu, String typPapieru, boolean zadruk, int iloscKolorow, boolean lakier,Powtarzalnosc powt, Integer poprzedniaIlosc){
		if (typProduktu != null && typProduktu != " "){
			this.typProduktu = typProduktu;
		}
		if (typPapieru != null && typPapieru != " "){
			this.typPapieru = typPapieru;
		}
		this.zadtuk = zadruk;
		if(iloscKolorow>=0){
			this.iloscKolorow = iloscKolorow;
		}
		this.lakier = lakier;
		this.poprzedniaIlosc = poprzedniaIlosc;
		//this.numerSpec = numerSpec+1;
		this.setPowtarzalnosc(powt);
		ilosc++;
		this.id=ilosc;
		produkty.add(this);
		produktyMapa.put(new Integer(id), this);
	}
	
	public SpecyfikacjaProduktu (String typProduktu, String typPapieru, boolean zadruk, int iloscKolorow, boolean lakier,Powtarzalnosc powt, boolean prototyp){
		if (typProduktu != null && typProduktu != " "){
			this.typProduktu = typProduktu;
		}
		if (typPapieru != null && typPapieru != " "){
			this.typPapieru = typPapieru;
		}
		this.zadtuk = zadruk;
		if(iloscKolorow>=0){
			this.iloscKolorow = iloscKolorow;
		}
		this.lakier = lakier;
		this.setPrototypZDarukiem(prototyp);
		//this.numerSpec = numerSpec+1;
		this.setPowtarzalnosc(powt);
		ilosc++;
		this.id=ilosc;
		produkty.add(this);
		produktyMapa.put(new Integer(id), this);
	}
	
	/*public static ArrayList<SpecyfikacjaProduktu> getProdukty () {
		ArrayList<SpecyfikacjaProduktu> produktyKopia = new ArrayList<>(produkty);
		return produktyKopia;
	}*/
	
	/*public static int getNumerSpec() {
		return numerSpec;
	}*/

	public String getTypProduktu() {
		return typProduktu;
	}

	public void setTypProduktu(String typProduktu) {
		if (typProduktu != null && typProduktu != " "){
			this.typProduktu = typProduktu;
		}
	}

	public String getTypPapieru() {
		return typPapieru;
	}

	public void setTypPapieru(String typPapieru) {
		if (typPapieru != null && typPapieru != " "){
			this.typPapieru = typPapieru;
		}
	}

	public boolean isZadtuk() {
		return zadtuk;
	}

	public void setZadtuk(boolean zadtuk) {
		this.zadtuk = zadtuk;
	}

	public int getIloscKolorow() {
		return iloscKolorow;
	}

	public void setIloscKolorow(int iloscKolorow) {
		if(iloscKolorow >=0){
			this.iloscKolorow = iloscKolorow;
		}
	}

	public boolean isLakier() {
		return lakier;
	}

	public void setLakier(boolean lakier) {
		this.lakier = lakier;
	}

	public ArrayList<String> getTypLakieru() {
		return typLakieru;
	}

	public void setTypLakieru(ArrayList<String> typLakieru) {
		this.typLakieru = typLakieru;
	}

	public int getPoprzedniaIlosc() {
		return poprzedniaIlosc;
	}

	public void setPoprzedniaIlosc(Integer poprzedniaIlosc) {
			this.poprzedniaIlosc = poprzedniaIlosc;
	}

	public boolean isPrototypZDarukiem() {
		return prototypZDarukiem;
	}

	public void setPrototypZDarukiem(boolean prototypZDarukiem) {
		this.prototypZDarukiem = prototypZDarukiem;
	}
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	////
	public static HashMap<Integer,SpecyfikacjaProduktu> getProduktyMap() {
		HashMap<Integer,SpecyfikacjaProduktu> produktyKopia = new HashMap<>(produktyMapa);
		return produktyKopia;
	}
	
	public static void setProduktyMapa(Integer id, SpecyfikacjaProduktu specka) {
		SpecyfikacjaProduktu.produktyMapa.put(id, specka);
	}

	public static ArrayList<SpecyfikacjaProduktu> getProd() {
		ArrayList<SpecyfikacjaProduktu> produktyKopia = new ArrayList<>(produkty);
		return produktyKopia;
	}
	public ArrayList<Wycena> getWyceny() {
		return wyceny;
	}

	public void setWyceny(ArrayList<Wycena> wyceny) {
		this.wyceny = wyceny;
	}
	
	// Asocjacja z wyceną
	public void dodajWycene (Wycena wycena){
		this.wyceny.add(wycena);
	//	wycena.setSpecka(this);
	}
	
	//// Kompozycja
	
	public static SpecyfikacjaProduktu utworzProdukt(Zamowienie zamowienie, String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp, double szerokosc, double wysokosc) throws Exception {
        if(zamowienie == null) {
            throw new Exception("Zamówienie nie istnieje!");
        }
        // Utwocz nowa czesc
        Produkt2D specyfikacja = new Produkt2D(typProduktu, typPapieru, zadruk, iloscKolorow, lakier,powtarzalnosc, prototyp, szerokosc, wysokosc);
 
        // Dodaj do calosci
        zamowienie.dodajSpecyfikacje(specyfikacja);
        return specyfikacja;
    }
    public static SpecyfikacjaProduktu utworzProdukt(Zamowienie zamowienie, String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp, double szerokosc, double wysokosc, double glebokosc) throws Exception {
        if(zamowienie == null) {
            throw new Exception("Zamówienie nie istnieje!");
        }
        // Utwocz nowa czesc
        Produkt3D specyfikacja = new Produkt3D(typProduktu, typPapieru, zadruk, iloscKolorow, lakier, powtarzalnosc, prototyp, szerokosc, wysokosc, glebokosc);
 
        // Dodaj do calosci
        zamowienie.dodajSpecyfikacje(specyfikacja);
        return specyfikacja;
    }
    public static SpecyfikacjaProduktu utworzProdukt(Zamowienie zamowienie, String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp,
			double szerokosc, double wysokosc, double glebokosc, String typMaterialu) throws Exception {
        if(zamowienie == null) {
            throw new Exception("Zamówienie nie istnieje!");
        }
        // Utwocz nowa czesc
        Gadzet specyfikacja = new Gadzet(typProduktu, typPapieru, zadruk, iloscKolorow, lakier, powtarzalnosc, prototyp, szerokosc, wysokosc, glebokosc, typMaterialu);
 
        // Dodaj do calosci
        zamowienie.dodajSpecyfikacje(specyfikacja);
        return specyfikacja;
    }

	@Override
	public String toString() {
		return 	typProduktu
				+ ", typPapieru=" + typPapieru + ", zadtuk=" + zadtuk
				+ ", iloscKolorow=" + iloscKolorow + ", lakier=" + lakier
				+ ", typLakieru=" + typLakieru + ", poprzedniaIlosc="
				+ poprzedniaIlosc + ", prototypZDarukiem=" + prototypZDarukiem
				+ "]";
	}

	public Powtarzalnosc getPowtarzalnosc() {
		return powtarzalnosc;
	}

	public void setPowtarzalnosc(Powtarzalnosc powtarzalnosc) {
		this.powtarzalnosc = powtarzalnosc;
	}

	public static ComboBoxModel<String> produktyComboBox() {
		// TODO Auto-generated method stub
		return null;
	}
}

