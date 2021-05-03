package pl.siewiera.library;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.siewiera.library.repository_Book.BookRepo;

@Component
public class Test_Book {

    private BookRepo bookRepo;

    @Autowired
    public Test_Book(BookRepo bookRepo) {

//        this.libraryRepo = libraryRepo;
//        Library library = new Library();
//        library.setTitle("Wiedźmin");
//        library.setAuthor("Andrzej Sapkowski");
//        library.setLibraryCategory(LibraryCategory.FANTASTYKA);
//        library.setReleaseDate(LocalDate.of(2008, 8, 12));
//        library.setEdition("super NOWA");
//        library.setImage("https://gamepedia.cursecdn.com/wiedzmin_gamepedia/thumb/9/90/Geralt_z_Rivii.png/264px-Geralt_z_Rivii.png?version=5e4e5785d51d1ba57be5d9f013ae33a2");
//        libraryRepo.save(library);

    }
}