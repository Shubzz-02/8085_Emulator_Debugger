import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;

public class Populate extends Controller {
    static String[][] codeP;
    Controller ct;
    private String[] mnemonics;
    private String[] opcodes;
    private String[] bytes;

    Populate() {
        mnemonics = new String[246];
        opcodes = new String[246];
        bytes = new String[246];
        codeP = new String[2][1000];
        ct = new Controller();
    }

    void getOperands() {
        ClassLoader loader = this.getClass().getClassLoader();
        try {
            int i = 0;
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = (JsonArray) parser.parse(new FileReader(loader.getResource("opcodes.json").getFile()));//"C:\\Users\\Shubzz\\IdeaProjects\\8085_Emulator\\res\\opcodes.json"));
            for (Object o : jsonArray) {
                JsonObject det = (JsonObject) o;
                mnemonics[i] = det.get("mnemonics").toString();
                opcodes[i] = det.get("operand").toString();
                bytes[i] = det.get("bytes").toString();
                i++;
            }
//            System.out.println("Mnemonics at 256= " + mnemonics[245]);
//            System.out.println("opcodes at 256= " + opcodes[245]);
//            System.out.println("bytes at 256= " + bytes[245]);
        } catch (Exception e) {
            System.out.println("ERORRRRR " + e.getMessage());
        }
    }


    void populateTable(String[] code, int sa) {
        if (mnemonics[0] == null) {
            getOperands();
        }
        int index = 0;
        // operand.set(0, new Operands("10000", "", "aaMVI A,B", ""));
        for (int i = 0; i < code.length; i++) {
            String[] m = code[i].split(" ");
            if (m[0].contains(":")) {
                operand.set(index, new Operands(String.format("%04X", sa), m[0].substring(0, m[0].indexOf(":")), code[i].substring(code[i].indexOf(":") + 1), ""));
            } else {
                operand.set(index, new Operands(String.format("%04X", sa), "", code[i], ""));
            }

            codeP[0][i] = code[i];
            String size = getSize(m[0]).substring(1, 2);
            codeP[1][i] = String.valueOf(Integer.parseInt(size));
            index += Integer.parseInt(size);
            sa += Integer.parseInt(size);
        }
    }

    private String getSize(String m) {
        if (m.contains(":"))
            m = m.substring(m.indexOf(":") + 1);
        for (int i = 0; i < mnemonics.length; i++) {
            // System.out.println("mnemonics[i]= "+mnemonics[i].substring(1,mnemonics[i].indexOf(" ")));
            if (m.trim().equalsIgnoreCase(mnemonics[i]) || mnemonics[i].contains(m.trim())) {
                //        System.out.println("M "+m+" found at = "+i);
                return bytes[i];
            }
        }
        return null;
    }
}

