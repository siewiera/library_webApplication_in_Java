package pl.siewiera.library.gui_Return_Admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Book;
import pl.siewiera.library.model.Rent;
import pl.siewiera.library.model.ReturnB;
import pl.siewiera.library.repository_Book.BookRepo;
import pl.siewiera.library.repository_Reader.ReaderRepo;
import pl.siewiera.library.repository_Rent.RentRepo;
import pl.siewiera.library.repository_Return.ReturnBRepo;

import java.util.List;

//system wypożyczeń
@Route("add_return_Admin")
@StyleSheet("/css/style.css")
public class ReturnAdderGuiAdmin extends VerticalLayout {
    String tytul = "Zwracanie";//tytuł podstrony

    @Autowired
    public ReturnAdderGuiAdmin(ReaderRepo readerRepo, BookRepo bookRepo, RentRepo rentRepo, ReturnBRepo returnRepo) {

        Icon back = new Icon(VaadinIcon.ARROW_BACKWARD);
        Icon refresh = new Icon(VaadinIcon.FILE_REFRESH);
        back.setSize("50px");
        back.setColor("#2196f3");

        refresh.setSize("50px");
        refresh.setColor("#2196f3");

        Anchor powrot = new Anchor("admin", back);
        Anchor odswiez = new Anchor("add_return_Admin", refresh);

        HorizontalLayout naglowek = new HorizontalLayout();
        VerticalLayout menu = new VerticalLayout();
        VerticalLayout zawartosc = new VerticalLayout();
        HorizontalLayout body = new HorizontalLayout();
        FormLayout display = new FormLayout();
        FormLayout display1 = new FormLayout();
        FormLayout buttonDisplay = new FormLayout();
        display1.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 2));
        display.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 3));
        buttonDisplay.setResponsiveSteps(
                new FormLayout.ResponsiveStep("50em", 5));

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

        //lista wszystkich czytelników z listy wypozyczeń
        List<Rent> rents = rentRepo.findAll();
        Select<Rent> rentSelect = new Select<>();
        rentSelect.setTextRenderer(rent1 -> rent1.getReader().getSurname());
        rentSelect.setReadOnly(false);
        rentSelect.setItems(rents);
        rentSelect.setPlaceholder("Czytelnik");

        //lista wszystkich książek z listy wypozyczen
//        List<Book> books = bookRepo.findAll();
        Select<Rent> rentSelect1 = new Select<>();
        rentSelect1.setTextRenderer(rent1 -> rent1.getBook().getTitle());
        rentSelect1.setItems(rents);
        rentSelect1.setPlaceholder("Książka");

        //ilość zwarana książek
        NumberField quantity = new NumberField("Ilość sztuk");
        quantity.setHasControls(true);
        quantity.setReadOnly(true);
        quantity.setValue(1d); //ilość zmienia się o 1
        quantity.setMin(1);
        quantity.clear();

        DatePicker datePickerReturn = new DatePicker("Termin zwrotu");
        datePickerReturn.setReadOnly(true); //okno tylko do odczytu

        GridPro<Rent> gridPro = new GridPro<>(Rent.class);
        gridPro.setItems(rents);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.setMinHeight("100px"); //maksymalna wysokość
        gridPro.setMaxHeight("300px"); //maksymalna wysokość
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSingleCellEdit(true);
        gridPro.setSelectionMode(Grid.SelectionMode.NONE);

//        //informacja po kliknieciu na książkę
//        gridPro.setItemDetailsRenderer(TemplateRenderer.<Rent>of(
//                "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box; word-wrap: break-word;'>"
//                        + "<div>Tytuł: <b>[[item.tytul]]</b>, edycja: <b>[[item.edycja]]</b>, opis: <b>[[item.opis]]</b>.</div>"
//                        + "<div><img style='height: 80px; width: 80px;' src='[[item.zdjecie]]'/></div>")
//                .withProperty("id", Rent::getId)
//                .withProperty("tytul", Rent::getBook)
//                .withProperty("edycja", Rent::get)
//                .withProperty("opis", Rent::getDescription)
//                .withProperty("zdjecie", Rent::getImage)
//                .withEventHandler("handleClick", rent -> {
//                    gridPro.getDataProvider().refreshItem(rent);
//                }));

        GridPro.Column<Rent> id = gridPro //brak edycji
                .addColumn(Rent::getId)
                .setHeader("ID")
                .setWidth("70px")
                .setFrozen(true);

        GridPro.Column<Rent> title = gridPro
                .addColumn(rent -> rent.getBook().getTitle())
                .setHeader("Tytuł")
                .setWidth("140px")
                .setFrozen(true);

        GridPro.Column<Rent> n_author = gridPro
                .addColumn(rent -> rent.getBook().getName_author())
                .setHeader("Imię autora")
                .setWidth("140px")
                .setFrozen(true);

        GridPro.Column<Rent> s_author = gridPro
                .addColumn(rent -> rent.getBook().getSurname_author())
                .setHeader("Nazwisko autora")
                .setWidth("140px")
                .setFrozen(true);

        GridPro.Column<Rent> b_quantity = gridPro
                .addColumn(Rent::getQuantity)
                .setHeader("Ilość")
                .setWidth("130px")
                .setFrozen(false);

        GridPro.Column<Rent> bookCategory = gridPro
                .addColumn(rent -> rent.getBook().getBookCategory())
                .setHeader("Kategoria")
                .setWidth("130px")
                .setFrozen(false);

        GridPro.Column<Rent> releaseDate = gridPro
                .addColumn(rent -> rent.getBook().getReleaseDate())
                .setHeader("Data wydania")
                .setWidth("120px")
                .setFrozen(false);
