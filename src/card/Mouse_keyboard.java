package card;

import java.util.ArrayList;
import java.util.List;

public class Mouse_keyboard {
    
    public final int initial = -1;
    public final int click = 0;
    public final int move = 1;
    private int mouse_status;
    
    public Mouse_keyboard(){
        change_mouse_status(initial);
    }
    
    public int get_mouse_status(){
        return mouse_status;
    }
    
    public void change_mouse_status(int status){
        mouse_status = status;
    }
    
    public boolean one_click_switch(Card_interface ci , float mouse_X , float mouse_Y){
        
        boolean pair_position = pair_position_less(ci , mouse_X , mouse_Y , 12);
        if(pair_position){
            if(ci.pc.get_positive_quantity(12) == 0){
                int start_position = 11;
                int end_position = 12;
                int move_quantity = ci.pc.get_positive_quantity(start_position);
                ci.pc.click_change(start_position , end_position , move_quantity);
                return true;
            }else{
                int start_position = 12;
                int end_position = 11;
                int move_quantity = 1;
                ci.pc.click_change(start_position , end_position , move_quantity);
                return false;
            }
        }else{
            return false;
        }
    }
    public int[] two_click_switch(Card_interface ci , float mouse_X , float mouse_Y){
        boolean judgment_start;
        int judgment_end;
        
        for(int start_position : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 11}){
            judgment_start = pair_position_less(ci , mouse_X , mouse_Y , start_position);
            if(judgment_start){
                
                for(int end_position : new int[]{10 , 9 , 8 , 7}){
                    
                    judgment_end = pair_position_type_number(ci , start_position , end_position);
                    int true_and_addfraction = 0;
                    int true_not_addfraction = 1;
                    
                    if((judgment_end == true_and_addfraction || judgment_end == true_not_addfraction) && judgment_start){
                        int move_quantity = 1;
                        ci.pc.click_change(start_position , end_position , move_quantity);
                        ci.pc.judgment_positive_and_negative(start_position);
                        return new int[]{judgment_end , start_position , end_position};
                    }
                }
            }
        }
        return new int[]{ci.incompatible};
    }
    
    public boolean mouse_move(Card_interface ci , float mouse_X , float mouse_Y){
        
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11}){
            boolean move_card_switch = pair_position_less(ci , mouse_X , mouse_Y , i);
            if(move_card_switch){
                int move_quantity = 1;
                add_move_card(ci , i , move_quantity);
                return true;
            }
            if(i < 7){
                int move_quantity = pair_position_many(ci , mouse_X , mouse_Y , i);
                if(move_quantity != ci.incompatible){
                    add_move_card(ci , i , move_quantity);
                    return true;
                }
            }
        }
        return false;
    }
    
    public int[] mouse_up(Card_interface ci){
        int true_and_addfraction = 0;
        int true_not_addfraction = 1;
        int true_and_add_negativefraction = 2;
        
        for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10}){
            boolean pair_end_position = pair_end_position(ci , i);
            if(pair_end_position){
                
                int pair_top_position = pair_position_type_number(ci , ci.pc.get_move_position() , i);
                if(pair_top_position == true_and_addfraction || pair_top_position == true_not_addfraction || pair_top_position == true_and_add_negativefraction){
                    add_mouse_up(ci , i);
                    return new int[]{pair_top_position , i};
                }
            }
        }
        return new int[]{ci.incompatible , ci.incompatible};
    }
    
    public boolean pair_end_position(Card_interface ci , int end_position){
        
        float card_point1 = ci.mouse_point[ci.row] - ci.getRow_card_size() / 2;
        float card_point2 = ci.mouse_point[ci.row] + ci.getRow_card_size() / 2;
        float card_point3;
        float card_point4;
        if(end_position < 7){
            card_point3 = ci.mouse_point[ci.column] - ci.get_gap(end_position) / 2;
            card_point4 = ci.mouse_point[ci.column] + ci.getColumn_card_size() - ci.get_gap(end_position) / 2;
        }else{
            if(ci.pc.get_move_quantity() != 1){
                return false;
            }
            card_point3 = ci.mouse_point[ci.column] - ci.getColumn_card_size() / 2;
            card_point4 = ci.mouse_point[ci.column] + ci.getColumn_card_size() / 2;
        }
        float card_point[][] = {{card_point1 , card_point3} , {card_point2 , card_point3} , {card_point1 , card_point4} , {card_point2 , card_point4}};
            
        for(int i = 0 ; i < card_point.length ; i ++){
            boolean in_switch = pair_position_less(ci , card_point[i][0] , card_point[i][1] , end_position);
            if(in_switch){
                return true;
            }
        }
        return false;
    }
    
    public boolean pair_position_less(Card_interface ci , float mouse_X , float mouse_Y , int position){
        
        if((ci.getRow_t_coordinate(position) <= mouse_X && ci.getRow_t_coordinate(position) + ci.getRow_card_size() >= mouse_X) &&
          (ci.getColumn_t_coordinate(position) <= mouse_Y && ci.getColumn_t_coordinate(position) + ci.getColumn_card_size() >= mouse_Y)){
            return true;
        }
        return false;
    }
    
    public int pair_position_many(Card_interface ci , float mouse_X , float mouse_Y , int position){
        if(ci.getRow_p_coordinate(position) <= mouse_X && ci.getRow_p_coordinate(position) + ci.getRow_card_size() >= mouse_X){
            
            int quantity = ci.pc.get_positive_quantity(position) - ci.pc.get_negative_quantity(position);
            for(int i = 0 ; i < quantity - 1 ; i ++){
                if(ci.getColumn_t_coordinate(position) - ((i + 1) * ci.get_gap(position)) <= mouse_Y){
                    if(ci.getColumn_t_coordinate(position) - (i * ci.get_gap(position)) >= mouse_Y){
                        return i + 2;
                    }
                }
            }
        }
        return ci.incompatible;
    }
    
    public int pair_position_type_number(Card_interface ci ,int start_position , int end_position){
        
        int move_position_quantity;
        int end_position_quantity = ci.pc.get_positive_quantity(end_position);
        
        int move_card_number;
        int end_card_number;
        
        int move_card_type;
        int end_card_type;
        
        int first_card_position = 1;
        if(ci.pc.get_move_position() != ci.incompatible){
            move_position_quantity = ci.pc.get_move_quantity();
            move_card_type = ci.pc.getMT(first_card_position);
            move_card_number = ci.pc.getMN(first_card_position);
        }else{
            move_position_quantity = 1;
            move_card_type = ci.pc.getT(start_position , first_card_position);
            move_card_number = ci.pc.getN(start_position , first_card_position);
        }
        
        if(end_position_quantity != 0){
            end_card_number = ci.pc.getN(end_position , first_card_position);
            end_card_type = ci.pc.getT(end_position , first_card_position);
        }else{
            end_card_number = ci.incompatible;
            end_card_type = ci.incompatible;
        }
        
        int[][] data = {{move_card_number , end_card_number} , {move_card_type , end_card_type} , {move_position_quantity , end_position_quantity}};
        if(end_position < 7){
            return pair_position_0_6(ci , start_position , end_position , data);
        }else{
            return pair_position_7_10(ci , data);
        }
    }
    
    public int pair_position_0_6(Card_interface ci , int start_position , int end_position , int[][] data){
        int true_and_addfraction = 0;
        int true_not_addfraction = 1;
        int true_and_add_negativefraction = 2;
        
        int number = 0 , type = 1 , quantity = 2;
        int move = 0 , end = 1;
        
        if(data[quantity][end] == 0 && data[number][move] == 12){
            if(data[number][move] == 12){
                if(ci.pc.get_positive_quantity(start_position) == 0){
                    return true_not_addfraction;
                }else if(ci.pc.get_move_position() < 11 && ci.pc.get_move_position() > 6){
                    return true_and_add_negativefraction;
                }else{
                    return true_and_addfraction;
                }
            }
        }else if(((data[type][move] == 0 || data[type][move] == 3) &&
                (data[type][end] == 1 || data[type][end] == 2)) ||
                ((data[type][move] == 1 || data[type][move] == 2) &&
                (data[type][end] == 0 || data[type][end] == 3)) ){
            
            if(data[number][move] == data[number][end] - 1 && start_position != end_position){
                if(ci.pc.getN(start_position , 1) == data[number][end]){
                    return true_not_addfraction;
                }else if(ci.pc.get_move_position() < 11 && ci.pc.get_move_position() > 6){
                    return true_and_add_negativefraction;
                }else{
                    return true_and_addfraction;
                }
            }
        }
        return ci.incompatible;
    }
    
    public int pair_position_7_10(Card_interface ci , int[][] data){
        int true_and_addfraction = 0;
        int true_not_addfraction = 1;
        
        int number = 0 , type = 1 , quantity = 2;
        int move = 0 , end = 1;
        
        if(data[quantity][end] == 0 && data[number][move] == 0){
            if(ci.pc.get_move_position() < 11 && ci.pc.get_move_position() > 6){
                return true_not_addfraction;
            }else{
                return true_and_addfraction;
            }
        }else if(data[type][move] == data[type][end]){
            if(data[number][move] == data[number][end] + 1){
                return true_and_addfraction;
            }
        }
        return ci.incompatible;
    }
    
    public void add_move_card(Card_interface ci , int start_position , int move_quantity){
        if(start_position < 7){
            ci.add_Mgap(ci.get_gap(start_position));
        }
        ci.pc.add_move_card(start_position, move_quantity);
        ci.pc.add_move_position(start_position);
        ci.pc.remove_card(start_position, move_quantity);
        ci.pc.remove_position_quantity(start_position , move_quantity);
    }
    
    public void add_mouse_up(Card_interface ci , int end_position){
        ci.pc.judgment_positive_and_negative(ci.pc.get_move_position());
        ci.pc.add_card(end_position , ci.pc.get_move_quantity());
        ci.pc.add_positive_quantity(end_position , ci.pc.get_move_quantity());
    }
    
    public void back_home(Card_interface ci){
        ci.pc.add_card(ci.pc.get_move_position() , ci.pc.get_move_quantity());
        ci.pc.add_positive_quantity(ci.pc.get_move_position() , ci.pc.get_move_quantity());
    }
}