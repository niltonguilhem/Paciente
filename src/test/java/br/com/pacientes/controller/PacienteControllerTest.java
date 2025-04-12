package br.com.clientes.cadastro.controller;

import br.com.clientes.cadastro.controller.json.ClienteJson;
import br.com.clientes.cadastro.domain.Cliente;
import br.com.clientes.cadastro.usecase.GerenciarClienteUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockBean
   private GerenciarClienteUsecase gerenciarClienteUsecase;

   @Test
   void deveCadastrarCliente() throws Exception {
      ClienteJson clienteJson = new ClienteJson("19276445854", "Anderson Rodrigues", "Rua Aura", "09981-400");

      mockMvc.perform(post("/api/v1/clientes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(clienteJson)))
              .andExpect(status().isOk())
              .andExpect(content().string("Cliente cadastrado com sucesso!"));

      Mockito.verify(gerenciarClienteUsecase, Mockito.times(1)).cadastrarCliente(any(Cliente.class));
   }

   @Test
   void deveBuscarClientePorNome() throws Exception {
      String nome = "Anderson Rodrigues";

      List<Cliente> clientesEsperados = List.of(new Cliente("19276445854", nome, "Rua Aura", "09981-400"));

      Mockito.when(gerenciarClienteUsecase.buscarClientePorNome(nome)).thenReturn(clientesEsperados);

      mockMvc.perform(get("/api/v1/clientes/nome/{nome}", nome))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].cpf").value("19276445854"));

      Mockito.verify(gerenciarClienteUsecase).buscarClientePorNome(nome);
   }

   @Test
   void deveAtualizarCliente() throws Exception {
      String cpf = "19276445854";
      Cliente clienteAtualizado = new Cliente(cpf, "Anderson Rodrigues 2", "Rua Aura 2", "09981-401");

      ClienteJson clienteJson = new ClienteJson(cpf, "Anderson Rodrigues 2", "Rua Aura 2", "09981-401");

      // Corrigindo o mock para retornar um Optional
      Mockito.when(gerenciarClienteUsecase.atualizarCliente(eq(cpf), any(Cliente.class)))
              .thenReturn(Optional.of(clienteAtualizado));

      mockMvc.perform(put("/api/v1/clientes/{cpf}", cpf)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(clienteJson)))
              .andExpect(status().isOk()) // Espera código 200
              .andExpect(jsonPath("$.cpf").value(cpf))
              .andExpect(jsonPath("$.nome").value("Anderson Rodrigues 2"));

      Mockito.verify(gerenciarClienteUsecase).atualizarCliente(eq(cpf), any(Cliente.class));
   }

   @Test
   void deveRemoverCliente() throws Exception {
      String cpf = "19276445854";

      mockMvc.perform(delete("/api/v1/clientes/{cpf}", cpf))
              .andExpect(status().isNoContent());

      Mockito.verify(gerenciarClienteUsecase).removerCliente(cpf);
   }
}