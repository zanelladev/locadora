package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.ResourceBundle;
import ifsc.tds.com.andre.artur.felipe.entity.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClienteEditController implements Initializable {

	@FXML
	private HBox bnlBotoes;

	@FXML
	private Tooltip tlpOK;

	@FXML
	private Tooltip tlpCancela;

	@FXML
	private Button btnOK;

	@FXML
	private GridPane pnlDados;

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lblNome;

	@FXML
	private Button btnCancela;

	

	private Stage janelaClienteEdit;
	private Cliente cliente;
	private boolean okClick = false;

	@FXML
	void onClickBtnOK(ActionEvent event) {

		if (validarCampos()) {
			this.cliente.setNome(this.txtNome.getText());

			this.okClick = true;
			this.getJanelaClienteEdit().close();
		}
	}

	@FXML
	void onClickBtnCancela(ActionEvent event) {

		this.getJanelaClienteEdit().close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Stage getJanelaClienteEdit() {
		return janelaClienteEdit;
	}

	public void setJanelaClienteEdit(Stage janelaClienteEdit) {
		this.janelaClienteEdit = janelaClienteEdit;
	}

	public void populaTela(Cliente cliente) {
		this.cliente = cliente;

		this.txtNome.setText(cliente.getNome());
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();
		if (this.txtNome.getText() == null || this.txtNome.getText().trim().length() == 0) {
			mensagemErros += "informe o nome!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaClienteEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir a aseguintes informações");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
}
