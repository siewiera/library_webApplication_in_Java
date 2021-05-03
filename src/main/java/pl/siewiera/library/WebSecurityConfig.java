package pl.siewiera.library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    DataSource dataSource;
    //Dodawanie użytkowników i ich ról
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("USER", "ADMIN");


        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username.password. true" +
                        "from Spitter where username=?")
                .authoritiesByUsernameQuery("select username. 'ROLE_USER' from Spitter where username=?");
    }
    //Autoryzacje dostępowe
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/admin", "/registration_Admin", "/edit_reader_Admin", "/list_reader_Admin", "/delete_reader_Admin",
                        "/add_rent_Admin", "/edit_rent_Admin", "/list_rent_Admin", "/delete_rent_Admin",
                        "/add_book_Admin", "/edit_book_Admin", "/list_book_Admin", "/delete_book_Admin").hasRole("ADMIN");

        http.csrf().disable();
        http.headers().disable();

        http.authorizeRequests()
                .antMatchers("/menu_Gui", "/edit_reader_User", "/delete_reader_User",
                        "/add_rent_User", "/list_rent_User", "/list_book_User").authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/library");

    }

    //Interface do zarządzanie hasłami (szyfrowania oraz deszyfrowania haseł)
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void get(){
//        Reader readerUser = new Reader();
//        readerUser.setName("Karol");
//        readerUser.setSurname("Kowalski");
//        readerUser.setEmail("testowy@test.pl");
//        readerUser.setPassword(passwordEncoder().encode("qwerty"));
//        readerUser.setDateOfBirth(LocalDate.of(1990, 8, 12));
//        readerUser.setPhoneNumber(123456789);
//        readerUser.setRole("ROLE_USER");
//        readerUser.setImage("https://i.pinimg.com/236x/29/50/93/2950938dcbcd97d244aaab276af543d4--emojis-emoticon.jpg");
//        readerRepo.save(readerUser);
//
//        Reader readerAdmin = new Reader();
//        readerAdmin.setName("Marcin");
//        readerAdmin.setSurname("Nowak");
//        readerAdmin.setEmail("testowyadmin@test.pl");
//        readerAdmin.setPassword(passwordEncoder().encode("asdfg"));
//        readerAdmin.setDateOfBirth(LocalDate.of(1988, 1, 05));
//        readerAdmin.setPhoneNumber(987654321);
//        readerAdmin.setRole("ROLE_ADMIN");
//        readerAdmin.setImage("http://strzelcepsp.edu.pl/images/thumbnails/images/dentobus/dentoemotka-fit-100x100.png");
//        readerRepo.save(readerAdmin);
//    }
}