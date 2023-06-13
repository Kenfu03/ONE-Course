public class Factorial {

    public static void main(String[] args) {
        int initialNumber = 1;

        while (initialNumber <= 10){
            int eachFactorial = 1;
            System.out.print(initialNumber + "! = ");
            for (int i = 1; i <= initialNumber; i++){
                if (i < initialNumber){
                    System.out.print(i + " x ");
                }
                else{
                    System.out.print(i);
                }
                eachFactorial *= i;
            }
            System.out.println(" = " + eachFactorial);
            initialNumber++;
        }
    }
}
