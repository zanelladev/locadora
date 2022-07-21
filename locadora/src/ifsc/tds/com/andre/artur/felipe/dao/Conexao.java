 package ifsc.tds.com.andre.artur.felipe.dao;

 import java.sql.Connection; 
 import java.sql.SQLException;
 import java.sql.DriverManager;
 
public class Conexao {

	private static final String LOGIN_BANCO = "root";
	private static final String SENHA_BANCO = "";
	private static final String URL_BANCO = "jdbc:mysql://localhost:3306/locadora?serverTimezone=UTC";
	
	
    
	public Connection getConnection() {
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(Conexao.URL_BANCO, Conexao.LOGIN_BANCO, Conexao.SENHA_BANCO);

		} catch (SQLException e) {
			System.out.println("Erro de conexão com o banco de dados. Erro:	" + e);
		}catch(ClassNotFoundException e) {
			System.out.println("Não foi possível carregar a classe JDBC MySQL . Erro:	" + e);
		}catch(Exception e) {
			System.out.println("Erro geral. Erro:	" + e);
		}
		return conexao;
	}	

}


