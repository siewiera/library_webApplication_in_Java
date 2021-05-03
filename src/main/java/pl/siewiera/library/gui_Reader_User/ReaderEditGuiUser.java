package pl.siewiera.library.gui_Reader_User;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.siewiera.library.model.Reader;
import pl.siewiera.library.repository_Reader.ReaderRepo;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

// edycja kont uprawnienia usera
@Route("edit_reader_User")
@StyleSheet("/css/style.css")
public class ReaderEditGuiUser extends VerticalLayout {
    String tytul = "Edycja danych";

    @Bean
    public Principal principal() {
        return principal();
    }
    @Autowired
    public ReaderEditGuiUser(ReaderRepo readerRepo) {

        Icon logo = new Icon(VaadinIcon.ARROW_BACKWARD);
        logo.setSize("50px");
        logo.setColor("#2196f3");

        Anchor powrot = new Anchor("menu_Gui", logo);

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

        Binder<Reader> binder = new Binder<>(Reader.class);
        Editor<Reader> editor = gridPro.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Div validationStatus = new Div();


        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        GridPro.Column<Reader> editorColumn = gridPro
                .addComponentColumn(rent1 -> {
                    Button edit = new Button("Edycja");
                    edit.addClassName("edit");
                    edit.addClickListener(e -> {
                        editor.editItem(rent1);
                    });
                    edit.setEnabled(!editor.isOpen());
                    editButtons.add(edit);
                    return edit;
                }).setFrozen(true)
                .setWidth("110px");

        GridPro.Column<Reader> id = gridPro //brak edycji
                .addColumn(Reader::getId)
                .setHeader("ID")
                .setWidth("70px")
                .setFrozen(true); //"zamrożenie w miejscu"

        GridPro.Column<Reader> name = gridPro
                .addColumn(Reader::getName)
                .setHeader("Imię")
                .setWidth("150px")
                .setFrozen(true);

        GridPro.Column<Reader> surname = gridPro
                .addColumn(Reader::getSurname)
                .setHeader("Nazwisko")
                .setWidth("150px")
                .setFrozen(true);

        GridPro.Column<Reader> email = gridPro
                .addColumn(Reader::getEmail)
                .setHeader("E-mail")
                .setWidth("200px")
                .setFrozen(false);

        GridPro.Column<Reader> password = gridPro
                .addColumn(Reader::getPassword)
                .setHeader("Hasło")
                .setWidth("300px")
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

        TextField emailField = new TextField();
        emailField.setSizeFull();
        binder.forField(emailField)
                .withValidator(new StringLengthValidator("Błędny email", 5, 20))
                .withStatusLabel(validationStatus).bind("email");
        email.setEditorComponent(emailField);

        TextField firstNameField = new TextField();
        firstNameField.setSizeFull();
        binder.forField(firstNameField)
                .withValidator(new StringLengthValidator("Liczba znaków musi być między 2, a 20", 2, 20))
                .withStatusLabel(validationStatus).bind("name");
        name.setEditorComponent(firstNameField);


        TextField surnameField = new TextField();
        surnameField.setSizeFull();
        binder.forField(surnameField)
                .withValidator(new StringLengthValidator("Liczba znaków musi być między 2, a 20", 2, 20))
                .withStatusLabel(validationStatus).bind("surname");
        surname.setEditorComponent(surnameField);


        DatePicker datePicker1 = new DatePicker();
        datePicker1.setSizeFull();
        binder.forField(datePicker1)
                .withStatusLabel(validationStatus).bind("dateOfBirth");
        dateOfBirth.setEditorComponent(datePicker1);


        TextField numberField = new TextField();
        numberField.setSizeFull();
        binder.forField(numberField)
                .withConverter(new StringToIntegerConverter("Numer telefonu składa się z liczb"))
                .withStatusLabel(validationStatus).bind("phoneNumber");
        phoneNumber.setEditorComponent(numberField);

        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));


        Button save = new Button("Zapisz", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Anuluj", e -> editor.cancel());
        cancel.addClassName("cancel");

        gridPro.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");


        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);

        editor.addSaveListener(
                event -> readerRepo.saveAll(readers));

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
        gridPro.removeColumnByKey("authorities");

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(gridPro);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}