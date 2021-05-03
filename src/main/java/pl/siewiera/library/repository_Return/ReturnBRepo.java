package pl.siewiera.library.repository_Return;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.siewiera.library.model.ReturnB;

@Repository
public interface ReturnBRepo extends JpaRepository<ReturnB, Long> {

}