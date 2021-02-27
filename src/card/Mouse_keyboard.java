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
    
    public boolean one_click_switch(Card_interface ci , float mouse_X , float mouse_Y){
        
        boolean pair_position = pair_position_less(ci , mouse_X , mouse_Y , 12);
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
    
    public boolean two_click_switch(Card_interface ci , float mouse_X , float mouse_Y){
        int incompatible = 100;
        
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 11}){
            boolean pair_position_switch = pair_position_less(ci , mouse_X , mouse_Y , i);
            if(pair_position_switch){
                add_move_card(ci , i);
                break;
            }
        }
        if(ci.pc.move_card_position != incompatible){
            int start = ci.catch_coordinate(ci.pc.move_card_position);
            for(int i : new int[]{10 , 9 , 8 , 7}){
                if(ci.position_quantity[ci.positive][i] == 0){
                    if(ci.pc.getN(start) == 0){
                        change(ci , i);
                        return true;
                    }
                }else{
                    if(ci.pc.getT(ci.catch_coordinate(i)) ==ci.pc.getT(start)){
                        if(ci.pc.getN(ci.catch_coordinate(i)) == ci.pc.getN(start) - 1){
                            change(ci , i);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
//    public int catch_position_card(Card_interface ci , int end_position){
//        float[] point = {ci.mouse_point[ci.row] - ci.papercard_size[ci.row] / 2 , ci.mouse_point[ci.row] + ci.papercard_size[ci.row] / 2 , 0};
//        if(ci.pc.move_card_number.size() == 1){
//            point[2] = ci.mouse_point[ci.column] - ci.papercard_size[ci.column] / 2;
//        }else{
//            point[2] = ci.mouse_point[ci.column] - ci.papercard_gap.get(end_position) / 2;
//        }
//        if(point[0])
//        int incompatible = 100;
//        return incompatible;
//    }
    
    public void mouse_move(Card_interface ci , float mouse_X , float mouse_Y){
        int incompatible = 100;
        boolean pair_position;
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11}){
            pair_position = pair_position_less(ci , mouse_X , mouse_Y , i);
            if(pair_position){
                add_move_card(ci , i);
                mouse_pick_up(ci , i);
                System.out.println(i + "   --94");
                break;
            }
            if(i < 7){
                pair_position = pair_position_many(ci , mouse_X , mouse_Y , i);
                if(pair_position){
                    mouse_pick_up(ci , i);
                    System.out.println("-~101");
                    break;
                }
            }
        }
    }
    
    public void mouse_pick_up(Card_interface ci , int start_position){
        mouse_status = pick_up;
        ci.position_quantity[ci.positive][start_position] -= ci.pc.move_card_number.size();
        ci.pc.deleteN(start_position , ci.pc.move_card_number.size());
        ci.pc.deleteT(start_position , ci.pc.move_card_number.size());
    }
    
    public boolean pair_position_less(Card_interface ci , float mouse_X , float mouse_Y , int position){
        
        if((ci.t_coordinate[position][ci.row] <= mouse_X && ci.t_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X) &&
          (ci.t_coordinate[position][ci.column] <= mouse_Y && ci.t_coordinate[position][ci.column] + ci.papercard_size[ci.column] >= mouse_Y)){
            int card_position = ci.catch_coordinate(position);
            ci.pc.move_card_position = position;
            return true;
        }
        return false;
    }
    
    public boolean pair_position_many(Card_interface ci , float mouse_X , float mouse_Y , int position){
        if(ci.p_coordinate[position][ci.row] <= mouse_X && ci.p_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X){
            if(ci.p_coordinate[position][ci.column] <= mouse_Y){
                for(int i = 1 ; i <= 13 ; i ++){
                    if(ci.p_coordinate[position][ci.column] + (i * ci.papercard_gap.get(position)) >= mouse_Y){
                        int card_position;
                        for(int k = 0 ; k < i ; k ++){
                            card_position = ci.catch_coordinate(position) - k;
                            add_move_card(ci , card_position);
                        }
                        ci.pc.move_card_position = position;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void add_move_card(Card_interface ci , int number){
        ci.pc.move_card_number.add(ci.pc.getN(number));
        ci.pc.move_card_type.add(ci.pc.getT(number));
    }
    
    public void change(Card_interface ci , int end_position){
        ci.pc.card_position_change(ci.catch_coordinate(ci.pc.move_card_position) , ci.catch_coordinate(end_position) , ci.pc.move_card_number.size());
        ci.position_quantity_change(ci.pc.move_card_position , end_position , ci.pc.move_card_number.size());
    }
}
