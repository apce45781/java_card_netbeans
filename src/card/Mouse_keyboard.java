package card;

import java.util.ArrayList;
import java.util.List;

public class Mouse_keyboard {
    protected int initial = 0;
    protected int pick_up = 1;
    protected int move = 2;
    private int mouse_status;
    
    public Mouse_keyboard(){
        change_mouse_ststus(initial);
    }
    
    public int mouse_status(){
        return mouse_status;
    }
    
    public void change_mouse_ststus(int status){
        mouse_status = status;
    }
    
    public boolean one_click(Card_interface ci , float mouse_X , float mouse_Y){
        
        boolean pair_position = pair_position(ci , mouse_X , mouse_Y , 12);
        if(pair_position){
            if(ci.position_quantity[ci.positive][12] == 0){
                
                ci.position_quantity[ci.positive][12] = ci.position_quantity[ci.positive][11];
                ci.position_quantity[ci.positive][11] = 0;
                return true;
            }else{
                ci.position_quantity[ci.positive][12] --;
                ci.position_quantity[ci.positive][11] ++;
                return false;
            }
        }else{
            return false;
        }
    }
    
    public int two_click(Card_interface ci , int start_position){
        int start = ci.catch_coordinate(start_position);
        int[] end = {10 , 9 , 8 , 7};
        for(int i : end){
            if(ci.position_quantity[ci.positive][i] == 0){
                if(ci.paper_card.getN(start) == 0){
                    return i;
                }
            }else{
                if(ci.paper_card.getT(ci.catch_coordinate(i)) ==ci.paper_card.getT(start)){
                    if(ci.paper_card.getN(ci.catch_coordinate(i)) == ci.paper_card.getN(start) - 1){
                        return i;
                    }
                }
            }
        }
        int incompatible = 100;
        return incompatible;
    }
    
    public int catch_start_position(Card_interface ci , float mouse_X , float mouse_Y , int[] position){
        for(int i : position){
            if(pair_position(ci , mouse_X , mouse_Y , i)){
                return i;
            }
        }
        int incompatible = 100;
        return incompatible;
    }
    
    public boolean pair_position(Card_interface ci , float mouse_X , float mouse_Y , int position){
        if(ci.p_coordinate[position][ci.row] <= mouse_X && ci.p_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X){
            if((ci.t_coordinate[position][ci.row] <= mouse_X && ci.t_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X) &&
            (ci.t_coordinate[position][ci.column] <= mouse_Y && ci.t_coordinate[position][ci.column] + ci.papercard_size[ci.column] >= mouse_Y)){
                int card_position = ci.catch_coordinate(position);
                ci.paper_card.move_card_number.add(ci.paper_card.getN(card_position));
                ci.paper_card.move_card_type.add(ci.paper_card.getT(card_position));
                return true;
            }else if(ci.p_coordinate[position][ci.column] <= mouse_Y){
                for(int i = 0 ; i < 13 ; i ++){
                    if(ci.p_coordinate[position][ci.column] + (i + 1) * ci.papercard_gap.get(position) >= mouse_Y){
                        for(int k = 0 ; k <= i ; k ++){
                            int card_position = ci.catch_coordinate(k);
                            ci.paper_card.move_card_number.add(ci.paper_card.getN(card_position));
                            ci.paper_card.move_card_type.add(ci.paper_card.getT(card_position));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void mouse_move(Card_interface ci , int start_position){
        mouse_status = pick_up;
        ci.position_quantity[ci.positive][start_position] -= ci.paper_card.move_card_number.size();
        ci.paper_card.move_card_position = start_position;
    }
}
