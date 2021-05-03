package pl.siewiera.library.gui_Reader_User;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.siewiera.library.model.Reader;
import pl.siewiera.library.repository_Reader.ReaderRepo;

import java.util.List;
import java.util.stream.Collectors;

@Route("delete_reader_User")
@StyleSheet("/css/style.css")
public class ReaderDeleteUser extends VerticalLayout {

    String tytul = "Usuwanie konta";//tytuł podstrony
    String czytelnik = ""; //zmienna w która będziemy wrzucać odmiany słowa czytelnik

    @Autowired
    public ReaderDeleteUser(ReaderRepo readerRepo) {

        Icon back = new Icon(VaadinIcon.ARROW_BACKWARD);
        Icon refresh = new Icon(VaadinIcon.FILE_REFRESH);
        back.setSize("50px");
        back.setColor("#2196f3");

        refresh.setSize("50px");
        refresh.setColor("#2196f3");

        Anchor powrot = new Anchor("menu_Gui", back);
        Anchor odswiez = new Anchor("delete_reader_User", refresh);

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

        //filtrujemy uzytkownika (po zalogowaniu)
        List<Reader> readers = readerRepo.findAll();
        List<Reader> collect = readers.stream()
                .filter(reader -> reader.toString().equals(SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal().toString()))
                .collect(Collectors.toList());
        Grid<Reader> gridPro = new Grid<>(Reader.class);
        gridPro.setItems(collect);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.getStyle().set("height","200px");
        gridPro.setMaxHeight("1000px"); //maksymalna wysokość
        gridPro.setMinWidth("auto");
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSelectionMode(Grid.SelectionMode.NONE);

        TextField deleteID = new TextField();
        deleteID.setValue("ID");
        deleteID.setReadOnly(true);
        deleteID.setWidth("80px");
        deleteID.getStyle().set("margin-right","10px");

        TextField deleteName = new TextField();
        deleteName.setValue("Imię");
        deleteName.setReadOnly(true);
        deleteName.setWidth("200px");
        deleteName.getStyle().set("margin-right","10px");

        TextField deleteSurname = new TextField();
        deleteSurname.setValue("Nazwisko");
        deleteSurname.setReadOnly(true);
        deleteSurname.setWidth("200px");
        deleteSurname.getStyle().set("margin-right","10px");

        gridPro.addItemClickListener(
                event -> {
                    deleteID.setValue(String.valueOf(event.getItem().getId()));
                    deleteName.setValue(String.valueOf(event.getItem().getName()));
                    deleteSurname.setValue(String.valueOf(event.getItem().getSurname()));
                });
        deleteID.setPlaceholder("ID");

        Button delete = new Button("Usuń");
        delete.setWidth("100px");
        Reader reader = new Reader();

        delete.addClickListener(clickEvent2 -> {
            if (deleteID.getValue().length() >= 1 && deleteID.getValue() != "ID") {


                Notification notification = new Notification();
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
                Label label = new Label("Jeśteś pewny że chcesz usunąć konto?");

                Button yes = new Button("Tak");
                yes.addClickListener(clickEvent -> {
                    reader.setId(Long.parseLong(deleteID.getValue()));
                    readerRepo.delete(reader);

                    Notification notification2 = new Notification(
                            "Konto zostało usunięte!", 3000,
                            Notification.Position.TOP_START);
                    notification2.open();
                    notification.close();

                    UI.getCurrent().getPage().addHtmlImport("/logout");
                });

                Button no = new Button("Nie",
                        e -> {notification.close();
                            deleteID.setValue("ID");
                            deleteName.setValue("Imię i nazwisko");
                        });
                notification.add(label, yes, no);
                yes.getStyle().set("margin-right", "25px");

            }
            //jeżeli zostawimy puste pole w okienku ID otrzymamy taki komunikat
            else {
                Notification notification = new Notification(
                        "Kliknij na dane swojego konta", 3000,
                        Notification.Position.TOP_START);
                notification.open();
            }
        });


        GridPro.Column<Reader> id = gridPro //brak edycji
                .addColumn(Reader::getId)
                .setHeader("ID")
                .setWidth("80px")
                .setFrozen(true); //"zamrożenie w miejscu"

        GridPro.Column<Reader> name = gridPro
                .addColumn(Reader::getName)
                .setHeader("Imię")
                .setWidth("150px")
                .setFrozen(true);

        GridPro.Column<Reader> surname = gridPro
                .addColumn(Reader::getSurname)
                .setHeader("Nazwisko")
                .setWidth("200px")
                .setFrozen(true);

        GridPro.Column<Reader> email = gridPro //brak edycji
                .addColumn(Reader::getEmail)
                .setHeader("E-mail")
                .setWidth("200px")
                .setFrozen(false);

        GridPro.Column<Reader> dateOfBirth = gridPro
                .addColumn(Reader::getDateOfBirth)
                .setHeader("Data urodzenia")
                .setWidth("200px")
                .setFrozen(false);

        GridPro.Column<Reader> phoneNumber = gridPro
                .addColumn(Reader::getPhoneNumber)
                .setHeader("Numer telefonu")
                .setWidth("150px")
                .setFrozen(false);

        Grid.Column<Reader> role = gridPro
                .addColumn(Reader::getRole)
                .setHeader("Rola")
                .setWidth("200px")
                .setFrozen(false);

        gridPro.removeColumnByKey("id");
        gridPro.removeColumnByKey("name");
        gridPro.removeColumnByKey("surname");
        gridPro.removeColumnByKey("email");
        gridPro.removeColumnByKey("password");
        gridPro.removeColumnByKey("dateOfBirth");
        gridPro.removeColumnByKey("phoneNumber");
        gridPro.removeColumnByKey("image");
        gridPro.removeColumnByKey("rents");

        gridPro.removeColumnByKey("username");
        gridPro.removeColumnByKey("role");
        gridPro.removeColumnByKey("accountNonExpired");
        gridPro.removeColumnByKey("accountNonLocked");
        gridPro.removeColumnByKey("credentialsNonExpired");
        gridPro.removeColumnByKey("enabled");
        gridPro.removeColumnByKey("authorities"); //wyrzucenie authorities z tabeli

        display1.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 4));
        display.setResponsiveSteps(
                new FormLayout.ResponsiveStep("50em", 5));
        display1.add(deleteID, deleteName, deleteSurname);
        display.add(display1, delete);

        naglowek.add(tytul);
        menu.add(odswiez, powrot);
        zawartosc.add(display, gridPro);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}
