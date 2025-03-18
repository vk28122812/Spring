package io.datajek.spring;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Ebook {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    private int id;
    private String title;
    @Column(name="publisher")
    private String author;
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    private double discount;
    // No-argument constructor
    public Ebook() {
    }

    // Parameterized constructor
    public Ebook(int id, String title, String author, double price, Date publishDate, double discount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishDate = publishDate;
        this.discount = discount;
    }

    //constructor without Id attribute
    public Ebook(String title, String author, double price, Date publishDate, double discount) {
        super();
        this.title = title;
        this.author = author;
        this.price = price;
        this.publishDate = publishDate;
        this.discount = discount;
    }

    // Getters and setters
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

    @Override
    public String toString() {
        return "Ebook [" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                ']';
    }
}