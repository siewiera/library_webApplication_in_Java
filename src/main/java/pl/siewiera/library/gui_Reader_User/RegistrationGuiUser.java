package pl.siewiera.library.gui_Reader_User;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.siewiera.library.model.Reader;
import pl.siewiera.library.repository_Reader.ReaderRepo;

//dodawanie czytelnika do bazy
@Route("registration_User")
@StyleSheet("/css/style.css")
public class RegistrationGuiUser extends VerticalLayout {
    String tytul = "Rejestracja";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public RegistrationGuiUser(ReaderRepo readerRepo) {

        Icon logo = new Icon(VaadinIcon.ARROW_BACKWARD);
        logo.setSize("50px");
        logo.setColor("#2196f3");
        logo.getStyle().set("margin-right", "80px");

        Anchor powrot = new Anchor("library", logo);

        HorizontalLayout naglowek = new HorizontalLayout();
        VerticalLayout menu = new VerticalLayout();
        VerticalLayout zawartosc = new VerticalLayout();
        HorizontalLayout body = new HorizontalLayout();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E")
                .set("font-family", "Impact")
                .set("font-size", "50px")
                .set("box-shadow", "0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3")
                .set("color", "#255784");
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
        body.getStyle().set("margin-top", "15px")
                .set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3")
                .set("color", "#255784");
        body.setJustifyContentMode(JustifyContentMode.CENTER);

        Reader reader = new Reader();
        Binder<Reader> binder = new Binder<>(Reader.class);
        Div validationStatus = new Div();

        EmailField emailField = new EmailField();
        emailField.getStyle().set("width", "40%")
                .set("margin-left", "0px");
        emailField.setLabel("DANE LOGOWANIA");
        emailField.setPlaceholder("Email");
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage("Proszę wpisać aktualny adress e-mail");

        PasswordField passwordField = new PasswordField();
        passwordField.getStyle().set("width", "40%")
                .set("margin-left", "30px");
        passwordField.setMaxLength(15);
        binder.forField(passwordField)
                .withValidator(min -> min.length() >= 5, "Minimum 5 znaków")
                .bind(Reader::getPassword, Reader::setPassword);
        passwordField.setErrorMessage("Za krótkie hasło!");
        passwordField.setPlaceholder("Hasło");

        TextField textFieldName = new TextField();
        textFieldName.getStyle().set("width", "30%")
                .set("margin-left", "0px");
        textFieldName.setLabel("DANE CZYTELNIKA");
        textFieldName.setPlaceholder("Imię");
        textFieldName.setMaxLength(20);
        binder.forField(textFieldName)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaków")
                .withValidator(max -> max.length() <= 20, "Maksimum 20 znaków")
                .bind(Reader::getName, Reader::setName);
        textFieldName.setErrorMessage("Za krótkie imię!");

        TextField textFieldSurname = new TextField();
        textFieldSurname.getStyle().set("width", "30%")
                .set("margin-left", "30px");
        textFieldSurname.setPlaceholder("Nazwisko");
        textFieldSurname.setMaxLength(20);
        binder.forField(textFieldSurname)
                .withValidator(min -> min.length() >= 2, "Minimum 2 znaków")
                .withValidator(max -> max.length() <= 20, "Maksimum 20 znaków")
                .bind(Reader::getSurname, Reader::setSurname);
        textFieldSurname.setErrorMessage("Za krótkie nazwisko!");

        DatePicker datePicker = new DatePicker();
        datePicker.getStyle().set("width", "30%")
                .set("margin-left", "60px");
        datePicker.setPlaceholder("Data urodzenia");

        TextField numberPhoneField = new TextField();
        numberPhoneField.getStyle().set("width", "30%")
                .set("margin-left", "90px");
        numberPhoneField.setPlaceholder("Numer telefonu");
        numberPhoneField.setMaxLength(9);
        binder.forField(numberPhoneField)
                .withValidator(min -> min.length() == 9, "Minimum 9 znaków");
        numberPhoneField.setErrorMessage("Wprowadzono błędny numer!");

        TextField textFieldImage = new TextField();
        textFieldImage.getStyle().set("width", "30%")
                .set("margin-left", "150px");
        textFieldImage.setPlaceholder("Link zdjęcia");

        Button button = new Button("Rejestruj");
        button.getStyle().set("width", "30%")
                .set("margin-left", "120px");
        Button clearButton = new Button("Wyczyść pola", event -> {

            textFieldName.clear();
            textFieldSurname.clear();
            emailField.clear();
            passwordField.clear();
            datePicker.clear();
            numberPhoneField.clear();
            textFieldImage.clear();

        });
        clearButton.getStyle().set("width", "30%").set("margin-left", "120px");

        button.addClickListener(clickEvent -> {
            if (emailField.getValue().length() >= 5) {
                if (passwordField.getValue().length() >= 5) {
                    if (textFieldName.getValue().length() >= 2) {
                        if (textFieldSurname.getValue().length() >= 2) {
                            if (numberPhoneField.getValue().length() == 9) {

                                reader.setEmail(emailField.getValue());
                                reader.setPassword(passwordEncoder()
                                        .encode(passwordField.getValue()));
                                reader.setName(textFieldName.getValue());
                                reader.setSurname(textFieldSurname.getValue());
                                reader.setDateOfBirth(datePicker.getValue());
                                reader.setPhoneNumber(Integer.valueOf
                                        (numberPhoneField.getValue()));
                                reader.setImage(textFieldImage.getValue());
                                reader.setRole("ROLE_USER");
                                if(reader.getImage().length()<=0){
                                    reader.setImage("https://external-content.duckduckgo.com/iu/" +
                                            "?u=https%3A%2F%2Fthumbs.dreamstime.com%2Ft%2Fu%25C5%" +
                                            "25BCytkownik-osoba-obrachunkowy-ikona-wektor-wype%" +
                                            "25C5%2582niaj%25C4%2585cy-mieszkanie-znak-sta%25C5%" +
                                            "2582y-piktogram-odizolowywaj%25C4%2585cy-na-bielu-95992530" +
                                            ".jpg&f=1&nofb=1");
                                }
                                readerRepo.save(reader);
                                Notification notification = new Notification(
                                        "Rejestracja przebiegła pomyślnie!" , 3000,
                                        Notification.Position.TOP_START);
                                notification.open();

                                clearButton.click();
                            } else {
                                Notification notification = new Notification(
                                        "Wprowadzono błędny numer telefonu!", 3000,
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
                            "Za krótkie hasło!", 3000,
                            Notification.Position.TOP_START);
                    notification.open();
                }
            } else {
                Notification notification = new Notification(
                        "Wprowadzono błędny e-mail!", 3000,
                        Notification.Position.TOP_START);
                notification.open();
            }
        });

        naglowek.add(tytul);
        menu.add(powrot);
        zawartosc.add(emailField, passwordField);
        zawartosc.add(textFieldName, textFieldSurname, datePicker,
                numberPhoneField, textFieldImage);
        zawartosc.add(button, clearButton);
        body.add(menu, zawartosc);
        add(naglowek, body);
    }
}
