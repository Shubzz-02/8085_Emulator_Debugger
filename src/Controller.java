import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    static ObservableList<Operands> operand;
    private static Operands operands;
    private static Data data;
    private int i = 0, point = 0, flag = 0, dSa = 0, jumped = 0;
    @FXML
    private TableView<Operands> tableCode;
    @FXML
    private TableColumn<Operands, String> memoryColumn;
    @FXML
    private TableColumn<Operands, String> labelColumn;
    @FXML
    private TableColumn<Operands, String> operandColumn;
    @FXML
    private TableColumn<Operands, String> hexColumn;
    @FXML
    private TableView<Data> dataCode;
    @FXML
    private TableColumn<Data, String> dataMemory;
    @FXML
    private TableColumn<Data, String> dataTdata;
    private ObservableList<Data> storeData;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem menuNew;

    @FXML
    private TextField sAddress;

    @FXML
    private TextField sDAddress;

    @FXML
    private TextArea codeArea;

    @FXML
    private TextField accText;

    @FXML
    private TextField bText;

    @FXML
    private TextField cText;

    @FXML
    private TextField dText;

    @FXML
    private TextField eText;

    @FXML
    private TextField hText;

    @FXML
    private TextField lText;

    @FXML
    private TextField ipText;

    @FXML
    private TextField sText;

    @FXML
    private TextField zText;

    @FXML
    private TextField acText;

    @FXML
    private TextField pText;

    @FXML
    private TextField cyText;

    @FXML
    private Button contButton;

    @FXML
    private Button runButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button loadAddButton;


    @FXML
    void continueButtonClicked(ActionEvent event) {
        Runnable selection = () -> {
            tableCode.requestFocus();
            tableCode.getSelectionModel().select(i);
            tableCode.getFocusModel().focus(i);
        };
        selection.run();
        String step = Populate.codeP[0][point];
//        for(int i=0;i<15;i++){
//            System.out.println("Point i -----=  "+Populate.codeP[0][i]);
//        }
        process(step);
    }

    @FXML
    void helpButtonClicked(ActionEvent event) {

    }

    @FXML
    void loadAddressClicked(ActionEvent event) {
        try {
            if (sAddress.getText() == null || sAddress.getText().isEmpty() || sDAddress.getText() == null || sDAddress.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enter Address");
                alert.setContentText("Address Empty!! Enter valid Address");
                alert.show();
            } else {
                int sa = Integer.parseInt(sAddress.getText(), 16);
                int da = Integer.parseInt(sDAddress.getText(), 16);
                if (sa > 65535 || sa < 0 || da > 65535 || da < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Enter valid Address");
                    alert.setContentText("Addresses can not be less than 0 and greater than FFFF");
                    alert.show();
                } else if (sa > 64534 || da > 64534) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Info");
                    alert.setContentText("Address recommended less than FC18 ");
                    alert.show();
                } else {
                    if ((sa + 1000) - da > 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Enter valid Address");
                        alert.setContentText("Addresses Overlapping!! Take minimum diff of 1000 b/w two address");
                        alert.show();
                    } else {
                        dSa = da;
                        for (int i = sa; i < 1000 + sa; i++) {
                            operand.add(new Operands(String.format("%04X", i), "", "", ""));
                        }
                        for (int i = da; i < 1000 + da; i++) {
                            storeData.add(new Data(String.format("%04X", i), ""));
                        }
                        tableCode.setItems(operand);
                        dataCode.setItems(storeData);
                        loadButton.setDisable(false);
                        loadAddButton.setDisable(true);
                        runButton.setDisable(false);
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Address");
            alert.setContentText("Pleaase Enter a valid Address");
            alert.show();
        }
    }

    @FXML
    void loadButtonClicked(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load .asm File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("asm Files", "*.asm"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                FileReader file = new FileReader(selectedFile);
                char[] data = new char[300];
                file.read(data);
                String codet = "";
                for (char c : data)
                    codet = codet.concat(String.valueOf(c));
                codeArea.setText(codet);
                file.close();
                loadButton.setDisable(true);
            }
        } catch (Exception error) {

        }
    }

    @FXML
    void runButtonClicked(ActionEvent event) {
        if (!codeArea.getText().trim().isEmpty()) {
            String[] asmCode = codeArea.getText().trim().replaceAll("(?m)^\\s*$[\n\r]{1,}", "").split("\n");
            new Populate().populateTable(asmCode, Integer.parseInt(sAddress.getText(), 16));
            // accText.setText(String.format("%02X", 0));
            bText.setText(String.format("%02X", 0));
            accText.setText(String.format("%02X", 0));
            cText.setText(String.format("%02X", 0));
            dText.setText(String.format("%02X", 0));
            eText.setText(String.format("%02X", 0));
            hText.setText(String.format("%02X", 0));
            lText.setText(String.format("%02X", 0));
            ipText.setText(String.format("%02X", 0));
            sText.setText(String.format("%02X", 0));
            zText.setText(String.format("%02X", 0));
            acText.setText(String.format("%02X", 0));
            pText.setText(String.format("%02X", 0));
            cyText.setText(String.format("%02X", 0));
            contButton.setDisable(false);
            runButton.setDisable(true);
            //  operand.set(0,new Operands("10000","","aaMVI A,B",""));

//        operand.add(new Operands("0000", "", "MVI A,B", ""));
//        operand.set(0,new Operands("0001","","aaMVI A,B",""));
//        tableCode.setItems(operand);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Code Empty");
            alert.setContentText("Please Enter code or load .asm file from disk");
            alert.show();
        }

    }

    @FXML
    void newProcess(ActionEvent event) {
        codeArea.deleteText(0, codeArea.getText().length());
        sAddress.setText("");
        sDAddress.setText("");
        accText.setText("");
        bText.setText("");
        cText.setText("");
        dText.setText("");
        eText.setText("");
        hText.setText("");
        lText.setText("");
        ipText.setText("");
        sText.setText("");
        zText.setText("");
        accText.setText("");
        pText.setText("");
        cText.setText("");
        tableCode.getItems().clear();
        dataCode.getItems().clear();
        i = 0;
        flag = 0;
        dSa = 0;
        point = 0;
        loadAddButton.setDisable(false);
        loadButton.setDisable(true);
        runButton.setDisable(true);
        contButton.setDisable(true);
    }

    @FXML
    void quit(ActionEvent event) {
        System.exit(0);
    }

    //    final  ObservableList<Operands> operand =FXCollections.observableArrayList(
//            new Operands("0000","","MVI A,B",""),
//                new Operands("0001","","MVI A,B","")
//    );
//    public ObservableList<Operands> getOperand(){
//        ObservableList<Operands> operand = FXCollections.observableArrayList();
//        operand.add(new Operands("0000","","MVI A,B",""));
//        operand.add(new Operands("0001","","MVI A,B",""));
//        return operand;
//    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        memoryColumn.setCellValueFactory(new PropertyValueFactory<>("Memory"));
        labelColumn.setCellValueFactory(new PropertyValueFactory<>("Label"));
        operandColumn.setCellValueFactory(new PropertyValueFactory<>("Operand"));
        hexColumn.setCellValueFactory(new PropertyValueFactory<>("Hex"));
        operands = new Operands();
        operand = FXCollections.observableArrayList();
        dataMemory.setCellValueFactory(new PropertyValueFactory<>("Memory"));
        dataTdata.setCellValueFactory(new PropertyValueFactory<>("Data"));
        dataTdata.setCellFactory(TextFieldTableCell.forTableColumn());

//        dataTdata.setCellFactory(col
//        );
//        dataTdata.setOnEditCommit(event -> {
//            final String value = event.getNewValue() != null ?
//                    event.getNewValue() : event.getOldValue();
//            ((Data) event.getTableView().getItems()
//            .get(event.getTablePosition().getRow())).setData(value);
//            tableCode.refresh();
//        });
        //data = new Data();
        storeData = FXCollections.observableArrayList();


//        assert codeArea != null : "fx:id=\"codeArea\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert accText != null : "fx:id=\"accText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert bText != null : "fx:id=\"bText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert cText != null : "fx:id=\"cText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert dText != null : "fx:id=\"dText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert eText != null : "fx:id=\"eText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert hText != null : "fx:id=\"hText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert lText != null : "fx:id=\"lText\" was not injected: check your FXML file '8085Gui.fxml'.";
//        assert ipText != null : "fx:id=\"ipText\" was not injected: check your FXML file '8085Gui.fxml'.";

    }

    /*----------------------------------------------------OPERANDS SECTION---------------------------------------------------------------------*/

    private void process(String step) {
        // System.out.println("STEP= " + step);
        String[] result = step.split(" ");

        //   System.out.println("i = " + 0 + " " + result[0]); MVI
        // System.out.println("i = " + 1 + " " + result[1]); A,25H
        if (result[0].contains(":")) {
            result[0] = result[0].substring(result[0].indexOf(":") + 1);
        }
        switch (result[0].trim()) {
            case "MOV":
                mov(result[1]);
                point++;
                break;
            case "MVI":
                mvi(result[1]);
                point++;
                break;
            case "SUB":
                sub(result[1]);
                point++;
                break;
            case "LDA":
                lda(result[1]);
                point++;
                break;
            case "JM":
                jm(result[1]);
                break;
            case "CALL":
                call(result[1]);
                break;
            case "STA":
                sta(result[1]);
                point++;
                break;
            case "RET":
                ret();
                break;
            case "HLT":
                hlt();
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Not Supported");
                alert.setContentText("This Operand is not yet Supported!!");
                alert.show();
                break;
        }
    }

    private void lda(String result) {
        int t = dSa;
        int kvb = 0;
        int sadd = Integer.parseInt(result.substring(0, result.length() - 1), 16);
        while (t != sadd) {
            kvb++;
            t++;
        }
        data = storeData.get(kvb);
        String datasto = data.getData();
        accText.setText(datasto);
        i = i + Integer.parseInt(Populate.codeP[1][point]);
    }

    private void mov(String result) {
        String[] comm = result.split(",");
        switch (comm[0]) {
            case "A":
                switch (comm[1]) {
                    case "A":
                        accText.setText(accText.getText());
                        break;
                    case "B":
                        accText.setText(bText.getText());
                        break;
                    case "C":
                        accText.setText(cText.getText());
                        break;
                    case "D":
                        accText.setText(dText.getText());
                        break;
                    case "E":
                        accText.setText(eText.getText());
                        break;
                    case "H":
                        accText.setText(hText.getText());
                        break;
                    case "L":
                        accText.setText(lText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "B":
                switch (comm[1]) {
                    case "A":
                        bText.setText(accText.getText());
                        break;
                    case "C":
                        bText.setText(cText.getText());
                        break;
                    case "D":
                        bText.setText(dText.getText());
                        break;
                    case "E":
                        bText.setText(eText.getText());
                        break;
                    case "H":
                        bText.setText(hText.getText());
                        break;
                    case "L":
                        bText.setText(lText.getText());
                        break;
                    case "B":
                        bText.setText(bText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "C":
                switch (comm[1]) {
                    case "A":
                        cText.setText(accText.getText());
                        break;
                    case "C":
                        cText.setText(cText.getText());
                        break;
                    case "B":
                        cText.setText(bText.getText());
                        break;
                    case "D":
                        cText.setText(dText.getText());
                        break;
                    case "E":
                        cText.setText(eText.getText());
                        break;
                    case "H":
                        cText.setText(hText.getText());
                        break;
                    case "L":
                        cText.setText(lText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "D":
                switch (comm[1]) {
                    case "A":
                        dText.setText(accText.getText());
                        break;
                    case "B":
                        dText.setText(bText.getText());
                        break;
                    case "C":
                        dText.setText(cText.getText());
                        break;
                    case "E":
                        dText.setText(eText.getText());
                        break;
                    case "H":
                        dText.setText(hText.getText());
                        break;
                    case "D":
                        dText.setText(dText.getText());
                        break;
                    case "L":
                        dText.setText(lText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "E":
                switch (comm[1]) {
                    case "A":
                        eText.setText(accText.getText());
                        break;
                    case "B":
                        eText.setText(bText.getText());
                        break;
                    case "C":
                        eText.setText(cText.getText());
                        break;
                    case "D":
                        eText.setText(dText.getText());
                        break;
                    case "H":
                        eText.setText(hText.getText());
                        break;
                    case "L":
                        eText.setText(lText.getText());
                        break;
                    case "E":
                        eText.setText(eText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "H":
                switch (comm[1]) {
                    case "A":
                        hText.setText(accText.getText());
                        break;
                    case "B":
                        hText.setText(bText.getText());
                        break;
                    case "H":
                        hText.setText(hText.getText());
                        break;
                    case "C":
                        hText.setText(cText.getText());
                        break;
                    case "D":
                        hText.setText(dText.getText());
                        break;
                    case "E":
                        hText.setText(eText.getText());
                        break;
                    case "L":
                        hText.setText(lText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "L":
                switch (comm[1]) {
                    case "A":
                        lText.setText(accText.getText());
                        break;
                    case "L":
                        lText.setText(lText.getText());
                        break;
                    case "B":
                        lText.setText(bText.getText());
                        break;
                    case "C":
                        lText.setText(cText.getText());
                        break;
                    case "D":
                        lText.setText(dText.getText());
                        break;
                    case "E":
                        lText.setText(eText.getText());
                        break;
                    case "H":
                        lText.setText(lText.getText());
                        break;
                }
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            default:
                break;
        }
    }

    private void hlt() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Finished");
        alert.setContentText("Debugging Complete");
        alert.show();
        contButton.setDisable(true);
    }

    private void ret() {

        for (int lo = point - 1; lo > flag; lo--) {
            i = i - Integer.parseInt(Populate.codeP[1][lo]);
        }
        point = flag + 1;
    }

    private void sta(String result) {
        int t = dSa;
        int kvb = 0;
        int sadd = Integer.parseInt(result.substring(0, result.length() - 1), 16);
        while (t != sadd) {
            kvb++;
            t++;
        }
        storeData.set(kvb, new Data(String.format("%04X", t), accText.getText()));
//        System.out.println("i =======" + i);
//        System.out.println("POINT---------= " + Populate.codeP[1][point]);
        i = i + Integer.parseInt(Populate.codeP[1][point]);
        //      System.out.println("i =======" + i);
    }

    private void call(String result) {
        int che = 0;
        if (jumped == 1) {
            //     System.out.println("I AM HERE________________________----------");
            while (Populate.codeP[0][che] != null) {
                // System.out.println("Populate.codeP[0][che] = "+Populate.codeP[0][che]+" && result = "+result);
                if (Populate.codeP[0][che].contains(result) && che != point) {
                    System.out.println("Found at= " + che);
                    break;
                }
                che++;
            }
            // System.out.println("i =======" + i);
            for (int lo = point; lo < che; lo++) {
                i = i + Integer.parseInt(Populate.codeP[1][lo]);
            }
            //  System.out.println("i =======" + i);
            flag = point;
            point = che;
            jumped = 0;
        } else {
            i = i + Integer.parseInt(Populate.codeP[1][point]);
            point++;
        }

    }

    private void jm(String result) {
        int che = 0;
        if (sText.getText().equalsIgnoreCase("1")) {
            //System.out.println("I AM HERE________________________----------");
            while (Populate.codeP[0][che] != null) {
                if (Populate.codeP[0][che].contains(result) && che != point) {
                    //    System.out.printf("Found at= "+che);
                    break;
                }
                che++;
            }
            for (int lo = point; lo < che; lo++) {
                i = i + Integer.parseInt(Populate.codeP[1][lo]);
            }
            jumped = 1;
            point = che;
        } else {
            i = i + Integer.parseInt(Populate.codeP[1][point]);
            point++;
        }
    }


    private void sub(String result) {
        int a = Integer.parseInt(accText.getText(), 16);
        // System.out.println("REG A= " + a);
        switch (result) {
            case "A":
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "B":
                int b = Integer.parseInt(bText.getText(), 16);
                // System.out.println("REG B= " + b);
                a = a - b;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "C":
                int c = Integer.parseInt(cText.getText(), 16);
                a = a - c;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "D":
                int d = Integer.parseInt(dText.getText(), 16);
                a = a - d;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "E":
                int e = Integer.parseInt(eText.getText(), 16);
                a = a - e;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "H":
                int h = Integer.parseInt(hText.getText(), 16);
                a = a - h;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "L":
                int l = Integer.parseInt(lText.getText(), 16);
                a = a - l;
                if (a < 0) {
                    sText.setText("1");
                }
                accText.setText(String.format("%02X", Math.abs(a)));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            default:
                break;
        }
    }

    private void mvi(String result) {
        String[] comm = result.split(",");
        switch (comm[0]) {
            case "A":
                accText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "B":
                bText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "C":
                cText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "D":
                dText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "E":
                eText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "H":
                hText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            case "L":
                lText.setText(comm[1].substring(0, 2));
                i = i + Integer.parseInt(Populate.codeP[1][point]);
                break;
            default:
                break;
        }
    }

    public void onDataEdit(TableColumn.CellEditEvent<Data, String> dataStringCellEditEvent) {
        data = dataCode.getSelectionModel().getSelectedItem();
        data.setData(dataStringCellEditEvent.getNewValue());
    }
}
