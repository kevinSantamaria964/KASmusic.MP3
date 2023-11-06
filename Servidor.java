/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */
package KASmusic;

import KASmusic.Cancion;
import KASmusic.Usuario;
import KASmusic.ValidacionCancionesRepetidas;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private List<Cancion> listaCanciones = new ArrayList<>();
    private List<Usuario> usuariosConectados = new ArrayList<>();
    private ValidacionCancionesRepetidas validacion = new ValidacionCancionesRepetidas();
    private ArrayList<Cancion> colaReproduccion = new ArrayList<>();
    private int tiempoEsperaRepetirCancion = 15; // Tiempo en minutos

    public Servidor() {
    }

    public void iniciar() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + socket.getInetAddress());

                ClienteThread cliente = new ClienteThread(socket);
                cliente.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClienteThread extends Thread {
        private Socket socket;
        private ObjectInputStream entrada;
        private ObjectOutputStream salida;
        private Usuario usuario;

        private static String usuarioActual;
        private static long tiempoUltimaReproduccion = 0;
        private static long tiempoEsperaRepetirCancion = 15 * 60 * 1000; // 15 minutos en milisegundos

        public ClienteThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                entrada = new ObjectInputStream(socket.getInputStream());
                salida = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    String accion = entrada.readUTF();

                    if (accion.equals("INICIAR_SESION")) {
                        iniciarSesion();
                    } else if (accion.equals("LISTAR_CANCIONES")) {
                        listarCanciones();
                    } else if (accion.equals("REPRODUCIR_CANCION")) {
                        reproducirCancion();
                    } else if (accion.equals("AGREGAR_CANCION_COLA")) {
                        agregarCancionCola();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void iniciarSesion() throws IOException, ClassNotFoundException {
            usuario = (Usuario) entrada.readObject();

            if (!usuariosConectados.contains(usuario)) {
                usuariosConectados.add(usuario);
                System.out.println("Usuario " + usuario.getNombre() + " se ha conectado.");
            }
        }

        private void listarCanciones() throws IOException {
            salida.writeObject(listaCanciones);
        }

        private void reproducirCancion() throws IOException {
            String rutaCancion = entrada.readUTF();

            if (puedeReproducirCancion(rutaCancion)) {
                Cancion cancion = obtenerCancionPorRuta(rutaCancion);

                if (cancion != null) {
                    colaReproduccion.add(cancion);
                    tiempoUltimaReproduccion = System.currentTimeMillis();
                    usuarioActual = usuario.getNombre();
                    System.out.println("Reproduciendo: " + cancion.getTitulo());
                    salida.writeObject(cancion);
                }
            } else {
                salida.writeObject(null); // Canción no válida
            }
        }

        private void agregarCancionCola() throws IOException {
            String rutaCancion = entrada.readUTF();
            Cancion cancion = obtenerCancionPorRuta(rutaCancion);

            if (cancion != null) {
                if (!existeCancionEnCola(cancion) && puedeReproducirCancion(rutaCancion)) {
                    colaReproduccion.add(cancion);
                    System.out.println("Canción agregada a la cola de reproducción: " + cancion.getTitulo());
                }
            }
        }

        private Cancion obtenerCancionPorRuta(String rutaCancion) {
            for (Cancion c : listaCanciones) {
                if (c.getRuta().equals(rutaCancion)) {
                    return c;
                }
            }
            return null;
        }

        private boolean existeCancionEnCola(Cancion cancion) {
            return colaReproduccion.contains(cancion);
        }

        private boolean puedeReproducirCancion(String rutaCancion) {
            if (usuarioActual == null || !usuarioActual.equals(usuario.getNombre())) {
                // El usuario actual no es el mismo que solicitó la canción anterior.
                long tiempoActual = System.currentTimeMillis();

                if (tiempoUltimaReproduccion == 0 || tiempoActual - tiempoUltimaReproduccion >= tiempoEsperaRepetirCancion) {
                    // Ha pasado suficiente tiempo desde la última reproducción.
                    return true;
                }
            }

            return false;
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar();
    }
}

