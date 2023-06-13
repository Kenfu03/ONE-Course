public class DiscountCalc {

    public static double checkDiscount(double number){
        if (number >= 100 && number < 200){
            return 0.10;
        }
        else if (number >= 200 && number < 300){
            return 0.15;
        }
        else {
            return 20;
        }
    }

    public static void main(String[] args) {
        double itemPrice = 250.0;
        double result = itemPrice - itemPrice * checkDiscount(itemPrice);

        System.out.println("The price of the item is " + itemPrice + " the discount applied is " + checkDiscount(itemPrice)*100 + "% ");
        System.out.println("Total price to pay: " + result);
    }
}
