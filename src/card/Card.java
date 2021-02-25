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
    private Mouse_keyboard mouse_keyboard;
    private Card_interface card_interface;
    
    private int fraction;
    
    Card(){
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addKeyListener(this);
        
        jframe = new JFrame();
        mouse_keyboard = new Mouse_keyboard();
        card_interface = new Card_interface();
        
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
        card_interface.position_setup(this.getWidth() , this.getHeight());
        card_interface.show_background(this , g);
        
        card_interface.show_picture(g , mouse_keyboard , this);
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
    
    public void change(int start_position , int end_position , int move_card_quantity , String fraction_key){
        card_interface.paper_card.change(card_interface.catch_coordinate(start_position) , card_interface.catch_coordinate(end_position) , move_card_quantity);
        card_interface.change(start_position , end_position , move_card_quantity);
        fraction(fraction_key);
    }
    
    public void restart(){
        
        card_interface.paper_card.shuffle();
        
        card_interface.position_quantity[card_interface.positive][0] = 7;
        card_interface.position_quantity[card_interface.positive][1] = 6;
        card_interface.position_quantity[card_interface.positive][2] = 5;
        card_interface.position_quantity[card_interface.positive][3] = 4;
        card_interface.position_quantity[card_interface.positive][4] = 3;
        card_interface.position_quantity[card_interface.positive][5] = 2;
        card_interface.position_quantity[card_interface.positive][6] = 1;
        card_interface.position_quantity[card_interface.positive][7] = 0;
        card_interface.position_quantity[card_interface.positive][8] = 0;
        card_interface.position_quantity[card_interface.positive][9] = 0;
        card_interface.position_quantity[card_interface.positive][10] = 0;
        card_interface.position_quantity[card_interface.positive][11] = 0;
        card_interface.position_quantity[card_interface.positive][12] = 24;
        
        card_interface.position_quantity[card_interface.negative][0] = 6;
        card_interface.position_quantity[card_interface.negative][1] = 5;
        card_interface.position_quantity[card_interface.negative][2] = 4;
        card_interface.position_quantity[card_interface.negative][3] = 3;
        card_interface.position_quantity[card_interface.negative][4] = 2;
        card_interface.position_quantity[card_interface.negative][5] = 1;
        card_interface.position_quantity[card_interface.negative][6] = 0;
        card_interface.position_quantity[card_interface.negative][7] = 0;
        card_interface.position_quantity[card_interface.negative][8] = 0;
        card_interface.position_quantity[card_interface.negative][9] = 0;
        card_interface.position_quantity[card_interface.negative][10] = 0;
        card_interface.position_quantity[card_interface.negative][11] = 0;
        card_interface.position_quantity[card_interface.negative][12] = 24;
        
        int initial = 100;
        card_interface.card_spacing(initial);
        
//        move_card.clear();
//        move_type.clear();
        fraction = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        滑鼠完整點擊

        float mouse_X = e.getX();
        float mouse_Y = e.getY();
        
        boolean one_click_switch = mouse_keyboard.one_click(card_interface , mouse_X , mouse_Y);
        if(one_click_switch){
            fraction("one_click");
        }
        
        int click_quantity = 2;
        if(e.getClickCount() == click_quantity) {
            int start_position = mouse_keyboard.catch_start_position(card_interface, mouse_X, mouse_Y , new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 11});
            
            int incompatible = 100;
            if(start_position != incompatible){
                int end_position = mouse_keyboard.two_click(card_interface , start_position);
                
                if(end_position != incompatible){
                    int move_card_quantity = 1;
                    change(start_position , end_position , move_card_quantity , "two_click");
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
        
        int incompatible = 100;
        int start_position = mouse_keyboard.catch_start_position(card_interface, mouse_X, mouse_Y , new int[]{0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 , 11});
        if(start_position != incompatible){
            card_interface.mouse_point[card_interface.row] = mouse_X;
            card_interface.mouse_point[card_interface.column] = mouse_Y;
            if(mouse_keyboard.mouse_status() == mouse_keyboard.initial){
                mouse_keyboard.mouse_move(card_interface , start_position);
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
