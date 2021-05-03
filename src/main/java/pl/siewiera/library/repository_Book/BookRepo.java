package pl.siewiera.library.repository_Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.siewiera.library.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}