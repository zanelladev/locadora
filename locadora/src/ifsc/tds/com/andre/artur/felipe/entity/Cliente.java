package ifsc.tds.com.andre.artur.felipe.entity;

public class Cliente {

	private Long id;
	
	private String nome;
	
	//private String login;
	
	//private String email;
	
   	//private String dataCadastro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	
	@Override
	public String toString() {
		return this.nome;
	}

	/*public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
*/

	
	
	
	

	
	
	

}
