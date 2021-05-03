package pl.siewiera.library.model;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Component
public class Reader implements UserDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name",nullable=false)
    private String name;
    @Column(name="surname",nullable=false)
    private String surname;
    @Email
    @Column(name="email",nullable=false)
    private String email;
    @Column(name="password",nullable=false)
    private String password;
    @Column(name="dateOfBirth")
    @Past //data z przeszłości
    private LocalDate dateOfBirth;
    @Max(999999999)//wartość max
    @Min(100000000)//wartość min
    @Column(name="phoneNumber",nullable=false)
    private int phoneNumber;
    @Column(name="role")
    private String role;
    @Lob
    @Column(name="image")
    private String image;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reader")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Rent> rents = new ArrayList<>();

//    public String toString() { return name+" "+surname; }
public String toString() { return name+" "+surname; }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public Reader(String name, String surname, String email, String password, LocalDate dateOfBirth, int phoneNumber, String role, String image) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.image = image;
    }

    public Reader() {
    }

//    public List<Return> getReturns() {
//        return returns;
//    }
//
//    public void setReturns(List<Return> returns) {
//        this.returns = returns;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname){ this.surname = surname; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; } //konto nie wygasło

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //konto nie jest zablokowane

    @Override
    public boolean isCredentialsNonExpired() { return true; } //kwalifikacje nie wygasły

    @Override
    public boolean isEnabled() {
        return true;
    } //konto jest aktywne

}
