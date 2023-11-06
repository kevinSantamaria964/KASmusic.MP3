/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */

package KASmusic;

import java.util.List;

public class ValidacionCancionesRepetidas {
    private List<Cancion> colaReproduccion;
    private int tiempoEsperaRepetirCancion = 15; // Tiempo en minutos

    public ValidacionCancionesRepetidas(List<Cancion> colaReproduccion) {
        this.colaReproduccion = colaReproduccion;
    }

    public boolean validarCancionRepetida(String rutaCancion) {
        for (Cancion cancion : colaReproduccion) {
            if (cancion.getRuta().equals(rutaCancion)) {
                return false; // La canción ya está en la cola de reproducción
            }
        }
        return true; // La canción es válida para ser agregada a la cola
    }

    public boolean cancionDistanteTiempo(Cancion cancion) {
        if (colaReproduccion.isEmpty()) {
            return true; // La cola está vacía, por lo que la canción se puede agregar
        }

        long tiempoEsperaMilisegundos = tiempoEsperaRepetirCancion * 60 * 1000; // Convertir a milisegundos
        long tiempoActual = System.currentTimeMillis();
        long tiempoUltimaCancion = colaReproduccion.get(colaReproduccion.size() - 1).getTiempoReproduccion();

        return tiempoActual - tiempoUltimaCancion >= tiempoEsperaMilisegundos;
    }
}

