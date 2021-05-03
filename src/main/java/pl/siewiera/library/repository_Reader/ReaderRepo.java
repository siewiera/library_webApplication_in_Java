package pl.siewiera.library.repository_Reader;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.siewiera.library.model.Reader;

import java.util.List;

@Repository
public interface ReaderRepo extends JpaRepository<Reader, Long> {


    List<Reader> findOneByEmail(String email);

    Reader findByEmail(String email);
}