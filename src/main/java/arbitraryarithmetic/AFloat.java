package arbitraryarithmetic;

public class AFloat {
    private String magnitude; // Stores the absolute value of the float as a string (without the decimal point)
    private int sign;         // +1 for positive, -1 for negative
    private int scale;        // Number of digits after the decimal point

    // Default constructor
    public AFloat() {
        this.magnitude = "0";  // Initializes number to 0
        this.sign = 1;         // Initializes sign to positive
        this.scale = 0;        // No digits after decimal
    }

    // Constructor that takes string input
    public AFloat(String s) {
        // s== null checks if it hasn't been initialized meaning it's not referring to a real object in memory 
        //s.isEmpty() checks if string is empty meaning a string exists but its initialized to ""
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input string is null or empty.");
        }
        //if the input given is "a123.456" then it'll show invalid format error
        if (!s.matches("[+-]?\\d*(\\.\\d*)?")) {
            throw new IllegalArgumentException("Invalid float format: " + s);
        }

        // If the number starts with '-' then initialize sign to -1
        if (s.charAt(0) == '-') {
            setSign(-1);
            this.magnitude = s.substring(1);
        } else {
            setSign(1);
            if (s.charAt(0) == '+') {
                this.magnitude = s.substring(1);
            } else {
                this.magnitude = s;
            }
        }

