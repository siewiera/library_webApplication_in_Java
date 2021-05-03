package pl.siewiera.library.gui_Reader_Admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siewiera.library.model.Reader;
import pl.siewiera.library.repository_Reader.ReaderRepo;

import java.util.*;
// edycja kont uprawnienia admin
@Route("edit_reader_Admin")
@StyleSheet("/css/style.css")
public class ReaderEditGuiAdmin extends VerticalLayout {
    String tytul = "Edycja danych czytelników";

    String czytelnik = "";
    Select<String> roleSelect = new Select<>();

    //lista wyboru roli dla użytkownika
    List<String> role1 = new ArrayList<>();

    @Autowired
    public ReaderEditGuiAdmin(ReaderRepo readerRepo) {

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

        List<Reader> readers = readerRepo.findAll();
        GridPro<Reader> gridPro = new GridPro<>(Reader.class);
        gridPro.setItems(readers);

        gridPro.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //linie obramowania kolumn pionowych
        gridPro.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //linie obramowania kolumn poziomych
        gridPro.addThemeVariants(GridVariant.LUMO_ROW_STRIPES); //kolor wypełnienia wierszy (szaro-białe)

        gridPro.setMinHeight("auto");
        gridPro.setMaxHeight("1000px"); //maksymalna wysokość
        gridPro.setMinWidth("auto");
        gridPro.setMaxWidth("2000px"); //maksymalna szerokość

        gridPro.setSingleCellEdit(true);
        gridPro.setSelectionMode(Grid.SelectionMode.NONE);

        Binder<Reader> binder = new Binder<>(Reader.class);
        Editor<Reader> editor = gridPro.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Div validationStatus = new Div();

        //informacja po kliknieciu na czytelnika
        gridPro.setItemDetailsRenderer(TemplateRenderer.<Reader>of(
                "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box; word-wrap: break-word;'>"
                        + "<div>Cześć mam na imię <b>[[item.imie]] [[item.nazwisko]]!</b></div>"
                        + "<div><img style='height: 80px; width: 80px;' src='[[item.zdjecie]]'/></div>"
                        + "</div>")
                .withProperty("id", Reader::getId)
                .withProperty("imie", Reader::getName)
                .withProperty("nazwisko", Reader::getSurname)
                .withProperty("zdjecie", Reader::getImage)
                .withEventHandler("handleClick", reader -> {
                    gridPro.getDataProvider().refreshItem(reader);
                }));

        ListDataProvider<Reader> dataProvider = new ListDataProvider<>(readers);
        gridPro.setDataProvider(dataProvider);

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

        if(readers.size()==1){czytelnik="czytelnik";}
        if(readers.size()>1){czytelnik="czytelników";}
        if(readers.size()==0){czytelnik="czytelników";}

        GridPro.Column<Reader> name = gridPro
                .addColumn(Reader::getName)
                .setHeader("Imię")
                .setWidth("150px")
                .setFrozen(true)
                .setFooter(readers.size() + " " + czytelnik);

        GridPro.Column<Reader> surname = gridPro
                .addColumn(Reader::getSurname)
                .setHeader("Nazwisko")
                .setWidth("150px")
                .setFrozen(true);

        GridPro.Column<Reader> email = gridPro //brak edycji
                .addColumn(Reader::getEmail)
                .setHeader("E-mail")
                .setWidth("200px")
                .setFrozen(false);

        GridPro.Column<Reader> password = gridPro //brak edycji
                .addColumn(Reader::getPassword)
                .setHeader("Hasło")
                .setWidth("300px")
                .setFrozen(false); //brak edycji

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

        role1.add("ROLE_USER");
        role1.add("ROLE_ADMIN");
        Grid.Column<Reader> role = gridPro
                .addColumn(Reader::getRole)
                .setHeader("Rola")
                .setWidth("200px")
                .setFrozen(false);

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


        Select<String> role2 = new Select<>();
        role2.setSizeFull();
        role2.setItems(role1);
        binder.forField(role2)
                .withStatusLabel(validationStatus).bind("role");
        role.setEditorComponent(role2);

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

        //stworzenie miejsca na filtry w nagłówku tabeli
        HeaderRow filterRow = gridPro.appendHeaderRow();

        // I filter name
        TextField textFieldName = new TextField();
        textFieldName.addValueChangeListener(event -> dataProvider.addFilter(
                reader -> StringUtils.containsIgnoreCase
                        (reader.getName(), textFieldName.getValue())));
        textFieldName.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(name).setComponent(textFieldName);
        textFieldName.setSizeFull();
        textFieldName.setPlaceholder("Filtr");

        // II filter surname
        TextField textFieldSurname = new TextField();
        textFieldSurname.addValueChangeListener(event -> dataProvider.addFilter(
                reader -> StringUtils.containsIgnoreCase
                        (reader.getSurname(), textFieldSurname.getValue())));
        textFieldSurname.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(surname).setComponent(textFieldSurname);
        textFieldSurname.setSizeFull();
        textFieldSurname.setPlaceholder("Filtr");

        // III filter email
        EmailField emailField = new EmailField();
        emailField.addValueChangeListener(event -> dataProvider
                .addFilter(reader -> StringUtils.containsIgnoreCase
                        (reader.getEmail(), emailField.getValue())));
        emailField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(email).setComponent(emailField);
        emailField.setSizeFull();
        emailField.setPlaceholder("Filtr");

        // IV filter dateOfBirth
        TextField datePicker = new TextField();
        datePicker.addValueChangeListener(event -> dataProvider
                .addFilter(reader -> StringUtils.containsIgnoreCase(String.valueOf
                        (reader.getDateOfBirth()), datePicker.getValue())));
        datePicker.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(dateOfBirth).setComponent(datePicker);
        datePicker.setSizeFull();
        datePicker.setPlaceholder("Filtr");

        // V filter phoneNumber
        TextField integerField = new TextField();
        integerField.addValueChangeListener(event -> dataProvider
                .addFilter(reader -> StringUtils.containsIgnoreCase(String.valueOf
                        (reader.getPhoneNumber()), integerField.getValue())));
        integerField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(phoneNumber).setComponent(integerField);
        integerField.setSizeFull();
        integerField.setPlaceholder("Filtr");

        // VI filter role
        roleSelect.setItems(role1);
        roleSelect.addValueChangeListener(event -> dataProvider
                .addFilter(reader -> StringUtils.containsIgnoreCase(String.valueOf
                        (reader.getRole()), role1.toString())));
        roleSelect.addValueChangeListener(event -> {
            applyFilter(dataProvider);
        });
        filterRow.getCell(role).setComponent(roleSelect);
        roleSelect.setSizeFull();
        roleSelect.setPlaceholder("Filtr");
        roleSelect.setEmptySelectionAllowed(true);

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

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(gridPro,textFieldName, textFieldSurname, emailField, datePicker, integerField);
        body.add(menu, zawartosc);
        add(naglowek, body);

    }
    private void applyFilter(ListDataProvider<Reader> dataProvider) {
        dataProvider.clearFilters();
        if (roleSelect.getValue() != null)
            dataProvider.addFilter(reader -> roleSelect.getValue().equals(reader.getRole()));
    }
}