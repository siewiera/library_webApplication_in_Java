package pl.siewiera.library.gui_Menu;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Route("menu_Gui")
@ResponseBody
@StyleSheet("/css/style.css")
public class MenuGuiUser extends VerticalLayout {
    String tytul = "Panel Użytkownika";

    //logout
    Anchor logout = new Anchor("/logout", "Wyloguj");

    //menu konta
    Anchor edycja_konta = new Anchor("edit_reader_User", "Edycja danych");
    Anchor usuwanie_konta = new Anchor("delete_reader_User", "Usuwanie konta");

    //wypozyczenie
    Anchor wypozycz = new Anchor("add_rent_User", "Wypożycz");
    Anchor historia_wypozyczen = new Anchor("list_rent_User", "Historia Wypożyczeń");

    //książki
    Anchor lista_ksiazek = new Anchor("list_book_User", "Lista książek");

    //zwracanie
    private Anchor zwroc = new Anchor("add_return_Admin", "Zwróć");
    private Anchor oddane_ksiazki = new Anchor("list_return_Admin", "Oddane książki");


    @Autowired
    public MenuGuiUser() {

        HorizontalLayout naglowek = new HorizontalLayout();
        HorizontalLayout menu = new HorizontalLayout();
        HorizontalLayout inf = new HorizontalLayout();
        HorizontalLayout zawartosc = new HorizontalLayout();
        HorizontalLayout body = new HorizontalLayout();

        naglowek.setWidth("100%");
        naglowek.setHeight("100%");
        naglowek.getStyle().set("border", "1px solid #9E9E9E").set("font-family", "Impact").set("font-size","50px")
                .set("box-shadow","0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3").set("color","#255784");
        naglowek.setJustifyContentMode(JustifyContentMode.CENTER);

        inf.setWidth("100%");
        inf.setHeight("auto");
        inf.getStyle().set("border", "1px solid #9E9E9E").set("font-family", "Impact").set("font-size","10px")
                .set("box-shadow","0 0 10px #2196f3,0 0 30px #2196f3,0 0 60px #2196f3").set("color","#255784");
        inf.setJustifyContentMode(JustifyContentMode.END);

        menu.setWidth("205px");
        menu.setHeight("550px");
        menu.getStyle().set("margin-top","30px").set("margin-bottom","10px").set("margin-right","5px").set("border-right","1px solid black");
        menu.setJustifyContentMode(JustifyContentMode.CENTER);

        zawartosc.setWidth("700px");
        zawartosc.setHeight("550px");
        zawartosc.getStyle().set("margin-top","30px").set("margin-bottom","10px");

        body.setWidth("100%");
        body.setHeight("auto");
        body.getStyle().set("margin-top","15px").set("margin-bottom","10px")
                .set("box-shadow","0 0 5px #2196f3,0 0 20px #2196f3,0 0 40px #2196f3").set("color","#255784");
        body.setJustifyContentMode(JustifyContentMode.END);

        logout.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","10px 20px").set("width","180px").set("height" ,"27px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","right");


        Tab konta = new Tab("Konto");
        konta.getStyle().set("font-size", "22px").set("font-family","Comic Sans MS").set("font-weight","900").set("border","2px solid green")
                .set("border-radius","0px 35px").set("margin","10px 5px").set("width","194px").set("padding","0px 20px 3px");

        Div p_konta = new Div();

        p_konta.add(edycja_konta);
        edycja_konta.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","10px 20px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        p_konta.add(usuwanie_konta);
        usuwanie_konta.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","50px 60px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        Tab wypozyczenia = new Tab("Wypożyczenia");
        wypozyczenia.getStyle().set("font-size", "22px").set("font-family","Comic Sans MS").set("font-weight","900").set("border","2px solid green")
                .set("border-radius","0px 35px").set("margin","10px 5px").set("width","194px").set("padding","0px 20px 3px");

        Div p_wypozyczenia = new Div();
        p_wypozyczenia.add(wypozycz, historia_wypozyczen);
        p_wypozyczenia.setVisible(false);
        wypozycz.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","10px 20px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        historia_wypozyczen.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","50px 60px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        Tab ksiazki = new Tab("Książki");
        ksiazki.getStyle().set("font-size", "22px").set("font-family","Comic Sans MS").set("font-weight","900").set("border","2px solid green")
                .set("border-radius","0px 35px").set("margin","10px 5px").set("width","194px").set("padding","0px 20px 3px");

        Div p_ksiazki = new Div();
        p_ksiazki.add(lista_ksiazek);
        p_ksiazki.setVisible(false);
        lista_ksiazek.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","10px 20px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        Tab zwracanie = new Tab("Zwracanie");
        zwracanie.getStyle().set("font-size", "22px").set("font-family","Comic Sans MS").set("font-weight","900").set("border","2px solid green")
                .set("border-radius","0px 35px").set("margin","10px 5px").set("width","194px").set("padding","0px 20px 3px");

        Div p_zwroty = new Div();
        p_zwroty.add(zwroc, oddane_ksiazki);
        p_zwroty.setVisible(false);
        zwroc.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","10px 20px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");

        oddane_ksiazki.getStyle().set("font-size", "16px").set("font-family","Comic Sans MS").set("border","2px solid green")
                .set("border-radius","40px 40px 40px 0px").set("margin","90px 100px").set("width","180px")
                .set("padding","1px").set("padding-left","15px").set("padding-right","15px").set("position","absolute");


        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(konta, p_konta);
        tabsToPages.put(wypozyczenia, p_wypozyczenia);
        tabsToPages.put(ksiazki, p_ksiazki);
        tabsToPages.put(zwracanie, p_zwroty);
        Tabs tabs = new Tabs(konta, wypozyczenia, ksiazki, zwracanie);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        Div pages = new Div(p_konta, p_wypozyczenia, p_ksiazki, p_zwroty);
        Set<Component> pagesShown = Stream.of(p_konta)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        TextField emailField = new TextField();
        emailField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        emailField.getStyle().set("width", "19%").set("margin","auto").set("margin-right", "10px");
        emailField.setReadOnly(true);
        emailField.setValue(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        naglowek.add(tytul);
        inf.add(emailField, logout);
        menu.add(tabs);
        zawartosc.add(pages);
        body.add(menu, zawartosc);
        add(naglowek, inf, body);
    }

}