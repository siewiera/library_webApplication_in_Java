package pl.siewiera.library.gui_Rent_Admin;


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
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Rent;
import pl.siewiera.library.repository_Rent.RentRepo;

import java.util.ArrayList;
import java.util.List;

// edycja kont uprawnienia admin
@Route("delete_rent_Admin")
@StyleSheet("/css/style.css")
public class RentDeleteAdmin extends VerticalLayout {

    String tytul = "Usuwanie wypożyczenia";//tytuł podstrony
    String wypozyczenie = "";

    Select<String> roleSelect = new Select<>(); //wybór roli czytelnika

    //lista możliwych roli czytelnika
    List<String> role1 = new ArrayList<>();

    @Autowired
    public RentDeleteAdmin(RentRepo rentRepo) {

        Icon back = new Icon(VaadinIcon.ARROW_BACKWARD);
        Icon refresh = new Icon(VaadinIcon.FILE_REFRESH);
        back.setSize("50px");
        back.setColor("#2196f3");

        refresh.setSize("50px");
        refresh.setColor("#2196f3");

        Anchor powrot = new Anchor("admin", back);
        Anchor odswiez = new Anchor("delete_rent_Admin", refresh);

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

        List<Rent> rents = rentRepo.findAll();
        GridPro<Rent> gridPro = new GridPro<>(Rent.class);
        ListDataProvider<Rent> dataProvider = new ListDataProvider<>(rents);
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

        TextField idField = new TextField();
        idField.setValue("ID");
        idField.setReadOnly(true);
        idField.setWidth("85px");
        idField.getStyle().set("margin-right","10px");

        TextField readerField = new TextField();
        readerField.setValue("Czytelnik");
        readerField.setReadOnly(true);
        readerField.setWidth("200px");
        readerField.getStyle().set("margin-right","10px");

        TextField bookField = new TextField();
        bookField.setValue("Książka");
        bookField.setReadOnly(true);
        bookField.setWidth("200px");
        bookField.getStyle().set("margin-right","10px");

        gridPro.addItemClickListener(
                event -> {
                    idField.setValue(String.valueOf(event.getItem().getId()));
                    readerField.setValue(String.valueOf(event.getItem().getReader()));
                    bookField.setValue(String.valueOf(event.getItem().getBook()));
                });
        idField.setPlaceholder("ID");

        Button delete = new Button("Usuń");
        delete.setWidth("100px");
        Rent rent = new Rent();

        delete.addClickListener(clickEvent2 -> {
            if (idField.getValue().length() >= 1 && idField.getValue() != "ID") {

                Notification notification = new Notification();
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
                Label label = new Label("Usunąć wypożyczenie o ID: " + idField.getValue() + " ?");

                Button yes = new Button("Tak");
                yes.addClickListener(clickEvent -> {
                    rent.setId(Long.parseLong(idField.getValue()));
                    rentRepo.delete(rent);

                    Notification notification2 = new Notification(
                            "Usunięto wypożyczneie o ID: " + idField.getValue() + " !!!", 3000,
                            Notification.Position.TOP_START);
                    notification2.open();

                    notification.close();
                });

                Button no = new Button("Nie",
                        e -> {notification.close();
                            idField.setValue("ID");
                            readerField.setValue("Czytelnik");
                            bookField.setValue("Książka");
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

        //odmiana słowa wypozyczenie
        if (rents.size() == 1) { wypozyczenie = "wypożyczenie"; }
        if (rents.size() > 1 && rents.size() < 5) { wypozyczenie = "wypożyczenia"; }
        if (rents.size() >= 5) { wypozyczenie = "wypożyczeń"; }

        GridPro.Column<Rent> id = gridPro //brak edycji
                .addColumn(Rent::getId)
                .setHeader("ID")
                .setWidth("70px")
                .setFrozen(true); //"zamrożenie w miejscu"

        GridPro.Column<Rent> readerColumn = gridPro
                .addColumn(Rent::getReader)
                .setHeader("Czytelnik")
                .setWidth("150px")
                .setFrozen(true)
                .setFooter(rents.size() + " " + wypozyczenie);

        GridPro.Column<Rent> bookColumn = gridPro
                .addColumn(Rent::getBook)
                .setHeader("Książka")
                .setWidth("150px")
                .setFrozen(true);

        GridPro.Column<Rent> quantityColumn = gridPro
                .addColumn(Rent::getQuantity)
                .setHeader("Ilość")
                .setWidth("150px")
                .setFrozen(false);

        GridPro.Column<Rent> rent_dateColumn = gridPro
                .addColumn(Rent::getDateOfRent)
                .setHeader("Data wypoż.")
                .setWidth("180px")
                .setFrozen(false); //brak edycji

        GridPro.Column<Rent> return_dateColumn = gridPro
                .addColumn(Rent::getReturnDate)
                .setHeader("Data zwrotu")
                .setWidth("180px")
                .setFrozen(false);

        //stworzenie miejsca na filtry w nagłówku tabeli
        HeaderRow filterRow = gridPro.appendHeaderRow();

        // I filter reader
        TextField readerField2 = new TextField();
        readerField2.addValueChangeListener(event -> dataProvider.addFilter(
                rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getReader()), readerField2.getValue())));
        readerField2.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(readerColumn).setComponent(readerField2);
        readerField2.setSizeFull();
        readerField2.setPlaceholder("Filter");

        // II filter book
        TextField bookField2 = new TextField();
        bookField2.addValueChangeListener(event -> dataProvider.addFilter(
                rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getBook()), bookField2.getValue())));
        bookField2.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(bookColumn).setComponent(bookField2);
        bookField2.setSizeFull();
        bookField2.setPlaceholder("Filter");

        gridPro.removeColumnByKey("id");
        gridPro.removeColumnByKey("book");
        gridPro.removeColumnByKey("dateOfRent");
        gridPro.removeColumnByKey("quantity");
        gridPro.removeColumnByKey("reader");
        gridPro.removeColumnByKey("returnDate");


        display1.setResponsiveSteps(
                new FormLayout.ResponsiveStep("50em", 6));
        display.setResponsiveSteps(
                new FormLayout.ResponsiveStep("75em", 4));
        display1.add(idField, readerField, bookField);
        display.add(display1, delete);

        naglowek.add(tytul);
        menu.add(odswiez, powrot);
        zawartosc.add(display, gridPro);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}