        this.magnitude = removeLeadingZeros(this.magnitude);
        this.scale = countDecimalPlaces(this.magnitude);
        this.magnitude = this.magnitude.replace(".", "");
    }

    // Helper method to count the number of digits after the decimal point
    private int countDecimalPlaces(String s) {
        int decimalIndex = s.indexOf(".");
        if (decimalIndex == -1) return 0; // No decimal point
        return s.length() - decimalIndex - 1;
    }

    // Remove leading zeros
    private String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }

    public int getSign() {
        return this.sign;
    }

    public String getMagnitude() {
        return this.magnitude;
    }

    public void setSign(int sign) {
        if (sign != 1 && sign != -1) {
            throw new IllegalArgumentException("Sign must be either 1 or -1.");
        }
        this.sign = sign;
    }

    // Converts the internal representation to a human-readable string
    @Override
    public String toString() {
        String mag = this.magnitude;
        String result;

        // Add leading zeros if necessary
        while (mag.length() <= scale) {
            mag = "0" + mag;
        }

        // Insert the decimal point
        if (scale > 0) {
            int dotIndex = mag.length() - scale;
            result = mag.substring(0, dotIndex) + '.' + mag.substring(dotIndex);
        } else {
            result = mag + '.' + "0";
        }

        
        // Handle negative numbers
        if (this.sign == -1 && !result.equals("0") && !result.equals("0.0")) {
            result = '-' + result;
        }

        return result;
    }

    // Helper method to add extra zeroes to the string (used for equalizing magnitude lengths)
    public String padToScale(String mag, int currentScale, int targetScale) {
        int diff = targetScale - currentScale;
        StringBuilder sb = new StringBuilder(mag);
        for (int i = 0; i < diff; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
    
    // Add two floats
    public AFloat add(AFloat other) {
        
        int totalScale;
        String mag1 = this.magnitude;
        String mag2 = other.magnitude;

        // Equalize the scale
        if (this.scale >= other.scale) {
           
            totalScale = this.scale;
            mag2 = padToScale(mag2, other.scale, totalScale);
        } else {
            
            totalScale = other.scale;
            mag1 = padToScale(mag1, this.scale, totalScale);
        }

        // Remove the decimal point for now
        mag1 = mag1.replace(".", "");
        mag2 = mag2.replace(".", "");

        // Perform the addition as integers
        AInteger a1 = new AInteger(mag1);
        a1.setSign(this.sign);
        AInteger a2 = new AInteger(mag2);
        a2.setSign(other.sign);

        AInteger addResult = a1.add(a2);

        // Convert the result back to AFloat
        String addResultMag = addResult.getMagnitude();
       

        AFloat result = new AFloat();
        result.magnitude = addResultMag;
        result.sign = addResult.getSign();
        result.scale = totalScale;

        if (result.scale > 30) {
            // Truncate the magnitude to 30 decimal places
            int excess = result.scale - 30;
            result.magnitude = result.magnitude.substring(0, result.magnitude.length() - excess);
            result.scale = 30;
        }
        while (result.scale >0 && result.magnitude.endsWith("0")) {
            result.magnitude = result.magnitude.substring(0, result.magnitude.length() - 1);
            result.scale-- ;
        }

        return result;
    }

    public AFloat subtract(AFloat other) {
        
        int totalScale;
        String mag1 = this.magnitude;
        String mag2 = other.magnitude;

        // Equalize the scale
        if (this.scale >= other.scale) {
           
            totalScale = this.scale;
            mag2 = padToScale(mag2, other.scale, totalScale);
        } else {
            
            totalScale = other.scale;
            mag1 = padToScale(mag1, this.scale, totalScale);
        }

        // Remove the decimal point for now
        mag1 = mag1.replace(".", "");
        mag2 = mag2.replace(".", "");

        // Perform the subtraction as integers
        AInteger a1 = new AInteger(mag1);
        a1.setSign(this.sign);
        AInteger a2 = new AInteger(mag2);
        a2.setSign(other.sign);

        AInteger addResult = a1.subtract(a2);

        // Convert the result back to AFloat
        String addResultMag = addResult.getMagnitude();
        

        AFloat result = new AFloat();
        result.magnitude = addResultMag;
        result.sign = addResult.getSign();
        result.scale = totalScale;

        return result;
    }

    public AFloat multiply(AFloat other){

        int totalScale = this.scale + other.scale;
        String mag1 = this.magnitude;
        String mag2 = other.magnitude;
        mag1 = mag1.replace(".", "");
        mag2 = mag2.replace(".", "");

        AInteger a1 = new AInteger(mag1);
        a1.setSign(this.sign);
        AInteger a2 = new AInteger(mag2);
        a2.setSign(other.sign);

        AInteger mulResult = a1.multiply(a2);

        // Convert the result back to AFloat
        String addResultMag = mulResult.getMagnitude();
       

        AFloat result = new AFloat();
        result.magnitude = addResultMag;
        result.sign = mulResult.getSign();
        result.scale = totalScale;

        return result;


    }

    public AFloat divide(AFloat other) {
        //if denominator is 0 , show "Division by zero" and exit
        if (other.magnitude.equals("0")) {
            throw new ArithmeticException("Division by Zero.");
        }
    
        
        String mag1 = this.magnitude;
        String mag2 = other.magnitude;
        //scaleDiff to add the decimal point in the end
        int scaleDiff = this.scale - other.scale;
    
        // pad the numberator with 30 zeroes to handle 30 decimal places
        String scaledMag1 = padToScale(mag1, 0, 30);
    
        // Perform integer division using AInteger
        AInteger numerator = new AInteger(scaledMag1);
        AInteger denominator = new AInteger(mag2);
        AInteger quotient = numerator.divide(denominator);
    
        // Convert the result to AFloat
        AFloat result = new AFloat();
        result.magnitude = quotient.getMagnitude(); // No decimal yet
        result.sign = (this.sign == other.sign) ? 1 : -1;
        result.scale = 30 + scaleDiff;  // Adjust final scale


        //if numerator is exactly divisible by denominator then no need for 30 digits after decimal
        while (result.scale >0 && result.magnitude.endsWith("0")) {
            result.magnitude = result.magnitude.substring(0, result.magnitude.length() - 1);
            result.scale-- ;
        }
    
        // Truncate the magnitude to 30 decimal places
        if (result.scale > 30) {
            int excess = result.scale - 30;
            result.magnitude = result.magnitude.substring(0, result.magnitude.length() - excess);
            result.scale = 30;
        }
        
        return result;
    }
    
    

    
    
}
