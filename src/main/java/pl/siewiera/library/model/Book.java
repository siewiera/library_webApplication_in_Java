package pl.siewiera.library.model;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public class Book {

  @Id
  @Column(name="id_book")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name="title")
  private String title;
  @Column(name="name_author")
  private String name_author;
  @Column(name="surname_author")
  private String surname_author;
  @Column(name="bookCategory")
  private BookCategory bookCategory;
  @Column(name="releaseDate")
  private LocalDate releaseDate;
  @Column(name="edition")
  private String edition;
  @Column(name="description")
  private String description;
  @Column(name="quantity")
  private int quantity;
  @Lob
  @Column(name="image")
  private String image;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
  @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
  private List<Rent> rents = new ArrayList<>();

//  public String toString() { return name_author+" "+surname_author+" \""+title+"\""+" "+ quantity; }
  public String toString() { return " \""+title+"\""; }
  public List<Rent> getRents() { return rents; }


  public void setRents(List<Rent> rents) { this.rents = rents; }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() { return title; }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getName_author() {
    return name_author;
  }

  public void setName_author(String name_author) {
    this.name_author = name_author;
  }

  public String getSurname_author() {
    return surname_author;
  }

  public void setSurname_author(String surname_author) {
    this.surname_author = surname_author;
  }

  public BookCategory getBookCategory() {
    return bookCategory;
  }

  public void setBookCategory(BookCategory bookCategory) {
    this.bookCategory = bookCategory;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getEdition() {
    return edition;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

}