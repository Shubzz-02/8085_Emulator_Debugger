import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;


public class EditCell<S, T> extends TableCell<S, T> {

    public static final StringConverter<String> IDENTITY_CONVERTER = new StringConverter<String>() {

        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            return string;
        }

    };
    private final TextField textField = new TextField();
    private final StringConverter<T> converter;


    public EditCell(StringConverter<T> converter) {
        this.converter = converter;

        itemProperty().addListener((obx, oldItem, newItem) -> {
            if (newItem == null) {
                setText(null);
            } else {
                setText(converter.toString(newItem));
            }
        });
        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);

        textField.setOnAction(evt -> {
            commitEdit(this.converter.fromString(textField.getText()));
        });
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(this.converter.fromString(textField.getText()));
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                textField.setText(converter.toString(getItem()));
                cancelEdit();
                event.consume();
            } else if (event.getCode() == KeyCode.RIGHT) {
                getTableView().getSelectionModel().selectRightCell();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                getTableView().getSelectionModel().selectLeftCell();
                event.consume();
            } else if (event.getCode() == KeyCode.UP) {
                getTableView().getSelectionModel().selectAboveCell();
                event.consume();
            } else if (event.getCode() == KeyCode.DOWN) {
                getTableView().getSelectionModel().selectBelowCell();
                event.consume();
            }
        });
    }

    public static <S> EditCell<S, String> createStringEditCell() {
        return new EditCell<S, String>(IDENTITY_CONVERTER);
    }


    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(converter.toString(getItem()));
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    public void commitEdit(T item) {

        if (!isEditing() && !item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                CellEditEvent<S, T> event = new CellEditEvent<>(table,
                        new TablePosition<S, T>(table, getIndex(), column),
                        TableColumn.editCommitEvent(), item);
                Event.fireEvent(column, event);
            }
        }

        super.commitEdit(item);

        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

}