//data ustalonego zwrotu pozycji
        GridPro.Column<Rent> returnDate = gridPro
                .addColumn(Rent::getReturnDate)
                .setHeader("Data wydania")
                .setWidth("120px")
                .setFrozen(false);

        Button cleanField = new Button("Wyszukaj ponownie"); //czyszczenie labelów
        cleanField.addClickListener(event -> {
            UI.getCurrent().getPage().reload();
        });

        //przycisk wypożyczenia
        Button buttonBorrow = new Button("Wypożycz");
        buttonBorrow.getStyle().set("width", "15em").set("background", "#1676f3").set("border-radius", "20px").set("float", "right")
                .set("color", "white");

//        datePickerRent.setValue(LocalDate.now());
//        datePickerReturn.setValue(datePickerRent.getValue().plusMonths(2).plusDays(20)); // automatczne wyliczenie terminu oddania książki (2miesiące 20dni)

        Rent rent = new Rent();

        rentSelect1.addValueChangeListener(event1 -> {
            quantity.clear();
//            rent.setBook(rentSelect1.getValue());
            gridPro.setItems(rentSelect1.getValue());

            ListBox<Book> listBox = new ListBox<>();
            listBox.setItems(rent.getBook()); //z listy książek wyszukuje pobraną z selecta bookSelect
            quantity.setReadOnly(false);

            if (rent.getBook().getQuantity() <= 0) {

                quantity.setMin(0);
                quantity.setMax(0);

                Notification notification = new Notification(
                        "Brak wystarczającej ilości!", 3000,
                        Notification.Position.TOP_START);
                notification.open();
            } else {
                quantity.setMax(rent.getBook().getQuantity()); //maksymalnie możemy wypożyczyć tyle danej książki ile jest dostęptne

                if (rentSelect.getValue().getReader().getSurname().length() <= 0) {

                    Notification notification = new Notification(
                            "Wybierz inną książkę", 3000,
                            Notification.Position.TOP_START);
                    notification.open();

                } else {
                    rentSelect.setEnabled(false);
                    rentSelect1.setEnabled(false);

                    listBox.setRenderer(new ComponentRenderer<>(book2 -> {


                        buttonBorrow.addClickListener(event2 -> {
//                        quantity.clear();
                            if (quantity.getValue() == null) {
                                Notification notification = new Notification(
                                        "Podaj ilość sztuk wypożyczeń!", 3000,
                                        Notification.Position.TOP_START);
                                notification.open();
                            }
                            //jeżeli któryś z warunków równy jest true wtedy wykona się ten blok
                            else if (quantity.getValue() <= 0) {

                                Notification notification = new Notification(
                                        "Liczba wypożyczanych książek musi być większa od 0", 3000,
                                        Notification.Position.TOP_START);
                                notification.open();

                            } else if (quantity.getValue() > rent.getBook().getQuantity()) {

                                Notification notification = new Notification(
                                        "Brak ilości na stanie", 3000,
                                        Notification.Position.TOP_START);
                                notification.open();
                            } else {
                                ReturnB returnB = new ReturnB();
//                                returnB.setRent(rent.getBook());
                                rent.setReader(rentSelect.getValue().getReader());
                                rent.setBook(rentSelect1.getValue().getBook());
                                rent.setQuantity(Integer.valueOf(quantity.getValue().intValue()));
//                                rent.setDateOfRent(datePickerRent.getValue());
                                rent.setReturnDate(datePickerReturn.getValue());

                                rent.getBook().setQuantity(rent.getBook().getQuantity() + rent.getQuantity());
                                rentRepo.saveAll(rents);
                                gridPro.getDataProvider().refreshItem(rent); //odświeżenie tablicy
                                bookRepo.saveAndFlush(book2);

                                UI.getCurrent().getPage().reload();
                            }
                        });

                        Div qwer = new Div();
                        return qwer;
                    }));
                }
            }
        });

//        gridPro.removeColumnByKey("id");
//        gridPro.removeColumnByKey("title");
//        gridPro.removeColumnByKey("name_author");
//        gridPro.removeColumnByKey("surname_author");
//        gridPro.removeColumnByKey("bookCategory");
//        gridPro.removeColumnByKey("releaseDate");
//        gridPro.removeColumnByKey("edition");
//        gridPro.removeColumnByKey("description");
//        gridPro.removeColumnByKey("quantity");
//        gridPro.removeColumnByKey("image");
//        gridPro.removeColumnByKey("rents");

        display1.add(rentSelect, rentSelect1);
        display.add(datePickerReturn, quantity);
        buttonDisplay.add(buttonBorrow, cleanField);

        naglowek.add(tytul);
        menu.add(odswiez, powrot);
        zawartosc.add(display1, display, buttonDisplay, gridPro);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}