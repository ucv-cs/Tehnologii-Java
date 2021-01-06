package jlib.controllers;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jlib.App;
import jlib.models.Book;
import jlib.models.Librarian;
import jlib.models.Reader;
import jlib.utils.Database;

/**
 * Controller for the main window.
 *
 * @see <a
 *      href=https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/'>JavaFX
 *      8 TableView Sorting and Filtering</a>
 * @see <a href=http://tutorials.jenkov.com/javafx/tableview.html>JavaFX
 *      TableView</a>
 */
public class MainController implements Initializable {

	private final Connection connection = Database.connection;

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
	private TextField readersSearch, readersName, readersStatus;

	@FXML
	private TextArea readersAddress;

	@FXML
	private Button readersAdd, readersClear, readersUpdate, readersDelete;

	@FXML
	private ImageView readersPhoto;

	@FXML
	private TableView<Reader> tblReaders;

	@FXML
	private TableColumn<Reader, String> tcReaderId, tcReaderName, tcReaderAddress, tcReaderStatus;

	// librarians tab
	@FXML
	private TextField librariansSearch, librariansUsername, librariansPassword, librariansName, librariansStatus;

	@FXML
	private TextArea librariansAddress;

	@FXML
	private Button librariansAdd, librariansClear, librariansUpdate, librariansDelete;

	@FXML
	private ImageView librariansPhoto;

	@FXML
	private TableView<Librarian> tblLibrarians;

	@FXML
	private TableColumn<Librarian, String> tcLibrarianId, tcLibrarianUsername, tcLibrarianPassword, tcLibrarianName,
			tcLibrarianStatus;

	private TableSelectionModel<Book> bookSelectionModel;
	private ObservableList<Book> selectedBook;

	private TableSelectionModel<Reader> readerSelectionModel;
	private ObservableList<Reader> selectedReader;

	private TableSelectionModel<Librarian> librarianSelectionModel;
	private ObservableList<Librarian> selectedLibrarian;

	@FXML
	private Button logout;

	@FXML
	public Label lblLoggedLibrarian;

	@FXML
	public Circle loggedLibrarian;

	// private double xOffset = 0;
	// private double yOffset = 0;

	protected int currentLibrarianId;

	/**
	 * Runs after FXML injection, so it can initialize the UI.
	 *
	 * @param location  the location used to resolve relative paths for the root
	 *                  object, or null if the location is not known
	 * @param resources the resources used to localize the root object, or null if
	 *                  the root object was not localized
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepareLibraryTab();
		prepareReadersTab();
		prepareLibrariansTab();
	}

	/**
	 * Opens the file chooser dialog and allows selection of an image from the file
	 * system. The selected image is displayed in the UI and its path is stored into
	 * the database.
	 *
	 * @param event the event that holds information about the widget that triggered
	 *              it
	 */
	@FXML
	private void chooseImage(MouseEvent event) {
		// get the id of the pressed button
		String buttonId = event.getPickResult().getIntersectedNode().getId();
		ImageView imageView;

		// detect which button was pressed and set the image view accordingly
		switch (buttonId) {
			case "btnLibraryCover":
				imageView = libraryCover;
				break;
			case "btnReadersPhoto":
				imageView = readersPhoto;
				break;
			case "btnLibrariansPhoto":
				imageView = librariansPhoto;
				break;
			default:
				return; // exit if no id matches
		}

		FileChooser fileChooser = new FileChooser();

		// Set extension filters
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
				new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);

