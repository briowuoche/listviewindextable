package com.index.table.to;

import java.util.Locale;

public class Book implements Comparable<Book>
{    
    private String title;
    
    private String author;
    
    private String year;
    
    private String country;
    
    private String language;

    public Book() {
        // TODO Auto-generated constructor stub
    }
    
    public Book(String title, String author, String year, String country, String language) {
        super();
        this.title = title;
        this.author = author;
        this.year = year;
        this.country = country;
        this.language = language;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public int compareTo(Book book) {
        return this.getTitle().toLowerCase(Locale.ENGLISH).compareTo(book.getTitle().toLowerCase(Locale.ENGLISH));
    }
}
