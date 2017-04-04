package gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * Created by dbrisingr on 04/04/2017.
 */
public class CommonFunctions {

    public static void setCopyAction(TableView tableView){
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MenuItem item = new MenuItem("Copy");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<TablePosition> posList = tableView.getSelectionModel().getSelectedCells();
                int old_r = -1;
                StringBuilder clipboardString = new StringBuilder();
                for (TablePosition p : posList) {
                    int r = p.getRow();
                    for(int i=0; i < 3; i++){
                        TableColumn tc = (TableColumn) tableView.getColumns().get(i);
                        Object cell = tc.getCellData(r);
                        if (cell == null)
                            cell = "";
                        if (old_r == r)
                            clipboardString.append('\t');
                        else if (old_r != -1)
                            clipboardString.append('\n');
                        clipboardString.append(cell);
                        old_r = r;
                    }
                }
                final ClipboardContent content = new ClipboardContent();
                content.putString(clipboardString.toString());
                Clipboard.getSystemClipboard().setContent(content);
            }
        });
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(item);
        tableView.setContextMenu(menu);
    }
}
