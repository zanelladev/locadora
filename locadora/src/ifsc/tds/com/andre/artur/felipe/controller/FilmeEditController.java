package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.ResourceBundle;

import ifsc.tds.com.andre.artur.felipe.entity.Filme;
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

public class FilmeEditController implements Initializable {

	@FXML
	private HBox bnlBotoes;

	@FXML
	private Tooltip tlpOK;

	@FXML
	private Tooltip tlpCancela;

	@FXML
	private Label lblFilme;

	@FXML
	private TextField txtFilme;

	@FXML
	private Button btnOK;

	@FXML
	private GridPane pnlDados;

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private Button btnCancela;

	private Stage janelaFilmeEdit;
	private Filme filme;
	private boolean okClick = false;

	@FXML
	void onClickBtnOK(ActionEvent event) {
		if (validarCampos()) {
			this.filme.setDescricao(this.txtFilme.getText());

			this.okClick = true;
			this.getJanelaFilmeEdit().close();
		}
	}

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaFilmeEdit().close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public Stage getJanelaFilmeEdit() {
		return janelaFilmeEdit;
	}

	public void setJanelaFilmeEdit(Stage janelaFilmeEdit) {
		this.janelaFilmeEdit = janelaFilmeEdit;
	}

	public void populaTela(Filme filme) {
		this.filme = filme;

		this.txtFilme.setText(filme.getDescricao());
	}

	public boolean isOkClick() {
		return okClick;
	}

	private boolean validarCampos() {
		String mensagemErros = new String();
		if (this.txtFilme.getText() == null || this.txtFilme.getText().trim().length() == 0) {
			mensagemErros += "informe o nome!\n";
		}

		if (mensagemErros.length() == 0) {
			return true;
		} else {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.initOwner(this.janelaFilmeEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir a aseguintes informações");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}
}
