package edu.propra.bomberman.zufallsgenerator;

import edu.propra.bomberman.gameengine.objects.GameObjectGroup;

/*
 * Autor Jens Herrmann
 * 
 * Bugfix by Alex Hepting 02.07.2012 for-Schleife 71 Zeile
 * Bugfix Zeile 114 Objektumbennenung
 * Bugfix Pakagename
 * 
 * 
 */

public class MapZufallsgenerator {

	int			Start1;											// wird
																	// angepasst
	int			Start2;											// wird
																	// angepasst
	int			Ende1;												// wird
																	// angepasst
	int			iceMauer;
	int			statMauer;

	boolean		Start1bel	= false;
	boolean		Start2bel	= false;
	boolean		Endebel		= false;
	boolean[]	ObjBel		= { Start1bel, Start2bel, Endebel };	// boolean,
																	// um bei
																	// erstellten
																	// Objekt
																	// auf true
																	// zu setzen
	int[]		Objekt		= { Start1, Start2, Ende1 };
	int[]		AltObjekt	= { iceMauer, statMauer };
	int			i, x, y, z;

	// Getter und Setter f�r die boole'schen Variablen. Werden gel�scht, wenn
	// nicht ben�tigt
	public boolean isStart1bel() {
		return Start1bel;
	}

	public void setStart1bel(boolean start1bel) {
		Start1bel = start1bel;
	}

	public boolean isStart2bel() {
		return Start2bel;
	}

	public void setStart2bel(boolean start2bel) {
		Start2bel = start2bel;
	}

	public boolean isEndebel() {
		return Endebel;
	}

	public void setEndebel(boolean endebel) {
		Endebel = endebel;
	}

	/*
	 * Die beiden obigen Werte sollen �ber Spielereingabe entgegengenommen
	 * werden, um eine m�gliche Unspielbarkeit vorzubeugen oder eine
	 * Beeinflussung des Schwierigkeitsgrades bereitzuhalten
	 */
	// _______________________________________________________________________________________

	public void RandomMap() {
		// Warum for nicht klappt, wei� ich nicht
		for (int n = 0; n <= 80; n++) {
			
			/*
			 * Zuf�llige Werte f�r die in den drei Arrays gespeicherten Variablen. x, y sind Werte f�r die
			 * Koordinaten auf dem Spielfeld  	
			 */
			i = (int) (Math.random()*3.0*1.0);
			x = (int) (Math.random()*80.0*1.0);
			y = (int) (Math.random()*80.0*1.0);
			z = (int) (Math.random()*2.0*1.0);			
				/*
				 * Die Abfrage pr�ft, ob Start1, Start2, Ende schon belegt sind,
				 * um Mehrfachbelegung zu vermeiden
				 */
				if (ObjBel[i] == false) {
				
						if (Objekt[i] == Start1) {
								
						GameObjectGroup Start1 = new GameObjectGroup(x, y);
									
						Start1bel=true;
							
						}
						if (Objekt[i] == Start2) {
							
						GameObjectGroup Start2 = new GameObjectGroup(x, y);
										
						Start2bel=true;
						
						}
						if (Objekt[i] == Ende1) {
							
						GameObjectGroup Ende1 = new GameObjectGroup(x, y);
											
						Endebel=true;
						}
						/*
						 * Sind die beide Start- und die Endeposition auf true gesetzt,
						 * dann wird in den else-Part gesprungen
						 */
						else {
							int obj = AltObjekt[z];
							GameObjectGroup obj2 = new GameObjectGroup(x, y);
						}	
				}
			}
		}
}
