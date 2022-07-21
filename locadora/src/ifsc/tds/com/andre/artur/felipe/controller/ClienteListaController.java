package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ifsc.tds.com.andre.artur.felipe.dao.ClienteDAO;
import ifsc.tds.com.andre.artur.felipe.entity.Cliente;
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

public class ClienteListaController implements Initializable {

	@FXML
	private AnchorPane pnlPrincipal;

	@FXML
	private SplitPane pnlDivisao;

	@FXML
	private AnchorPane pnlEsquerda;

	@FXML
	private TableView<Cliente> tbvCliente;

	@FXML
	private TableColumn<Cliente, Long> tbcCodigo;

	@FXML
	private TableColumn<Cliente, String> tbcNome;

	@FXML
	private AnchorPane pnlDireita;

	@FXML
	private Label lblDetelhes;

	@FXML
	private GridPane pnlDetalhes;

	@FXML
	private Label lblNome;

	@FXML
	private Label lblNomeValor;
	
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

	private List<Cliente> listaClientes;
	private ObservableList<Cliente> observableListaClientes = FXCollections.observableArrayList();
	private ClienteDAO clienteDAO;

	public static final String CLIENTE_EDITAR = " - Editar";
	public static final String CLIENTE_INCLUIR = " - Incluir";

	@FXML
	void onClickBtnEditar(ActionEvent event) {

		Cliente cliente = this.tbvCliente.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			boolean btnConfirmarClick = this.onShowTelaClienteEditar(cliente, ClienteListaController.CLIENTE_EDITAR);

			if (btnConfirmarClick) {
				this.getClienteDAO().update(cliente, null);
				this.carregarTableViewlClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo na tabela");
			alerta.show();
		}

	}

	@FXML
	void onClickBtnExcluir(ActionEvent event) {

		Cliente cliente = this.tbvCliente.getSelectionModel().getSelectedItem();

		if (cliente != null) {
			Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
			alerta.setTitle("Pergunta");
			alerta.setHeaderText("Confirmar a exclusão da caixa?\n" + cliente.getNome());

			ButtonType botaoNao = ButtonType.NO;
			ButtonType botaoSim = ButtonType.YES;
			alerta.getButtonTypes().setAll(botaoSim, botaoNao);
			Optional<ButtonType> resultado = alerta.showAndWait();

			if (resultado.get() == botaoSim) {
				this.getClienteDAO().delete(cliente);
				this.carregarTableViewlClientes();
			}
		} else {
			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setContentText("Por favor, selecione uma campo na tabela");
			alerta.show();
		}
	}

	@FXML
	void onClickBtnIncluir(ActionEvent event) {

		Cliente cliente = new Cliente();

		boolean btnConfirmarClick = this.onShowTelaClienteEditar(cliente, ClienteListaController.CLIENTE_INCLUIR);

		if (btnConfirmarClick) {
			this.getClienteDAO().save(cliente);
			this.carregarTableViewlClientes();
		}
	}

	public ClienteDAO getClienteDAO() {
		return clienteDAO;
	}

	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public ObservableList<Cliente> getObservableListaClientes() {
		return observableListaClientes;
	}

	public void setObservableListaClientes(ObservableList<Cliente> observableListaCliente) {
		this.observableListaClientes = observableListaCliente;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.setClienteDAO(new ClienteDAO()); 
		this.carregarTableViewlClientes();
		this.selecionarItemTableViewCliente(null);

		this.tbvCliente.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewCliente(newValue));
	}

	public void carregarTableViewlClientes() {
		this.tbcCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		this.setListaClientes(this.getClienteDAO().getAll());
		this.setObservableListaClientes(FXCollections.observableArrayList(this.getListaClientes()));
		this.tbvCliente.setItems(this.getObservableListaClientes());

	}

	public void selecionarItemTableViewCliente(Cliente cliente) {
		if (cliente != null) {
			this.lblNomeValor.setText(cliente.getNome());

		} else {
			this.lblNomeValor.setText("");

		}
	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do cadastro de cliente?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim, botaoNao);
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}

	public boolean onShowTelaClienteEditar(Cliente cliente, String operacao) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/ClienteEdit.fxml"));
			Parent clienteEditXML = loader.load();

			Stage janelaClienteEditar = new Stage();
			janelaClienteEditar.setTitle("Cadastro de cliente" + operacao);
			janelaClienteEditar.initModality(Modality.APPLICATION_MODAL);
			janelaClienteEditar.resizableProperty().setValue(Boolean.FALSE);

			Scene clienteEditLayout = new Scene(clienteEditXML);
			janelaClienteEditar.setScene(clienteEditLayout);

			ClienteEditController clienteEditController = loader.getController();
			clienteEditController.setJanelaClienteEdit(janelaClienteEditar);
			clienteEditController.populaTela(cliente);

			janelaClienteEditar.showAndWait();
			return clienteEditController.isOkClick();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public List<Cliente> retornaListagemCliente() {
		if (getClienteDAO() == null) {
			this.setClienteDAO(new ClienteDAO());
		}

		return this.getClienteDAO().getAll();
	}

}
