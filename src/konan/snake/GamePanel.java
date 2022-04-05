package konan.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    int length;//蛇的长度
    int[] snakeX=new int[600];//x坐标
    int[] snakeY=new int[500];//y坐标
    String fx;  //蛇头方向
    int score=0;//积分系统
    int grade=1;//等级
    int vi=200;

    boolean isStart=false;//游戏是否开始
    boolean isFail=false;//失败判定

    Timer timer=new Timer(vi ,this);//计时器，根据等级重设计时器部分尚未实现

    int foodX;//食物生成
    int foodY;
    Random random=new Random();

    public GamePanel(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();//让时间动起来
    }

    public void init(){
        length=3;
        score=0;
        snakeX[0]=100;snakeY[0]=100;
        snakeX[1]=75;snakeY[1]=100;
        snakeX[2]=50;snakeY[2]=100;
        fx="R";
        foodX=25+25*random.nextInt(33);
        foodY=75+25*random.nextInt(21);
    }

    //画板：画界面，画蛇🐍
    //Graphics：画笔🖌️
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);//清屏
        this.setBackground(Color.WHITE);//设置背景颜色
        //Data.header.paintIcon(this,g,25,11);//绘制头部的广告栏
        g.fillRect(25,75,850,600);//绘制游戏区域

        if(fx.equals("R")){                 //打印头部
            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("L")){
            Data.left.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("U")){
            Data.up.paintIcon(this,g,snakeX[0],snakeY[0]);
        }else if(fx.equals("D")){
            Data.down.paintIcon(this,g,snakeX[0],snakeY[0]);
        }

        for(int i=1;i<length;i++){
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);//打印身体
        }

        Data.food.paintIcon(this,g,foodX,foodY);//打印食物

        g.setColor(Color.BLACK);//打印分数
        g.setFont(new Font("宋体",Font.BOLD,18));
        g.drawString("长度"+length,750,35);
        g.drawString("分数"+score,750,52);
        g.drawString("等级"+grade,750,68);

        //游戏提示：是否开始
        if(isStart==false){
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("按下空格开始游戏",300,300);
        }

        //游戏失败
        if(isFail){
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑",Font.BOLD,40));
            g.drawString("游戏失败，按下空格键重新开始",200,300);
        }
    }

    @Override//接收键盘输入：监听
    public void keyPressed(KeyEvent e) {
        //获取按下的键是哪个键
        int keyCode=e.getKeyCode();

        if(keyCode==KeyEvent.VK_SPACE) {//空格
            if(isFail){//失败，游戏再来一次
                isFail=false;
                init();//重新初始化
            }else{
                isStart = !isStart;
            }
            repaint();//刷新页面
        }
        //键盘控制走向
        if(keyCode== KeyEvent.VK_UP&&fx!="D"){
            fx="U";
        }else if(keyCode== KeyEvent.VK_DOWN&&fx!="U"){
            fx="D";
        }else if(keyCode== KeyEvent.VK_LEFT&&fx!="R"){
            fx="L";
        }else if(keyCode== KeyEvent.VK_RIGHT&&fx!="L"){
            fx="R";
        }
    }

    @Override//定时器，监听时间，帧：执行定时操作
    public void actionPerformed(ActionEvent e) {
        if(isStart!=false&&isFail!=true){
            for(int i=length-1;i>0;i--){//身体移动
                snakeX[i]=snakeX[i-1];
                snakeY[i]=snakeY[i-1];
            }
            //通过控制方向让头部移动
            if(fx.equals("R")){
                snakeX[0]=snakeX[0]+25;//头部移动
                if(snakeX[0]>850) isFail=true;//边界判断
            }else if(fx.equals("L")){
                snakeX[0]=snakeX[0]-25;//头部移动
                if(snakeX[0]<25) isFail=true;//边界判断
            }else if(fx.equals("U")){
                snakeY[0]=snakeY[0]-25;//头部移动
                if(snakeY[0]<75) isFail=true;//边界判断
            }else if(fx.equals("D")){
                snakeY[0]=snakeY[0]+25;//头部移动
                if(snakeY[0]>650) isFail=true;//边界判断
            }

            if(snakeX[0]==foodX&&snakeY[0]==foodY){//判断是否吃到食物
                length++;
                score+=10;
                //重新生成食物
                foodX=25+25*random.nextInt(33);
                foodY=75+25*random.nextInt(21);
            }

            for (int i=length-1;i>0;i--) {//判断是否撞到自己
                if(snakeX[0]==snakeX[i]&&snakeY[0]==snakeY[i]){
                    isFail=!isFail;
                }
            }

            if(score<=100){ //判断并修改等级
                grade=1;vi=200;
            }else if(score<=200){
                grade=2;vi=150;
            }else if(score<=300){
                grade=3;vi=100;
            }else {
                grade=4;vi=60;
            }

            repaint();
        }
        timer.start();//让时间动起来
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
