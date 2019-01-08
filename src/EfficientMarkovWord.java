import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EfficientMarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> map;

    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        buildHashMap();
        printHashMapInfo();
    }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram key = new WordGram(myText,index,myOrder);
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }

        return sb.toString().trim();
    }

    private ArrayList<String> getFollows(WordGram kGram) {
        // do to
        return map.get(kGram);

    }

    private int indexOf (String[] words, WordGram target, int start){
        for(int k=start; k<words.length - myOrder; k++){
            WordGram word = new WordGram(words,k,myOrder);
            if(word.equals(target)){
                return k;
            }
        }
        return -1;

    }

   private void buildHashMap() {
       map = new HashMap<WordGram, ArrayList<String>>();
       // Loop over training text, keeping in mind size of WordGram
       for (int i = 0; i <= myText.length - myOrder; i++) {
           WordGram word = new WordGram(myText, i, myOrder);
           int code = word.hashCode();
           // If that WordGram is not in the HashMap yet, then it should be put in mapped to an empty ArrayList
           if(!map.containsKey(word)){
               map.put(word,new ArrayList<String>());
           }
           // Add following word to the WordGram's ArrayList in the map, if there is one
            if(i+myOrder < myText.length){
                String follower = myText[i+myOrder];
                ArrayList<String> follows = map.get(word);
                follows.add(follower);
                map.put(word,follows);
            }
       }
   }


    private void printHashMapInfo() {
        // Print HashMap (only if it is small)
        System.out.println(map);
        // Print number of keys in HashMap
        System.out.println(map.size());
        // Print the size of the largest value in the HashMap
        int largest = 0;
        for (WordGram code : map.keySet()) {
            int current = map.get(code).size();
            if (current > largest) {
                largest = current;
            }
        }
        System.out.println("Size of largest value in the HashMap: " + largest);
        // Print the keys that have the maximum size value
        for (WordGram code : map.keySet()) {
            if (map.get(code).size() == largest) {
                System.out.println("The keys that have the maximum size value and their follow words: ");
                System.out.println(code + " .... " + map.get(code));
            }
        }
    }
}
