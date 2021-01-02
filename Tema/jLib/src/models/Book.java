package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty title;
	private final SimpleStringProperty author;
	private final SimpleStringProperty edition;
	private final SimpleStringProperty year;
	private final SimpleStringProperty publisher;
	private final SimpleStringProperty status;

	public Book(int id, String title, String author, String edition, String year, String publisher, String status) {
		this.id = new SimpleIntegerProperty(id);
		;
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.edition = new SimpleStringProperty(edition);
		this.year = new SimpleStringProperty(year);
		this.publisher = new SimpleStringProperty(publisher);
		this.status = new SimpleStringProperty(status);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getTitle() {
		return title.get();
	}

	public void setTitle(String title) {
		this.title.set(title);
	}

	public String getAuthor() {
		return author.get();
	}

	public void setAuthor(String author) {
		this.author.set(author);
	}

	public String getEdition() {
		return edition.get();
	}

	public void setEdition(String edition) {
		this.edition.set(edition);
	}

	public String getYear() {
		return year.get();
	}

	public void setYear(String year) {
		this.year.set(year);
	}

	public String getPublisher() {
		return publisher.get();
	}

	public void setPublisher(String publisher) {
		this.publisher.set(publisher);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
}
