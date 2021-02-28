package card;

import java.util.ArrayList;
import java.util.List;

public class Paper_card {
    private int shuffle_frequency = 100;
    private List<Integer> papercard_number = new ArrayList();
    private List<Integer> papercard_type = new ArrayList();
    
    protected int move_card_position = 100;
    protected List<Integer> move_card_number = new ArrayList();
    protected List<Integer> move_card_type = new ArrayList();
    
    public Paper_card(){
        for(int i = 0 ; i < 52 ; i ++){
            papercard_number.add(i % 13);
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
    
    protected void delete(int start_position , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_type.remove(start_position - count + 1);
            papercard_number.remove(start_position - count + 1);
        }
    }
    
    protected void add(int end_position , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_type.add(end_position , move_card_type.get(i));
            papercard_number.add(end_position , move_card_number.get(i));
        }
    }
    
    public void paper_card_change(int start , int end){
        if(start > end){
            delete(start , move_card_number.size());
            add(end , move_card_number.size());
        }else{
            add(end , move_card_number.size());
            delete(start , move_card_number.size());
        }
    }
}