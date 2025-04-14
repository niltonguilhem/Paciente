package br.com.pacientes.controller;

import br.com.pacientes.cadastro.PacienteServiceApplication;
import br.com.pacientes.cadastro.controller.json.PacienteJson;
import br.com.pacientes.cadastro.domain.Paciente;
import br.com.pacientes.cadastro.usecase.GerenciarPacienteUsecase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = PacienteServiceApplication.class)
@AutoConfigureMockMvc
class PacienteControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private ObjectMapper objectMapper;

   @MockBean
   private GerenciarPacienteUsecase gerenciarPacienteUsecase;

   private PacienteJson pacienteJson;
   private Paciente paciente;

   @BeforeEach
   void setUp() {
      pacienteJson = new PacienteJson(
              "12345678901",
              "João da Silva",
              "30",
              "M",
              "São Paulo",
              "Rua A, 123",
              "12345-678",
              "joao@email.com",
              "11999999999"
      );

      paciente = new Paciente(
              "12345678901",
              "João da Silva",
              "30",
              "M",
              "São Paulo",
              "Rua A, 123",
              "12345-678",
              "joao@email.com",
              "11999999999"
      );
   }

   @Test
   void deveCadastrarPaciente() throws Exception {
      mockMvc.perform(post("/api/v1/pacientes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(pacienteJson)))
              .andExpect(status().isOk())
              .andExpect(content().string("Paciente cadastrado com sucesso!"));

      Mockito.verify(gerenciarPacienteUsecase, Mockito.times(1)).cadastrarPaciente(any(Paciente.class));
   }

   @Test
   void deveBuscarPacientePorCpf() throws Exception {
      Mockito.when(gerenciarPacienteUsecase.buscarPacientePorCpf("12345678901")).thenReturn(Optional.of(paciente));

      mockMvc.perform(get("/api/v1/pacientes/12345678901"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.cpf").value("12345678901"))
              .andExpect(jsonPath("$.nome").value("João da Silva"));

      Mockito.verify(gerenciarPacienteUsecase).buscarPacientePorCpf("12345678901");
   }

   @Test
   void deveBuscarPacientePorCpf_NotFound() throws Exception {
      Mockito.when(gerenciarPacienteUsecase.buscarPacientePorCpf("99999999999")).thenReturn(Optional.empty());

      mockMvc.perform(get("/api/v1/pacientes/99999999999"))
              .andExpect(status().isNotFound());

      Mockito.verify(gerenciarPacienteUsecase).buscarPacientePorCpf("99999999999");
   }

   @Test
   void deveBuscarPacientePorNome() throws Exception {
      Mockito.when(gerenciarPacienteUsecase.buscarPacientePorNome("João da Silva")).thenReturn(Collections.singletonList(paciente));

      mockMvc.perform(get("/api/v1/pacientes/nome/João da Silva"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[0].cpf").value("12345678901"))
              .andExpect(jsonPath("$[0].nome").value("João da Silva"));

      Mockito.verify(gerenciarPacienteUsecase).buscarPacientePorNome("João da Silva");
   }

   @Test
   void deveAtualizarPaciente() throws Exception {
      Mockito.when(gerenciarPacienteUsecase.atualizarPaciente(eq("12345678901"), any(Paciente.class)))
              .thenReturn(Optional.of(paciente));

      mockMvc.perform(put("/api/v1/pacientes/12345678901")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(pacienteJson)))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.cpf").value("12345678901"))
              .andExpect(jsonPath("$.nome").value("João da Silva"));

      Mockito.verify(gerenciarPacienteUsecase).atualizarPaciente(eq("12345678901"), any(Paciente.class));
   }

   @Test
   void deveAtualizarPaciente_NotFound() throws Exception {
      Mockito.when(gerenciarPacienteUsecase.atualizarPaciente(eq("99999999999"), any(Paciente.class)))
              .thenReturn(Optional.empty());

      mockMvc.perform(put("/api/v1/pacientes/99999999999")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(pacienteJson)))
              .andExpect(status().isNotFound());

      Mockito.verify(gerenciarPacienteUsecase).atualizarPaciente(eq("99999999999"), any(Paciente.class));
   }

   @Test
   void deveRemoverPaciente() throws Exception {
      mockMvc.perform(delete("/api/v1/pacientes/12345678901"))
              .andExpect(status().isNoContent());

      Mockito.verify(gerenciarPacienteUsecase).removerPaciente("12345678901");
   }

}