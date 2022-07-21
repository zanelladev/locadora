package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ifsc.tds.com.andre.artur.felipe.dao.LocacaoDAO;
import ifsc.tds.com.andre.artur.felipe.entity.Locacao;
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

public class LocacaoListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Locacao> tbvLocacao;

	@FXML
	private TableColumn<Locacao, Long> tbcCodigo;

	@FXML
	private TableColumn<Locacao, String> tbcNome;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetelhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblDataEmprestimo;

	@FXML
	private Label lblCliente;

	@FXML
	private Label lblFilme;

	@FXML
	private Label lblNomeValor;

	@FXML
	private Label lblDataEmprestimoValor;

	@FXML
	private Label lblClienteValor;

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

	private List<Locacao> listaLocacao;
	private ObservableList<Locacao> observableListaLocacao = FXCollections.observableArrayList();
	private LocacaoDAO locacaoDAO;
	public static final String LOCACAO_EDITAR = " - Editar";
	public static final String LOCACAO_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {

		Locacao locacao = this.tbvLocacao.getSelectionModel().getSelectedItem();

		if (locacao != null) {
			boolean btnConfirmarClick = this.onShowTelaLocacaoEditar(locacao, LocacaoListaController.LOCACAO_EDITAR);

			if (btnConfirmarClick) {
				this.getLocacaoDAO().update(locacao, null);
				this.carregarTableViewlLocacao();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo na tabela");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {

		Locacao locacao = this.tbvLocacao.getSelectionModel().getSelectedItem();

		if (locacao != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirmar a exclusão da caixa?\n" + locacao.getDescricao());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getLocacaoDAO().delete(locacao);
				this.carregarTableViewlLocacao();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo na tabela");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {
		Locacao locacao = new Locacao();

		boolean btnConfirmarClick = this.onShowTelaLocacaoEditar(locacao, LocacaoListaController.LOCACAO_INCLUIR);

		if (btnConfirmarClick) {
			this.getLocacaoDAO().save(locacao);
			this.carregarTableViewlLocacao();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.setLocacaoDAO(new LocacaoDAO());
		this.carregarTableViewlLocacao();
		this.selecionarItemTableViewLocacao(null);

		this.tbvLocacao.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewLocacao(newValue));
	}

	public LocacaoDAO getLocacaoDAO() {
		return locacaoDAO;
	}

	public void setLocacaoDAO(LocacaoDAO locacaoDAO) {
		this.locacaoDAO = locacaoDAO;
	}

	public List<Locacao> getListaLocacao() {
		return listaLocacao;
	}

	public void setListaLocacao(List<Locacao> listaLocacao) {
		this.listaLocacao = listaLocacao;
	}

	public ObservableList<Locacao> getObservableListaLocacao() {
		return observableListaLocacao;
	}

	public void setObservableListaLocacao(ObservableList<Locacao> observableListaLocacao) {
		this.observableListaLocacao = observableListaLocacao;
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de locação?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim, botaoNao);
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}

	public void carregarTableViewlLocacao() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaLocacao(this.getLocacaoDAO().getAll());
		this.setObservableListaLocacao(FXCollections.observableArrayList(this.getListaLocacao()));
		this.tbvLocacao.setItems(this.getObservableListaLocacao());

	}

	public void selecionarItemTableViewLocacao(Locacao locacao) {
		if (locacao != null) {
			this.lblNomeValor.setText(locacao.getDescricao());
			this.lblDataEmprestimoValor.setText(locacao.getDataEmprestimoFormatado());
			this.lblClienteValor.setText(locacao.getCliente().getNome());
			this.lblFilmeValor.setText(locacao.getFilme().getDescricao());

		} else {
			this.lblNomeValor.setText("");
			this.lblDataEmprestimoValor.setText("");
			this.lblClienteValor.setText("");
			this.lblFilmeValor.setText("");

		}
	}

	public boolean onShowTelaLocacaoEditar(Locacao locacao, String operacao) {
		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/LocacaoEdit.fxml"));
			Parent locacaoEditXML = loader.load();

			Stage janelaLocacaoEditar = new Stage();
			janelaLocacaoEditar.setTitle("Cadastro de revista" + operacao);
			janelaLocacaoEditar.initModality(Modality.APPLICATION_MODAL);
			janelaLocacaoEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene locacaoEditLayout = new Scene(locacaoEditXML);
			janelaLocacaoEditar.setScene(locacaoEditLayout);

			LocacaoEditController locacaoEditController = loader.getController();
			locacaoEditController.setJanelaLocacaoEdit(janelaLocacaoEditar);
			locacaoEditController.populaTela(locacao);

			janelaLocacaoEditar.showAndWait();
			return locacaoEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
