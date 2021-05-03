package pl.siewiera.library.gui_Book_Admin;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Book;
import pl.siewiera.library.repository_Book.BookRepo;

import java.util.List;

@Route("list_book_Admin")
@StyleSheet("/css/style.css")
public class BookListGuiAdmin  extends VerticalLayout {
    String tytul = "Lista książek";

    String książka="";
    @Autowired
    public BookListGuiAdmin(BookRepo bookRepo) {

        Icon logo = new Icon(VaadinIcon.ARROW_BACKWARD);
        logo.setSize("50px");
        logo.setColor("#2196f3");

        Anchor powrot = new Anchor("admin", logo);

        HorizontalLayout naglowek = new HorizontalLayout();
        VerticalLayout menu = new VerticalLayout();
        VerticalLayout  zawartosc = new VerticalLayout ();
        HorizontalLayout  body = new HorizontalLayout ();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E").set("font-family", "Impact").set("font-size","50px")
                .set("box-shadow","0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3").set("color","#255784");
        naglowek.setJustifyContentMode(JustifyContentMode.CENTER);

        menu.setWidth("10%");
        menu.setHeight("auto");
        menu.getStyle().set("margin-bottom","20px");
        menu.setJustifyContentMode(JustifyContentMode.CENTER);

        zawartosc.setWidth("90%");
        zawartosc.setHeight("auto");
        zawartosc.getStyle().set("margin","15px 15px 15px -10px");
        zawartosc.setJustifyContentMode(JustifyContentMode.BETWEEN);

        body.setWidth("100%");
        body.setHeight("auto");
        body.getStyle().set("margin-top","15px").set("margin-bottom","10px")
                .set("box-shadow","0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3").set("color","#255784");
        body.setJustifyContentMode(JustifyContentMode.CENTER);

        List<Book> books = bookRepo.findAll();
        GridPro<Book> gridPro = new GridPro<>(Book.class);
        gridPro.setItems(books);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.setMinHeight("auto");
        gridPro.setMaxHeight("1000px"); //maksymalna wysokość
        gridPro.setMinWidth("auto");
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSingleCellEdit(true);
        gridPro.setSelectionMode(Grid.SelectionMode.NONE);
        Binder<Book> binder = new Binder<>(Book.class);
        Editor<Book> editor = gridPro.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        //informacja po kliknieciu na książkę
        gridPro.setItemDetailsRenderer(TemplateRenderer.<Book>of(
                "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box; word-wrap: break-word;'>"
                        + "<div><b>Autor: [[item.imie_autora]] [[item.nazwisko_autora]], tytuł: ''[[item.tytul]]'', "
                        + "kategoria: [[item.kategoria]], opis: [[item.opis]].</b></div>"
                        + "<div><img style='height: 80px; width: 80px;' src='[[item.zdjecie]]'/></div>"
                        + "</div>")
                .withProperty("id", Book::getId)
                .withProperty("imie_autora", Book::getName_author)
                .withProperty("nazwisko_autora", Book::getSurname_author)
                .withProperty("tytul", Book::getTitle)
                .withProperty("kategoria", Book::getBookCategory)
                .withProperty("opis", Book::getDescription)
                .withProperty("zdjecie", Book::getImage)
                .withEventHandler("handleClick", book -> {
                    gridPro.getDataProvider().refreshItem(book);
                }));

        ListDataProvider<Book> dataProvider = new ListDataProvider<>(books);
        gridPro.setDataProvider(dataProvider);

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

        // V filter releaseDate
        TextField datePicker = new TextField();
        datePicker.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(String.valueOf
                        (book.getReleaseDate()), datePicker.getValue())));
        datePicker.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(releaseDate).setComponent(datePicker);
        datePicker.setSizeFull();
        datePicker.setPlaceholder("Filtr");

        // VI filter edition
        TextField textFieldEdition = new TextField();
        textFieldEdition.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase
                        (book.getEdition(), textFieldEdition.getValue())));
        textFieldEdition.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(edition).setComponent(textFieldEdition);
        textFieldEdition.setSizeFull();
        textFieldEdition.setPlaceholder("Filtr");

        // VII filter quantity
        TextField textFieldQuantity = new TextField();
        textFieldQuantity.addValueChangeListener(event -> dataProvider
                .addFilter(book -> StringUtils.containsIgnoreCase(String.valueOf
                        (book.getQuantity()), textFieldQuantity.getValue())));
        textFieldQuantity.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(quantity).setComponent(textFieldQuantity);
        textFieldQuantity.setSizeFull();
        textFieldQuantity.setPlaceholder("Filtr");

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

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(gridPro,textFieldTitle, textFieldNameAuthor, textFieldSurnameAuthor, bookCategoryComboBox
                ,datePicker, textFieldEdition, textFieldQuantity);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}