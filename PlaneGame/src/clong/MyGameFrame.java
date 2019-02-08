package clong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.JFrame;

import Untils.Const;
import Untils.GameUtil;

/**
 * 飞机游戏的主窗口
 *
 */
public class MyGameFrame  extends  JFrame {
	//获取图片
	Image bg  = GameUtil.getImage("images/bg.jpg");
	Image planeImage = GameUtil.getImage("images/plane.png");
	Plane plane = new Plane(planeImage,250,250);
	Shell[] shells = new Shell[50];
	Explode explode ;
	Date  startTime = new Date();
	Date  endTime;
	int period;   //游戏持续的时间
	//设置坐标
//	int planeX = 250;
//	int planeY = 250;
	@Override
	public void paint(Graphics g) {		//自动被调用。  g相当于一只画笔
		g.drawImage(bg, 0, 0, null);
//		g.drawImage(planeImage,planeX,planeY,null);
//		planeX++;
		plane.drawSelf(g);
		for (int i=0;i<shells.length;i++){
			shells[i].draw(g);
			boolean flage = shells[i].getRect().intersects(plane.getRect());
			if(flage){
				plane.live = false;
				if(explode ==null){
					explode  = new Explode(plane.x, plane.y);
					explode  = new Explode(plane.x, plane.y);
					endTime = new Date();
					period = (int)((endTime.getTime()-startTime.getTime())/1000);
				}
				explode.draw(g);
			}
			
			//计时功能，给出提示
			if(!plane.live){
				g.setColor(Color.red);
				Font   f  =  new Font("宋体", Font.BOLD, 50);
				g.setFont(f);
				g.drawString("时间："+period+"秒", (int)plane.x, (int)plane.y);
			}
		}
		
	}
	class PaintThread extends Thread{
		@Override
		public void run() {
			while(true){
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class KeyMoniter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
		}
	}
	
	/**
	 * 初始化窗口
	 */
	public  void  launchFrame(){
		this.setTitle("飞机大战-简约版");//设置标题
		this.setVisible(true);//设置可见的
		this.setSize(Const.GAME_WIGHT, Const.GAME_HEIGHT);//设置大小
		this.setLocation(300, 150);//设置出现的位置
		//
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start();
		addKeyListener(new KeyMoniter());
		
		//初始化50个炮弹
		for(int i=0;i<shells.length;i++){
			shells[i] = new Shell();
		}
	}
	
	public static void main(String[] args) {
		MyGameFrame  f = new MyGameFrame();
		f.launchFrame();
		
	}
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Const.GAME_WIGHT,Const.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	    	Graphics gOff = offScreenImage.getGraphics();
	    	paint(gOff);
	    	g.drawImage(offScreenImage, 0, 0, null);
	}
	
}
