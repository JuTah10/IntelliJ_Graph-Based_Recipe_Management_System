public class Cookbook {
    //set Size to everything, cap to 1000
    Graph graph = new Graph(1000);                     //graph size
    BiMap bimap = new BiMap(1000);                 //parallel array size
    String[] storedLabel = new String[1000];                //accumulation of all dishes, ingredients(both base and derivable)
    String[] dishes = new String[1000];                     //dishes only
    String[] derivableIngredients = new String[1000];       //derivable ingredients only


    //to keep track of adding number-label's index
    int index = 0;

    //process string array after split between "\t" (dish-ingredient)
    public void processText(String[] splitLine, boolean beforeDashLines){
        //assume the label hasn't been added to array (signed label-number) to avoid duplication
        boolean labelExisted = false;
        for(int i=0; i<storedLabel.length;i++){
            if(splitLine[0].equals(storedLabel[i])){ //check if it was added already
                labelExisted=true;                    //set labelExisted to true if it already existed in the array
            }
        }
        if(!labelExisted){                              //if not existed
            //added that dishes/derivable ingredient to the array to keep track if an element got added before
            storedLabel = addNewElement(storedLabel,splitLine[0]);
            if(beforeDashLines){                       //before the "---------" dash line then it is a dish
                dishes = addNewElement(dishes,splitLine[0]);       //add it to the dish array
            }
            //add mapping for parallel array, string splitLine and use index for number
            bimap.addMapping(splitLine[0],index);
            index++;                            //increase index
        }
        if(!beforeDashLines){           //after the dashLine
            derivableIngredients = addNewElement(derivableIngredients,splitLine[0]); //add before "\t" to derivable
        }
        labelExisted = false;                       //set labelExisted back to false

        String[] ingreSplitted = splitLine[1].split(",");       //split everything after "\t" by ","
        for(int i =0; i< ingreSplitted.length;i++){
            for(int j =0; j<storedLabel.length;j++){                    //check if already existed
                if(ingreSplitted[i].equals(storedLabel[j])){
                    labelExisted = true;
                }
            }
            if(!labelExisted){          //else if not existed then add to storedLabel, then add mapping
                storedLabel = addNewElement(storedLabel,ingreSplitted[i]);
                bimap.addMapping(ingreSplitted[i],index);
                index++;
            }
            labelExisted = false;           //set it back to false
        }
        ajancencyMatrix(splitLine);             //call ajancencyMatrix and pass in splitLine
    }

    //method to add new element to an array, add when it hit its first null then break
    public String[] addNewElement(String[] array, String newElement) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] == null){
                array[i] = newElement;
                break;
            }
        }
        return array;
    }

    public void ajancencyMatrix(String[] splitLine){
        String[] ingreSplitted = splitLine[1].split(","); //split everything after "\t" by ","
        //run through every element in ingreSplitted then add edge between them and splitLine[0] (dishes/derivable)
        for(int i =0; i<ingreSplitted.length;i++){
            graph.addEdge(bimap.getNumber(ingreSplitted[i]),bimap.getNumber(splitLine[0]));
        }
    }

}

