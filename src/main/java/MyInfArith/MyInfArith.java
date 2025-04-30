package MyInfArith;
import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Not enough Arguments");
            return;
        }

        String type = args[0];
        String op = args[1];
        String val1 = args[2];
        String val2 = args[3];

        switch (type) {
            case "int":
                handleInteger(op, val1, val2);
                break;
            case "float":
                handleFloat(op, val1, val2);
                break;
            default:
                System.out.println("Unsupported type: " + type);
        }
    }

    private static void handleInteger(String op, String val1, String val2) {
        AInteger num1 = new AInteger(val1);
        AInteger num2 = new AInteger(val2);
        AInteger result;

        switch (op) {
            case "add":
                result = num1.add(num2);
                break;
            case "sub":
                result = num1.subtract(num2);
                break;
            case "mul":
                result = num1.multiply(num2);
                break;
            case "div":
                result = num1.divide(num2);
                break;
            default:
                System.out.println("Unsupported operation for int: " + op);
                return;
        }

        System.out.println(result);
    }

    private static void handleFloat(String op, String val1, String val2) {
        AFloat num1 = new AFloat(val1);
        AFloat num2 = new AFloat(val2);
        AFloat result;

        switch (op) {
            case "add":
                result = num1.add(num2);
                break;
            case "sub":
                result = num1.subtract(num2);
                break;
            case "mul":
                result = num1.multiply(num2);
                break;
            case "div":
                result = num1.divide(num2);  // Uncomment once implemented
                break;
            default:
                System.out.println("Unsupported operation for float: " + op);
                return;
        }

        System.out.println(result);
    }
}
