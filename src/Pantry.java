//for user pantry
public class Pantry {
    String[] pantry = new String[1000]; //set pantry array size to 1000
    //to add new element to pantry, if hit a null then assign new element to that index then break
    public void addToPantry(String toAdd){
        for(int i=0; i <pantry.length;i++){
            if(pantry[i] ==null){
                pantry[i] = toAdd;
                break;
            }
            if(pantry[i].equals(toAdd)){ //already in pantry
                break;
            }
        }
    }
    public String[] dishesFromPantry(Cookbook cookbook){
        String[] dishesFromPantry = new String[cookbook.dishes.length];                         //to store dishes that could be made from current pantry
        for(int i=0;i<cookbook.dishes.length;i++){ //loop through dishes array
            if(cookbook.dishes[i]==null){           //break when it hits null
                break;
            }

            boolean hasStock = true;            //assume ingredient is in stock (in pantry)
            for(int j=0; j <cookbook.graph.matrix[i].length;j++){    //before dashes ingredient
                if(cookbook.bimap.getLabel(j) == null){
                    break;
                }
                if(cookbook.graph.matrix[j][cookbook.bimap.getNumber(cookbook.dishes[i])] == 1){
                    if(!checkStock(cookbook.bimap.getLabel(j))){ //cant find it stock (call checkStock method)
                        hasStock = false;           //set hasStock to false bc that ingredient is not in stock
                        for(int g=0;g<cookbook.derivableIngredients.length;g++){   //check if its derivable
                            if(cookbook.derivableIngredients[g] ==null){
                                break;
                            }
                            if(cookbook.derivableIngredients[g].equals(cookbook.bimap.getLabel(j))){ //it is derivable ingr
                                hasStock = true;   //set it back to true since it is derivable and can check its base ingre
                                for(int a =0; a<cookbook.graph.matrix[g].length;a++){
                                    if(cookbook.graph.matrix[a][cookbook.bimap.getNumber(cookbook.derivableIngredients[g])] == 1){ //what needed to make that derivable ingredient
                                        if(!checkStock(cookbook.bimap.getLabel(a))){                  // can't find the base ingredient that can make that derivable
                                            hasStock = false;   //set hasStock to false and break out
                                            break;

                                        }
                                    }
                                }
                            }
                        }
                        if(!hasStock){ //after the loop and it still false, means one of the ingredient doesnt exist,
                            // =>not enough to make the dish so just break out and move on the next dish
                            break;
                        }
                    }
                }
            }
            if(hasStock){  //either found it in stock or find enough base ingredient to make derivable to make that dish
                for(int s=0; s<dishesFromPantry.length; s++){
                    if(dishesFromPantry[s] == null){
                        dishesFromPantry[s] = cookbook.dishes[i];   //assign the dish to the array after hit a null
                        break;
                    }
                }
            }
        }
        return dishesFromPantry;        //return the array
    }
    //loop through pantry and return true if find a match, false if not
    public boolean checkStock(String toCheck){
        for(int i =0; i <pantry.length;i++){
            if(pantry[i] == null){
                break;
            }
            if(pantry[i].equals(toCheck)){
                return true;
            }
        }
        return false;
    }


