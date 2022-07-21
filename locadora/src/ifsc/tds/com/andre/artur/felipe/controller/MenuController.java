package ifsc.tds.com.andre.artur.felipe.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	@FXML
	private MenuBar barMenu;

	@FXML
	private SeparatorMenuItem sepCadastro;

	@FXML
	private MenuItem mnoFilmes;

	@FXML
	private MenuItem mnoSobre;

	@FXML
	private MenuItem mnoEmprestimo;

	@FXML
	private VBox pnlPrincipal;

	@FXML
	private AnchorPane pnlMeio;

	@FXML
	private MenuItem mnoSair;

	@FXML
	private Menu mnuCadastro;

	@FXML
	private MenuItem mnoCliente;

	@FXML
	private Menu mnuAjuda;

	private Stage stage;

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	void onClickMnoCliente(ActionEvent event) {
		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/ClienteLista.fxml"));
			Parent clienteListaXML = loader.load();

			ClienteListaController clienteListaController = loader.getController();
			Scene clienteListaLayout = new Scene(clienteListaXML);
			this.getStage().setScene(clienteListaLayout);

			this.getStage().setTitle("Cadastro de cliente");

			this.getStage().setOnCloseRequest(e -> {
				if (clienteListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});

			this.stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoFilme(ActionEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/FilmeLista.fxml"));
			Parent filmeListaXML = loader.load();

			FilmeListaController filmeListaController = loader.getController();
			Scene filmeListaLayout = new Scene(filmeListaXML);
			this.getStage().setScene(filmeListaLayout);

			this.getStage().setTitle("Cadastro de filme");

			this.getStage().setOnCloseRequest(e -> {
				if (filmeListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});

			this.stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoEmprestimo(ActionEvent event) {

		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/ifsc/tds/com/andre/artur/felipe/view/LocacaoLista.fxml"));
			Parent locacaoListaXML = loader.load();

			LocacaoListaController locacaoListaController = loader.getController();
			Scene locacaoListaLayout = new Scene(locacaoListaXML);
			this.getStage().setScene(locacaoListaLayout);
			
			this.getStage().setTitle("Cadastro de locação");

			this.getStage().setOnCloseRequest(e -> {
				if (locacaoListaController.onCloseQuery()) {
					this.getStage().close();
				} else {
					e.consume();
				}
			});

			this.stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickMnoSair(ActionEvent event) {
		if (this.onCloseQuery()) {
			System.exit(0);
		} else {
			event.consume();
		}
	}

	@FXML
	void onClickMnoSobre(ActionEvent event) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle("Sobre");
		alerta.setHeaderText("Sistema desenvolvido por: Andre, Artur e Felipe - 2022.\nDesenvolvido com javaFX");
		alerta.showAndWait();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.configuraStage();

	}

	public boolean onCloseQuery() {
		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle("Pergunta");
		alerta.setHeaderText("Deseja sair do Sistema?");
		ButtonType botaoNao = ButtonType.NO;
		ButtonType botaoSim = ButtonType.YES;
		alerta.getButtonTypes().setAll(botaoSim, botaoNao);
		Optional<ButtonType> resultado = alerta.showAndWait();
		return resultado.get() == botaoSim ? true : false;
	}

	public void configuraStage() {
		this.setStage(new Stage());
		this.getStage().initModality(Modality.APPLICATION_MODAL);
		this.getStage().resizableProperty().setValue(Boolean.FALSE);
	}

}
