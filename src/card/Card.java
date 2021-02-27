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
    private Mouse_keyboard mk;
    private Card_interface ci;
    
    private int fraction;
    
    Card(){
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        jframe = new JFrame();
        mk = new Mouse_keyboard();
        ci = new Card_interface();
        
        fraction = 0;
        
        jframe.setBounds(100 , 50 , 875 , 700);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setTitle("接龍    點擊F2即可重新開局  分數 : " + fraction);
        
        restart();
        
        jframe.add(this);
        jframe.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Card();
    }
    
    @Override
    public void paint(Graphics g){
        ci.position_setup(this.getWidth() , this.getHeight());
        ci.show_background(this , g);
        
        ci.show_picture(g , mk , this);
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
    
    public void restart(){
        
        ci.pc.shuffle();
        
        ci.position_quantity[ci.positive][0] = 7;
        ci.position_quantity[ci.positive][1] = 6;
        ci.position_quantity[ci.positive][2] = 5;
        ci.position_quantity[ci.positive][3] = 4;
        ci.position_quantity[ci.positive][4] = 3;
        ci.position_quantity[ci.positive][5] = 2;
        ci.position_quantity[ci.positive][6] = 1;
        ci.position_quantity[ci.positive][7] = 0;
        ci.position_quantity[ci.positive][8] = 0;
        ci.position_quantity[ci.positive][9] = 0;
        ci.position_quantity[ci.positive][10] = 0;
        ci.position_quantity[ci.positive][11] = 0;
        ci.position_quantity[ci.positive][12] = 24;
        
        ci.position_quantity[ci.negative][0] = 6;
        ci.position_quantity[ci.negative][1] = 5;
        ci.position_quantity[ci.negative][2] = 4;
        ci.position_quantity[ci.negative][3] = 3;
        ci.position_quantity[ci.negative][4] = 2;
        ci.position_quantity[ci.negative][5] = 1;
        ci.position_quantity[ci.negative][6] = 0;
        ci.position_quantity[ci.negative][7] = 0;
        ci.position_quantity[ci.negative][8] = 0;
        ci.position_quantity[ci.negative][9] = 0;
        ci.position_quantity[ci.negative][10] = 0;
        ci.position_quantity[ci.negative][11] = 0;
        ci.position_quantity[ci.negative][12] = 24;
        
        int initial = 100;
        ci.card_spacing(initial);
        
//        move_card.clear();
//        move_type.clear();
        fraction = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        滑鼠完整點擊

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        boolean one_click_switch = mk.one_click_switch(ci , mouse_X , mouse_Y);
        if(one_click_switch){
            fraction("one_click");
        }
        
        int click_quantity = 2;
        if(e.getClickCount() == click_quantity) {
            boolean two_click_switch = mk.two_click_switch(ci , mouse_X , mouse_Y);
            if(two_click_switch){
                fraction("two_click");
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
////        滑鼠單一放開動作
//        if(mk.mouse_status() == mk.pick_up){
//            int end_position;
//            
//            if(ci.pc.move_card_number.size() == 1){
//                for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10}){
//                    end_position = mk.catch_position_card(ci , i);
//                }
//            }else{
//                for(int i : new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6}){
//                    end_position = mk.catch_position_card(ci , i);
//                }
//            }
//            
//        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        滑鼠點擊並移動

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        ci.mouse_point[ci.row] = mouse_X;
        ci.mouse_point[ci.column] = mouse_Y;
        
        if(mk.mouse_status() == mk.initial){
            mk.mouse_move(ci , mouse_X , mouse_Y);
        }
        repaint();
        try{
            Thread.sleep(30);
        }catch(InterruptedException ex){}
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
