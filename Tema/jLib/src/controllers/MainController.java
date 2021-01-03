package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Book;
import models.Librarian;
import models.Reader;
import utils.Database;

public class MainController implements Initializable {

	private static Connection connection;
	private FileChooser fileChooser;

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
	private Button libraryBorrow, libraryMarkReturned, libraryAdd, libraryClear, libraryUpdate, libraryDelete,
			btnLibraryCover;

	@FXML
	private ImageView libraryCover;

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

	TableSelectionModel<Book> selectionModel;
	ObservableList<Book> selectedBook;

	/**
	 * Runs after FXML injection, so it can initialize the UI.
	 *
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		connection = Database.connect();
		selectionModel = tblLibrary.getSelectionModel();
		displayBooks();

		selectedBook = selectionModel.getSelectedItems();

		selectedBook.addListener(new ListChangeListener<Book>() {
			@Override
			public void onChanged(Change<? extends Book> change) {
				if (!selectedBook.isEmpty()) {
					libraryTitle.setText(selectedBook.get(0).getTitle());
					libraryAuthors.setText(selectedBook.get(0).getAuthor());
					librarySummary.setText(selectedBook.get(0).getSummary());
					libraryPublisher.setText(selectedBook.get(0).getPublisher());
					libraryEdition.setText(selectedBook.get(0).getEdition());
					libraryYear.setText(selectedBook.get(0).getYear());
					libraryPrice.setText(selectedBook.get(0).getPrice());
					libraryStatus.setText(selectedBook.get(0).getStatus());
				}
			}
		});

		/*
		 * fileChooser = new FileChooser();
		 *
		 * libraryCover.setOnAction(new EventHandler<ActionEvent>() {
		 *
		 * @Override public void handle(final ActionEvent e) { File file =
		 * fileChooser.showOpenDialog(stage); if (file != null) { openFile(file); } }
		 * });
		 */
	}

	// Library methods.

	/**
	 * Queries the database and retrieves a list of books.
	 *
	 * @return
	 */
	public ObservableList<Book> getBooksList() {
		ObservableList<Book> booksList = FXCollections.observableArrayList();
		String query = "SELECT * FROM books;";
		Statement statement;
		ResultSet results;

		try {
			statement = connection.createStatement();
			results = statement.executeQuery(query);
			Book books;
			while (results.next()) {
				books = new Book(results.getInt("id"), results.getString("title"), results.getString("author"),
						results.getString("edition"), results.getString("year"), results.getString("publisher"),
						results.getString("summary"), results.getString("cover"), results.getString("price"),
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
				if (book == null || newValue == null || newValue.isEmpty()) {
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

	/**
	 * Adds book field values to the database.
	 */
	@FXML
	private void addBook() {
		String query = "INSERT INTO books('title','author','edition','year','publisher','summary','price','status') VALUES"
				+ String.format(" ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", libraryTitle.getText(),
						libraryAuthors.getText(), libraryEdition.getText(), libraryYear.getText(),
						libraryPublisher.getText(), librarySummary.getText(), libraryPrice.getText(),
						libraryStatus.getText());
		Database.query(query);
		displayBooks();
	}

	/**
	 * Clears the book fields.
	 */
	@FXML
	private void clearBook() {
		String empty = "";
		libraryTitle.setText(empty);
		libraryAuthors.setText(empty);
		librarySummary.setText(empty);
		libraryPublisher.setText(empty);
		libraryEdition.setText(empty);
		libraryYear.setText(empty);
		libraryPrice.setText(empty);
		libraryStatus.setText(empty);
	}

	/**
	 * Updates the book info in the database.
	 */
	@FXML
	private void updateBook() {
		if (!selectedBook.isEmpty()) {
			int id = selectedBook.get(0).getId();
			String query = String.format(
					"UPDATE books SET \"title\"=\"%s\", \"author\"=\"%s\", \"edition\"=\"%s\", \"year\"=\"%s\", \"publisher\"=\"%s\", \"summary\"=\"%s\", \"price\"=\"%s\", \"status\"=\"%s\" WHERE \"id\"=\"%s\";",
					libraryTitle.getText(), libraryAuthors.getText(), libraryEdition.getText(), libraryYear.getText(),
					libraryPublisher.getText(), librarySummary.getText(), libraryPrice.getText(),
					libraryStatus.getText(), id);
			Database.query(query);
			displayBooks();
		}
	}

	/**
	 * Deletes the book from the database.
	 */
	@FXML
	private void deleteBook() {
		if (!selectedBook.isEmpty()) {
			int id = selectedBook.get(0).getId();
			String query = String.format("DELETE FROM books WHERE \"id\"=\"%s\";", id);
			Database.query(query);
			displayBooks();
		}

	}

	/**
	 * Opens the file chooser dialog and allows selection of an image from the file
	 * system. The selected image is displayed in the UI and its path is stored into
	 * the database.
	 */
	@FXML
	private void chooseImage() {
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
		FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
		fileChooser.getExtensionFilters().addAll(extFilterjpg, extFilterpng);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			libraryCover.setImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Reader methods.
}