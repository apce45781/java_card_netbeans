package card;

public class Mouse_click {
    
    public boolean one_click(Card_interface ci , float mouse_X , float mouse_Y) {
//        只有位置12需要起作用
        if((mouse_X >= ci.t_coordinate[12][ci.row] && mouse_X <= ci.t_coordinate[12][ci.row] + ci.papercard_size[ci.row] ) && (mouse_Y >= ci.t_coordinate[12][ci.column] && mouse_Y <= ci.t_coordinate[12][ci.column] + ci.papercard_size[ci.column])){
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
    
    public int judgment_click_position(Card_interface ci , float mouse_X , float mouse_Y){
//        只有位置1、2、3、4、5、6、7、12需要起作用
        for(int i = 0 ; i < 12 ; i ++){
            if(i > 7 && i < 11){
                continue;
            }else if(ci.position_quantity[ci.positive][i] != 0 && (mouse_X >= ci.t_coordinate[i][ci.row] && mouse_X <= ci.t_coordinate[i][ci.row] + ci.papercard_size[ci.row]) && (mouse_Y >= ci.t_coordinate[i][ci.column] && mouse_Y <= ci.t_coordinate[i][ci.column] + ci.papercard_size[ci.column])){
                return i;
            }
        }
        int incompatible = 100;
        return incompatible;
    }
    
    public int two_click(Card_interface ci , int start){
        
        int[] destination = new int[4];
        for(int i = 0 ; i < 4 ; i ++){
            destination[i] = ci.catch_coordinate(7 + i);
        }
        
        for(int i = 10 ; i > 6 ; i --){
            if(ci.position_quantity[ci.positive][i] == 0){
                if(ci.paper_card.getN(start) == 0){
                    return i;
                }
            }else{
                if(ci.paper_card.getT(destination[i - 7]) == ci.paper_card.getT(start)){
                    if(ci.paper_card.getN(destination[i - 7]) == ci.paper_card.getN(start) - 1){
                        return i;
                    }
                }
            }
        }
        int incompatible = 100;
        return incompatible;
    }
}
