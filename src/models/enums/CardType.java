/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models.enums;

public enum CardType {
	energy, pokemon, trainer;
	
	public char getLetter() {
		return this.toString().charAt(0);
	}
}
