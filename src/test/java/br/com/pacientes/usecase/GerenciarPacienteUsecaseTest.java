package br.com.clientes.cadastro.usecase;

import br.com.clientes.cadastro.domain.Cliente;
import br.com.clientes.cadastro.gateway.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

 class GerenciarClienteUsecaseTest {
    @InjectMocks
    private GerenciarClienteUsecase gerenciarClienteUsecase;

    @Mock
    private ClienteGateway clienteGateway;

    @BeforeEach
    void setup() { MockitoAnnotations.openMocks(this); }

    @Test
     void deveCadastrarCliente() {
        Cliente cliente = new Cliente(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        );

        gerenciarClienteUsecase.cadastrarCliente(cliente);

        Mockito.verify(clienteGateway, Mockito.times(1)).cadastrarCliente(cliente);
    }

    @Test
     void deveBuscarClientePorNome() {
        String nome = "Anderson Rodrigues";

        List<Cliente> clientesEsperados = Arrays.asList(new Cliente(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        Mockito.when(clienteGateway.buscarClientePorNome(nome)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = gerenciarClienteUsecase.buscarClientePorNome(nome);

        assertEquals(clientesEsperados.get(0).getCpf(), clientesRetornados.get(0).getCpf());
        assertEquals(clientesEsperados.get(0).getNome(), clientesRetornados.get(0).getNome());
        assertEquals(clientesEsperados.get(0).getEndereco(), clientesRetornados.get(0).getEndereco());
        assertEquals(clientesEsperados.get(0).getCep(), clientesRetornados.get(0).getCep());
        Mockito.verify(clienteGateway, Mockito.times(1)).buscarClientePorNome(nome);
    }

    @Test
     void deveBuscarClientePorCpf() {
        String cpf = "19276445854";

        List<Cliente> clientesEsperados = Arrays.asList(new Cliente(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        Mockito.when(clienteGateway.buscarClientePorCpf(cpf)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = gerenciarClienteUsecase.buscarClientePorCpf(cpf);

        assertEquals(clientesEsperados.get(0).getCpf(), clientesRetornados.get(0).getCpf());
        assertEquals(clientesEsperados.get(0).getNome(), clientesRetornados.get(0).getNome());
        assertEquals(clientesEsperados.get(0).getEndereco(), clientesRetornados.get(0).getEndereco());
        assertEquals(clientesEsperados.get(0).getCep(), clientesRetornados.get(0).getCep());
        Mockito.verify(clienteGateway, Mockito.times(1)).buscarClientePorCpf(cpf);
    }

    @Test
     void deveBuscarClientePorCep() {
        String cep = "09981400";

        List<Cliente> clientesEsperados = Arrays.asList(new Cliente(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        Mockito.when(clienteGateway.buscarClientePorCep(cep)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = gerenciarClienteUsecase.buscarClientePorCep(cep);

        assertEquals(clientesEsperados.get(0).getCpf(), clientesRetornados.get(0).getCpf());
        assertEquals(clientesEsperados.get(0).getNome(), clientesRetornados.get(0).getNome());
        assertEquals(clientesEsperados.get(0).getEndereco(), clientesRetornados.get(0).getEndereco());
        assertEquals(clientesEsperados.get(0).getCep(), clientesRetornados.get(0).getCep());
        Mockito.verify(clienteGateway, Mockito.times(1)).buscarClientePorCep(cep);
    }

    @Test
     void deveAtualizarCliente() {
        String cpf = "19276445854";

        Cliente clienteAtualizado = new Cliente(
                cpf,
                "Anderson Rodrigues 2",
                "Rua Aura 2",
                "09981401"
        );

        Mockito.when(clienteGateway.atualizarCliente(cpf, clienteAtualizado)).thenReturn(Optional.of(clienteAtualizado));

        Optional<Cliente> clienteRetornado = gerenciarClienteUsecase.atualizarCliente(cpf, clienteAtualizado);

        assertTrue(clienteRetornado.isPresent());
        assertEquals(clienteAtualizado.getCpf(), clienteRetornado.get().getCpf());
        assertEquals(clienteAtualizado.getNome(), clienteRetornado.get().getNome());
        assertEquals(clienteAtualizado.getEndereco(), clienteRetornado.get().getEndereco());
        assertEquals(clienteAtualizado.getCep(), clienteRetornado.get().getCep());
        Mockito.verify(clienteGateway, Mockito.times(1)).atualizarCliente(cpf, clienteAtualizado);
    }

    @Test
     void deveRemoverCliente() {
        String cpf = "19276445854";

        gerenciarClienteUsecase.removerCliente(cpf);

        Mockito.verify(clienteGateway, Mockito.times(1)).removerCliente(cpf);
    }
}
