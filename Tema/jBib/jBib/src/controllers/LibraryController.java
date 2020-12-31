package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Book;

public class LibraryController implements Initializable {

	@FXML
	private TableView<Book> table;

	@FXML
	private TableColumn<Book, String> tcTitle, tcAuthor, tcEdition, tcYear, tcStatus;

	private final ObservableList<Book> bookList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL url, ResourceBundle res) {
		tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
	}
}
