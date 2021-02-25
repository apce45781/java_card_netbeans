package card;

import java.util.ArrayList;
import java.util.List;

public class Paper_card {
    private int shuffle_frequency = 100;
    private List<Integer> papercard_number = new ArrayList();
    private List<Integer> papercard_type = new ArrayList();
    
    protected int move_card_position = 0;
    
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
    
    private void deleteT(int number , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_type.remove(number);
        }
    }
    
    private void deleteN(int number , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_number.remove(number);
        }
    }
    
    private void setT(int position , int[] value , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_type.add(position , value[i]);
        }
    }
    
    private void setN(int position , int[] value , int count){
        for(int i = 0 ; i < count ; i ++){
            papercard_number.add(position , value[i]);
        }
    }
    
    public void change(int start , int end , int quantity){
        int number = 0;
        int type = 1;
        int[][] change_card = new int[2][quantity];
        for(int i = 0 ; i < quantity ; i ++){
            change_card[number][i] = getN(start - quantity + 1 + i);
            change_card[type][i] = getT(start - quantity + 1 + i);
        }
        if(start > end){
            deleteN(start - quantity + 1 , quantity);
            deleteT(start - quantity + 1 , quantity);
            setT(end - quantity + 2 , change_card[type] , quantity);
            setN(end - quantity + 2 , change_card[number] , quantity);
            
        }else{
            setT(end - quantity + 2 , change_card[type] , quantity);
            setN(end - quantity + 2 , change_card[number] , quantity);
            deleteN(start - quantity + 1 , quantity);
            deleteT(start - quantity + 1 , quantity);
            
        }
    }
}