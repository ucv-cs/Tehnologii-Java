package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.ChoiceBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import models.Reader;
import utils.Database;

public class BorrowController implements Initializable {
	public int selectedBookId;

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
	 * Displays into the tableview the retrieved list of readers.
	 */
	public void displayReaders() {
		ObservableList<Reader> readersList = getReadersList();

		FilteredList<Reader> filteredData = new FilteredList<>(readersList, flag -> true);

		borrowReaderSearch.getEditor().textProperty().addListener(new ChangeListener<Reader>() {

			@Override
			public void changed(ObservableValue<? extends Reader> observable, Reader oldReader, Reader newReader) {
				// TODO Auto-generated method stub

			}
		});

		// borrowReaderSearch.getEditor().textProperty().addListener((observable,
		// oldValue, newValue) -> {
		// filteredData.setPredicate(reader -> {
		// if (reader == null || newValue == null || newValue.isEmpty()) {
		// return true;
		// }
		// borrowReaderSearch.show();
		// String lowerCaseFilter = newValue.toLowerCase();
		// if (reader.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		// return true;
		// } else {
		// return false;
		// }
		// });
		// });

		// borrowReaderSearch.setConverter(new StringConverter<Reader>() {
		// @Override
		// public String toString(Reader reader) {
		// return reader.getName();
		// }

		// @Override
		// public Reader fromString(String reader) {
		// return null; // FIXME
		// }
		// });

		borrowReaderSearch.setItems(filteredData);
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
