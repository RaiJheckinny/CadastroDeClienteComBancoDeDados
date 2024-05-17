import br.com.raijheckinny.dao.generic.jdbc.dao.ClienteaDAO;
import br.com.raijheckinny.dao.generic.jdbc.dao.IClienteDAO;
import br.com.raijheckinny.domin.Cliente;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ClienteTest {
    private IClienteDAO clienteDAO;

    @Test
    public void cadastrarTest() throws Exception {
        clienteDAO = new ClienteaDAO();

        Cliente cliente = new Cliente();
        cliente.setCodigo("10");
        cliente.setNome("Rai Jheckinny");
        Integer countCad = clienteDAO.cadastrar(cliente);
        Assert.assertTrue(countCad.equals(1));

        Cliente clienteBD = clienteDAO.buscar("10");
        Assert.assertNotNull(clienteBD);
        Assert.assertEquals(cliente.getCodigo(), clienteBD.getCodigo());
        Assert.assertEquals(cliente.getNome(), clienteBD.getNome());

        Integer contDel = clienteDAO.excluir(clienteBD);
        Assert.assertTrue(contDel.equals(1));

    }

    @Test
    public void alterarTest() throws Exception {
        clienteDAO = new ClienteaDAO();

        Cliente clienteBD = new Cliente();
        clienteBD.setNome("Rai Jheckinny");
        clienteBD.setCodigo("20");
        Integer countCad = clienteDAO.cadastrar(clienteBD);
        Assert.assertTrue(countCad.equals(1));


        Cliente clienteAlter = new Cliente();
        clienteBD.setNome("Pedro");
        clienteBD.setCodigo("20");

        Integer contAlter = clienteDAO.atualizar(clienteBD);
        Assert.assertTrue(contAlter.equals(1));


        Integer contDel = clienteDAO.excluir(clienteBD);
        Assert.assertTrue(contDel.equals(1));

    }
    @Test
    public void buscarTodos() throws Exception {
        clienteDAO = new ClienteaDAO();

        Cliente clienteBD1 = new Cliente();
        clienteBD1.setNome("Rai Jheckinny");
        clienteBD1.setCodigo("20");

        Integer countCad = clienteDAO.cadastrar(clienteBD1);
        Assert.assertTrue(countCad.equals(1));

        Cliente clienteBD2 = new Cliente();
        clienteBD2.setNome("Rai Jheckinny");
        clienteBD2.setCodigo("20");

        Integer countCad2 = clienteDAO.cadastrar(clienteBD2);
        Assert.assertTrue(countCad.equals(1));

        List<Cliente> clientesList = clienteDAO.buscarTodos();
        clientesList.forEach(element-> Assert.assertTrue(element instanceof Cliente));
    }
}
