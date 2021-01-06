package jlib.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Defines a librarian object with all its properties matching the database
 * attributes.
 */
public class Librarian {
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty username;
	private final SimpleStringProperty password;
	private final SimpleStringProperty name;
	private final SimpleStringProperty address;
	private final SimpleStringProperty photo;
	private final SimpleStringProperty status;

	public Librarian(int id, String username, String password, String name, String address, String photo,
			String status) {
		this.id = new SimpleIntegerProperty(id);
		this.username = new SimpleStringProperty(username);
		this.password = new SimpleStringProperty(password);
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.photo = new SimpleStringProperty(photo);
		this.status = new SimpleStringProperty(status);
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
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
