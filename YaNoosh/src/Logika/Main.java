package Logika;

import java.sql.Date;


public class Main {

	public static void main(String[] args) throws Exception {
		
		Dostawa d1 = new Dostawa();

		LaczenieZBaza lb = new LaczenieZBaza();
		lb.pobranieAdresow();
		lb.pobranieDostaw();
		lb.pobranieDostawcow();
		lb.pobranieKlientow();
		lb.pobraniePracownikow();
		lb.pobranieCen();
		lb.pobranieProduktow2D();
		lb.pobranieProduktow3D();
		lb.pobranieGadzetow();
		lb.pobranieWycen();
		lb.pobranieOfert();
		lb.pobranieZamowien();
		
		//System.our.print(Klient.pobranieOfert(Klient.getKlienciMapa().get(6));
		
		// DO POKAZANIA W SZKOLE
		/*// Powiązanie Adres Dostawa
		System.out.println("Powiązanie wiele do wiele \n");
		Adres adres = new Adres("Polska", "Warszawa", "Zakopiańska",32,"00-000");
		Dostawa dostawa = new Dostawa();
		adres.dodajDostaweDoAdresu(dostawa);
		System.out.println("adresD: "+ adres.getDostawy());
		System.out.println("dostawaA: "+ dostawa.getAdresyDostawy());
		System.out.println("\n \n");
		
		// Kompozycja Wycena i Ceny dostawców
		System.out.println("Kompozycja \n");
		CenaDostawcy cena1 = new CenaDostawcy(1475.55, Dostawca.getDostawcyMapa().get(1),"Koszula S");
		CenaDostawcy cena2 = new CenaDostawcy(1500.00, Dostawca.getDostawcyMapa().get(2),"Koszula M");
		CenaDostawcy cena3 = new CenaDostawcy(1650.00, Dostawca.getDostawcyMapa().get(3),"Koszula L");
		
		Produkt3D produkt1 = new Produkt3D("Koszulka","Bawewna",true,0,true,Powtarzalnosc.ProduktNowy,false,55,55,55);
		Wycena w1 = new Wycena(cena1, cena2, cena3, produkt1);
		System.out.println(w1.getCeny()+"\n");
		System.out.println(Wycena.getWyceny());
		
		//Wycena.usunWycene(w1);
		//System.out.println(Wycena.getWyceny());
		//Wycena w2 = new Wycena(cena1, cena2, cena3, produkt1); //Błąd, te ceny są juz powiązane z wyceną
		//System.out.println(w2);
		System.out.println("\n \n");
		
		// Zamówienie
		String d = "2020-08-15";
		Date data = Date.valueOf(d);
		Zamowienie zamowienie1 = new Zamowienie(Klient.getKlienciMapa().get(5),1500,5300.00,data);
		zamowienie1.dodajSpecyfikacje(produkt1);
		zamowienie1.dodajDostawe(dostawa);
		
		System.out.println(zamowienie1);*/
		
		
		// TESTOWANIE POWIĄZAŃ Z BAZA
		
		//Adres.getAdresyMapa().get(1).dodajDostaweDoAdresu(Dostawa.getDostawyMapa().get(1));
		//System.out.println(Klient.getKlienciMapa().get(9));
		//System.out.println(Zamowienie.getZamowieniaMapa().get(7));
		Zamowienie.getZamowieniaMapa().get(9).dodajKlienta(Klient.getKlienciMapa().get(6));
		
		//Klient.getKlienciMapa().get(7).usunZamowienie(Zamowienie.getZamowieniaMapa().get(9));
/*		Wycena w1 = new Wycena(
		CenaDostawcy.getCenaDostawcyMapa().get(5),
		CenaDostawcy.getCenaDostawcyMapa().get(7),
		CenaDostawcy.getCenaDostawcyMapa().get(10),
		Produkt2D.getProduktyMap().get(10)
		);
		w1.dodajWyceneDoBazy();*/
		
		
		//adres.dodajAdresDoBazy();
		//dostawa.dodajDostaweDoBazy();
		
		//Produkt3D prod3 = new Produkt3D("Opakowanie","Karton",true,0,true,Powtarzalnosc.ProduktNowy,false,55,55,55);
		//prod3.dodajProdukt3dDoBazy();
/*		String data = "2020-05-12";
		Date d = Date.valueOf(data);
		Zamowienie zam = new Zamowienie(Klient.getKlienciMapa().get(5),5000,35888.25, d );
		zam.dodajZamówienieDoBazy();*/
		
		
		//LaczenieZBaza lb = new LaczenieZBaza();
		//lb.pobranieAdresow();
		//lb.pobranieDostaw();
		//Adres.getAdresyMapa().get(3).dodajDostaweDoAdresu(Dostawa.getDostawyMapa().get(1));
		
		//Adres.getAdresyMapa().get(1).usunDostawe(Dostawa.getDostawyMapa().get(2));
		
		//Pracownik p1 = new Pracownik ("Jan", "Kowalski","2222222","CRM");
		//Pracownik p2 = new Pracownik ("Katarzyna", "Kowalska","33333333","BPC");
		
		
		/*
		Produkt2D prod1 = new Produkt2D("Ulotka","Alaska",true,4,true,Powtarzalnosc.ProduktNowy,false,214,145);
		Produkt2D prod2 = new Produkt2D("Broszura","Arctica",true,4,true,Powtarzalnosc.ProduktPowtarzalny,false,155,155);
		Produkt2D prod3 = new Produkt2D("Wobbler","Alaska",true,4,true,Powtarzalnosc.ProduktNowy,false,55,87);
		Adres ad1 = new Adres("Polska","Warszawa","Pulawska",111,"1111");
		
		Dostawca dost1 = new Dostawca("Marcin","Kowalski","666555444","Fromegon",new Date(12/8/2016));
		Dostawca dost2 = new Dostawca("Michał","Walewski","666555444","Asprint",new Date(12/8/2016));
		Dostawca dost3 = new Dostawca("Karolina","Janowska","666555444","Karo",new Date(12/8/2016));
		//
		
		Klient klient1 = new Klient("Kamila", "Kowalska", "55885588","Nestle", 10);
		Klient klient2 = new Klient("Katarzyna", "Kowalska", "89778899","Samsung", 8);
		Klient klient3 = new Klient("Dawid", "Jankowski", "44775588","Sony", 7);
		Klient klient4 = new Klient("Grzegorz", "Domela", "47852147","Bacardi", 12);
		Klient klient5 = new Klient("Karol", "Palemko", "78521478","LG", 9);
		
		System.out.println(klient1);
		System.out.println(klient2);
		System.out.println(klient3);
		System.out.println(klient4);
		System.out.println(klient5);
		
		
		CenaDostawcy cenad4 = new CenaDostawcy(1350.00, dost1,"Ulotka A4, folia błysk lub lakier dyspersyjny");
		
		//Wycena w1 = new Wycena(cenad1,cenad2,cenad3,prod1);
		//System.out.println(w1);
		System.out.println("");
		*/
		//OfertaDoKlienta ofdk1 = new OfertaDoKlienta(4,2,null,w1);
		//ofdk1.dodajKlienta(klient1);
		
//		System.out.print(SpecyfikacjaProduktu.getProd());

/*		Vector<SpecyfikacjaProduktu> prod = new Vector<>(SpecyfikacjaProduktu.getProd());
		String [] tab = new String[prod.size()];
		for(int i=0;i<prod.size();i++){
			tab[i]=prod.get(i).getTypProduktu();
		}*/		
/*		for(int i=0;i<SpecyfikacjaProduktu.pr.size();i++){
			System.out.println(SpecyfikacjaProduktu.pr);
		}*/
		
		//System.out.println(ofdk1);

	}
}
