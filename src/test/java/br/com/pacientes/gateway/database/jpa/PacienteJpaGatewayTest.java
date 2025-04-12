package br.com.clientes.cadastro.gateway.database.jpa;

import br.com.clientes.cadastro.domain.Cliente;
import br.com.clientes.cadastro.exception.ClienteNaoEncontradoException;
import br.com.clientes.cadastro.exception.ErroAcessarRepositorioException;
import br.com.clientes.cadastro.gateway.database.jpa.entity.ClienteEntity;
import br.com.clientes.cadastro.gateway.database.jpa.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteJpaGatewayTest {
    @InjectMocks
    private ClienteJpaGateway clienteJpaGateway;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarCliente() {
        Cliente cliente = new Cliente(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        );

        // Crie o clienteEntity esperado com base no cliente
        ClienteEntity clienteEntityEsperado = new ClienteEntity(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        );

        // Chame o método de cadastro do cliente
        clienteJpaGateway.cadastrarCliente(cliente);

        // Use o ArgumentCaptor para capturar o argumento passado para o save()
        ArgumentCaptor<ClienteEntity> captor = ArgumentCaptor.forClass(ClienteEntity.class);
        verify(clienteRepository, times(1)).save(captor.capture());

        // Obtenha o cliente capturado
        ClienteEntity clienteEntityCapturado = captor.getValue();

        // Verifique se o cliente capturado tem os mesmos valores que o esperado
        assertEquals(clienteEntityEsperado.getCpf(), clienteEntityCapturado.getCpf());
        assertEquals(clienteEntityEsperado.getNome(), clienteEntityCapturado.getNome());
        assertEquals(clienteEntityEsperado.getEndereco(), clienteEntityCapturado.getEndereco());
        assertEquals(clienteEntityEsperado.getCep(), clienteEntityCapturado.getCep());
    }

//    @Test
//    void deveCadastrarCliente() {
//        Cliente cliente = new Cliente(
//                "19276445854",
//                "Anderson Rodrigues",
//                "Rua Aura",
//                "09981-400"
//        );
//
//        ClienteEntity clienteEntity = new ClienteEntity(
//                "19276445854",
//                "Anderson Rodrigues",
//                "Rua Aura",
//                "09981-400"
//        );
//
//        clienteJpaGateway.cadastrarCliente(cliente);
//
//        Mockito.verify(clienteRepository, Mockito.times(1)).save(clienteEntity);
//    }

    @Test
    void deveBuscarClientePorNome() {
        String nome = "Anderson Rodrigues";

        List<ClienteEntity> clientesEsperados = Arrays.asList(new ClienteEntity(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        Mockito.when(clienteRepository.findByNome(nome)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = clienteJpaGateway.buscarClientePorNome(nome);

        assertEquals(clientesEsperados.get(0).getCpf(), clientesRetornados.get(0).getCpf());
        assertEquals(clientesEsperados.get(0).getNome(), clientesRetornados.get(0).getNome());
        assertEquals(clientesEsperados.get(0).getEndereco(), clientesRetornados.get(0).getEndereco());
        assertEquals(clientesEsperados.get(0).getCep(), clientesRetornados.get(0).getCep());
        Mockito.verify(clienteRepository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    void deveBuscarClientePorNome_LancarClienteNaoEncontradoException_QuandoClienteNaoForEncontrado() {
        String nome = "Anderson Rodrigues";

        Mockito.when(clienteRepository.findByNome(nome)).thenReturn(Collections.emptyList());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteJpaGateway.buscarClientePorNome(nome);
        });
        Mockito.verify(clienteRepository, Mockito.times(1)).findByNome(nome);
    }

    @Test
    void deveBuscarClientePorCep() {
        String cep = "09981400";

        List<ClienteEntity> clientesEsperados = Arrays.asList(new ClienteEntity(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        Mockito.when(clienteRepository.findByCep(cep)).thenReturn(clientesEsperados);

        List<Cliente> clientesRetornados = clienteJpaGateway.buscarClientePorCep(cep);

        assertEquals(clientesEsperados.get(0).getCpf(), clientesRetornados.get(0).getCpf());
        assertEquals(clientesEsperados.get(0).getNome(), clientesRetornados.get(0).getNome());
        assertEquals(clientesEsperados.get(0).getEndereco(), clientesRetornados.get(0).getEndereco());
        assertEquals(clientesEsperados.get(0).getCep(), clientesRetornados.get(0).getCep());
        Mockito.verify(clienteRepository, Mockito.times(1)).findByCep(cep);
    }

    @Test
    void deveBuscarClientePorCep_LancarClienteNaoEncontradoException_QuandoClienteNaoForEncontrado() {
        String cep = "09981400";

        Mockito.when(clienteRepository.findByCep(cep)).thenReturn(Collections.emptyList());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteJpaGateway.buscarClientePorCep(cep);
        });
        Mockito.verify(clienteRepository, Mockito.times(1)).findByCep(cep);
    }

//    @Test
//    void deveAtualizarCliente() {
//        String cpf = "19276445854";
//
//        Cliente clienteAtualizado = new Cliente(
//                cpf,
//                "Anderson Rodrigues 2",
//                "Rua Aura 2",
//                "09981401"
//        );
//
//        Optional<ClienteEntity> clienteAntigoRetornado = Optional.of(new ClienteEntity(
//                "19276445854",
//                "Anderson Rodrigues",
//                "Rua Aura",
//                "09981400"
//        ));
//
//        ClienteEntity clienteAtualizadoSalvo = new ClienteEntity(
//                cpf,
//                "Anderson Rodrigues 2",
//                "Rua Aura 2",
//                "09981401"
//        );
//
//        Mockito.when(clienteRepository.findByCpf(cpf)).thenReturn(clienteAntigoRetornado);
//        Mockito.when(clienteRepository.save(clienteAtualizadoSalvo)).thenReturn(clienteAtualizadoSalvo);
//
//        Optional<Cliente> clienteRetornado = clienteJpaGateway.atualizarCliente(cpf, clienteAtualizado);
//
//        assertTrue(clienteRetornado.isPresent());
//        assertEquals(clienteAtualizado.getCpf(), clienteRetornado.get().getCpf());
//        assertEquals(clienteAtualizado.getNome(), clienteRetornado.get().getNome());
//        assertEquals(clienteAtualizado.getEndereco(), clienteRetornado.get().getEndereco());
//        assertEquals(clienteAtualizado.getCep(), clienteRetornado.get().getCep());
//        Mockito.verify(clienteRepository, Mockito.times(1)).findByCpf(cpf);
//    }

    @Test
    void deveAtualizarCliente() {
        String cpf = "19276445854";

        Cliente clienteAtualizado = new Cliente(
                cpf,
                "Anderson Rodrigues 2",
                "Rua Aura 2",
                "09981401"
        );

        Optional<ClienteEntity> clienteAntigoRetornado = Optional.of(new ClienteEntity(
                "19276445854",
                "Anderson Rodrigues",
                "Rua Aura",
                "09981400"
        ));

        ClienteEntity clienteAtualizadoSalvo = new ClienteEntity(
                cpf,
                "Anderson Rodrigues 2",
                "Rua Aura 2",
                "09981401"
        );

        Mockito.when(clienteRepository.findByCpf(cpf)).thenReturn(clienteAntigoRetornado);
        Mockito.when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteAtualizadoSalvo);

        Optional<Cliente> clienteRetornado = clienteJpaGateway.atualizarCliente(cpf, clienteAtualizado);

        assertTrue(clienteRetornado.isPresent());
        assertEquals(clienteAtualizado.getCpf(), clienteRetornado.get().getCpf());
        assertEquals(clienteAtualizado.getNome(), clienteRetornado.get().getNome());
        assertEquals(clienteAtualizado.getEndereco(), clienteRetornado.get().getEndereco());
        assertEquals(clienteAtualizado.getCep(), clienteRetornado.get().getCep());

        // Verifique se o método save foi chamado com o cliente atualizado
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

    @Test
    void deveAtualizarCliente_LancarClienteNaoEncontradoException_QuandoClienteNaoForEncontrado() {
        String cpf = "19276445854";

        Cliente clienteAtualizado = new Cliente(
                cpf,
                "Anderson Rodrigues 2",
                "Rua Aura 2",
                "09981401"
        );

        Mockito.when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            clienteJpaGateway.atualizarCliente(cpf, clienteAtualizado);
        });
        Mockito.verify(clienteRepository, Mockito.times(1)).findByCpf(cpf);
    }

    @Test
    void deveRemoverCliente() {
        String cpf = "19276445854";

        clienteJpaGateway.removerCliente(cpf);

        Mockito.verify(clienteRepository, Mockito.times(1)).deleteByCpf(cpf);
    }

    @Test
    void criar_DeveLancarErroAoAcessarRepositorioException_QuandoOcorreErro() {
        // Arrange
        Cliente cliente = new Cliente();

        when(clienteRepository.save(any(ClienteEntity.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        // Act & Assert
        assertThrows(ErroAcessarRepositorioException.class, () -> clienteJpaGateway.cadastrarCliente(cliente));
        verify(clienteRepository, times(1)).save(any(ClienteEntity.class));
    }

}
