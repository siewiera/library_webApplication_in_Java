package pl.siewiera.library.model;

import org.hibernate.annotations.Cascade;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Component
public class Rent {

    @Id
    @Column(name="rentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="readerId",nullable=false)
    private Reader reader;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="bookId")
    private Book book;

    @Column(name="quantity")
    private int quantity;

    @Column(name="dateOfRent")
    private LocalDate dateOfRent;

    @Column(name="returnDate")
    private LocalDate returnDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "rent")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<ReturnB> returns = new ArrayList<>();

    public String toString() {
        return getDateOfRent().toString(); }

//    public List<Return> getReturns() {
//        return returns;
//    }
//
//    public void setReturns(List<Return> returns) {
//        this.returns = returns;
//    }

    public List<ReturnB> getReturns() {
        return returns;
    }

    public void setReturns(List<ReturnB> returns) {
        this.returns = returns;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(LocalDate dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
