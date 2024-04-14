package TaskNest.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import TaskNest.api.constants.Prioridade;
import TaskNest.api.constants.Status;
import TaskNest.api.model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    
    @Query("SELECT t FROM tarefa t WHERE t.dataDeCriacao = :dataDeCriacao")
    List<Tarefa> findAllByDataCriacao(@Param("dataDeCriacao") LocalDate dataDeCriacao);

    @Query("SELECT v FROM tarefa v WHERE v.dataDeVencimento = :dataDeVencimento")
    List<Tarefa> findAllByDataVencimento(@Param("dataDeVencimento") LocalDate dataDeVencimento);

    @Query("SELECT p FROM tarefa p WHERE p.prioridade = :prioridade")
    List<Tarefa> findAllByPrioridade(@Param("prioridade") Prioridade prioridade);

    @Query("SELECT s FROM tarefa s WHERE s.status = :status")
    List<Tarefa> findAllByStatus(@Param("status") Status status);

    @Query("SELECT t FROM tarefa t WHERE t.titulo = :titulo")
    Optional<Tarefa> findByTitulo(@Param("titulo") String titulo);
}
