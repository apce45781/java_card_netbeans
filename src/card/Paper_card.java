package card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Paper_card {
    
    private int[][] position_quantity = new int[2][13];
    
    private List<Integer>[] papercard_type = new ArrayList[13];
    private List<Integer>[] papercard_number = new ArrayList[13];
    
    public final int initial = -1;
    private int move_card_position = initial;
    private List<Integer> move_card_number = new ArrayList();
    private List<Integer> move_card_type = new ArrayList();
    
    public Paper_card(){
        for(int i = 0 ; i < 13 ; i ++){
            papercard_type[i] = new ArrayList<Integer>();
            papercard_number[i] = new ArrayList<Integer>();
        }
    }
    
    public void create_and_shuffle(){
        int[][] papercard = new int[2][52];
        
        int type = 0;
        int number = 1;
        
        for(int i = 0 ; i < 52 ; i ++){
            papercard[type][i] = i / 13;
            papercard[number][i] = i % 13;
        }
        int count , random_type , random_number;
        for(int i = 0 ; i < 200 ; i ++){
            count = (int) (Math.random() * 51);
            random_type = papercard[type][count];
            random_number = papercard[number][count];
            for(int q = count ; q < 51 ; q ++){
                papercard[type][q] = papercard[type][q + 1];
                papercard[number][q] = papercard[number][q + 1];
            }
            papercard[type][51] = random_type;
            papercard[number][51] = random_number;
        }
        
        int[][] position_quantity = {{7 , 6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 24} , {6 , 5 , 4 , 3 , 2 , 1 , 0 , 0 , 0 , 0 , 0 , 0 , 23}};
        
        paper_card_input(papercard , position_quantity);
    }
    
    public void paper_card_input(int[][] paper_card , int[][] position_quantity){
        
        int count = 0;
        
        int positive = 0;
        int negative = 1;
        
        int type = 0;
        int number = 1;
        
        for(int i = 0 ; i < 13 ; i ++){
            this.position_quantity[positive][i] = position_quantity[positive][i];
            this.position_quantity[negative][i] = position_quantity[negative][i];
            for(int k = 0 ; k < this.position_quantity[positive][i] ; k ++ , count ++){
                this.papercard_type[i].add(paper_card[type][count]);
                this.papercard_number[i].add(paper_card[number][count]);
            }
        }
    }
    
    public int getT(int position , int card_position){
        int positive = 0;
        if(position_quantity[positive][position] == 0){
            return initial;
        }else{
            return papercard_type[position].get(card_position - 1);
        }
    }
    
    public int getN(int position , int card_position){
        int positive = 0;
        if(position_quantity[positive][position] == 0){
            return initial;
        }else{
            return papercard_number[position].get(card_position - 1);
        }
    }
    
    protected int get_card_quantity(int position){
        return papercard_number[position].size();
    }
    
    public void add_card(int end_position , int quantity){
        for(int i = 0 ; i < quantity ; i ++){
            papercard_type[end_position].add(0 , move_card_type.get(i));
            papercard_number[end_position].add(0 , move_card_number.get(i));
        }
    }
    
    protected void remove_card(int position , int quantity){
        for(int i = 0 ; i < quantity ; i ++){
            papercard_type[position].remove(0);
            papercard_number[position].remove(0);
        }
    }
    
    public void clear_papercard(){
        for(int i = 0 ; i < 13 ; i ++){
            papercard_type[i].clear();
            papercard_number[i].clear();
        }
    }
    
    public int getMT(int card_position){
        if(move_card_position == initial){
            return initial;
        }else{
            return move_card_type.get(card_position - 1);
        }
    }
    
    public int getMN(int card_position){
        if(move_card_position == initial){
            return initial;
        }else{
            return move_card_number.get(card_position - 1);
        }
    }
    
    public void add_move_card(int start_position , int quantity){
        for(int i = 0 ; i < quantity ; i ++){
            move_card_type.add(0 , papercard_type[start_position].get(i));
            move_card_number.add(0 , papercard_number[start_position].get(i));
        }
    }
    
    public int get_move_position(){
        return move_card_position;
    }
    
    public int get_move_quantity(){
        return move_card_number.size();
    }
    
    public void clear_move_card(){
        move_card_type.clear();
        move_card_number.clear();
        move_card_position = initial;
    }
    
    public void add_move_position(int position){
        move_card_position = position;
    }
    
    public int get_positive_quantity(int position){
        int positive = 0;
        return position_quantity[positive][position];
    }
    
    public int get_negative_quantity(int position){
        int negative = 1;
        return position_quantity[negative][position];
    }
    
    public void add_positive_quantity(int position , int quantity){
        int positive = 0;
        position_quantity[positive][position] += quantity;
    }
    
    public void addAll_positive_quantity(int position , int quantity){
        int positive = 0;
        position_quantity[positive][position] = quantity;
    }
    
    public void addAll_negative_quantity(int position , int quantity){
        int negative = 1;
        position_quantity[negative][position] = quantity;
    }
    
    public void remove_position_quantity(int position , int quantity){
        int positive = 0;
        position_quantity[positive][position] -= quantity;
        
    }
    
    public void click_change(int start_position , int end_position , int quantity){
        for(int i = 0 ; i < quantity ; i ++){
            papercard_type[end_position].add(0 , papercard_type[start_position].get(0));
            papercard_number[end_position].add(0 , papercard_number[start_position].get(0));
            papercard_type[start_position].remove(0);
            papercard_number[start_position].remove(0);
        }
        int positive = 0;
        position_quantity[positive][start_position] -= quantity;
        position_quantity[positive][end_position] += quantity;
    }
    
    public void judgment_positive_and_negative(int start_position){
        int positive = 0;
        int negative = 1;
        if(position_quantity[positive][start_position] >= position_quantity[negative][start_position]){
            if(position_quantity[negative][start_position] != 0){
                position_quantity[negative][start_position] --;
            }
        }
    }
}