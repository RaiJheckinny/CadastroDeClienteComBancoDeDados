import br.com.raijheckinny.dao.generic.jdbc.dao.ClienteaDAO;
import br.com.raijheckinny.dao.generic.jdbc.dao.IClienteDAO;
import br.com.raijheckinny.domin.Cliente;
import org.junit.Assert;
import org.junit.Test;

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
}
