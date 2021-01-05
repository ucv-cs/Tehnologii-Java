package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Defines a reader object with all its properties matching the database
 * attributes.
 */
public class Reader {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty address;
	private final SimpleStringProperty photo;
	private final SimpleStringProperty status;

	public Reader(int id, String name, String address, String photo, String status) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.photo = new SimpleStringProperty(photo);
		this.status = new SimpleStringProperty(status);
	}

	public Reader(int id, String name) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.address = null;
		this.photo = null;
		this.status = null;
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public String getPhoto() {
		return photo.get();
	}

	public void setPhoto(String photo) {
		this.photo.set(photo);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status.set(status);
	}
}
