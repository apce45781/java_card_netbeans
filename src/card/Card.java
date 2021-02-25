package card;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Card extends JPanel implements MouseListener , MouseMotionListener , KeyListener{
    
    private JFrame jframe;
    private Card_interface card_interface;
    private Mouse_click mouse_click;
    private Mouse_move mm;
    private Mouse_up mu;
    
    private int fraction;
    
    Card(){
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        jframe = new JFrame();
        mouse_click = new Mouse_click();
        mm = new Mouse_move();
        mu = new Mouse_up();
        card_interface = new Card_interface();
        
        fraction = 0;
        
        jframe.setBounds(100 , 50 , 875 , 700);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("接龍    點擊F2即可重新開局  分數 : " + fraction);
        
        jframe.add(this);
        jframe.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Card();
    }
    
    @Override
    public void paint(Graphics g){
        card_interface.position_setup(this.getWidth() , this.getHeight());
        card_interface.show_background(this , g);
        
        card_interface.show_picture(g , this);
    }
    
    public void fraction(String keyword){
        int number = 0;
        if(keyword.equals("one_click")){
            number = -10;
        }else if(keyword.equals("two_click")){
            number = 50;
        }
        fraction += number;
        jframe.setTitle("接龍    點擊F2即可重新開局  分數 : " + fraction);
    }
    
    public void change(int change_data[] , String fraction_key){
        card_interface.paper_card.change(card_interface.catch_coordinate(change_data[0]) , card_interface.catch_coordinate(change_data[1]) , change_data[2]);
        card_interface.change(change_data);
        fraction(fraction_key);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        滑鼠完整點擊

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        boolean one_click_switch = mouse_click.one_click(card_interface , mouse_X , mouse_Y);
        if(one_click_switch){
            fraction("one_click");
        }
        
        int incompatible = 100;
        if(e.getClickCount() == 2) {
            int start_position = mouse_click.judgment_click_position(card_interface , mouse_X , mouse_Y);
            
            if(start_position != incompatible){
                int end_position = mouse_click.two_click(card_interface , card_interface.catch_coordinate(start_position));
                
                if(end_position != incompatible){
                    change(new int[]{start_position , end_position , 1} , "two_click");
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        滑鼠單一放開動作
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        滑鼠點擊並移動

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        //判斷起始點位置是否在卡片範圍內(100 == false)
        if(mm.judge_position() != 100){
            //判斷是否牌已"拿起"，並且避免滑鼠按住造成一直重複"拿起"
            if(mm.card_move()){
                repaint();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
//        鍵盤敲擊
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
