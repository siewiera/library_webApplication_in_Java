package pl.siewiera.library.gui_Menu;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("library")
@StyleSheet("/css/style.css")
public class GuiLibrary extends VerticalLayout {

    String tytul = "Biblioteka";
    //logout
    Anchor logowanie = new Anchor("/menu_Gui", "Zaloguj się!");
    Anchor rejestracja = new Anchor("/registration_User", "Zarejestruj się!");                                              //!//!

    @Autowired
    public GuiLibrary() {

        HorizontalLayout naglowek = new HorizontalLayout();
        HorizontalLayout logo = new HorizontalLayout();
        HorizontalLayout zawartosc = new HorizontalLayout();
        HorizontalLayout body = new HorizontalLayout();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E")
                .set("font-family", "Impact")
                .set("font-size", "50px")
                .set("box-shadow", "0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3")
                .set("color", "#255784");
        naglowek.setJustifyContentMode(JustifyContentMode.CENTER);

        logo.setWidth("23%");
        logo.setHeight("auto");
        logo.getStyle().set("margin-top", "15px")
                .set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3")
                .set("color", "#255784");
        logo.setJustifyContentMode(JustifyContentMode.CENTER);

        zawartosc.setWidth("70%");
        zawartosc.setHeight("auto");
        zawartosc.getStyle().set("margin-top", "15px")
                .set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3")
                .set("color", "#255784");
        zawartosc.setJustifyContentMode(JustifyContentMode.CENTER);

        body.setWidth("100%");
        body.setHeight("auto");
        body.getStyle().set("margin-top", "15px")
                .set("margin-bottom", "10px")
                .set("box-shadow", "0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3")
                .set("color", "#255784");
        body.setJustifyContentMode(JustifyContentMode.CENTER);

        logowanie.getStyle().set("font-size", "26px")
                .set("font-family", "Comic Sans MS")
                .set("border", "2px solid green")
                .set("border-radius", "40px 40px 40px 0px")
                .set("margin", "25px 20px")
                .set("width", "250px")
                .set("height","45px")
                .set("padding", "1px")
                .set("padding-left", "15px")
                .set("padding-right", "15px").
                set("position", "absolute");

        rejestracja.getStyle().set("font-size", "26px")
                .set("font-family", "Comic Sans MS")
                .set("border", "2px solid green")
                .set("border-radius", "40px 40px 40px 0px")
                .set("margin", "100px 60px")
                .set("width", "250px")
                .set("height","45px")
                .set("padding", "1px")
                .set("padding-left", "15px")
                .set("padding-right", "15px")
                .set("position", "absolute");

        add(logowanie, rejestracja);

        Image image = new Image("https://i.ibb.co/3MncSPD/Logo2.png", "library");
        image.getStyle().set("width","250px")
                .set("height","170px");
        logo.add(image);
        zawartosc.add(logowanie, rejestracja);
        naglowek.add(tytul);
        body.add(logo, zawartosc);
        add(naglowek, body);
    }
}

