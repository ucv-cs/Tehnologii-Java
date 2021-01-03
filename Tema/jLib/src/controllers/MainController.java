package controllers;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Book;
import models.Librarian;
import models.Reader;
import utils.Database;

public class MainController implements Initializable {

	private static Connection connection;

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

	/**
	 * Runs after FXML injection, so it can initialize the UI.
	 *
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		connection = Database.connect();
		prepareLibraryTab();
		prepareReadersTab();
		prepareLibrariansTab();
	}

	/**
	 * Opens the file chooser dialog and allows selection of an image from the file
	 * system. The selected image is displayed in the UI and its path is stored into
	 * the database.
	 *
	 * @param MouseEvent the event holds information about the widget that triggered
	 *                   it
	 */
	@FXML
	private void chooseImage(MouseEvent event) {
		// get the id of the pressed button
		String buttonId = event.getPickResult().getIntersectedNode().getId();
		ImageView imageView;
		String table;
		String field;
		int id;

		// detect which button was pressed and set the image view accordingly
		switch (buttonId) {
			case "btnLibraryCover":
				imageView = libraryCover;
				table = "books";
				field = "cover";
				id = !selectedBook.isEmpty() ? selectedBook.get(0).getId() : -1;
				break;
			case "btnReadersPhoto":
				imageView = readersPhoto;
				table = "readers";
				field = "photo";
				id = !selectedReader.isEmpty() ? selectedReader.get(0).getId() : -1;
				break;
			case "btnLibrariansPhoto":
				imageView = librariansPhoto;
				table = "librarians";
				field = "photo";
				id = !selectedLibrarian.isEmpty() ? selectedLibrarian.get(0).getId() : -1;
				break;
			default:
				return; // exit if no id matches
		}

		// test if there's a selected item, exit if there is none
		if (id == -1) {
			return;
		}

		FileChooser fileChooser = new FileChooser();

		// Set extension filters
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
				new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);

