package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;
import models.Librarian;
import models.Reader;
import utils.Database;

public class MainController implements Initializable {

	// tabs
	@FXML
	private Tab tabLibrary, tabReaders, tabLibrarians;

	// library tab
	@FXML
	private TextField librarySearch, libraryTitle, libraryAuthors, libraryPublisher, libraryEdition, libraryYear,
			libraryPrice, libraryStatus;

	@FXML
	private TextArea librarySummary;

	@FXML
	private Button libraryBorrow, libraryMarkReturned, libraryAdd, libraryUpdate, libraryDelete;

	@FXML
	private TableView<Book> tblLibrary;

	@FXML
	private TableColumn<Book, String> tcBookId, tcBookTitle, tcBookAuthor, tcBookEdition, tcBookYear, tcBookPublisher,
			tcBookStatus;

	// readers tab
	@FXML
	private TextField readersSearch, readersName, readersCity, readersCounty, readersStatus;

	@FXML
	private TextArea readersAddress;

	@FXML
	private Button readersAdd, readersUpdate, readersDelete;

	@FXML
	private TableView<Reader> tblReaders;

	@FXML
	private TableColumn<Reader, String> tcReaderName, tcReaderDateJoined, tcReaderStatus;

	// librarians tab
	@FXML
	private TextField librariansSearch, librariansName, librariansCity, librariansCounty, librariansStatus;

	@FXML
	private TextArea librariansAddress;

	@FXML
	private Button librariansAdd, librariansUpdate, librariansDelete;

	@FXML
	private TableView<Librarian> tblLibrarians;

	@FXML
	private TableColumn<Librarian, String> tcLibrarianUsername, tcLibrarianPassword, tcLibrarianName,
			tcLibrarianDateJoined, tcLibrarianStatus;

	/**
	 * Runs after FXML injection, so it can initialize the UI.
	 *
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayBooks();
	}

	// Library methods.

	/**
	 * Queries the database and retrieves a list of books.
	 *
	 * @return
	 */
	public ObservableList<Book> getBooksList() {
		Connection connection = Database.connect();

		ObservableList<Book> booksList = FXCollections.observableArrayList();
		String query = "SELECT * FROM books ";
		Statement statement;
		ResultSet results;

		try {
			statement = connection.createStatement();
			results = statement.executeQuery(query);
			Book books;
			while (results.next()) {
				books = new Book(results.getInt("id"), results.getString("title"), results.getString("author"),
						results.getString("edition"), results.getString("year"), results.getString("publisher"),
						results.getString("status"));
				booksList.add(books);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return booksList;
	}

	/**
	 * Displays into the tableview the retrieved list of books.
	 */
	public void displayBooks() {
		ObservableList<Book> booksList = getBooksList();

		tcBookId.setCellValueFactory(new PropertyValueFactory<Book, String>("id"));
		tcBookTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		tcBookAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
		tcBookEdition.setCellValueFactory(new PropertyValueFactory<Book, String>("edition"));
		tcBookYear.setCellValueFactory(new PropertyValueFactory<Book, String>("year"));
		tcBookPublisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
		tcBookStatus.setCellValueFactory(new PropertyValueFactory<Book, String>("status"));

		FilteredList<Book> filteredData = new FilteredList<>(booksList, flag -> true);

		librarySearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(book -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (book.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (book.getAuthor().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (book.getYear().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (book.getPublisher().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (book.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});
		});

		SortedList<Book> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tblLibrary.comparatorProperty());
		tblLibrary.setItems(sortedData);
	}
}