package konan.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        //1、绘制一个静态窗口 JFrame
        JFrame frame=new JFrame("こなんの贪吃蛇小游戏");
        frame.setBounds(10,10,900,720);//设置界面大小
        frame.setResizable(false);//窗口大小不可改变
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭事件
        frame.setVisible(true);//让窗口能够展示出来

        //2、画板JPanel 可以加入到JFrame
        frame.add(new GamePanel());
        frame.setVisible(true);//为了让窗口能展现出来
    }
}
