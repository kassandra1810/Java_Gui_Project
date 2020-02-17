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

public class Gadzet extends Produkt3D implements IProdukt2D, Serializable {
	
	private String typMaterialu;
	private static ArrayList<Gadzet> gadzety = new ArrayList<>();

	public Gadzet(String typProduktu, String typPapieru, boolean zadruk,
			int iloscKolorow, boolean lakier,Powtarzalnosc powtarzalnosc, boolean prototyp,
			double szerokosc, double wysokosc, double glebokosc, String typMaterialu) {
		super(typProduktu, typPapieru, zadruk, iloscKolorow, lakier,powtarzalnosc, prototyp,
				szerokosc, wysokosc, glebokosc);
		if (typMaterialu != null && typMaterialu != " "){
			this.setTypMaterialu(typMaterialu);
		}
	}

	@Override
	public ArrayList<Gadzet> znajdzProdukt(String typProduktu) {
		ArrayList<Gadzet> gadzety = new ArrayList<>();
		gadzety = getGadzety();
		ArrayList<Gadzet> pasujaceProdukty = new ArrayList<>();
		for (int i=0; i<gadzety.size(); i++) {
			if (gadzety.get(i).getTypProduktu().equals(typProduktu)) {
				pasujaceProdukty.add(gadzety.get(i));
			}
		}
		return pasujaceProdukty;
	}

	public String getTypMaterialu() {
		return typMaterialu;
	}

	public void setTypMaterialu(String typMaterialu) {
		this.typMaterialu = typMaterialu;
	}

	public static ArrayList<Gadzet> getGadzety() {
		return gadzety;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//Trwałość
	public void dodajGadzetDoBazy(){		
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ya-noosh?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "admin","admin");
	        String sql = "INSERT INTO produkty(typ_produktu, typ_papieru, zadruk, ilosc_kolorow, lakier, powtarzalnosc, wysokosc, szerokosc, glebokosc, typ_materialu, typ_specki) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	        PreparedStatement statement = con.prepareStatement(sql);
	        statement.setString(1,this.getTypProduktu());
	        statement.setString(2,this.getTypPapieru());
	        statement.setBoolean(3, this.isZadtuk());
	        statement.setInt(4,this.getIloscKolorow());
	        statement.setBoolean(5, this.isLakier());
	        statement.setBoolean(6, false);
	        statement.setDouble(7, this.getWysokosc());
	        statement.setDouble(8, this.getSzerokosc());
	        statement.setDouble(9, this.getGlebokosc());
	        statement.setString(10,this.typMaterialu);
	        statement.setString(11,"Gadzet");
	        statement.executeUpdate();
	     }catch(SQLException e){
	    	 e.printStackTrace();
	     }
	}
}
