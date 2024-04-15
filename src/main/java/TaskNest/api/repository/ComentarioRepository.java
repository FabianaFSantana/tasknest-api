package TaskNest.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import TaskNest.api.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
}
