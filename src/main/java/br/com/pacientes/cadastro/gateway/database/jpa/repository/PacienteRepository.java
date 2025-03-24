package br.com.pacientes.cadastro.gateway.database.jpa.repository;

import br.com.pacientes.cadastro.gateway.database.jpa.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PacienteRepository extends JpaRepository<PacienteEntity, String>{
	Optional<PacienteEntity> findByCpf(String cpf);
	List<PacienteEntity> findByNome(String nome);

	void deleteByCpf(String cpf);
}
