package card;

import java.util.LinkedList;
import java.util.List;

public class Paper_card {
    private int shuffle_frequency = 100;
    private List<Integer> papercard_number = new LinkedList();
    private List<Integer> papercard_type = new LinkedList();
    
    public Paper_card(){
        for(int i = 0 ; i < 52 ; i ++){
            papercard_number.add(i + 1);
            papercard_type.add(i / 13);
        }
        shuffle();
    }
    
    public void shuffle(){
        int number = 0;
        int[] random = new int[2];
        for(int i = 0 ; i < shuffle_frequency ; i ++){
            number = (int)(Math.random() * 52);
            random[0] = (int) papercard_number.get(number);
            random[1] = (int) papercard_type.get(number);
            papercard_number.remove(number);
            papercard_type.remove(number);
            papercard_number.add(random[0]);
            papercard_type.add(random[1]);
        }
    }
    
    public int getT(int number){
        return papercard_type.get(number);
    }
    
    public int getN(int number){
        return papercard_number.get(number);
    }
}