    //check if you can make user enter dishes or not
    //same logic as dishFromPantry method
    //but instead of looping through each dishes to check, canMakeDish only check user entered dish (dishName)
    //also return a boolean instead of pushing to an array and return
    public boolean canMakeDish(String dishName,Cookbook cookbook){
        boolean hasStock = true;
        for(int j=0; j <cookbook.graph.matrix[cookbook.bimap.getNumber(dishName)].length;j++){    //before dashes ingre
            if(cookbook.bimap.getLabel(j) == null){
                break;
            }
            if(cookbook.graph.matrix[j][cookbook.bimap.getNumber(dishName)] == 1){
                if(!checkStock(cookbook.bimap.getLabel(j))){ //cant find it stock
                    hasStock = false;
                    for(int g=0;g<cookbook.derivableIngredients.length;g++){   //check if its derivable
                        if(cookbook.derivableIngredients[g] ==null){
                            break;
                        }
                        if(cookbook.derivableIngredients[g].equals(cookbook.bimap.getLabel(j))){ //it is derivable
                            hasStock = true;
                            for(int a =0; a<cookbook.graph.matrix[g].length;a++){
                                if(cookbook.graph.matrix[a][cookbook.bimap.getNumber(cookbook.derivableIngredients[g])] == 1){ //what needed to make that derivable ingredient
                                    if(!checkStock(cookbook.bimap.getLabel(a))){
                                        hasStock = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if(!hasStock){ //after the loop and it still false, means one of the ingredient doesnt exist,
                        // just break out and move on the next dish
                        break;
                    }
                }
            }

        }
        return hasStock;
    }

    public String[] shoppingList(String desiredDishes,Cookbook cookbook){

        String[] shoppingList = new String[1000]; //to store shopping list

        String[] userDesiredDishes = desiredDishes.split(","); //split up user entered string then store it in userDesiredDishes
        //logic is mostly same with canMakeDish and dishFromPantry methods except some parts
        for(int i =0; i<userDesiredDishes.length; i++){
            for(int j=0; j<cookbook.graph.matrix[i].length; j++){
                if(cookbook.bimap.getLabel(j) == null){
                    break;
                }
                if(cookbook.graph.matrix[j][cookbook.bimap.getNumber(userDesiredDishes[i])] == 1){
                    if(!checkStock(cookbook.bimap.getLabel(j))){ //cant find it stock
                        boolean isDerivable = false;        //to check if it's a derivable ingredient
                        for(int g=0;g<cookbook.derivableIngredients.length;g++){   //check if its derivable
                            if(cookbook.derivableIngredients[g] ==null){
                                break;
                            }
                            if(cookbook.derivableIngredients[g].equals(cookbook.bimap.getLabel(j))){ //it is derivable
                                isDerivable = true;     //set to true because it is derivable
                                for(int a =0; a<cookbook.graph.matrix[g].length;a++){
                                    if(cookbook.graph.matrix[a][cookbook.bimap.getNumber(cookbook.derivableIngredients[g])] == 1){ //what needed to make that derivable ingredient
                                        //there is at least 1 missing base ingredient in that derivable Ingredient
                                        if(!checkStock(cookbook.bimap.getLabel(a))){
                                            for(int l=0;l<shoppingList.length;l++){
                                                if(shoppingList[l] == null){
                                                    //push that derivable ingredient to the shopping list
                                                    shoppingList[l] = cookbook.derivableIngredients[g];
                                                    break;
                                                }
                                                if(shoppingList[l].equals(cookbook.derivableIngredients[g])){//already existed
                                                    break;
                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(!isDerivable){       //if it's not derivable then push the ingredient into shoppingList
                            for(int l=0;l<shoppingList.length;l++){
                                if(shoppingList[l] == null){
                                    shoppingList[l] = cookbook.bimap.getLabel(j);
                                    break;
                                }
                                if(shoppingList[l].equals(cookbook.derivableIngredients[j])){ //already existed in array
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return shoppingList;
    }

    public String[] miseEnPlace(String userDesiredDishes, Cookbook cookbook){
        String[] miseEnPlace = new String[1000];

        String[] userDesiredDishesArray = userDesiredDishes.split(",");

        for(int i =0; i<userDesiredDishesArray.length; i++){
            for(int j=0; j<cookbook.graph.matrix[i].length; j++){
                if(cookbook.bimap.getLabel(j) == null){
                    break;
                }
                if(cookbook.graph.matrix[j][cookbook.bimap.getNumber(userDesiredDishesArray[i])] == 1){
                    if(!checkStock(cookbook.bimap.getLabel(j))){ //cant find it stock
                        boolean isDerivable = false;
                        for(int g=0;g<cookbook.derivableIngredients.length;g++){   //check if its derivable
                            if(cookbook.derivableIngredients[g] ==null){
                                break;
                            }
                            if(cookbook.derivableIngredients[g].equals(cookbook.bimap.getLabel(j))){ //it is derivable
                                isDerivable = true;
                                for(int a =0; a<cookbook.graph.matrix[g].length;a++){
                                    if(cookbook.graph.matrix[a][cookbook.bimap.getNumber(cookbook.derivableIngredients[g])] == 1){ //what needed to make that derivable ingredient
                                        //if find 1 missing ingredient to make that derivable ingr then the dish is
                                        // not possible to make, therefore return null
                                        if(!checkStock(cookbook.bimap.getLabel(a))){
                                            return null;
                                        }
                                        else{                       //it is in stock, push it to miseEnPlace array
                                            for(int m=0; m <miseEnPlace.length;m++){
                                                if(miseEnPlace[m] == null){
                                                    miseEnPlace[m] = cookbook.bimap.getLabel(a);
                                                    break;
                                                }
                                                if(miseEnPlace[m].equals(cookbook.derivableIngredients[a])){//already
                                                    // existed
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                }

                            }
                        }
                        if(!isDerivable){   //if not derivable and since it's not in stock, return null
                            return null;
                        }
                    }
                    else{   //else found it in stock so just push it to the array
                        for(int m=0; m <miseEnPlace.length;m++){
                            if(miseEnPlace[m] == null){
                                miseEnPlace[m] = cookbook.bimap.getLabel(j);
                                break;
                            }
                            if(miseEnPlace[m].equals(cookbook.derivableIngredients[j])){//already
                                // existed
                                break;
                            }
                        }
                    }
                }
            }
        }
        return miseEnPlace;
    }

}

