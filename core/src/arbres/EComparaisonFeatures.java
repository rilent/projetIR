package arbres;

public enum EComparaisonFeatures {
	
//		TankxSupAlienPlusProchex("Tank.x > AlienPlusProche.x"),
//		TankxSupProtectionPlusProche("Tank.x > ProtectionPlusProche.x"),
//		TankxSupMissilePlusProche("Tank.x > MissilePlusProche.x");
		
	//TankxSupAlienPlusProchex("Tank.x > AlienPlusProche.x"),

	
		TankxSupAlienPlusProchex("TankxSupAlienPlusProchex", "tk.x>A.x"),
		TankxSupProtectionPlusProche("TankxSupProtectionPlusProche", "Tk.x>P.x"),
		TankxSupMissilePlusProche("TankxSupMissilePlusProche","Tk.x>M.x"),
	
		AlienLePlusBasEstAuDessusDeLaMoitieDuTerrain("AlienLePlusBasEstAuDessusDeLaMoitieDuTerrain", "A<moit");
		
		private String name = "";
		private String affichage = "";
		
		//Constructeur
		EComparaisonFeatures(String name, String affichage){
		  this.name = name;
		  this.affichage = affichage;
		}
		 
		public String toString(){
		  return name;
		}
		
		public String getAffichage()
		{
			return affichage;
		}
		
		
		}
	