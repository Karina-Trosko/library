package edu.grsu.karina.library.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "issuance_requests")
public class IssuanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Boolean valid;

    //private  Boolean got;


    @ManyToOne
    @JoinColumn
    private User reader;

    @OneToOne
    @JoinColumn
    private Book book;

    private Date date;

    /*public Boolean getGot() {
        return got;
    }

    public void setGot(Boolean got) {
        this.got = got;
    }*/

    public Boolean getValid() {
        return valid;
    }
    public Boolean CheckValid()
    {
        Date  currentDate = new Date();
        long difference = currentDate.getTime()-date.getTime();
        if(((int)(difference / (24 * 60 * 60 * 1000)))>=1)
            return false;
        else return true;
    }
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public IssuanceRequest(){}
    public IssuanceRequest(String name, User reader, Book book) {
        this.name = name;
        this.reader = reader;
        this.book = book;
        SetDateOfIssuance();
    }
    void SetDateOfIssuance(){
    this.date=new Date();
}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }
}
