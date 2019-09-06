package edu.grsu.karina.library.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
public class Book  {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotNull
    @Column(name = "title")
    private String title;
//    private String author;
    @NotNull
    @Column(name = "publishing_house")
    private String publishingHouse;
    @NotNull
    @Column(name = "numb_of_pages")
    private int numbOfPages;
    @NotNull
    @Column(name = "year_of_publishing")
    private int yearOfPublishing;

    @Column(name = "here")
    private Boolean here;

    @Column(name = "only_in_reading_room")
    private Boolean onlyInReadingRoom;


    //private String genre;
    //private String typeL;
    @Column(name = "date_of_issuance")
    private Date dateOfIssuance;
    @Column(name = "path")
    private String path;


    @ManyToOne
    @JoinColumn
    private Author author;

    @ManyToOne
    @JoinColumn
    private LiteratureType literatureType;

    @ManyToOne
    @JoinColumn
    private Genre genres;

    @OneToOne
    @JoinColumn
    private IssuanceRequest issuanceRequest;


    public Book(){}
    public Book(@NotNull String title, @NotNull String publishingHouse, @NotNull int numbOfPages, @NotNull int yearOfPublishing, Boolean here, Boolean onlyInReadingRoom, Author author, LiteratureType literatureType, Genre genres) {
        this.title = title;
        this.publishingHouse = publishingHouse;
        this.numbOfPages = numbOfPages;
        this.yearOfPublishing = yearOfPublishing;
        this.here = here;
        this.onlyInReadingRoom = onlyInReadingRoom;
        this.author = author;
        this.literatureType = literatureType;
        this.genres = genres;
    }

    public IssuanceRequest getIssuanceRequest() {
        return issuanceRequest;
    }

    public void setIssuanceRequest(IssuanceRequest issuanceRequest) {
        this.issuanceRequest = issuanceRequest;
    }

    public Genre getGenre() {
        return genres;
    }

    public void setGenre(Genre genres) {
        this.genres = genres;
    }

    public LiteratureType getLiteratureType() {
        return literatureType;
    }

    public void setLiteratureType(LiteratureType literatureType) {
        this.literatureType = literatureType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getNumbOfPages() {
        return numbOfPages;
    }

    public void setNumbOfPages(int numbOfPages) {
        this.numbOfPages = numbOfPages;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public Date getDateOfIssuance() {
        return dateOfIssuance;
    }

    public void setDateOfIssuance(Date dateOfIssuance) {
        this.dateOfIssuance = dateOfIssuance;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getHere() {
        return here;
    }

    public void setHere(Boolean here) {
        this.here = here;
    }

    public Boolean getOnlyInReadingRoom() {
        return onlyInReadingRoom;
    }

    public void setOnlyInReadingRoom(Boolean onlyInReadingRoom) {
        this.onlyInReadingRoom = onlyInReadingRoom;
    }


}
