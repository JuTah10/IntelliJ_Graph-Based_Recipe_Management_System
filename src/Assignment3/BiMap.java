package Assignment3;

/*
    using parallel array to keep track of label(dishes/ingredient names) - number, number - label
 */
public class BiMap {

    private String[] labelArray;   //to store label
    private int[] numberArray;    //to store num corresponding
    private int index; //to keep track of index for adding

    //receiving array size then assize it "labelArray" and "numberArray"
    public BiMap(int arraySize){
        labelArray = new String[arraySize];
        numberArray = new int[arraySize];
        index = 0; //initialize first index
    }
    //take a text string and a number (label and its number) assign it with the same index, also increase the index
    // to keep track of the arrays index
    public void addMapping(String text, int number){
        labelArray[index] = text;
        numberArray[index] = number;
        index++;
    }

    //take a text string then loop through labelArray until you find the one that match with the text provided
    // return the element in numberArray at the exact same index to get the number
    //else return null (-1)
    public int getNumber(String text){
        for(int i =0; i < index; i++){
            if(labelArray[i].equals(text)){
                return numberArray[i];
            }
        }
        return -1;
    }
    //same as getNumber method, but opposite, take a number then loop through numberArray it til you find a match
    //return element in labelArray at the exact same index
    //else return null
    public String getLabel(int number){
        for(int i = 0; i <index; i++){
            if(numberArray[i] == number){
                return labelArray[i];
            }
        }
        return null;
    }
}

