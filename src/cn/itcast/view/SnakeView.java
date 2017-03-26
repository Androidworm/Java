package cn.itcast.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.itcast.util.FrameUtil;

public class SnakeView  extends JPanel{

	//地图的宽(列数)
	public static final int WIDTH = 40;
	
	//地图高（行数）
	public static final int HEIGHT = 30;	
	
	
	//格子宽
	public static final int CELLWIDTH = 20;
	
	//格子高
	public static final int CELLHEIGHT = 20;
	
	//地图
	private boolean[][] background = new boolean[HEIGHT][WIDTH];
	

	//使用集合保存蛇节点的所有信息
	LinkedList<Point>  snake = new LinkedList<Point>(); 
	
	
	//初始化蛇
	public void initSnake(){
		int x = WIDTH/2;
		int y = HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
	}
		
	
	//向上移动
	public void moveUp(){
		//获取原来蛇头 
		Point head = snake.getFirst();
		//添加新的蛇头
		snake.addFirst(new Point(head.x,head.y-1));
		//删除蛇尾
		snake.removeLast();
	}
	
	
	//初始化地图
	public void initBackground(){
		for(int rows = 0 ; rows<background.length ; rows++){
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				if(rows==0||rows==(HEIGHT-1)||cols==0||cols==(WIDTH-1)){  //第一行、最后一行、 第一列与最后一列
					background[rows][cols] = true;
				}
			}
		}
	}
		
	
	@Override
	public void paint(Graphics g) {
		//画地图		
		for(int rows = 0 ; rows<background.length ; rows++){ // rows =0 
			for(int cols = 0  ; cols<background[rows].length ; cols++ ){
				if(background[rows][cols]){
					//石头
					g.setColor(Color.GRAY);
				}else{
					//空地
					g.setColor(Color.WHITE);
				}
				//画矩形
				g.fill3DRect(cols*CELLWIDTH, rows*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
			}
		}
		
		//画蛇
		
		//取出蛇头
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x*CELLWIDTH, head.y*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		//画蛇身
		g.setColor(Color.GREEN);
		for(int i =1; i<snake.size() ; i++ ){
			Point body = snake.get(i);
			g.fill3DRect(body.x*CELLWIDTH, body.y*CELLHEIGHT, CELLWIDTH, CELLHEIGHT, true);
		}
		
		
		
	
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("贪吃蛇");
		final SnakeView snakeView = new SnakeView();
		snakeView.initBackground();
		snakeView.initSnake();
		frame.add(snakeView);
		FrameUtil.initFrame(frame, WIDTH*CELLWIDTH+20, HEIGHT*CELLHEIGHT+35);
		
		//给窗口添加事件监听
		frame.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				switch (code) {
				case KeyEvent.VK_UP:
						snakeView.moveUp();
						//重画游戏
						snakeView.repaint(); //调用repaint方法的时候实际上就是调用了paint方法。
					break;

				default:
					break;
				}
				
				
			}
			
			
			
		});
		
		
		
		
		
	}
	
}
