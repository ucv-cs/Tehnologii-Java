package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import models.Reader;
import utils.AutoComplete;
import utils.Database;

public class BorrowController implements Initializable {
	public int selectedBookId;
	private int selectedReaderId;

	@FXML
	private Button borrow, cancel;

	@FXML
	private ComboBox<Reader> borrowReaderSearch;

	/**
	 * Runs after FXML injection.
	 *
	 * @param location
	 * @param resource
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		displayReaders();
	}

	/**
	 * Queries the database and retrieves a list of readers.
	 *
	 * @return
	 */
	public ObservableList<Reader> getReadersList() {
		ObservableList<Reader> readersList = FXCollections.observableArrayList();
		String query = "SELECT * FROM readers;";
		Statement statement;
		ResultSet results;

		try {
			Connection connection = Database.connect();
			statement = connection.createStatement();
			results = statement.executeQuery(query);
			Reader readers;
			while (results.next()) {
				readers = new Reader(results.getInt("id"), results.getString("name"));
				readersList.add(readers);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return readersList;
	}

	/**
	 * Displays into the combobox the retrieved list of readers.
	 */
	public void displayReaders() {
		ObservableList<Reader> readersList = getReadersList();

		borrowReaderSearch.setItems(readersList);
		borrowReaderSearch.setMaxHeight(50);

		AutoComplete.autoCompleteComboBoxPlus(borrowReaderSearch,
				(typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

		borrowReaderSearch.setConverter(new StringConverter<>() {
			@Override
			public String toString(Reader object) {
				return object != null ? object.getName() : "";
			}

			@Override
			public Reader fromString(String string) {
				return borrowReaderSearch.getItems().stream().filter(object -> object.getName().equals(string))
						.findFirst().orElse(null);
			}
		});
	}

	/**
	 * Marks the book as borrowed and closes the dialog window.
	 */
	@FXML
	private void borrow() {
		System.out.println("borrow clicked");
		cancel();
	}

	/**
	 * Closes the dialog window.
	 */
	@FXML
	private void cancel() {
		cancel.getScene().getWindow().hide();
		System.out.println("cancel clicked");
	}

}
