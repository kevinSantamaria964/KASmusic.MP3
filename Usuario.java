/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */


package KASmusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class Usuario implements Serializable {
    private String username;
    private boolean premium;
    private String nameOfFile;

    public Usuario() {
        this.nameOfFile = "sessionData.upm";
        // Nada :v
    }

    public Usuario(String username, boolean premium) {
        this.nameOfFile = "sessionData.upm";
        this.username = username;
        this.premium = premium;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getUser() {
        return this.username;
    }

    public boolean getPremiumStatus() {
        return this.premium;
    }

    public void almacenarUsuario(String data) {
        try {
            PrintWriter escritura = new PrintWriter(nameOfFile);
            escritura.write(data);
            escritura.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se pudo escribir en el archivo...");
        }
    }

    public String recuperarUsuario() {
        File file;
        FileReader reader;
        BufferedReader buffer;
        String linea = "";
        try {
            file = new File(nameOfFile);
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String tmp;
            while ((tmp = buffer.readLine()) != null) {
                System.out.println("Línea = " + linea);
                System.out.println("Tmp = " + tmp);
                linea = tmp;
                System.out.println("Línea = " + linea);
            }
            buffer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("No se encuentra el archivo...");
        } catch (IOException ex) {
            System.out.println("No se puede leer el archivo...");
        }
        return linea;
    }
}

