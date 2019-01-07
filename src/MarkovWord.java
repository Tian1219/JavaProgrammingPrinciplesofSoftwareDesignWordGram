import java.util.ArrayList;
import java.util.Random;

public class MarkovWord implements IMarkovModel {

    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
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

    private ArrayList<String> getFollows(WordGram KGram) {
        ArrayList<String> follows = new ArrayList<String>();
        // do to

        int index = 0 ;
        while (true){
            index = indexOf(myText,KGram,index);

            if(index == -1 || (index  + myOrder) >= myText.length){
                break;
            }
            follows.add((myText[index+myOrder]));
            index = index + myOrder;

        }



        return follows;
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

    public void testIndexOf(){


    }


    public int hashCode(){
        return this.toString().hashCode();
    }
}
