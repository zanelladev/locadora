package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import ifsc.tds.com.andre.artur.felipe.entity.Cliente;
import ifsc.tds.com.andre.artur.felipe.entity.Filme;
import ifsc.tds.com.andre.artur.felipe.entity.Locacao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LocacaoEditController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private GridPane pnlDados;

	@FXML
	private Label lblNome;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lblDataEmprestimo;

	@FXML
	private DatePicker dtpDataEmprestimo;

	@FXML
	private Label lblCliente;

	@FXML
	private ComboBox<Cliente> cbxCliente;

	@FXML
	private Label lblFilme;

	@FXML
	private ComboBox<Filme> cbxFilme;

	@FXML
	private HBox bnlBotoes;

	@FXML
	private Button btnOK;

	@FXML
	private Tooltip tlpOK;

	@FXML
	private Button btnCancela;

	@FXML
	private Tooltip tlpCancela;

	private Stage janelaLocacaoEdit;
	private Locacao locacao;
	private boolean okClick = false;
	private ClienteListaController clienteListaController;
	private FilmeListaController filmeListaController;

	@FXML
	void onClickBtnOK(ActionEvent event) {

		if (validarCampos()) {
			this.locacao.setDescricao(this.txtNome.getText());
			this.locacao.setDataemprestimo(Date.valueOf(this.dtpDataEmprestimo.getValue()));
			this.locacao.setCliente(this.cbxCliente.getSelectionModel().getSelectedItem());
			this.locacao.setFilme(this.cbxFilme.getSelectionModel().getSelectedItem());

			this.okClick = true;
			this.getJanelaLocacaoEdit().close();
		}
	}

	@FXML
	void onClickBtnCancela(ActionEvent event) {
		this.getJanelaLocacaoEdit().close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.clienteListaController = new ClienteListaController();
		this.filmeListaController = new FilmeListaController();

		this.carregarComboBoxClientes();
		this.carregarComboBoxFilmes();

	}

	public Stage getJanelaLocacaoEdit() {
		return janelaLocacaoEdit;
	}

	public void setJanelaLocacaoEdit(Stage janelaLocacaoEdit) {
		this.janelaLocacaoEdit = janelaLocacaoEdit;
	}

	public void populaTela(Locacao locacao) {
		this.locacao = locacao;

		this.txtNome.setText(locacao.getDescricao());
		if (this.locacao.getDataemprestimo() != null) {
			this.dtpDataEmprestimo.setValue(this.locacao.getDataemprestimo().toLocalDate());
		}

		if (this.locacao.getCliente() != null) {
			this.cbxCliente.setValue(this.locacao.getCliente());
		}
		if (this.locacao.getFilme() != null) {
			this.cbxFilme.setValue(this.locacao.getFilme());
		}
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
			alerta.initOwner(this.janelaLocacaoEdit);
			alerta.setTitle("Dados inválidos!");
			alerta.setHeaderText("Favor corrigir a aseguintes informações");
			alerta.setContentText(mensagemErros);
			alerta.showAndWait();

			return false;
		}
	}

	public void carregarComboBoxClientes() {
		ObservableList<Cliente> observableListaCliente = FXCollections
				.observableArrayList(this.clienteListaController.retornaListagemCliente());

		this.cbxCliente.setItems(observableListaCliente);
	}

	public void carregarComboBoxFilmes() {
		ObservableList<Filme> observableListaFilme = FXCollections
				.observableArrayList(this.filmeListaController.retornaListagemFilme());

		this.cbxFilme.setItems(observableListaFilme);
	}

}
