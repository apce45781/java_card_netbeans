package card;

import java.util.ArrayList;
import java.util.List;

public class Mouse_keyboard {
    private final int incompatible = 100;
    
    protected int initial = 0;
    protected int pick_up = 1;
    protected int move = 2;
    private int mouse_status;
    
    public Mouse_keyboard(){
        change_mouse_status(initial);
    }
    
    public int mouse_status(){
        return mouse_status;
    }
    
    public void change_mouse_status(int status){
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
        
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 11}){
            boolean pair_position_switch = pair_position_less(ci , mouse_X , mouse_Y , i);
            if(pair_position_switch){
                int move_quantity = 1;
                add_move_card(ci , i , move_quantity , pick_up);
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
    
    public int mouse_up(Card_interface ci){
        
        float card_point1 = ci.mouse_point[ci.row] - ci.papercard_size[ci.row] / 2;
        float card_point2 = ci.mouse_point[ci.row] + ci.papercard_size[ci.row] / 2;
        float card_point3;
        float card_point4;
        
        if(ci.pc.move_card_number.size() == 1){
            card_point3 = ci.mouse_point[ci.column] - ci.papercard_size[ci.column] / 2;
            card_point4 = ci.mouse_point[ci.column] + ci.papercard_size[ci.column] / 2;
            
            float card_point[][] = {{card_point1 , card_point3} , {card_point2 , card_point3} , {card_point1 , card_point4} , {card_point2 , card_point4}};
            
            for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10}){
                boolean end_position = pair_position_card(ci , card_point , i);
                if(end_position){
                    ci.judgment_positive_and_negative(ci.pc.move_card_position);
                    ci.position_quantity_add(i);
                    return i;
                }
            }
        }else{
            for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6}){
                card_point3 = ci.mouse_point[ci.column] - ci.papercard_gap.get(i);
                card_point4 = ci.mouse_point[ci.column] + ci.papercard_size[ci.column] - ci.papercard_gap.get(i);
            
                float card_point[][] = {{card_point1 , card_point3} , {card_point2 , card_point3} , {card_point1 , card_point4} , {card_point2 , card_point4}};
                
                boolean end_position = pair_position_card(ci , card_point , i);
                if(end_position){
                    ci.judgment_positive_and_negative(ci.pc.move_card_position);
                    ci.position_quantity_add(i);
                    return i;
                }
            }
        }
        return incompatible;
    }
    
    public void back_home(Card_interface ci){
        ci.judgment_positive_and_negative(ci.pc.move_card_position);
        ci.position_quantity_add(ci.pc.move_card_position);
    }
    
    public boolean pair_position_card(Card_interface ci , float[][] card_point , int end_position){
        boolean point1 = false;
        boolean point2 = false;
        boolean point3 = false;
        boolean point4 = false;
        for(float[] i : card_point){
            point1 = pair_position_less(ci , i[0] , i [1] , end_position);
            point2 = pair_position_less(ci , i[0] , i [1] , end_position);
            point3 = pair_position_less(ci , i[0] , i [1] , end_position);
            point4 = pair_position_less(ci , i[0] , i [1] , end_position);
        }
        if(point1 && point2 && point3 && point4){
            return true;
        }
        return false;
    }
    
    public void mouse_move(Card_interface ci , float mouse_X , float mouse_Y){
        
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11}){
            boolean move_card_switch = pair_position_less(ci , mouse_X , mouse_Y , i);
            if(move_card_switch){
                int move_card_quantity = 1;
                add_move_card(ci , i , move_card_quantity , move);
                ci.position_quantity_remove(i);
                break;
            }
            if(i < 7){
                int move_card_quantiy = pair_position_many(ci , mouse_X , mouse_Y , i);
                if(move_card_quantiy != incompatible){
                    add_move_card(ci , i , move_card_quantiy , move);
                    ci.position_quantity_remove(i);
                    break;
                }
            }
        }
    }
    
    public void add_move_card(Card_interface ci , int position , int move_card_quantity , int status){
        int card_position = ci.catch_coordinate(position);
        for(int i = 0 ; i < move_card_quantity ; i ++){
            ci.pc.move_card_number.add(ci.pc.getN(card_position - i));
            ci.pc.move_card_type.add(ci.pc.getT(card_position - i));
        }
        ci.pc.move_card_position = position;
        change_mouse_status(status);
    }
    
    public boolean pair_position_less(Card_interface ci , float mouse_X , float mouse_Y , int position){
        
        if((ci.t_coordinate[position][ci.row] <= mouse_X && ci.t_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X) &&
          (ci.t_coordinate[position][ci.column] <= mouse_Y && ci.t_coordinate[position][ci.column] + ci.papercard_size[ci.column] >= mouse_Y)){
            return true;
        }
        return false;
    }
    
    public int pair_position_many(Card_interface ci , float mouse_X , float mouse_Y , int position){
        if(ci.p_coordinate[position][ci.row] <= mouse_X && ci.p_coordinate[position][ci.row] + ci.papercard_size[ci.row] >= mouse_X){
            if(ci.p_coordinate[position][ci.column] <= mouse_Y){
                for(int i = 1 ; i <= 13 ; i ++){
                    if(ci.p_coordinate[position][ci.column] + (i * ci.papercard_gap.get(position)) >= mouse_Y){
                        return i;
                    }
                }
            }
        }
        return incompatible;
    }
    
    public void change(Card_interface ci , int end_position){
        ci.pc.paper_card_change(ci.catch_coordinate(ci.pc.move_card_position) , ci.catch_coordinate(end_position));
        ci.position_quantity_change(ci.pc.move_card_position , end_position);
    }
}
