package pl.siewiera.library.repository_Rent;

        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;
        import pl.siewiera.library.model.Rent;

@Repository
public interface RentRepo extends JpaRepository<Rent, Long> {

}