		try {
			if (file != null) {
				// BufferedImage bufferedImage = ImageIO.read(file);
				// Image image = SwingFXUtils.toFXImage(bufferedImage, null);

				String imagePath = file.getAbsolutePath();
				Image image = new Image("file:" + imagePath);
				imageView.setImage(image);

				String query = String.format("UPDATE %s SET %s='%s' WHERE id='%s';", table, field, imagePath, id);
				// updating the observable lists...
				// FIXME: an abstract Model class would be very useful here...
				switch (buttonId) {
					case "btnLibraryCover":
						selectedBook.get(0).setCover(imagePath);
						break;
					case "btnReadersPhoto":
						selectedReader.get(0).setPhoto(imagePath);
						break;
					case "btnLibrariansPhoto":
						selectedLibrarian.get(0).setPhoto(imagePath);
						break;
					default:
						return; // exit if no id matches
				}

				Database.query(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
					try {
						libraryCover.setImage(new Image("file:" + selectedBook.get(0).getCover()));
					} catch (Exception e) {
						System.out.println(e.toString());
					}

				}
			}
		});
	}

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
			clearBook();
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
		// TODO: add cover
		String query = "INSERT INTO books('title','author','edition','year','publisher','summary','price','status') VALUES"
				+ String.format(" ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", libraryTitle.getText(),
						libraryAuthors.getText(), libraryEdition.getText(), libraryYear.getText(),
						libraryPublisher.getText(), librarySummary.getText(), libraryPrice.getText(),
						libraryStatus.getText());
		Database.query(query);
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
			String query = String.format(
					"UPDATE books SET title='%s', author='%s', edition='%s', year='%s', publisher='%s', summary='%s', price='%s', status='%s' WHERE id='%s';",
					libraryTitle.getText(), libraryAuthors.getText(), libraryEdition.getText(), libraryYear.getText(),
					libraryPublisher.getText(), librarySummary.getText(), libraryPrice.getText(),
					libraryStatus.getText(), id);
			Database.query(query);
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
			Database.query(query);
			displayBooks();
			clearBook();
		}
	}

	/**
	 * Displays a dialog for selecting readers and borrowing the selected book.
	 *
	 * @throws Exception
	 */
	@FXML
	public void showBorrowDialog() throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/borrow_dialog.fxml"));
			Parent window = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UTILITY);
			stage.setResizable(false);
			stage.setScene(new Scene(window));
			stage.show();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Reader methods.
	/**
	 * Retrieves the reader data and displays it.
	 */
	public void prepareReadersTab() {

		readerSelectionModel = tblReaders.getSelectionModel();
		displayReaders();

		selectedReader = readerSelectionModel.getSelectedItems();

		selectedReader.addListener(new ListChangeListener<Reader>() {
			@Override
			public void onChanged(Change<? extends Reader> change) {
				if (!selectedReader.isEmpty()) {
					readersName.setText(selectedReader.get(0).getName());
					readersAddress.setText(selectedReader.get(0).getAddress());
					readersStatus.setText(selectedReader.get(0).getStatus());
					try {
						readersPhoto.setImage(new Image("file:" + selectedReader.get(0).getPhoto()));
					} catch (Exception e) {
						System.out.println(e.toString());
					}

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
		ResultSet results;

		try {
			statement = connection.createStatement();
			results = statement.executeQuery(query);
			Reader readers;
			while (results.next()) {
				readers = new Reader(results.getInt("id"), results.getString("name"), results.getString("address"),
						results.getString("photo"), results.getString("status"));
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

		tcReaderId.setCellValueFactory(new PropertyValueFactory<Reader, String>("id"));
		tcReaderName.setCellValueFactory(new PropertyValueFactory<Reader, String>("name"));
		tcReaderAddress.setCellValueFactory(new PropertyValueFactory<Reader, String>("address"));
		tcReaderStatus.setCellValueFactory(new PropertyValueFactory<Reader, String>("status"));

		FilteredList<Reader> filteredData = new FilteredList<>(readersList, flag -> true);

		readersSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			clearReader();
			filteredData.setPredicate(reader -> {
				if (reader == null || newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (reader.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (reader.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (reader.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
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
		// TODO: add photo
		String query = "INSERT INTO readers ('name','address','status') VALUES" + String.format(" ('%s', '%s', '%s');",
				readersName.getText(), readersAddress.getText(), readersStatus.getText());
		Database.query(query);
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
			String query = String.format("UPDATE readers SET name='%s', address='%s', status='%s' WHERE id='%s';",
					readersName.getText(), readersAddress.getText(), readersStatus.getText(), id);
			Database.query(query);
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
			Database.query(query);
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

		selectedLibrarian.addListener(new ListChangeListener<Librarian>() {
			@Override
			public void onChanged(Change<? extends Librarian> change) {
				if (!selectedLibrarian.isEmpty()) {
					librariansUsername.setText(selectedLibrarian.get(0).getUsername());
					librariansPassword.setText(selectedLibrarian.get(0).getPassword());
					librariansName.setText(selectedLibrarian.get(0).getName());
					librariansAddress.setText(selectedLibrarian.get(0).getAddress());
					librariansStatus.setText(selectedLibrarian.get(0).getStatus());
					try {
						librariansPhoto.setImage(new Image("file:" + selectedLibrarian.get(0).getPhoto()));
					} catch (Exception e) {
						System.out.println(e.toString());
					}

				}
			}
		});
	}

	/**
	 * Queries the database and retrieves a list of librarians.
	 *
	 * @return
	 */
	public ObservableList<Librarian> getLibrariansList() {
		ObservableList<Librarian> librariansList = FXCollections.observableArrayList();
		String query = "SELECT * FROM librarians;";
		Statement statement;
		ResultSet results;

		try {
			statement = connection.createStatement();
			results = statement.executeQuery(query);
			Librarian librarians;
			while (results.next()) {
				librarians = new Librarian(results.getInt("id"), results.getString("username"),
						results.getString("password"), results.getString("name"), results.getString("address"),
						results.getString("photo"), results.getString("status"));
				librariansList.add(librarians);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return librariansList;
	}

	/**
	 * Displays into the tableview the retrieved list of librarians.
	 */
	public void displayLibrarians() {
		ObservableList<Librarian> librariansList = getLibrariansList();

		tcLibrarianId.setCellValueFactory(new PropertyValueFactory<Librarian, String>("id"));
		tcLibrarianUsername.setCellValueFactory(new PropertyValueFactory<Librarian, String>("username"));
		tcLibrarianPassword.setCellValueFactory(new PropertyValueFactory<Librarian, String>("password"));
		tcLibrarianName.setCellValueFactory(new PropertyValueFactory<Librarian, String>("name"));
		tcLibrarianStatus.setCellValueFactory(new PropertyValueFactory<Librarian, String>("status"));

		FilteredList<Librarian> filteredData = new FilteredList<>(librariansList, flag -> true);

		librariansSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			clearLibrarian();
			filteredData.setPredicate(librarian -> {
				if (librarian == null || newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (librarian.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (librarian.getUsername().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (librarian.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
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
		// TODO: add photo
		String query = "INSERT INTO librarians ('username', 'password', 'name','address','status') VALUES" + String
				.format(" ('%s', '%s','%s', '%s', '%s');", librariansUsername.getText(), librariansPassword.getText(),
						librariansName.getText(), librariansAddress.getText(), librariansStatus.getText());
		Database.query(query);
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
			String query = String.format(
					"UPDATE librarians SET username='%s', password='%s', name='%s', address='%s', status='%s' WHERE id='%s';",
					librariansUsername.getText(), librariansPassword.getText(), librariansName.getText(),
					librariansAddress.getText(), librariansStatus.getText(), id);
			Database.query(query);
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
			String query = String.format("DELETE FROM librarians WHERE id='%s';", id);
			Database.query(query);
			displayLibrarians();
			clearLibrarian();
		}
	}
}