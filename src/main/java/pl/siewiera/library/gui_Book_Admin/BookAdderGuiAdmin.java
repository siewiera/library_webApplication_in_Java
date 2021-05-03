package pl.siewiera.library.gui_Book_Admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Book;
import pl.siewiera.library.model.BookCategory;
import pl.siewiera.library.repository_Book.BookRepo;

import java.time.LocalDate;


@Route("add_book_Admin")
@StyleSheet("/css/style.css")
public class BookAdderGuiAdmin extends VerticalLayout {

    String tytul = "Dodaj książkę";

    @Autowired
    public BookAdderGuiAdmin(BookRepo bookRepo) {

        Icon logo = new Icon(VaadinIcon.ARROW_BACKWARD);
        logo.setSize("50px");
        logo.setColor("#2196f3");
        logo.getStyle().set("margin-right", "80px");

        Anchor powrot = new Anchor("admin", logo);

        HorizontalLayout naglowek = new HorizontalLayout();
        VerticalLayout menu = new VerticalLayout();
        VerticalLayout zawartosc = new VerticalLayout();
        HorizontalLayout body = new HorizontalLayout();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E").set("font-family", "Impact").set("font-size", "50px")
                .set("box-shadow", "0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3").set("color", "#255784");
        naglowek.setJustifyContentMode(JustifyContentMode.CENTER);


        menu.setWidth("10%");
        menu.setHeight("auto");
        menu.getStyle().set("margin-bottom", "20px");
        menu.setJustifyContentMode(JustifyContentMode.CENTER);


        zawartosc.setWidth("80%");
        zawartosc.setHeight("auto");
        zawartosc.getStyle().set("margin-top", "10px").set("margin-left", "10%");
        zawartosc.setJustifyContentMode(JustifyContentMode.BETWEEN);


        body.setWidth("100%");
        body.setHeight("auto");
        body.getStyle().set("margin-top", "15px").set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3").set("color", "#255784");
        body.setJustifyContentMode(JustifyContentMode.CENTER);

        Binder<Book> binder = new Binder<>(Book.class);

        TextField textFieldTitle = new TextField();
        textFieldTitle.setPlaceholder("Tytuł");
        textFieldTitle.getStyle().set("width", "40%").set("margin-left", "0px");
        textFieldTitle.setMaxLength(20);
        binder.forField(textFieldTitle)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaki")
                .bind(Book::getTitle, Book::setTitle);
        textFieldTitle.setErrorMessage("Za krótki tytuł!");

        TextField textFieldNameAuthor = new TextField();
        textFieldNameAuthor.setPlaceholder("Imię autora");
        textFieldNameAuthor.getStyle().set("width", "40%").set("margin-left", "30px");
        binder.forField(textFieldNameAuthor)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaki")
                .bind(Book::getName_author, Book::setName_author);
        textFieldNameAuthor.setErrorMessage("Za krótkie imię!");

        TextField textFieldSurnameAuthor = new TextField();
        textFieldSurnameAuthor.setPlaceholder("Nazwisko autora");
        textFieldSurnameAuthor.getStyle().set("width", "40%").set("margin-left", "60px");
        binder.forField(textFieldSurnameAuthor)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaki")
                .bind(Book::getSurname_author, Book::setSurname_author);
        textFieldSurnameAuthor.setErrorMessage("Za krótkie nazwisko!");

        ComboBox<BookCategory> bookCategoryComboBox = new ComboBox<>("", BookCategory.values());
        bookCategoryComboBox.setPlaceholder("Kategoria");
        bookCategoryComboBox.getStyle().set("width", "40%").set("margin-left", "90px");

        DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder("Data wydania");
        datePicker.getStyle().set("width", "40%").set("margin-left", "120px");
        datePicker.setMax(LocalDate.MAX.minusMonths(5));
        binder.forField(datePicker)
                .withValidator(min -> min.getYear() <= 2020, "Rok nie może być nowszy od 2020")
                .bind(Book::getReleaseDate, Book::setReleaseDate);
        datePicker.setErrorMessage("Data wydania nie może być z przyszłości :)");

        TextField textFieldEdition = new TextField();
        textFieldEdition.setPlaceholder("Wydawnictwo");
        textFieldEdition.getStyle().set("width", "40%").set("margin-left", "150px");
        binder.forField(textFieldEdition)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaki")
                .bind(Book::getEdition, Book::setEdition);
        textFieldEdition.setErrorMessage("Za krótka nazwa wydawnictwa!");

        TextArea textAreaDescription = new TextArea();
        textAreaDescription.getStyle().set("maxHeight", "150px");
        textAreaDescription.setPlaceholder("Opis, pisz tutaj ...");
        textAreaDescription.getStyle().set("width", "40%").set("margin-left", "180px");
        binder.forField(textAreaDescription)
                .withValidator(min -> min.length() >= 10, "Minimum 10 znaków opisu")
                .bind(Book::getDescription, Book::setDescription);
        textAreaDescription.setErrorMessage("Za krótki opis!");

        NumberField textFieldQuantity = new NumberField();
        textFieldQuantity.setPlaceholder("Ilość");
        textFieldQuantity.getStyle().set("width", "40%").set("margin-left", "210px");
        textFieldQuantity.setHasControls(true);
        textFieldQuantity.setValue(1d);
        textFieldQuantity.setMin(1);
        textFieldQuantity.clear();
        textFieldQuantity.setErrorMessage("ilość nie może byc równa lub mniejsza od zera!");

        TextField textFieldImage = new TextField();
        textFieldImage.setPlaceholder("Zdjęcie link");
        textFieldImage.getStyle().set("width", "40%").set("margin-left", "240px");

        Button button = new Button("Dodaj książkę");
        button.getStyle().set("width", "30%").set("margin-left", "120px");
        Button clearButton = new Button("Wyczyść pola", event -> {
            textFieldTitle.clear();
            textFieldNameAuthor.clear();
            textFieldSurnameAuthor.clear();
            textAreaDescription.clear();
            textFieldNameAuthor.clear();
            textFieldQuantity.clear();
            datePicker.clear();
            textFieldEdition.clear();
            bookCategoryComboBox.clear();
            textFieldImage.clear();
        });
        clearButton.getStyle().set("width", "30%").set("margin-left", "120px");

        button.addClickListener(clickEvent -> {
            if (textFieldTitle.getValue().length() >= 2) {
                if (textFieldNameAuthor.getValue().length() >= 2) {
                    if (textFieldSurnameAuthor.getValue().length() >= 2) {
                        if (bookCategoryComboBox.getValue() != null) {
                            if (datePicker.getValue() != null && datePicker.getValue().getYear()<=2020) {
                                if (textFieldEdition.getValue().length() >= 2) {
                                    if (textAreaDescription.getValue().length() >= 10) {
                                        if (textFieldQuantity.getValue() !=null && textFieldQuantity.getValue() >= 1) {

                                            Book book = new Book();
                                            book.setTitle(textFieldTitle.getValue());
                                            book.setName_author(textFieldNameAuthor.getValue());
                                            book.setSurname_author(textFieldSurnameAuthor.getValue());
                                            book.setBookCategory(bookCategoryComboBox.getValue());
                                            book.setReleaseDate(datePicker.getValue());
                                            book.setEdition(textFieldEdition.getValue());
                                            book.setDescription(textAreaDescription.getValue());
                                            book.setQuantity(Integer.valueOf(textFieldQuantity.getValue().intValue()));
                                            book.setImage(textFieldImage.getValue());
                                            if (book.getImage().length() <= 0) {
                                                book.setImage("https://static.thenounproject.com/png/3542-200.png");
                                            }

                                            bookRepo.save(book);
                                            Notification notification = new Notification(
                                                    "Dodano książkę o tytule: \"" + textFieldTitle.getValue() + "\" "+
                                                            " " + "autora: " + textFieldNameAuthor.getValue() + " " + textFieldSurnameAuthor.getValue(), 3000,
                                                    Notification.Position.TOP_START);
                                            notification.open();
                                            Anchor odswiez = new Anchor("delete_reader_Admin");

                                            clearButton.click();
                                        } else {
                                            Notification notification = new Notification(
                                                    "Ilość powinna być większa od zera!", 3000,
                                                    Notification.Position.TOP_START);
                                            notification.open();
                                        }
                                    } else {
                                        Notification notification = new Notification(
                                                "Za krótki opis!", 3000,
                                                Notification.Position.TOP_START);
                                        notification.open();
                                    }
                                } else {
                                    Notification notification = new Notification(
                                            "Za krótka nazwa edycji!", 3000,
                                            Notification.Position.TOP_START);
                                    notification.open();
                                }
                            } else {
                                Notification notification = new Notification(
                                        "Błędna data wydania!", 3000,
                                        Notification.Position.TOP_START);
                                notification.open();
                            }
                        } else {
                            Notification notification = new Notification(
                                    "Wybierz z opcji kategorie dodawanej pozycji!", 3000,
                                    Notification.Position.TOP_START);
                            notification.open();
                        }
                    } else {
                        Notification notification = new Notification(
                                "Za krótkie nazwisko!", 3000,
                                Notification.Position.TOP_START);
                        notification.open();
                    }
                } else {
                    Notification notification = new Notification(
                            "Za krótkie imię!", 3000,
                            Notification.Position.TOP_START);
                    notification.open();
                }
            } else {
                Notification notification = new Notification(
                        "Za krótki tytuł!", 3000,
                        Notification.Position.TOP_START);
                notification.open();
            }
        });

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(textFieldTitle, textFieldNameAuthor, textFieldSurnameAuthor, bookCategoryComboBox, datePicker,
                textFieldEdition, textAreaDescription, textFieldQuantity, textFieldImage);
        zawartosc.add(button, clearButton);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}

