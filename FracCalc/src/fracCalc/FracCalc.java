package fracCalc;
import java.util.*;
/* Does various operations (addition, subtraction, multiplication, and division) with two given fractions. 
 * 
 * @author Douglas Hong
 * @version 11/15/2018
 */
public class FracCalc {
	
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please type the first operand(fraction), the operator (+,-,*,/), and the second operand (fraction) (Type 'quit' to quit)");
        String expression = userInput.nextLine();
        while(!expression.equalsIgnoreCase("quit")) {
        	System.out.println(produceAnswer(expression));
        	System.out.println("Please type the first operand(fraction), the operator (+,-,*,/), and the second operand (fraction) (Type 'quit' to quit)");
        	expression = userInput.nextLine();
        }
        System.out.println("Calculator stopped");
    }
    
    // ** IMPORTANT ** DO NOT DELETE THIS FUNCTION.  This function will be used to test your code
    // This function takes a String 'input' and produces the result
    //
    // input is a fraction string that needs to be evaluated.  For your program, this will be the user input.
    //      e.g. input ==> "1/2 + 3/4"
    //        
    // The function should return the result of the fraction after it has been calculated
    //      e.g. return ==> "1_1/4"
    public static String produceAnswer(String input) { 
    	String[] splitInput = input.split(" ");
    	int firstWholeNum = parseWholeNum(splitInput, 0);
    	int firstNumerator = parseNumerator(splitInput, 0);
    	int firstdenominator = parseDenominator(splitInput, 0);
    	int secondWholeNum = parseWholeNum(splitInput, 2);
    	int secondNumerator = parseNumerator(splitInput, 2);
    	int secondDenominator = parseDenominator(splitInput, 2);
    	int[] firstOperand = toImproperFrac(firstWholeNum, firstNumerator, firstdenominator);
    	int[] secondOperand = toImproperFrac(secondWholeNum, secondNumerator, secondDenominator);
    	int[] answer = new int[2];
    	if(splitInput[1].equals("+")) {
    		answer = add(firstOperand, secondOperand);
    	}else if(splitInput[1].equals("-")) {
    		answer = subtract(firstOperand, secondOperand);
    	}else if(splitInput[1].equals("*")) {
    		answer = multiply(firstOperand, secondOperand);
    	}else {
    		answer = divide(firstOperand, secondOperand);
    	} 
    	//return Arrays.toString(firstOperand) + "" + Arrays.toString(secondOperand);
    	//return Arrays.toString(answer);
       return toMixedNum(answer[0], answer[1]);
        //return "whole:" + secondWholeNum + " numerator:" + secondNumerator + " denominator:" + secondDenominator;
    	//return splitInput[2];
    }
    public static int[] makeRealFraction(String operand) {
    	int[] realFrac = {0, 0, 1};
    	if(operand.contains("_")) {
    		String[] wholeAndFrac = operand.split("_");
    		realFrac[0] = Integer.parseInt(wholeAndFrac[0]);
    		operand = wholeAndFrac[1];
    	}
    	if(operand.contains("/")) {
    		String[] numAndDenom = operand.split("/");
    		realFrac[1] = Integer.parseInt(numAndDenom[0]);
    		realFrac[2] = Integer.parseInt(numAndDenom[1]);
    	}else {
    		realFrac[0] = Integer.parseInt(operand);
    	}
    	return realFrac;
    }
    public static int parseWholeNum(String[] input, int index) {
    	String wholeNum;
    	if(input[index].indexOf("_") != -1) {
    		wholeNum = input[index].substring(0, input[index].indexOf("_"));
    	}else if(input[index].indexOf("_") == -1 && input[index].indexOf("/") == -1){
    		wholeNum = input[index];
    	}else{ //if(splitInput[2].indexOf("_") == -1)
    		wholeNum = "0";
    	}
    	return Integer.parseInt(wholeNum);
    }
    public static int parseNumerator(String[] input, int index) {
    	String numerator;
    	if(input[index].indexOf("_") == -1 && input[index].indexOf("/") != -1) {
			numerator = input[index].substring(0, input[index].indexOf("/"));
		}else if(input[index].indexOf("_") != -1 && input[index].indexOf("/") != -1) {
			numerator = input[index].substring(input[index].indexOf("_") + 1, input[index].indexOf("/"));
		}else {
			numerator = "0";
		}
    	return Integer.parseInt(numerator);
    }
    public static int parseDenominator(String[] input, int index) {
    	String denominator;
    	if(input[index].indexOf("/") != -1) {
    		denominator = input[index].substring(input[index].indexOf("/") + 1, input[index].length());
    	}else {
    		denominator = "1";
    	}
    	return Integer.parseInt(denominator);
    }
    public static int[] toImproperFrac(int wholeNum, int numerator, int denominator) {
    	int[] improper = new int[2];
    	if(wholeNum >= 0) {
    		improper[0] = wholeNum*denominator+numerator;
    	}else {
    		improper[0] = wholeNum*denominator-numerator;
    	}
       	improper[1] = denominator;
    	return improper;
    }

    public static int[] add(int[] frac1, int[] frac2) {
    	int[] sum = new int[2];
    	int gcf = gcf(frac1[1], frac2[1]);
    	frac1[0] *= (gcf/frac1[1]);
    	frac2[0] *= (gcf/frac2[1]);
    	sum[0] = frac1[0] + frac2[0];
    	sum[1] = gcf;
    	return sum;
    }
    
    public static int[] subtract(int[] frac1, int[] frac2) {
    	int[] difference = new int[2];
    	difference[0] = frac1[0] - frac2[0];
    	if(frac1[1] != frac2[1]) {
    		difference[1] = gcf(frac1[1], frac2[1]);
    	}else {
    		difference[1] = frac1[1];
    	}
    	return difference;
    }
    
    public static int[] multiply(int[] frac1, int[] frac2) {
    	int[] product = new int[2];
    	product[0] = frac1[0] * frac2[0];
    	product[1] = frac1[1] * frac2[1];
    	return product;
    }
    
    public static int[] divide(int[] frac1, int[] frac2) {
    	int[] quotient = new int[2];
    	quotient[0] = frac1[0] * frac2[1];
    	quotient[1] = frac1[1] * frac2[0];
    	return quotient;
    }
    //determines the greatest common factor of two positive integers 
  	public static int gcf(int num1, int num2) {
  		int greatestFactor = 1;
  		for(int i = 2; i <= num1; i++) {
  			if(isDivisibleBy(num1, i) && isDivisibleBy(num2, i)) {
  				greatestFactor = i;
  			}
  		}
  		if(num1 == 1) {
  			greatestFactor = num2;
  		}else if(num2 == 1) {
  			greatestFactor = num1;
  		}
  		return greatestFactor;
  	}
    //determines if a number is evenly divisible by another number
  	public static boolean isDivisibleBy(int num1, int num2) {
  		if(num2 == 0) {
  			throw new IllegalArgumentException("A number cannot be divided by 0.");
  		}
  		if(num1 % num2 == 0) {
  			return true;
  		}else {
  			return false;
  		}
  	}
    //converts an improper fraction to a mixed number
  	public static String toMixedNum(int numerator, int denominator) {
  		String mixedNum = numerator/denominator + "_" + (numerator%denominator) + "/" + denominator;
  		//gets rid of 0/denominator  
  		if(mixedNum.substring(mixedNum.indexOf("_") + 1).startsWith("0")) {
  			mixedNum = mixedNum.substring(0, mixedNum.indexOf("_"));
  		}else if(mixedNum.substring(mixedNum.indexOf("/") + 1).contains("-")){ //gets rid of negative sign after "/"
  			denominator*=-1;
  			mixedNum = mixedNum.substring(0, mixedNum.indexOf("/") + 1) + denominator;
  		}
  		if(mixedNum.substring(mixedNum.indexOf("_") + 1).contains("-")) { //gets rid of negative sign after "_"
  			mixedNum = numerator/denominator + "_" + (numerator%denominator)*-1 + "/" + denominator;
  		}
  		if(mixedNum.startsWith("0")) { //gets rid of 0 as whole number
  			mixedNum = mixedNum.substring(mixedNum.indexOf("_") + 1, mixedNum.length());
  		}
  		return mixedNum;
  	}
}
