package Assignment3;

import BasicIO.*;
import java.util.Scanner;

/**
 * @author Vu Nguyen
 * @version 1.0
 * @course COSC 2P03
 * @assignment #3
 * @student Id 7576200
 * @since March 24, 2023
 */

public class Main {

    //create cook book,pantry and scanner object
    Cookbook cookbook = new Cookbook();
    Pantry pantry = new Pantry();
    Scanner scanner = new Scanner(System.in);

    //call read File method to open file
    public Main(){
        readFile();
    }

    public void readFile(){
        //to keep track if the first element after split "\t" is a dish or a derivable
        //before the dash is a dish and after is derivable
        boolean beforeDashLine = true;
        String document = "";
        ASCIIDataFile file = new ASCIIDataFile();
        ASCIIDataFile pantryFile = new ASCIIDataFile();
        while(!file.isEOF()){   //before ----------
            document = file.readLine();
            if(document != null){
                if(document.equals("----------")){
                    beforeDashLine = false;         //when it hit the dash line, set it to false
                }
                if(!document.equals("----------")){ //split the line read by "\t" then pass it to processText(cookbook)
                    String[] splitLine = document.split("\t");
                    cookbook.processText(splitLine,beforeDashLine);
                }
            }
        }

        while(!pantryFile.isEOF()){ //to read pantry file and add it to pantry
            document = pantryFile.readLine();
            pantry.addToPantry(document);

        }
        handleUserCommands();   //call the method to hand user command
    }
    public void handleUserCommands(){
        userPrompt();                       //for user prompt

        while(true){
            String userInput = scanner.nextLine();      //read user input
            if(userInput.equals("10")){
                System.out.println("Bye bye");
                break;
            }
            if(userInput.equals("1")){            //everything that could be made from recipe (dishes/derivable)
                System.out.println("Everything that could be made from the recipe: ");
                System.out.print("Dishes: ");
                //loop to print out dishes array inside cookbook class
                for(int i = 0; i< cookbook.dishes.length;i++){
                    if(cookbook.dishes[i] == null){                 //break when hit a null
                        break;
                    }
                    System.out.print(cookbook.dishes[i]);           //print out element at "i"
                    if(cookbook.dishes[i+1] != null){               //if the next element is not null then print ","
                        System.out.print(",");
                    }
                }
                System.out.println();
                System.out.print("Derivable Ingredients: ");
                //same as above loop but for derivableIngredients array inside cookbook class
                for(int i = 0; i< cookbook.derivableIngredients.length;i++){
                    if(cookbook.derivableIngredients[i] == null){
                        break;
                    }
                    System.out.print(cookbook.derivableIngredients[i]);
                    if(cookbook.derivableIngredients[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("2")){             //all available dishes from the recipe(same as the one in "1")
                System.out.print("All available dishes: ");
                for(int i = 0; i< cookbook.dishes.length;i++){
                    if(cookbook.dishes[i] == null){
                        break;
                    }
                    System.out.print(cookbook.dishes[i]);
                    if(cookbook.dishes[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("3")){             //dishes can make from current pantry
                //assign return String[] from dishesFromPantry from pantry class to dishesFromPantry (pass in cookbook)
                String[] dishesFromPantry = pantry.dishesFromPantry(cookbook);
                System.out.print("Dishes you could make base on current pantry: ");
                //same as loops "1" and "2" userInput, print content in array dishesFromPantry out
                for(int i =0; i<dishesFromPantry.length;i++){
                    if(dishesFromPantry[i] == null){
                        break;
                    }
                    System.out.print(dishesFromPantry[i]);
                    if(dishesFromPantry[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("4")){                 //user current pantry
                System.out.print("Current Pantry: ");
                //to print pantry array in pantry class out
                for(int i=0; i<pantry.pantry.length; i++){
                    if(pantry.pantry[i] == null){
                        break;
                    }
                    System.out.print(pantry.pantry[i]);
                    if(pantry.pantry[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("5")){     //add item to pantry
                System.out.print("Enter what you want to add to the pantry: ");
                String toAdd = scanner.nextLine();
                //call addToPantry method inside pantry class to add passed in value to pantry
                pantry.addToPantry(toAdd);
                System.out.println(toAdd + " has been added");
                System.out.print("Current Pantry(After added): ");
                //print out pantry
                for(int i= 0; i<pantry.pantry.length;i++){
                    if(pantry.pantry[i] == null){
                        break;
                    }
                    System.out.print(pantry.pantry[i]);
                    if(pantry.pantry[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("6")){           //see if you can make a user-entered dish base on current pantry
                System.out.print("Dish name: ");
                String dishName = scanner.nextLine();
                //call method canMakeDish, pass in dishName, cookbook, print Yes, you can't if true else No, you can't
                System.out.println(pantry.canMakeDish(dishName,cookbook) ? "Yes, you can" : "No, you can't");
            }
            else if(userInput.equals("7")){         //generate shopping list
                System.out.println("Enter your desired dishes (Ex: Dish Name,Dish Name,...)");
                System.out.print("Desired Dish/Dishes: ");
                String shoppingList = scanner.nextLine();
                //assign return value from shoppingList class inside pantry to shoppingListArray
                //pass in shoppingList string (user-entered, and cookbook object(
                String[] shoppingListArray = pantry.shoppingList(shoppingList,cookbook);
                System.out.print("Shopping List: ");
                // print shoppingListArray
                for(int i =0; i <shoppingListArray.length; i++){
                    if(shoppingListArray[i] == null){
                        break;
                    }
                    System.out.print(shoppingListArray[i]);
                    if(shoppingListArray[i+1] != null){
                        System.out.print(",");
                    }
                }
                System.out.println();
            }
            else if(userInput.equals("8")){         //Mise en place
                System.out.println("Enter your desired dishes (Ex: Dish Name,Dish Name,...)");
                System.out.print("Desired Dish/Dishes: ");
                String userDesiredDish = scanner.nextLine();

                //call miseEnPlace method inside pantry
                //pass in userDesiredDish string (user input) and cookbook
                String[] userDesiredDishArray = pantry.miseEnPlace(userDesiredDish,cookbook);
                //if the return value is null then print not enough ingredient
                if(userDesiredDishArray == null){
                    System.out.println("Not enough ingredient to make this dish/dishes");
                }
                else{
                    System.out.print("Need to prepare: ");
                    //else pantry has enough ingredient to make user-entered dish, then print it out
                    for(int i=0; i<userDesiredDishArray.length;i++){
                        if(userDesiredDishArray[i] ==null){
                            break;
                        }
                        System.out.print(userDesiredDishArray[i]);
                        if(userDesiredDishArray[i+1] != null){
                            System.out.print(",");
                        }
                    }
                    System.out.println();
                }

            }
            else if(userInput.equals("9")){
                userPrompt();
            }

        }
    }

    public void userPrompt(){   //for instruction commands prompts
        System.out.println("Enter a number corresponding to the command you want");
        System.out.println("1. Display everything that could be made using the recipes in cookbook.");
        System.out.println("2. Display all dishes that could be made(from the recipes)");
        System.out.println("3. Available dishes from current stock");
        System.out.println("4. Content of pantry");
        System.out.println("5. Add items to pantry");
        System.out.println("6. Check if a dish can be made based on current Pantry");
        System.out.println("7. Shopping List");
        System.out.println("8. Mise en place");

        System.out.println("9. Commands");
        System.out.println("10. Quit");

    }



    public static void main(String[] args) {
        Main t = new Main();
    }
}