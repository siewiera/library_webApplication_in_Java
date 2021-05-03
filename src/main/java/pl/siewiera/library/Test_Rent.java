package pl.siewiera.library;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.siewiera.library.repository_Rent.RentRepo;

@Component
public class Test_Rent {

    private RentRepo rentRepo;

    @Autowired
    public Test_Rent(RentRepo rentRepo) {

//        this.rentRepo = rentRepo;
//        Rent rent = new Rent();
//        rent.setBook(null);
//        rent.setReader(null);
//        rent.setDateOfRent(LocalDate.of(2008, 7, 12));
//        rent.setReturnDate(LocalDate.of(2008, 8, 12));
//        rentRepo.save(rent);

    }
}