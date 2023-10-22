import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SmallVm {
    private static final int MAX_MEMORY_SIZE = 1000;
    private boolean goProg = true;

    //Stores instructions to execute
    private ArrayList<String> instructions = new ArrayList<>(MAX_MEMORY_SIZE);

    //Stores this.descriptors of variables
    private ArrayList<Descriptor> descriptors = new ArrayList<>();

    //Initialize Method object to execute basic methods
    private Operation opExe = new Operation();

    public static void main(String[] args) throws Exception {
        SmallVm vm = new SmallVm();
        vm.run();
    }

    public SmallVm() {
    }

    public void run() throws Exception
    {
        pullInstructions();

        while(goProg){
            //Iterates through each instruction in the instructions list
            for (String instruct: this.instructions) {

                //Splits instruction into String array of individual words/inputs for easier interpretation
                String[] inputs = instruct.split(" ");
                String[] args = instruct.split(" ", 2);

//                //Test print inputs
//                for (String part: inputs) {
//                    System.out.print("["+part+"]");
//                }
//                System.out.println();

                //The first element represents the operation to execute
                String cmd = inputs[0];

                if(cmd.equals("OUT")){
                    cmdOut(args[1]);
                } else if (cmd.equals("IN")) {
                    cmdIn(args[1]);
                } else if (cmd.equals("ADD")) {
                    cmdAdd(args[1]);
                } else if (cmd.equals("SUB")) {
                    cmdSub(inputs);
                } else if (cmd.equals("DIV")) {
                    cmdDiv(inputs);
                } else if (cmd.equals("MUL")) {
                    cmdMul(inputs);
                } else if (cmd.equals("HALT")) {
                    cmdHalt();
                }
            }
        }
//        for (Descriptor d: this.descriptors) {
//            System.out.println("name: "+d.getName()+", value: "+d.getValue());
//        }
    }

    private void pullInstructions() throws IOException {
        // Open the main file
        //FileInputStream fileIn = new FileInputStream("SmallVm/mySmallVm_Prog.txt");
        //Open test file 1
        FileInputStream fileIn = new FileInputStream("SmallVm/IN_OUT_ADD_test.txt");
        //Open test file 2
        //FileInputStream fileIn = new FileInputStream("SmallVm/IN_OUT_SUB_test.txt");
        //Open test file 3
        //FileInputStream fileIn = new FileInputStream("SmallVm/IN_OUT_DIV_test.txt");
        //Open test file 4
        //FileInputStream fileIn = new FileInputStream("SmallVm/IN_OUT_MUL_test.txt");

        BufferedReader br = new BufferedReader(new InputStreamReader(fileIn));

        String line;

        //Read File Line By Line
        while ((line = br.readLine()) != null)   {
            // Print the content on the console
            this.instructions.add(line);
        }
        //Close the input stream
        fileIn.close();
    }

    private void cmdHalt() {
        goProg = false;
    }

    private void cmdMul(String[] inputs) {
        String name = inputs[1];

        String strIn1 = inputs[2];

        String strIn2 = inputs[3];

        int num1;

        int num2;

        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn1)){

            num1 = Integer.parseInt(strIn1);

        }
        //If not, finds descriptor and assigns value to num1
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn1);

            num1 = this.descriptors.get(varIndex).getValue();

        }
        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn2)){

            num2 = Integer.parseInt(strIn2);

        }
        //If not, finds descriptor and assigns value to num2
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn2);

            num2 = this.descriptors.get(varIndex).getValue();

        }

        //calculates difference value
        int value = num1 * num2;

        //Adds new variable descriptor
        this.descriptors.add(new Descriptor(name,value));
    }

    private void cmdDiv(String[] inputs) {
        String name = inputs[1];

        String strIn1 = inputs[2];

        String strIn2 = inputs[3];

        int num1;

        int num2;

        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn1)){

            num1 = Integer.parseInt(strIn1);

        }
        //If not, finds descriptor and assigns value to num1
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn1);

            num1 = this.descriptors.get(varIndex).getValue();

        }
        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn2)){

            num2 = Integer.parseInt(strIn2);

        }
        //If not, finds descriptor and assigns value to num2
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn2);

            num2 = this.descriptors.get(varIndex).getValue();

        }

        //calculates quotient value
        int value = num1 / num2;

        //Adds new variable descriptor
        this.descriptors.add(new Descriptor(name,value));
    }

    private void cmdSub(String[] inputs) {
        String name = inputs[1];

        String strIn1 = inputs[2];

        String strIn2 = inputs[3];

        int num1;

        int num2;

        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn1)){

            num1 = Integer.parseInt(strIn1);

        }
        //If not, finds descriptor and assigns value to num1
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn1);

            num1 = this.descriptors.get(varIndex).getValue();

        }

        //Checks if string contains literal int
        if (this.opExe.isNumeric(strIn2)){

            num2 = Integer.parseInt(strIn2);

        }
        //If not, finds descriptor and assigns value to num2
        else{

            int varIndex = this.opExe.findVar(this.descriptors, strIn2);

            num2 = this.descriptors.get(varIndex).getValue();

        }
        //calculates difference value
        int value = num1 - num2;

        //Adds new variable descriptor
        this.descriptors.add(new Descriptor(name,value));
    }

    //Checks if string contains literal int
    private int getNum(String strNum) {
        int result = 0;

        if (this.opExe.isNumeric(strNum)) {
            result = Integer.parseInt(strNum);
        } else {
            //If not, finds descriptor and assigns value to num1
            int varIndex = this.opExe.findVar(this.descriptors, strNum);
            result = this.descriptors.get(varIndex).getValue();
        }

        return result;
    }

    private void cmdAdd(String input) {
        String[] inputs = input.split(" ", 3);
        String name = inputs[0];
        String strNum1 = inputs[1];
        String strNum2 = inputs[2];
        int num1;
        int num2;

        num1 = getNum(strNum1);
        num2 = getNum(strNum2);

        //calculates sum value
        int value = num1 + num2;

        //Adds new variable descriptor
        this.descriptors.add(new Descriptor(name,value));
    }

    private void cmdIn(String name) {
        Scanner in = new Scanner(System.in);
        int value = in.nextInt();
        this.descriptors.add(new Descriptor(name, value));
    }

    private void cmdOut(String input) {
        if (this.opExe.isVar(this.descriptors, input)){
            int varIndex = this.opExe.findVar(this.descriptors, input);
            System.out.println(this.descriptors.get(varIndex).getValue());
        } else {
            System.out.println(input);//new line for output readability
        }
    }
}