package jlib.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import jlib.models.Reader;
import jlib.utils.AutoComplete;
import jlib.utils.Database;

/**
 * Controller for the borrow dialog.
 */
public class BorrowController implements Initializable {
	protected int currentLibrarianId;
	protected int selectedBookId;

	private final Connection connection = Database.connection;
	protected MainController mainController;

	@FXML
	private Button borrow, cancel;

	@FXML
	private ComboBox<Reader> borrowReaderSearch;

	@FXML
	private DatePicker returnDate;

	/**
	 * Runs after FXML injection.
	 *
	 * @param location  the location used to resolve relative paths for the root
	 *                  object, or null if the location is not known
	 * @param resources the resources used to localize the root object, or null if
	 *                  the root object was not localized
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addReadersToCombobox();
		LocalDate dateToReturn = LocalDate.now().plusDays(15); // TODO: make a variable setting from 15
		returnDate.setValue(dateToReturn);
	}

	/**
	 * Queries the database and retrieves a list of readers.
	 *
	 * @return ObservableList<Reader> list of readers
	 */
	public ObservableList<Reader> getReadersList() {
		ObservableList<Reader> readersList = FXCollections.observableArrayList();
		String query = "SELECT * FROM readers;";
		Statement statement;
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			Reader readers;
			while (resultSet.next()) {
				readers = new Reader(resultSet.getInt("id"), resultSet.getString("name"));
				readersList.add(readers);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return readersList;
	}

	/**
	 * Displays into the combobox the retrieved list of readers.
	 */
	public void addReadersToCombobox() {
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
		int selectedReaderId = Objects.requireNonNull(AutoComplete.getComboBoxValue(borrowReaderSearch)).getId();

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateBorrowed = LocalDate.now();
		String dateBorrowedText = dateTimeFormatter.format(dateBorrowed);
		String dateToReturnText = dateTimeFormatter.format(returnDate.getValue());
		String query = String.format(
				"INSERT INTO borrows (book_id, reader_id, librarian_id, date_borrowed, date_to_return) "
						+ "VALUES ('%d', '%d', '%d', '%s', '%s'); "
						+ "UPDATE books SET status='borrowed' WHERE id='%d';",
				selectedBookId, selectedReaderId, currentLibrarianId, dateBorrowedText, dateToReturnText,
				selectedBookId);

		Database.modify(query);
		mainController.displayBooks();
		cancel();
	}

	/**
	 * Closes the dialog window.
	 */
	@FXML
	private void cancel() {
		cancel.getScene().getWindow().hide();
	}

}
