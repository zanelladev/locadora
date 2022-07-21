package ifsc.tds.com.andre.artur.felipe.entity;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class Locacao {

	private Long id;
	private String descricao;
	private Date dataemprestimo;
//	private Date datadevolucao;
	private Cliente cliente;
	private Filme filme;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataemprestimo() {
		return dataemprestimo;
	}
	public void setDataemprestimo(Date dataemprestimo) {
		this.dataemprestimo = dataemprestimo;
	}
/*	public Date getDatadevolucao() {
		return datadevolucao;
	}
	public void setDatadevolucao(Date datadevolucao) {
		this.datadevolucao = datadevolucao;
	}*/
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	
	
	public String getDataEmprestimoFormatado() {
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return this.dataemprestimo.toLocalDate().format(dataFormatada).toString(); 
	}
	
	

	
}
