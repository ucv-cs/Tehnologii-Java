package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
	private final SimpleStringProperty title;
	private final SimpleStringProperty author;
	private final SimpleStringProperty edition;
	private final SimpleIntegerProperty year;
	private final SimpleStringProperty status;

	Book(String title, String author, String edition, int year, String status) {
		this.title = new SimpleStringProperty(title);
		this.author = new SimpleStringProperty(author);
		this.edition = new SimpleStringProperty(edition);
		this.year = new SimpleIntegerProperty(year);
		this.status = new SimpleStringProperty(status);
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

	public int getYear() {
		return year.get();
	}

	public void setYear(int year) {
		this.year.set(year);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
}
