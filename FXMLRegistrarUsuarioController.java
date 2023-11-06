/*
 * Programa creado por kevin Andres Santamaria
 * Universidad de Cundinamarca, Programacion ll
 */

package KASmusic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * Clase que será de utilidad para crear un nuevo usuario y su estado
 */

public class FXMLRegistrarUsuarioController {
    
    @FXML
    private Button INPUT_BOTON_Registrar_usuario;
    
    @FXML
    private CheckBox modo_invitado;
    
    @FXML
    private TextField INPUT_TEXTO_Usuario;
    
    @FXML
    private TextField INPUT_TEXTO_Email;
    
    @FXML
    private PasswordField INPUT_TEXTO_Password;
    
    private boolean modo_invitado_seleccionado = false;
    
    @FXML
    public void RegistrarUsuario(ActionEvent event) {
        boolean usuario_ok = false;
        boolean password_ok = false;
        boolean email_ok = false;
        boolean invitado = false;
        
        CrearNuevoUsuario nuevo_usuario = new CrearNuevoUsuario();
        String nombreDeUsuario = nuevo_usuario.ObtenerNombreDeUsuario(INPUT_TEXTO_Usuario);
        String contrasenia = nuevo_usuario.ObtenerContraseniaDeUsuario(INPUT_TEXTO_Password);
        String email = nuevo_usuario.ObtenerEmailDeUsuario(INPUT_TEXTO_Email);
        
        if (modo_invitado.isSelected()) {
            usuario_ok = true;
            password_ok = true;
            email_ok = true;
            invitado = true;
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modo de prueba");
            alert.setHeaderText("¿Qué es el modo de prueba?");
            alert.setContentText("El modo de prueba le permite usar las características gratuitas sin necesidad de registrarse.");
            
            if (alert.showAndWait().get() == ButtonType.OK) {
                // Entrar en el modo invitado
                AbrirVentanaDelModoInvitado();
            }
        } else {
            usuario_ok = nuevo_usuario.ValidarLongitudDelCampo(INPUT_TEXTO_Usuario);
            password_ok = nuevo_usuario.ValidarLongitudDelCampo(INPUT_TEXTO_Password);
            email_ok = nuevo_usuario.ValidarFormatoDeEmail(INPUT_TEXTO_Email);
            
            if (usuario_ok && password_ok && email_ok) {
                nuevo_usuario.EscribirArchivoDeUsuarios(nombreDeUsuario, contrasenia, email);
                AbrirVentanaDeInicioSesion();
            } else {
                MostrarAlerta("Datos erróneos", "Verifique los campos seleccionados en rojo.");
            }
        }
    }
    
    @FXML
    public void ModoInvitadoSeleccionado(ActionEvent event) {
        modo_invitado_seleccionado = modo_invitado.isSelected();
        INPUT_TEXTO_Usuario.setDisable(modo_invitado_seleccionado);
        INPUT_TEXTO_Password.setDisable(modo_invitado_seleccionado);
        INPUT_TEXTO_Email.setDisable(modo_invitado_seleccionado);
    }
    
    private void AbrirVentanaDeInicioSesion() {
    try {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaInicioSesion.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Inicio de Sesión");
        stage.show();
        
        // Cierra la ventana actual
        INPUT_BOTON_Registrar_usuario.getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private void AbrirVentanaDelModoInvitado() {
    try {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaModoInvitado.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Modo Invitado");
        stage.show();
        
        // Cierra la ventana actual
        INPUT_BOTON_Registrar_usuario.getScene().getWindow().hide();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    
    private void MostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

