package jlib.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Utility class for creating autocomplete comboboxes in JavaFX.
 *
 * @author Mateus Viccari
 * @see <a href="https://stackoverflow.com/a/27384068">AutoComplete ComboBox in
 *      JavaFX</a>
 */
public class AutoComplete {

	public interface AutoCompleteComparator<T> {
		boolean matches(String typedText, T objectToCompare);
	}

	/**
	 * Attaches the autocomplete functionality to the given combobox.
	 *
	 * @param <T>              supplied model type
	 * @param comboBox         control to be attached to
	 * @param comparatorMethod method used to compare text changes
	 */
	public static <T> void autoCompleteComboBoxPlus(ComboBox<T> comboBox, AutoCompleteComparator<T> comparatorMethod) {
		ObservableList<T> data = comboBox.getItems();

		comboBox.setEditable(true);
		comboBox.getEditor().focusedProperty().addListener(observable -> {
			if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
				comboBox.getEditor().setText(null);
			}
		});
		comboBox.addEventHandler(KeyEvent.KEY_PRESSED, t -> comboBox.hide());
		comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<>() {

			private boolean moveCaretToPos = false;
			private int caretPos;

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP) {
					caretPos = -1;
					if (comboBox.getEditor().getText() != null) {
						moveCaret(comboBox.getEditor().getText().length());
					}
					return;
				} else if (event.getCode() == KeyCode.DOWN) {
					if (!comboBox.isShowing()) {
						comboBox.show();
					}
					caretPos = -1;
					if (comboBox.getEditor().getText() != null) {
						moveCaret(comboBox.getEditor().getText().length());
					}
					return;
				} else if (event.getCode() == KeyCode.BACK_SPACE) {
					if (comboBox.getEditor().getText() != null) {
						moveCaretToPos = true;
						caretPos = comboBox.getEditor().getCaretPosition();
					}
				} else if (event.getCode() == KeyCode.DELETE) {
					if (comboBox.getEditor().getText() != null) {
						moveCaretToPos = true;
						caretPos = comboBox.getEditor().getCaretPosition();
					}
				} else if (event.getCode() == KeyCode.ENTER) {
					int selectedIndex = comboBox.getSelectionModel().getSelectedIndex();
					if (selectedIndex > 0)
						comboBox.getSelectionModel().select(selectedIndex);
					else
						comboBox.getSelectionModel().selectFirst();
					return;
				}

				if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
						|| event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
						|| event.isControlDown() || event.getCode() == KeyCode.HOME || event.getCode() == KeyCode.END
						|| event.getCode() == KeyCode.TAB) {
					return;
				}

				ObservableList<T> list = FXCollections.observableArrayList();
				for (T aData : data) {
					if (aData != null && comboBox.getEditor().getText() != null
							&& comparatorMethod.matches(comboBox.getEditor().getText(), aData)) {
						list.add(aData);
					}
				}
				String text = "";
				if (comboBox.getEditor().getText() != null) {
					text = comboBox.getEditor().getText();
				}

				comboBox.setItems(list);
				comboBox.getEditor().setText(text);
				if (!moveCaretToPos) {
					caretPos = -1;
				}
				moveCaret(text.length());
				if (!list.isEmpty()) {
					comboBox.show();
				}
			}

			private void moveCaret(int textLength) {
				if (caretPos == -1) {
					comboBox.getEditor().positionCaret(textLength);
				} else {
					comboBox.getEditor().positionCaret(caretPos);
				}
				moveCaretToPos = false;
			}
		});
	}

	/**
	 * Retrieves the selected object in the combobox.
	 *
	 * @param <T>      supplied model type
	 * @param comboBox control from which the T value is retrieved
	 * @return T the selected object as generic type T
	 */
	public static <T> T getComboBoxValue(ComboBox<T> comboBox) {
		if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
			return null;
		} else {
			return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
		}
	}
}