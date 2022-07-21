package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ifsc.tds.com.andre.artur.felipe.dao.FilmeDAO;
import ifsc.tds.com.andre.artur.felipe.entity.Filme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FilmeListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Filme> tbvFilme;

	@FXML
	private TableColumn<Filme, Long> tbcCodigo;

	@FXML
	private TableColumn<Filme, String> tbcFilme;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetelhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblFilmeValor;

	@FXML
	private ButtonBar barBotoes;

	@FXML
	private Button btnIncluir;

	@FXML
	private Tooltip tlpIncluir;

	@FXML
	private Button btnEditar;

	@FXML
	private Tooltip tlpEditar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Tooltip tlpExcluir;

	private List<Filme> listaFilmes;
	private ObservableList<Filme> observableListaFilmes = FXCollections.observableArrayList();
	private FilmeDAO filmeDAO;

	public static final String FILME_EDITAR = " - Editar";
	public static final String FILME_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {

		Filme filme = this.tbvFilme.getSelectionModel().getSelectedItem();

		if (filme != null) {
			boolean btnConfirmarClick = this.onShowTelaFilmeEditar(filme, FilmeListaController.FILME_EDITAR);

			if (btnConfirmarClick) {
				this.getFilmeDAO().update(filme, null);
				this.carregarTableViewlFilme();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo na tabela");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {

		Filme filme = this.tbvFilme.getSelectionModel().getSelectedItem();

		if (filme != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirmar a exclusão de filme?\n" + filme.getDescricao());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getFilmeDAO().delete(filme);
				this.carregarTableViewlFilme();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo de filme na tabela");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {

		Filme filme = new Filme();

		boolean btnConfirmarClick = this.onShowTelaFilmeEditar(filme, FilmeListaController.FILME_INCLUIR);

		if (btnConfirmarClick) {
			this.getFilmeDAO().save(filme);
			this.carregarTableViewlFilme();
		}
	}

	public List<Filme> getListaFilmes() {
		return listaFilmes;
	}

	public void setListaFilmes(List<Filme> listaFilmes) {
		this.listaFilmes = listaFilmes;
	}

	public ObservableList<Filme> getObservableListaFilmes() {
		return observableListaFilmes;
	}

	public void setObservableListaFilmes(ObservableList<Filme> observableListaFilmes) {
		this.observableListaFilmes = observableListaFilmes;
	}

	public FilmeDAO getFilmeDAO() {
		return filmeDAO;
	}

	public void setFilmeDAO(FilmeDAO filmeDAO) {
		this.filmeDAO = filmeDAO;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setFilmeDAO(new FilmeDAO()); 
		this.carregarTableViewlFilme();
		;
		this.selecionarItemTableViewFilme(null);

		this.tbvFilme.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewFilme(newValue));

	}

	public void carregarTableViewlFilme() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcFilme.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		this.setListaFilmes(this.getFilmeDAO().getAll());
		this.setObservableListaFilmes(FXCollections.observableArrayList(this.getListaFilmes()));
		this.tbvFilme.setItems(this.getObservableListaFilmes());

	}

	public void selecionarItemTableViewFilme(Filme filme) {
		if (filme != null) {
			this.lblFilmeValor.setText(filme.getDescricao());

		} else {
			this.lblFilmeValor.setText("");

		}
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de filmes?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim, botaoNao);
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}

	public boolean onShowTelaFilmeEditar(Filme filme, String operacao) {
		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/FilmeEdit.fxml"));
			Parent filmeEditXML = loader.load();

			Stage janelaFilmeEditar = new Stage();
			janelaFilmeEditar.setTitle("Cadastro de filme" + operacao);
			janelaFilmeEditar.initModality(Modality.APPLICATION_MODAL);
			janelaFilmeEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene filmeEditLayout = new Scene(filmeEditXML);
			janelaFilmeEditar.setScene(filmeEditLayout);

			FilmeEditController filmeEditController = loader.getController();
			filmeEditController.setJanelaFilmeEdit(janelaFilmeEditar);
			filmeEditController.populaTela(filme);

			janelaFilmeEditar.showAndWait();
			return filmeEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Filme> retornaListagemFilme() {
		if (getFilmeDAO() == null) {
			this.setFilmeDAO(new FilmeDAO());
		}

		return this.getFilmeDAO().getAll();
	}

}
