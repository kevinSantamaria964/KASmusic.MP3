/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */

package KASmusic;

import javafx.beans.property.SimpleStringProperty;

public class Genero{
    
    //private final SimpleStringProperty genero;
    private SimpleStringProperty genero;
    
    public Genero(String s){
        this.genero = new SimpleStringProperty(s);
    }
    
    public String getGenero(){
        return genero.get();
    }
}