		try {
			if (file != null) {
				String imagePath = file.getAbsolutePath();
				Image image = new Image("file:" + imagePath);
				imageView.setImage(image);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	// Library methods.
	/**
	 * Retrieves the book data and displays it.
	 */
	public void prepareLibraryTab() {

		bookSelectionModel = tblLibrary.getSelectionModel();
		displayBooks();

		selectedBook = bookSelectionModel.getSelectedItems();

		selectedBook.addListener((ListChangeListener<Book>) change -> {
			if (!selectedBook.isEmpty()) {
				libraryTitle.setText(selectedBook.get(0).getTitle());
				libraryAuthors.setText(selectedBook.get(0).getAuthor());
				librarySummary.setText(selectedBook.get(0).getSummary());
				libraryPublisher.setText(selectedBook.get(0).getPublisher());
				libraryEdition.setText(selectedBook.get(0).getEdition());
				libraryYear.setText(selectedBook.get(0).getYear());
				libraryPrice.setText(selectedBook.get(0).getPrice());
				libraryStatus.setText(selectedBook.get(0).getStatus());
				try {
					libraryCover.setImage(new Image("file:" + selectedBook.get(0).getCover()));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}

			}
		});
	}

	/**
	 * Queries the database and retrieves a list of books.
	 *
	 * @return ObservableList<Book>
	 */
	public ObservableList<Book> getBooksList() {
		ObservableList<Book> booksList = FXCollections.observableArrayList();
		String query = "SELECT * FROM books;";
		Statement statement;
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			Book books;
			while (resultSet.next()) {
				books = new Book(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("author"),
						resultSet.getString("edition"), resultSet.getString("year"), resultSet.getString("publisher"),
						resultSet.getString("summary"), resultSet.getString("cover"), resultSet.getString("price"),
						resultSet.getString("status"));
				booksList.add(books);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return booksList;
	}

	/**
	 * Displays into the tableview the retrieved list of books.
	 */
	public void displayBooks() {
		ObservableList<Book> booksList = getBooksList();

		tcBookId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tcBookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		tcBookEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));
		tcBookYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		tcBookPublisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		tcBookStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		FilteredList<Book> filteredData = new FilteredList<>(booksList, flag -> true);

		librarySearch.textProperty().addListener((observable, oldValue, newValue) -> {
			clearBook();
			filteredData.setPredicate(book -> {
				if (book == null || newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (book.getYear().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (book.getPublisher().contains(lowerCaseFilter)) {
					return true;
				} else {
					return book.getStatus().toLowerCase().contains(lowerCaseFilter);
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
		String imagePath = libraryCover.getImage() != null ? libraryCover.getImage().getUrl().replace("file:", "") : "";
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO books (title, author, edition, year, publisher, summary, price, cover, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement.setString(1, libraryTitle.getText());
			statement.setString(2, libraryAuthors.getText());
			statement.setString(3, libraryEdition.getText());
			statement.setString(4, libraryYear.getText());
			statement.setString(5, libraryPublisher.getText());
			statement.setString(6, librarySummary.getText());
			statement.setString(7, libraryPrice.getText());
			statement.setString(8, imagePath);
			statement.setString(9, libraryStatus.getText());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		displayBooks();
		clearBook();
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
		libraryCover.setImage(null);
		bookSelectionModel.clearSelection();
	}

	/**
	 * Updates the book info in the database.
	 */
	@FXML
	private void updateBook() {
		if (!selectedBook.isEmpty()) {
			int id = selectedBook.get(0).getId();
			String imagePath = libraryCover.getImage() != null ? libraryCover.getImage().getUrl().replace("file:", "")
					: "";
			try {
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE books SET title=?, author=?, edition=?, year=?, publisher=?, summary=?, price=?, cover=?, status=? WHERE id=?;");
				statement.setString(1, libraryTitle.getText());
				statement.setString(2, libraryAuthors.getText());
				statement.setString(3, libraryEdition.getText());
				statement.setString(4, libraryYear.getText());
				statement.setString(5, libraryPublisher.getText());
				statement.setString(6, librarySummary.getText());
				statement.setString(7, libraryPrice.getText());
				statement.setString(8, imagePath);
				statement.setString(9, libraryStatus.getText());
				statement.setInt(10, id);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			displayBooks();
			clearBook();
		}
	}

	/**
	 * Deletes the book from the database.
	 */
	@FXML
	private void deleteBook() {
		if (!selectedBook.isEmpty()) {
			int id = selectedBook.get(0).getId();
			String query = String.format("DELETE FROM books WHERE id='%s';", id);
			Database.modify(query);
			displayBooks();
			clearBook();
		}
	}

	/**
	 * Displays a dialog for selecting readers and borrowing the selected book.
	 */
	@FXML
	public void showBorrowDialog() {
		if (selectedBook.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("jLib");
			alert.setHeaderText("jLib 1.0\nEasy Library Management");
			alert.setContentText("First you have to select a book, then click Borrow.");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("resources/logo.png"));
			alert.showAndWait();
			return;
		}

		int id = selectedBook.get(0).getId();

		// check if the book is already borrowed and notify the user
		if (selectedBook.get(0).getStatus().equals("borrowed")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("jLib");
			alert.setHeaderText("jLib 1.0\nEasy Library Management");
			alert.setContentText("The selected book is already borrowed."); // to?, due date?
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("/jlib/resources/logo.png"));
			alert.showAndWait();
			return;
		}

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/jlib/views/borrow_dialog.fxml"));
			Parent window = fxmlLoader.load();

			BorrowController borrowController = fxmlLoader.getController();
			borrowController.selectedBookId = id;
			borrowController.currentLibrarianId = currentLibrarianId;
			borrowController.mainController = this;

			Stage stage = new Stage();
			stage.initStyle(StageStyle.UTILITY);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);
			stage.setScene(new Scene(window));
			stage.show();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Marks the currently selected book as returned (available).
	 */
	@FXML
	public void markReturned() {
		// libraryMarkReturned
		if (selectedBook.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("jLib");
			alert.setHeaderText("jLib 1.0\nEasy Library Management");
			alert.setContentText("First you have to select a book, then click Mark returned.");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("resources/logo.png"));
			alert.showAndWait();
			return;
		}

		int id = selectedBook.get(0).getId();

		// check if the book is already available and notify the user
		if (!selectedBook.get(0).getStatus().equals("borrowed")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("jLib");
			alert.setHeaderText("jLib 1.0\nEasy Library Management");
			alert.setContentText("The selected book is already available for borrowing.");
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("resources/logo.png"));
			alert.showAndWait();
			return;
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateReturned = LocalDate.now();
		String dateReturnedText = dateTimeFormatter.format(dateReturned);
		String query = String.format(
				"UPDATE borrows SET date_returned='%s', active=0 WHERE book_id='%d' AND active=1;"+
				"UPDATE books SET status='available' WHERE id='%d';",
				dateReturnedText, id, id);
		Database.modify(query);
		displayBooks();
	}

	// Reader methods.
	/**
	 * Retrieves the reader data and displays it.
	 */
	public void prepareReadersTab() {

		readerSelectionModel = tblReaders.getSelectionModel();
		displayReaders();

		selectedReader = readerSelectionModel.getSelectedItems();

		selectedReader.addListener((ListChangeListener<Reader>) change -> {
			if (!selectedReader.isEmpty()) {
				readersName.setText(selectedReader.get(0).getName());
				readersAddress.setText(selectedReader.get(0).getAddress());
				readersStatus.setText(selectedReader.get(0).getStatus());
				try {
					readersPhoto.setImage(new Image("file:" + selectedReader.get(0).getPhoto()));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
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
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			Reader readers;
			while (resultSet.next()) {
				readers = new Reader(resultSet.getInt("id"), resultSet.getString("name"),
						resultSet.getString("address"), resultSet.getString("photo"), resultSet.getString("status"));
				readersList.add(readers);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return readersList;
	}

	/**
	 * Displays into the tableview the retrieved list of readers.
	 */
	public void displayReaders() {
		ObservableList<Reader> readersList = getReadersList();

		tcReaderId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcReaderName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcReaderAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		tcReaderStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		FilteredList<Reader> filteredData = new FilteredList<>(readersList, flag -> true);

		readersSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			clearReader();
			filteredData.setPredicate(reader -> {
				if (reader == null || newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (reader.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (reader.getAddress().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return reader.getStatus().toLowerCase().contains(lowerCaseFilter);
				}
			});
		});

		SortedList<Reader> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tblReaders.comparatorProperty());
		tblReaders.setItems(sortedData);
	}

	/**
	 * Adds reader field values to the database.
	 */
	@FXML
	private void addReader() {
		String imagePath = readersPhoto.getImage() != null ? readersPhoto.getImage().getUrl().replace("file:", "") : "";
		try {
			PreparedStatement statement = connection
					.prepareStatement("INSERT INTO readers (name, address, photo, status) VALUES (?, ?, ?, ?)");
			statement.setString(1, readersName.getText());
			statement.setString(2, readersAddress.getText());
			statement.setString(3, imagePath);
			statement.setString(4, readersStatus.getText());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		displayReaders();
		clearReader();
	}

	/**
	 * Clears the reader fields.
	 */
	@FXML
	private void clearReader() {
		String empty = "";
		readersName.setText(empty);
		readersAddress.setText(empty);
		readersStatus.setText(empty);
		readersPhoto.setImage(null);
		readerSelectionModel.clearSelection();
	}

	/**
	 * Updates the reader info in the database.
	 */
	@FXML
	private void updateReader() {
		if (!selectedReader.isEmpty()) {
			int id = selectedReader.get(0).getId();
			String imagePath = readersPhoto.getImage() != null ? readersPhoto.getImage().getUrl().replace("file:", "")
					: "";
			try {
				PreparedStatement statement = connection
						.prepareStatement("UPDATE readers SET name=?, address=?, photo=?, status=? WHERE id=?;");
				statement.setString(1, readersName.getText());
				statement.setString(2, readersAddress.getText());
				statement.setString(3, imagePath);
				statement.setString(4, readersStatus.getText());
				statement.setInt(5, id);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			displayReaders();
			clearReader();
		}
	}

	/**
	 * Deletes the reader from the database.
	 */
	@FXML
	private void deleteReader() {
		if (!selectedReader.isEmpty()) {
			int id = selectedReader.get(0).getId();
			String query = String.format("DELETE FROM readers WHERE id='%s';", id);
			Database.modify(query);
			displayReaders();
			clearReader();
		}
	}

	// Librarian methods.
	/**
	 * Retrieves the librarian data and displays it.
	 */
	public void prepareLibrariansTab() {

		librarianSelectionModel = tblLibrarians.getSelectionModel();
		displayLibrarians();

		selectedLibrarian = librarianSelectionModel.getSelectedItems();

		selectedLibrarian.addListener((ListChangeListener<Librarian>) change -> {
			if (!selectedLibrarian.isEmpty()) {
				librariansUsername.setText(selectedLibrarian.get(0).getUsername());
				librariansPassword.setText(selectedLibrarian.get(0).getPassword());
				librariansName.setText(selectedLibrarian.get(0).getName());
				librariansAddress.setText(selectedLibrarian.get(0).getAddress());
				librariansStatus.setText(selectedLibrarian.get(0).getStatus());
				try {
					librariansPhoto.setImage(new Image("file:" + selectedLibrarian.get(0).getPhoto()));
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
	}

	/**
	 * Queries the database and retrieves a list of librarians.
	 *
	 * @return ObservableList<Librarian>
	 */
	public ObservableList<Librarian> getLibrariansList() {
		ObservableList<Librarian> librariansList = FXCollections.observableArrayList();
		String query = "SELECT * FROM librarians;";
		Statement statement;
		ResultSet resultSet;

		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			Librarian librarians;
			while (resultSet.next()) {
				librarians = new Librarian(resultSet.getInt("id"), resultSet.getString("username"),
						resultSet.getString("password"), resultSet.getString("name"), resultSet.getString("address"),
						resultSet.getString("photo"), resultSet.getString("status"));
				librariansList.add(librarians);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return librariansList;
	}

	/**
	 * Displays into the tableview the retrieved list of librarians.
	 */
	public void displayLibrarians() {
		ObservableList<Librarian> librariansList = getLibrariansList();

		tcLibrarianId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcLibrarianUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		tcLibrarianPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		tcLibrarianName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tcLibrarianStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

		FilteredList<Librarian> filteredData = new FilteredList<>(librariansList, flag -> true);

		librariansSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			clearLibrarian();
			filteredData.setPredicate(librarian -> {
				if (librarian == null || newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (librarian.getName().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (librarian.getUsername().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					return librarian.getStatus().toLowerCase().contains(lowerCaseFilter);
				}
			});
		});

		SortedList<Librarian> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tblLibrarians.comparatorProperty());
		tblLibrarians.setItems(sortedData);
	}

	/**
	 * Adds librarian field values to the database.
	 */
	@FXML
	private void addLibrarian() {
		String imagePath = librariansPhoto.getImage() != null ? librariansPhoto.getImage().getUrl().replace("file:", "")
				: "";
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO librarians (username, password, name, address, photo, status) VALUES (?, ?, ?, ?, ?, ?)");
			statement.setString(1, librariansUsername.getText());
			statement.setString(2, librariansPassword.getText());
			statement.setString(3, librariansName.getText());
			statement.setString(4, librariansAddress.getText());
			statement.setString(5, imagePath);
			statement.setString(6, librariansStatus.getText());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		displayLibrarians();
		clearLibrarian();
	}

	/**
	 * Clears the librarian fields.
	 */
	@FXML
	private void clearLibrarian() {
		String empty = "";
		librariansUsername.setText(empty);
		librariansPassword.setText(empty);
		librariansName.setText(empty);
		librariansAddress.setText(empty);
		librariansStatus.setText(empty);
		librariansPhoto.setImage(null);
		librarianSelectionModel.clearSelection();
	}

	/**
	 * Updates the librarian info in the database.
	 */
	@FXML
	private void updateLibrarian() {
		if (!selectedLibrarian.isEmpty()) {
			int id = selectedLibrarian.get(0).getId();
			String imagePath = librariansPhoto.getImage() != null
					? librariansPhoto.getImage().getUrl().replace("file:", "")
					: "";
			try {
				PreparedStatement statement = connection.prepareStatement(
						"UPDATE librarians SET username=?, password=?, name=?, address=?, photo=?, status=? WHERE id=?;");
				statement.setString(1, librariansUsername.getText());
				statement.setString(2, librariansPassword.getText());
				statement.setString(3, librariansName.getText());
				statement.setString(4, librariansAddress.getText());
				statement.setString(5, imagePath);
				statement.setString(6, librariansStatus.getText());
				statement.setInt(7, id);
				statement.executeUpdate();
				statement.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			// update the user info in the UI
			if (id == currentLibrarianId) {
				lblLoggedLibrarian.setText(librariansName.getText());
				Image librarianPhoto = new Image("file:" + imagePath);
				if (!librarianPhoto.isError()) {
					loggedLibrarian.setFill(new ImagePattern(new Image("file:" + imagePath)));
				}
			}
			displayLibrarians();
			clearLibrarian();
		}
	}

	/**
	 * Deletes the librarian from the database.
	 */
	@FXML
	private void deleteLibrarian() {
		if (!selectedLibrarian.isEmpty()) {
			int id = selectedLibrarian.get(0).getId();
			String query = String.format("DELETE FROM librarians WHERE id='%d';", id);
			Database.modify(query);
			displayLibrarians();
			clearLibrarian();
		}
	}

	/**
	 * Logs out the current librarian, hides the main window and displays the login
	 * window.
	 */
	@FXML
	private void logout() {
		// hide the main window
		logout.getScene().getWindow().hide();
		// close the current database connection
		Database.disconnect();
		try {
			App.app.start(new Stage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Closes the app window.
	 */
	@FXML
	private void closeWindow() {
		Database.disconnect();
		Platform.exit();
	}

	/**
	 * Shows the about dialog.
	 */
	@FXML
	private void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("jLib");
		alert.setHeaderText("jLib 1.0\nEasy Library Management");
		alert.setContentText("Manage the library from your desktop\nCopyright (C) 2021 Alin Clincea");
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/jlib/resources/logo.png"));
		alert.showAndWait();
	}
}