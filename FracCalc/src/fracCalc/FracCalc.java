package fracCalc;
import java.util.*;
/* This program does various operations (addition, subtraction, multiplication, and division) with two given fractions. 
 * The program first converts each operand into an improper fraction, does the math with the operation the user chose, and converts to a mixed number.
 * 
 * @author Douglas Hong
 * @version 11/15/2018
 */
public class FracCalc {
	//the main method handles the userInput and prints out the output from produceAnswer
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
    //produceAnswer parses the operands in the user's input, does the operations with them, and returns a String mixed number
    public static String produceAnswer(String input) { 
    	String[] splitInput = input.split(" ");
    	int[] firstOperand = makeRealFraction(splitInput, 0);
    	int[] secondOperand = makeRealFraction(splitInput, 2);
    	if(firstOperand[2] == 0 || secondOperand[2] == 0) {
    		throw new IllegalArgumentException("ERROR: Cannot divide by zero.");
    	}
    	int[] firstImproper = toImproperFrac(firstOperand);
    	int[] secondImproper = toImproperFrac(secondOperand);
    	int[] answer = new int[2];
    	if(splitInput[1].equals("+")) {
    		answer = add(firstImproper, secondImproper);
    	}else if(splitInput[1].equals("-")) {
    		answer = subtract(firstImproper, secondImproper);
    	}else if(splitInput[1].equals("*")) {
    		answer = multiply(firstImproper, secondImproper);
    	}else {
    		answer = divide(firstImproper, secondImproper);
    	}
    	reduce(answer);
        return toMixedNum(answer);
    }
    public static int[] makeRealFraction(String[] input, int index) {
    	int[] realFrac = {0, 0, 1};
    	//handles whole number
    	if(input[index].indexOf("_") != -1) {
    		realFrac[0] = Integer.parseInt(input[index].substring(0, input[index].indexOf("_")));
    	}else if(input[index].indexOf("_") == -1 && input[index].indexOf("/") == -1){
    		realFrac[0] = Integer.parseInt(input[index]);
    	}
    	//handles numerator
    	if(input[index].indexOf("_") == -1 && input[index].indexOf("/") != -1) {
			realFrac[1] = Integer.parseInt(input[index].substring(0, input[index].indexOf("/")));
		}else if(input[index].indexOf("_") != -1 && input[index].indexOf("/") != -1) {
			realFrac[1] = Integer.parseInt(input[index].substring(input[index].indexOf("_") + 1, input[index].indexOf("/")));
		}
    	//handles denominator
    	if(input[index].indexOf("/") != -1) {
    		realFrac[2] = Integer.parseInt(input[index].substring(input[index].indexOf("/") + 1, input[index].length()));
    	}
    	return realFrac;
    }
    //creates an improper fraction using the operand's whole number, numerator, and denominator
    public static int[] toImproperFrac(int[] operand) {
    	int[] improper = new int[2];
    	if(operand[0] >= 0) {
    		improper[0] = operand[0]*operand[2]+operand[1];
    	}else {
    		improper[0] = operand[0]*operand[2]-operand[1];
    	}
       	improper[1] = operand[2];
    	return improper;
    }
    //adds two improper fractions together 
    public static int[] add(int[] frac1, int[] frac2) {
    	int[] sum = new int[2];
    	int gcf = gcf(frac1[1], frac2[1]);
        frac1[0] *= (gcf/frac1[1]);
        frac2[0] *= (gcf/frac2[1]);
    	sum[0] = frac1[0] + frac2[0];
    	sum[1] = gcf;
    	return sum;
    }
    //subtracts two improper fractions
    public static int[] subtract(int[] frac1, int[] frac2) {
    	int[] difference = new int[2];
    	int gcf = gcf(frac1[1], frac2[1]);
    	frac1[0] *= (gcf/frac1[1]);
        frac2[0] *= (gcf/frac2[1]);
    	difference[0] = frac1[0] - frac2[0];
    	difference[1] = gcf;
    	return difference;
    }
    //multiplies two improper fractions
    public static int[] multiply(int[] frac1, int[] frac2) {
    	int[] product = new int[2];
    	product[0] = frac1[0] * frac2[0];
    	product[1] = frac1[1] * frac2[1];
    	return product;
    }
    //divides two improper fractions
    public static int[] divide(int[] frac1, int[] frac2) {
    	int[] quotient = new int[2];
    	quotient[0] = frac1[0] * frac2[1];
    	quotient[1] = frac1[1] * frac2[0];
    	return quotient;
    }
    //determines the greatest common factor of two integers
  	public static int gcf(int num1, int num2) {
  		return num1 * num2;
  	}
  	public static void reduce(int[] frac) {
  		int greaterNum;
  		if(frac[0] > frac[1]) {
  			greaterNum = frac[0];
  		}else {
  			greaterNum = frac[1];
  		}
  		for(int i = greaterNum; i >= 2; i--) {
			if(isDivisibleBy(frac[0], i) && isDivisibleBy(frac[1], i)) {
				frac[0] /= i;
				frac[1] /= i;
			}
		}
  	}
    //determines if a number is evenly divisible by another number
  	public static boolean isDivisibleBy(int num1, int num2) {
  		if(num1 % num2 == 0) {
  			return true;
  		}else {
  			return false;
  		}
  	}
    //converts an improper fraction to a mixed number
  	public static String toMixedNum(int[] improperFrac) {
  		//improperFrac[0] is the numerator and improperFrac[1] is the denominator
  		String mixedNum = improperFrac[0]/improperFrac[1] + "_" + (improperFrac[0]%improperFrac[1]) + "/" + improperFrac[1];
  		//gets rid of negative sign in numerator and/or denominator
  		if(improperFrac[0] < 0 && improperFrac[1] < 0) {
  			mixedNum = improperFrac[0]/improperFrac[1] + "_" + (improperFrac[0]%improperFrac[1])*-1 + "/" + improperFrac[1]*-1;
  		}else if(improperFrac[0] < 0 && improperFrac[1] > 0) {
  			mixedNum = improperFrac[0]/improperFrac[1] + "_" + (improperFrac[0]%improperFrac[1])*-1 + "/" + improperFrac[1];
  		}else if(improperFrac[1] < 0 && improperFrac[0] > 0) {
  			mixedNum = improperFrac[0]/improperFrac[1] + "_" + (improperFrac[0]%improperFrac[1]) + "/" + improperFrac[1]*-1;
  		}
  		//gets rid of numerator and denominator if numerator = 0
  		if(mixedNum.substring(mixedNum.indexOf("_") + 1).startsWith("0")) {
  			mixedNum = mixedNum.substring(0, mixedNum.indexOf("_"));
  		}
  		//gets rid of 0 as whole number
  		if(mixedNum.startsWith("0")) { 
  			mixedNum = mixedNum.substring(mixedNum.indexOf("_") + 1, mixedNum.length());
  		}
  		return mixedNum;
  	}
}
