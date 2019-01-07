
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt " + index);
        }
        return myWords[index];
    }

    public int length() {
        // TODO: Complete this method
        return myWords.length;
    }

    public String toString() {
        String ret = "";
        // TODO: Complete this method
        for (int k = 0; k < myWords.length; k++) {
            ret = ret + myWords[k] + " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        // TODO: Complete this method

        if (this.length() != other.length()) {
            return false;
        }
        for (int k = 0; k<myWords.length;k++ ){
            if(!myWords[k].equals(other.wordAt(k))){
                return false;
            }
        }

            return true;

    }

    public WordGram shiftAdd(String word) {
       // WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        // TODO: Complete this method
      //  return out;

        String[] words = new String[this.length()];

        for(int i =0; i<words.length-1;i++){
            words[i] = this.wordAt(i+1);
        }
        words[words.length-1] =word;

        return new WordGram(words,0,words.length);
    }

}