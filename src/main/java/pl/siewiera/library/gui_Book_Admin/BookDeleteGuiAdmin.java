package pl.siewiera.library.gui_Book_Admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Book;
import pl.siewiera.library.repository_Book.BookRepo;

import java.util.List;


@Route("delete_book_Admin")
@StyleSheet("/css/style.css")
public class BookDeleteGuiAdmin extends VerticalLayout {
    String tytul = "Usuwanie książki";//tytuł podstrony
    String książka = "";
    @Autowired
    public BookDeleteGuiAdmin(BookRepo bookRepo) {

        Icon back = new Icon(VaadinIcon.ARROW_BACKWARD);
        Icon refresh = new Icon(VaadinIcon.FILE_REFRESH);
        back.setSize("50px");
        back.setColor("#2196f3");

        refresh.setSize("50px");
        refresh.setColor("#2196f3");

        Anchor powrot = new Anchor("admin", back);
        Anchor odswiez = new Anchor("delete_book_Admin", refresh);

        HorizontalLayout naglowek = new HorizontalLayout();
        VerticalLayout menu = new VerticalLayout();
        VerticalLayout zawartosc = new VerticalLayout();
        HorizontalLayout body = new HorizontalLayout();
        FormLayout display = new FormLayout();
        FormLayout display1 = new FormLayout();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E").set("font-family", "Impact").set("font-size", "50px")
                .set("box-shadow", "0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3").set("color", "#255784");
        naglowek.setJustifyContentMode(JustifyContentMode.CENTER);

        menu.setWidth("10%");
        menu.setHeight("auto");
        menu.getStyle().set("margin-bottom", "15px");
        menu.setJustifyContentMode(JustifyContentMode.CENTER);

        zawartosc.setWidth("90%");
        zawartosc.setHeight("auto");
        zawartosc.getStyle().set("margin", "15px 15px 15px -10px");
        zawartosc.setJustifyContentMode(JustifyContentMode.BETWEEN);

        body.setWidth("100%");
        body.setHeight("auto");
        body.getStyle().set("margin-top", "15px").set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3").set("color", "#255784");
        body.setJustifyContentMode(JustifyContentMode.CENTER);

        List<Book> books = bookRepo.findAll();
        GridPro<Book> gridPro = new GridPro<>(Book.class);
        ListDataProvider<Book> dataProvider = new ListDataProvider<>(books);
        gridPro.setDataProvider(dataProvider);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.setMinHeight("auto");
        gridPro.setMaxHeight("1000px"); //maksymalna wysokość
        gridPro.setMinWidth("auto");
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSingleCellEdit(true);
        gridPro.setSelectionMode(Grid.SelectionMode.NONE);

        TextField deleteID = new TextField();
        deleteID.setValue("ID");
        deleteID.setReadOnly(true);
        deleteID.setWidth("80px");
        deleteID.getStyle().set("margin-right","10px");

        TextField deleteBook = new TextField();
        deleteBook.setValue("Książka");
        deleteBook.setReadOnly(true);
        deleteBook.setWidth("200px");
        deleteBook.getStyle().set("margin-right","10px");

        gridPro.addItemClickListener(
                event -> {
                    deleteID.setValue(String.valueOf(event.getItem().getId()));
                    deleteBook.setValue(String.valueOf(event.getItem().getTitle()));
                });
        deleteID.setPlaceholder("ID");

        Button delete = new Button("Usuń");
        delete.setWidth("100px");

        delete.addClickListener(clickEvent2 -> {
            if (deleteID.getValue().length() >= 1 && deleteID.getValue() != "ID") {

                Book book = new Book();
                Notification notification = new Notification();
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
                Label label = new Label("Usunąć czytelnika o ID: " + deleteID.getValue() + " ?");

                Button yes = new Button("Tak");
                yes.addClickListener(clickEvent -> {
                    book.setId(Long.parseLong(deleteID.getValue()));
                    bookRepo.delete(book);

                    Notification notification2 = new Notification(
                            "Usunięto czytelnika o ID: " + deleteID.getValue() + " !!!", 3000,
                            Notification.Position.TOP_START);
                    notification2.open();

                    Notification notification3 = new Notification(
                            "Odśwież stronę!", 3000,
                            Notification.Position.TOP_START);
                    notification3.open();

                    notification.close();
                });

                Button no = new Button("Nie",
                        e -> {notification.close();
                            deleteID.setValue("ID");
                            deleteBook.setValue("Książka");
                        });
                notification.add(label, yes, no);
                yes.getStyle().set("margin-right", "25px");

//               UI.getCurrent().getPage().reload();
            }
            //jeżeli zostawimy puste pole w okienku ID otrzymamy taki komunikat
            else {
                Notification notification = new Notification(
                        "Kliknij na wybranego czytelnika aby usunąć z bazy.", 3000,
                        Notification.Position.TOP_START);
                notification.open();
            }
        });

        if(books.size()==0){książka="książek";}
        if(books.size()==1){książka="książka";}
        if(books.size()>1 && books.size()<5){książka="książki";}
        if(books.size()>=5){książka="książek";}

        GridPro.Column<Book> id = gridPro //brak edycji
                .addColumn(Book::getId)
                .setHeader("ID")
                .setWidth("70px")
                .setFrozen(true); //"zamrożenie w miejscu"

        GridPro.Column<Book> title = gridPro
                .addColumn(Book::getTitle)
                .setHeader("Tytuł")
                .setWidth("150px")
                .setFrozen(true)
                .setFooter(books.size() + " " + książka);

        GridPro.Column<Book> name_author = gridPro
                .addColumn(Book::getName_author)
                .setHeader("Imię autora")
                .setWidth("150px")
                .setFrozen(false);

        GridPro.Column<Book> surname_author = gridPro
                .addColumn(Book::getSurname_author)
                .setHeader("Nazwisko autora")
                .setWidth("150px")
                .setFrozen(false);

        Grid.Column<Book> bookCategory = gridPro
                .addColumn(Book::getBookCategory)
                .setHeader("Kategoria")
                .setWidth("180px")
                .setFrozen(false);

        GridPro.Column<Book> releaseDate = gridPro
                .addColumn(Book::getReleaseDate)
                .setHeader("Data wydania")
                .setWidth("150px")
                .setFrozen(false);

        GridPro.Column<Book> edition = gridPro
                .addColumn(Book::getEdition)
                .setHeader("Wydanie")
                .setWidth("150px")
                .setFrozen(false);

        GridPro.Column<Book> description = gridPro
                .addColumn(Book::getDescription)
                .setHeader("Opis")
                .setWidth("300px")
                .setFrozen(false);

        GridPro.Column<Book> quantity = gridPro
                .addColumn(Book::getQuantity)
                .setHeader("Ilość")
                .setWidth("130px")
                .setFrozen(false);

        //stworzenie miejsca na filtry w nagłówku tabeli
        HeaderRow filterRow = gridPro.appendHeaderRow();

        // I filter title
        TextField textFieldTitle = new TextField();
        textFieldTitle.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase
                        (book.getTitle(), textFieldTitle.getValue())));
        textFieldTitle.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(title).setComponent(textFieldTitle);
        textFieldTitle.setSizeFull();
        textFieldTitle.setPlaceholder("Filtr");

        // II filter name_author
        TextField textFieldNameAuthor = new TextField();
        textFieldNameAuthor.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase
                        (book.getName_author(), textFieldNameAuthor.getValue())));
        textFieldNameAuthor.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(name_author).setComponent(textFieldNameAuthor);
        textFieldNameAuthor.setSizeFull();
        textFieldNameAuthor.setPlaceholder("Filtr");

        // III filter surname_author
        TextField textFieldSurnameAuthor = new TextField();
        textFieldSurnameAuthor.addValueChangeListener(event -> dataProvider.addFilter(
                book -> StringUtils.containsIgnoreCase
                        (book.getSurname_author(), textFieldSurnameAuthor.getValue())));
        textFieldSurnameAuthor.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(surname_author).setComponent(textFieldSurnameAuthor);
        textFieldSurnameAuthor.setSizeFull();
        textFieldSurnameAuthor.setPlaceholder("Filtr");

//        // IV filter bookCategory
        TextField bookCategoryComboBox = new TextField();
        bookCategoryComboBox.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(String.valueOf
                        (book.getBookCategory()), bookCategoryComboBox.getValue())));
        bookCategoryComboBox.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(bookCategory).setComponent(bookCategoryComboBox);
        bookCategoryComboBox.setSizeFull();
        bookCategoryComboBox.setPlaceholder("Filtr");

        gridPro.removeColumnByKey("id");
        gridPro.removeColumnByKey("title");
        gridPro.removeColumnByKey("name_author");
        gridPro.removeColumnByKey("surname_author");
        gridPro.removeColumnByKey("bookCategory");
        gridPro.removeColumnByKey("releaseDate");
        gridPro.removeColumnByKey("edition");
        gridPro.removeColumnByKey("description");
        gridPro.removeColumnByKey("quantity");
        gridPro.removeColumnByKey("image");
        gridPro.removeColumnByKey("rents");

        display1.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 4));
        display.setResponsiveSteps(
                new FormLayout.ResponsiveStep("50em", 5));
        display1.add(deleteID, deleteBook);
        display.add(display1, delete);

        naglowek.add(tytul);
        menu.add(odswiez, powrot);
        zawartosc.add(display, gridPro);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}
