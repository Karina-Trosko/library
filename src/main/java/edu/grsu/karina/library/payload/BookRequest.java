package edu.grsu.karina.library.payload;

public class BookRequest {
    private String title;
    private int yearOfPublishing;
    private int authorId;
    private String publishingHouse;
    private int numbOfPages;
    private Boolean here;
    private  int literatureTypeId;
    private int genreId;

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getLiteratureTypeId() {
        return literatureTypeId;
    }

    public void setLiteratureTypeId(int literatureTypeId) {
        this.literatureTypeId = literatureTypeId;
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

    private Boolean onlyInReadingRoom;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }
}
