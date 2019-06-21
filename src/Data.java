import javafx.beans.property.SimpleStringProperty;

public class Data {
    private SimpleStringProperty memory;
    private SimpleStringProperty data;

    public Data() {

    }

    public Data(String memory, String data) {
        this.memory = new SimpleStringProperty(memory);
        this.data = new SimpleStringProperty(data);
    }

    public String getMemory() {
        return memory.get();
    }

    public void setMemory(String memory) {
        this.memory.set(memory);
    }

    public SimpleStringProperty memoryProperty() {
        return memory;
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }
}
