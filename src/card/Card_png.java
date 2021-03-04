package card;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Card_png {
    
    private String papercard_type;
    
    private String path;
    private Map<Integer , Image> paper_card = new HashMap();
    private Image card;
    private Image back;
    
    Card_png(String papercard_type){
        this.papercard_type = papercard_type;
        path = new File("paper_card").getAbsolutePath();
        setCard_type();
    }
    
    public void setCard_type(){
        if(papercard_type.equals("background")){
            paper_card.put(4 , Toolkit.getDefaultToolkit().getImage(path + "/" + papercard_type + ".png"));
        }else if(papercard_type.equals("card")){
            paper_card.put(5 , Toolkit.getDefaultToolkit().getImage(path + "/" + papercard_type + ".png"));
        }else{
            for(int i = 0 ; i < 13 ; i ++){
                paper_card.put(i , Toolkit.getDefaultToolkit().getImage(path + "/" + papercard_type + "_" + (i + 1) + ".png"));
            }
        }
    }
    
    public Image get(int key){
        return paper_card.get(key);
    }
}
