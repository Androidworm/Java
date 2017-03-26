
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import cn.itcast.util.FrameUtil;
public class SnakeGame extends JPanel {
	
	//地图的高宽	
	public static final int WIDTH =80;	
	public static final int HEIGHT =50;
	public static final int CELLWIDTH =10;
	public static final int CELLHEIGHT =10;
	private boolean[][] background = new boolean[HEIGHT][WIDTH];
		
	//初始化地图
	public void initBackground()
	{
		for(int rows=0; rows<background.length ; rows++)
		{
			for(int cols=0;cols<background[rows].length;cols++)
			{
				if(rows==0||rows==(HEIGHT-1)||cols==0||cols==(WIDTH-1))
				{ background[rows][cols] =true;}
			}
		}
	}
	//使用集合保存蛇节点的所有信息
	LinkedList<Point>  snake = new LinkedList<Point>(); 
	//初始化蛇
	public void initSnake()
	{
		int x = WIDTH/2;
		int y = HEIGHT/2;
		snake.addFirst(new Point(x-1,y));
		snake.addFirst(new Point(x,y));
		snake.addFirst(new Point(x+1,y));
	}
	//食物
	Point	food;
	//生成食物 
	public void createFood()
	{
		Random random = new Random();
		while(true){
			int x = random.nextInt(WIDTH); 
			int y = random.nextInt(HEIGHT);			
/*			if(background[y][x]!='*')
			{
				food = new Point(x,y);
				break;
			}*/
		}
	}
	
	@Override
	public void paint(Graphics g) 
	{	//画地图
		for(int rows = 0 ; rows<background.length ; rows++)
		{ 
			for(int cols = 0  ; cols<background[rows].length ; cols++ )
			{
				if(background[rows][cols])
				{g.setColor(Color.GRAY);}
				else
				{g.setColor(Color.WHITE);}
				g.fill3DRect(cols*CELLWIDTH, rows*CELLHEIGHT,CELLWIDTH, CELLHEIGHT, true);
			}
		}
		//取出蛇头
		Point head = snake.getFirst();
		g.setColor(Color.RED);
		g.fill3DRect(head.x*CELLWIDTH, head.y*CELLHEIGHT,CELLWIDTH, CELLHEIGHT, true);		
		//画蛇身
		g.setColor(Color.GREEN);
		for(int i =1; i<snake.size() ; i++ )
		{
			Point body=snake.get(i);	
			g.fill3DRect(body.x*CELLWIDTH, body.y*CELLHEIGHT,CELLWIDTH, CELLHEIGHT, true);
		}
		//生成食物

		
			
	}
	
	//使用四个常量表示四个方向
	public static final int UP_DIRECTION = 1;    //上	
	public static final int DOWN_DIRECTION = -1; //下
	public static final int LEFT_DIRECTION = 2;  //左	
	public static final int RIGHT_DIRECTION =-2; //右
	//蛇当前的方向
	int currentDrection = -2; // 蛇默认是向右行走	
	//蛇移动的方法
	public void move()
	{
		Point head = snake.getFirst();
		switch (currentDrection) 
		{
			case UP_DIRECTION:snake.addFirst(new Point(head.x,head.y-1));break;
			case DOWN_DIRECTION:snake.addFirst(new Point(head.x,head.y+1));break;
			case LEFT_DIRECTION:
				if(head.x==0)
				{snake.addFirst(new Point(WIDTH-1,head.y));}
				else
				{snake.addFirst(new Point(head.x-1,head.y));}
				break;
			case RIGHT_DIRECTION:
				if(head.x==WIDTH-1)
				{snake.addFirst(new Point(0,head.y));}
				else
				{snake.addFirst(new Point(head.x+1,head.y));}
				break;
			default:break;
		}
		
	}
	//改变当前方向的方法
	public  void changeDirection(int newDirection)
	{
		//判断新方向是否与当前方向是否是相反方向，才允许其改变
		if(newDirection+currentDrection!=0)
		{this.currentDrection = newDirection;}
	}	
	
	//记录游戏是否结束
	static	boolean isGameOver = false; //默认游戏没有结束的。
	//游戏结束的方法
	/*public void isGameOver()
	{	//撞墙死亡
		Point head = snake.getFirst();
		if(background[head.y][head.x]=='*')
		{isGameOver = true;}
		//咬到自己  蛇身
		for(int i = 1; i<snake.size() ; i++)
		{
			Point body = snake.get(i);
			if(head.equals(body))
			{isGameOver = true;}
		}
	}*/	
/***********************************************************/
/**************************main入口**************************/
/***********************************************************/
	public static void main(String[] args) 
	{
		SnakeGame snake = new SnakeGame();
		JFrame Gameboy=new JFrame("贪吃蛇");
		snake.initBackground();
		snake.initSnake();
	
		Gameboy.add(snake);
		FrameUtil.initFrame(Gameboy,WIDTH*CELLWIDTH+16,HEIGHT*CELLHEIGHT+39);


		//snake.createFood();
		//snake.showFood();
		//snake.showBackground();
	
		//给按钮添加事件监听器
		Gameboy.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				int code = e.getKeyCode();				
				switch (code)
				{
					case KeyEvent.VK_UP:snake.changeDirection(UP_DIRECTION);break;
					case KeyEvent.VK_DOWN:snake.changeDirection(DOWN_DIRECTION);break;
					case KeyEvent.VK_LEFT:snake.changeDirection(LEFT_DIRECTION);break;
					case KeyEvent.VK_RIGHT:snake.changeDirection(RIGHT_DIRECTION);break;
					default:break;
				}
				snake.move();
				snake.repaint();
				//snake.isGameOver();//判断是否游戏结束
				//snake.refrash();
				//if(isGameOver)
				//{System.out.println("游戏结束了..");System.exit(0);}	
			}
		});		


		
		
		
	}

}
