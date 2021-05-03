package pl.siewiera.library.gui_Rent_Admin;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Rent;
import pl.siewiera.library.repository_Rent.RentRepo;

import java.util.List;

// edycja kont uprawnienia admin
@Route("list_rent_Admin")
@StyleSheet("/css/style.css")
public class RentListAdmin extends VerticalLayout {

    String tytul = "Lista wypożyczeń";
    String wypozyczenie = "";

    @Autowired
    public RentListAdmin(RentRepo rentRepo) {

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

        List<Rent> rents = rentRepo.findAll();
        GridPro<Rent> gridPro = new GridPro<>(Rent.class);
        gridPro.setItems(rents);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.setMinHeight("auto");
        gridPro.setMaxHeight("1000px"); //maksymalna wysokość
        gridPro.setMinWidth("auto");
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSingleCellEdit(true);
        gridPro.setSelectionMode(Grid.SelectionMode.NONE);

        ListDataProvider<Rent> dataProvider = new ListDataProvider<>(rents);
        gridPro.setDataProvider(dataProvider);

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
        TextField readerField = new TextField();
        readerField.addValueChangeListener(event -> dataProvider.addFilter(
                rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getReader()), readerField.getValue())));
        readerField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(readerColumn).setComponent(readerField);
        readerField.setSizeFull();
        readerField.setPlaceholder("Filtr");

        // II filter book
        TextField bookField = new TextField();
        bookField.addValueChangeListener(event -> dataProvider.addFilter(
                rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getBook()), bookField.getValue())));
        bookField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(bookColumn).setComponent(bookField);
        bookField.setSizeFull();
        bookField.setPlaceholder("Filtr");

        // III filter quantity
        TextField quantityField = new TextField();
        quantityField.addValueChangeListener(event -> dataProvider
                .addFilter(rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getQuantity()), quantityField.getValue())));
        quantityField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(quantityColumn).setComponent(quantityField);
        quantityField.setSizeFull();
        quantityField.setPlaceholder("Filtr");

        // IV filter rent_date
        TextField rent_dateField = new TextField();
        rent_dateField.addValueChangeListener(event -> dataProvider
                .addFilter(rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getDateOfRent()), rent_dateField.getValue())));
        rent_dateField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(rent_dateColumn).setComponent(rent_dateField);
        rent_dateField.setSizeFull();
        rent_dateField.setPlaceholder("Filtr");

        // V filter return_date
        TextField return_dateField = new TextField();
        return_dateField.addValueChangeListener(event -> dataProvider
                .addFilter(rent1 -> StringUtils.containsIgnoreCase(String.valueOf
                        (rent1.getReturnDate()), return_dateField.getValue())));
        return_dateField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(return_dateColumn).setComponent(return_dateField);
        return_dateField.setSizeFull();
        return_dateField.setPlaceholder("Filtr");

        gridPro.removeColumnByKey("id");
        gridPro.removeColumnByKey("book");
        gridPro.removeColumnByKey("dateOfRent");
        gridPro.removeColumnByKey("quantity");
        gridPro.removeColumnByKey("reader");
        gridPro.removeColumnByKey("returnDate");

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(gridPro, readerField, bookField, quantityField, rent_dateField, return_dateField);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}