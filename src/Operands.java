import javafx.beans.property.SimpleStringProperty;

public class Operands {

    private SimpleStringProperty memory;
    private SimpleStringProperty label;
    private SimpleStringProperty operand;
    private SimpleStringProperty hex;

    public Operands() {
    }

    public Operands(String memory, String label, String operand, String hex) {
        this.memory = new SimpleStringProperty(memory);
        this.label = new SimpleStringProperty(label);
        this.operand = new SimpleStringProperty(operand);
        this.hex = new SimpleStringProperty(hex);
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

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public SimpleStringProperty labelProperty() {
        return label;
    }

    public String getOperand() {
        return operand.get();
    }

    public void setOperand(String operand) {
        this.operand.set(operand);
    }

    public SimpleStringProperty operandProperty() {
        return operand;
    }

    public String getHex() {
        return hex.get();
    }

    public void setHex(String hex) {
        this.hex.set(hex);
    }

    public SimpleStringProperty hexProperty() {
        return hex;
    }
}
