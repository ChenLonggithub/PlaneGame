package clong;

import java.awt.Color;
import java.awt.Graphics;

import Untils.Const;

public class Shell extends GameObject{

	double degree;
	
	public Shell(){
		x = 200;
		y = 200;
		width = 9;
		height = 9;
		speed = 3;
		degree = Math.random()*Math.PI*2;
	}
	
	public void draw(Graphics g){
		Color color = g.getColor();
		g.setColor(Color.YELLOW);
		
		g.fillOval((int)x,(int)y, width, height);
		//炮弹沿着任意角度去飞
		x += speed*Math.cos(degree);
		y += speed*Math.sin(degree);
		
		//当小球撞到上下两个边框时，反弹的计算
		if(x<0||x>Const.GAME_WIGHT-width){
			degree  = Math.PI - degree;
		}
		//当小球撞到左右两个边框时，反弹的计算
		if(y<30||y>Const.GAME_HEIGHT-height){
			degree  = - degree;
		}
		g.setColor(color);
	}
}
