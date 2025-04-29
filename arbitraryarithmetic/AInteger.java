package arbitraryarithmetic;

public class AInteger {
    private String magnitude; // stores absolute value as string
    private int sign;         // 1 for positive, -1 for negative

    // Default constructor
    public AInteger() {
        this.magnitude = "0"; //initializes number to 0
        this.sign = 1;  //initializes sign to positive
    }

    // Constructor that takes string as input
    public AInteger(String s) {
        // s== null checks if it hasn't been initialized meaning it's not referring to a real object in memory 
        //s.isEmpty() checks if string is empty meaning a string exists but its initialized to ""
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("Input string is null or empty.");
        }
        
        //if number starts with '-' then initialize sign to -1 and get the magnitude of the number 
        if (s.charAt(0) == '-') {
            setSign(-1);
            this.magnitude = s.substring(1);
        } else {
            setSign(1);
            if(s.charAt(0) == '+'){
                this.magnitude = s.substring(1);
            }else{
                this.magnitude = s;
            }

        }
        //removes unnecessary zeroes from the beginning (0067-> 67)
        this.magnitude = removeLeadingZeros(this.magnitude);
    }

    // Copy constructor -> creates a new AInteger with same sign and magnitude
    public AInteger(AInteger other) {
        this.magnitude = other.magnitude;
        this.sign = other.sign;
    }

    // Static parse method
    public static AInteger parse(String s) {
        return new AInteger(s);
    }

    // Remove leading zeros from magnitude
    private String removeLeadingZeros(String s) {
        int i = 0;
        while (i < s.length() - 1 && s.charAt(i) == '0') {
            i++;
        }
        return s.substring(i);
    }

    // Getters
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
    // In the system it is stores as this.sign = -1 and this.magnitude = 1234. It'' convert this to "-1234'
    @Override
    public String toString() {
        
        String result;
        if(this.sign == -1 && !magnitude.equals("0")){
            result = "-" + magnitude;
        }else{
            result = magnitude;
        }
        
        return result;
    }

    // Add another AInteger to this one
    public AInteger add(AInteger other) {
        // Same sign: add magnitudes
        if (this.sign == other.sign) {

            String resultMagnitude = addMagnitudes(this.magnitude, other.magnitude);
            String result;

            if(this.sign == 1){
                result = resultMagnitude;
            }else{
                result = "-" + resultMagnitude;
            }
            return new AInteger(result);

        }else{

            int comparison = compareMagnitudes(this.magnitude, other.magnitude);
            String resultMagnitude;
            int resultSign;

            if(comparison == 0){
                return new AInteger("0");
            }else if(comparison >0){
                resultSign = this.sign;
                resultMagnitude = subtractMagnitudes(this.magnitude, other.magnitude);
            }else{
                resultSign = other.sign;
                resultMagnitude = subtractMagnitudes(other.magnitude, this.magnitude);
            }

            String result;
            if(resultSign == 1){
                result = resultMagnitude;
            }else{
                result = "-" + resultMagnitude;
            }

            return new AInteger(result);

        }
            
    }
    public AInteger subtract(AInteger other){
        int comparison = compareMagnitudes(this.magnitude, other.magnitude);
        String diffMagnitude;
        int diffSign;
        //Same sign: result wil be subtraction of magnitudes and the its sign will be the sign of the larger number
        if(this.sign == other.sign){
            
            if(comparison == 0){
                return new AInteger("0");
            }else if(comparison > 0){
                diffSign = this.sign;
                diffMagnitude = subtractMagnitudes(this.magnitude, other.magnitude);
            }else{
                diffSign = -this.sign;
                diffMagnitude = subtractMagnitudes(other.magnitude, this.magnitude);
            }
        }else{
            diffSign = this.sign;
            diffMagnitude = addMagnitudes(this.magnitude, other.magnitude);                                
        }

        String diffResult;
        if(diffSign == 1){
            diffResult = diffMagnitude;
        }else{
            diffResult = "-" + diffMagnitude;
        }

        return new AInteger(diffResult);
    }

    public AInteger multiply(AInteger other){
        String mulMagnitude = multiplyMagnitudes(this.magnitude, other.magnitude);
        String mulResult;

        if(this.sign == other.sign){
            mulResult = mulMagnitude;
        }else{
            mulResult = "-" + mulMagnitude;
        }
        
        return new AInteger(mulResult);
    }

    public AInteger divide(AInteger other) {
        if (other.magnitude.equals("0")) {
           System.out.println("error: Division by Zero");
           System.exit(1);
        }
    
        String quotientMagnitude = divideMagnitudes(this.magnitude, other.magnitude);
        
        int resultSign = (this.sign == other.sign) ? 1 : -1;
    
        // If quotient is zero, set sign to positive
        if (quotientMagnitude.equals("0")) {
            resultSign = 1;
        }
    
        AInteger result = new AInteger();
        result.magnitude = quotientMagnitude;
        result.sign = resultSign;
        return result;
    }

    // Helper method to compare magnitudes (returns 1 if this > other, -1 if this < other, 0 if equal)
    private int compareMagnitudes(String mag1, String mag2) {
        if (mag1.length() > mag2.length()) {
            return 1;
        } else if (mag1.length() < mag2.length()) {
            return -1;
        }
        return mag1.compareTo(mag2); // Compare lexicographically if lengths are the same
    }

    // Helper method to add magnitudes
    private String addMagnitudes(String mag1, String mag2) {
        int len1 = mag1.length();
        int len2 = mag2.length();
        int maxLen = Math.max(len1, len2);
        
        StringBuilder sum = new StringBuilder();
        int carry = 0;
        
        for (int i = 0; i < maxLen || carry != 0; i++) {
            int digit1 = 0;
            int digit2 = 0;
        
            // Extract digit1 from mag1 if within bounds
            if (i < len1) {
                char ch1 = mag1.charAt(len1 - 1 - i);
                digit1 = ch1 - '0';
            }
        
            // Extract digit2 from mag2 if within bounds
            if (i < len2) {
                char ch2 = mag2.charAt(len2 - 1 - i);
                digit2 = ch2 - '0';
            }
        
            // Calculate the sum of both digits plus any carry
            int total = digit1 + digit2 + carry;
        
            // Append the unit digit to the result
            int digitToAppend = total % 10;
            sum.append(digitToAppend);
        
            // Update carry for next iteration
            carry = total / 10;
        }
         
        

        
        return sum.reverse().toString();
    }

    // Helper method to subtract magnitudes (assumes mag1 >= mag2)
    private String subtractMagnitudes(String mag1, String mag2) {
        int len1 = mag1.length();
        int len2 = mag2.length();
        
        StringBuilder sb = new StringBuilder();
        int borrow = 0;
        
        for (int i = 0; i < len1; i++) {
            int digit1 = 0; // Default value for digit1
            int digit2 = 0; // Default value for digit2
        
            // Extract digit1 from mag1 if within bounds
            if (i < len1) {
                char ch1 = mag1.charAt(len1 - 1 - i); // Get character from right to left
                digit1 = ch1 - '0'; // Convert char to integer
            }
        
            // Extract digit2 from mag2 if within bounds
            if (i < len2) {
                char ch2 = mag2.charAt(len2 - 1 - i); // Get character from right to left
                digit2 = ch2 - '0'; // Convert char to integer
            }
        
            // Subtract digit2 and any previous borrow
            int diff = digit1 - digit2 - borrow;
        
            // If the result is negative, adjust the diff and set borrow
            if (diff < 0) {
                diff += 10;    // Adjust by adding 10 (carry over for subtraction)
                borrow = 1;    // Set borrow flag to 1 (carry forward to next digit)
            } else {
                borrow = 0;    // No borrow needed, reset borrow flag
            }
        
            // Append the result (least significant digit) to the StringBuilder
            sb.append(diff);
        }
        
        
        // Remove leading zeros
        String result = sb.reverse().toString();
        return removeLeadingZeros(result);
    }
    private String multiplyMagnitudes(String mag1, String mag2){
        int len1 = mag1.length();
        int len2 = mag2.length();
        int[] mul = new int[len1 + len2];

        for (int i = len1 -1 ; i>=0 ; i--){
            int digit1 = mag1.charAt(i) - '0';
            for (int j=len2 -1 ; j>=0;j--){
                int digit2 = mag2.charAt(j) - '0';

                int product = digit1*digit2;
                int pos2 = i + j +1;
                int pos1 = i+j;

                int sum = product + mul[pos2];

                mul[pos2] = sum%10;
                mul[pos1] += sum / 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int digit : mul) {
            if(!(sb.length() == 0 && digit == 0)){
                sb.append(digit);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    private String divideMagnitudes(String dividend, String divisor) {
        StringBuilder result = new StringBuilder();
        StringBuilder current = new StringBuilder();
    
        for (int i = 0; i < dividend.length(); i++) {
            current.append(dividend.charAt(i));
            current = new StringBuilder(removeLeadingZeros(current.toString()));
    
            int quotientDigit = 0;
            while (compareMagnitudes(current.toString(), divisor) >= 0) {
                current = new StringBuilder(subtractMagnitudes(current.toString(), divisor));
                quotientDigit++;
            }
    
            result.append((char) (quotientDigit + '0'));
        }
    
        return removeLeadingZeros(result.toString());
    }

   
}
