package br.com.raijheckinny.dao.generic.jdbc.dao;

import br.com.raijheckinny.dao.generic.jdbc.ConnectionFactory;
import br.com.raijheckinny.domin.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteaDAO implements IClienteDAO {
    @Override
    public Integer cadastrar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            adicionarParametrosInsert(stm,cliente);
            return stm.executeUpdate();
        } catch (Exception e){
            throw e;
        } finally {
            closeConnection(connection,stm, null);
        }
    }

    @Override
    public Integer atualizar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlAlter();
            stm = connection.prepareStatement(sql);
            atualizarParametrosAlter(stm,cliente);
            return stm.executeUpdate();
        }catch(Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm, null);
        }
    }

    @Override
    public Cliente buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSearch();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSearch(stm, codigo);
            rs = stm.executeQuery();

            if(rs.next()){
                cliente = new Cliente();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cd = rs.getString("CODIGO");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cd);
            }
        }catch(Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm, null);
            return cliente;
        }
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlRemove();
            stm = connection.prepareStatement(sql);
            adicionarParametrosRemove(stm, cliente);
            return stm.executeUpdate();
        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm, null);
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Cliente> list = new ArrayList<>();
        Cliente cliente = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlAllSearch();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()){
                cliente = new Cliente();
                Long id = rs.getLong("ID");
                String nome = rs.getString("NOME");
                String cd = rs.getString("CODIGO");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cd);
                list.add(cliente);
            }
            return list;

        }catch (Exception e){
            throw e;
        }finally {
            closeConnection(connection,stm, null);
        }
    }

    private String getSqlAllSearch() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM tb_cliente");

        return sb.toString();
    }

    private void adicionarParametrosRemove(PreparedStatement stm, Cliente cliente) throws SQLException{
        stm.setString(1, cliente.getCodigo());
    }

    private String getSqlRemove() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM tb_cliente ");
        sb.append("WHERE CODIGO = ?;");

        return sb.toString();
    }

    private void adicionarParametrosSearch(PreparedStatement stm, String codigo) throws SQLException {
        stm.setString(1,codigo);
    }

    private String getSqlSearch() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM tb_cliente ");
        sb.append("WHERE CODIGO = ?;");

        return sb.toString();
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try{
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
            if(stm != null && !stm.isClosed()){
                stm.close();
            }
            if(connection != null && !stm.isClosed()){
                connection.close();
            }
        } catch (SQLException e1){
            e1.printStackTrace();
        }
    }

    private void adicionarParametrosInsert(PreparedStatement stm, Cliente cliente) throws SQLException {
        stm.setString(1, cliente.getCodigo());
        stm.setString(2, cliente.getNome());
    }

    private String getSqlInsert() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO tb_cliente (ID, CODIGO, NOME) ");
        sb.append("VALUES (nextval('sq_cliente'), ?, ?);");

        return sb.toString();
    }

    private void atualizarParametrosAlter(PreparedStatement stm, Cliente cliente)  throws SQLException  {
        stm.setString(1, cliente.getNome());
        stm.setString(2, cliente.getCodigo());
    }

    private String getSqlAlter() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE tb_cliente ");
        sb.append("SET NOME = ? ");
        sb.append("WHERE CODIGO = ?;");

        return sb.toString();
    }

}
