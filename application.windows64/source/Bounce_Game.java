import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Bounce_Game extends PApplet {

Game g;
Screen start_screen, current_screen, game_screen, end_screen;
PFont stdFont;
ButtonWidget game_name, play, exit;

Timer time;
Ball ball;
Block block;
Collision cl;
int score;

public void setup() {
  size(SCREEN_X, SCREEN_Y);
  g = new Game();
  
  time = new Timer();
  ball = new Ball();
  block = new Block();
  cl = new Collision(ball, block); 
  start_screen = new Screen(color(0, 255, 255));
  game_screen = new Screen(SCREEN_COLOR);


  stdFont=loadFont("Georgia-BoldItalic-48.vlw");
  game_name = new ButtonWidget(0, 125, 1000, 80, " ", 45, stdFont, NOTHING);
  play = new ButtonWidget(0, 325, 1000, 80, " ", color(154, 205, 50), stdFont, PLAY);
  exit = new ButtonWidget(0, 525, 1000, 80, " ", PROJECT_COLOR, stdFont, QUIT);
  start_screen.add(game_name);
  start_screen.add(play);
  start_screen.add(exit);

  current_screen = start_screen;
  smooth();
}

public void draw() {
  
  //main menu
  if (current_screen == start_screen) {
    current_screen.draw();
    textFont(stdFont);
    fill(255);
    text("Ball Bouncer", 275, 150);
    fill(0);
    text("Play", 370, 360);
    text("Quit", 370, 545);
  }
  else if (current_screen == end_screen) {
    
    current_screen.draw();

    int quit_x = 280;
    int quit_y = 300;
    int quit_w = 130;
    int quit_h = 60;

    int play_x = 535;
    int play_y = 300;
    int play_w = 300;
    int play_h = 60;

    /*fill(45, 255, 79);
    stroke(0);
    rect(450, 205, 500, 300);*/
    
    fill(255);
    text("Well done!", 300, 200);
    text("Your score is "+ g.score, 220, 250);

    fill(255, 0, 0);
    rect(quit_x, quit_y, quit_w, quit_h);
    rect(play_x, play_y, play_w, play_h);

    fill(0);
    text("Quit", 220, 315);
    text("Play Again", 400, 315);
  }
  else {
    ///start the game if there is no current screen
    g.draw();
  }
}

public void mousePressed() {
  //get the button that was clicked
  if (current_screen != null) {
    int event_case = current_screen.getEvent(mouseX, mouseY);

    switch(event_case) {

    case NOTHING:
      current_screen = start_screen;
      break;

    case PLAY:
      current_screen = null;
      break;

    case QUIT:
      System.out.println("Quit button was press.");
      System.exit(0);
      break;


    case REPLAY:
      current_screen = null;
      System.out.println("Replay button was press.");      
      break;
    }
  }
}

class Ball {

  int rad = 30;        // Width of the shape
  int xpos, ypos;    // Starting position of shape    

  int xspeed = 6;  // Speed of the shape
  int yspeed = 6;  // Speed of the shape

  int xdirection = 1;  // Left or Right
  
  /*This was initially called ydirection
   *but becuase I was using this as the bounce variable
   *I changed the name but then saw that speed was what worked better
   *but renaming in evcery pother class cuold damage the progarm, I just left it.
   */
  int bounce = 1; // Top to Bottom

  Ball() {
    ellipseMode(RADIUS);
    // Set the starting position of the shape
    xpos = ((int) width/2);
    ypos = ((int) height/2);
  }


  public void display() {
    ellipseMode(CENTER);
    ellipse(xpos, ypos, 410, 40);
  }

  public void move() {
    // Update the position of the shape
    xpos = xpos + ( xspeed * xdirection );
    ypos = ypos + ( yspeed * bounce );

    // Test to see if the shape exceeds the boundaries of the screen
    // If it does, reverse its direction by multiplying by -1

    if (xpos > width-rad || xpos < rad) {
      xdirection *= -1;
    }
    if (ypos > height-rad || ypos < rad) {
      bounce *= -1;
    }

    // Draw the shape
    ellipse(xpos, ypos, rad, rad);
  }
}

class Block {
  
  int width = 300;
  int height = 50;
  int controlx =400;
  int controly = 550;
  
  Ball ball;
  //l_r is used to control the leght at
  //which the block moves when the LEFT or RIGHT keys are pressed.
  int l_r =5;
  
  Block() {
    rectMode(RADIUS);
    rect(0, 0, width, height);
  }

  public void display() {
    rectMode(CENTER);
    rect(controlx, controly, width, height);
  }

  public void Control() {
    //Pretty simple controls
    if (keyPressed == true) {
      if (keyCode == LEFT) {
        controlx = controlx -l_r;
      } 
      else if (keyCode == RIGHT) {
        controlx = controlx +l_r;
      }
    }
    //If the block goes past the boundries, it is brought back.
    //I use widht/2 because the rectMode is in the center
    if ((controlx + width/2) > 900) {
      controlx = controlx - (15);
    }
    if ((controlx - width/2) < 0) {
      controlx = controlx + (15);
    }
  }
}

class ButtonWidget extends Widget {

  ButtonWidget(int x, int y, int width, int height, 
  String label, int widgetColor, PFont font, int event ){

    this.x=x;  
    this.y=y; 
    this.width = width; 
    this.height= height;
    this.label=label; 
    this.event=event; 
    this.widgetColor=widgetColor; 
    this.widgetFont=font;
    this.labelColor=0;
    isTextWidget = false;
  }
}

class Collision {
  //So many variables, some aare just being used for testing/printing 
  
  int num_of_hearts = 5;
  
  int ball_xpos;
  int ball_rad;
  int ball_ypos, ball_ypos_temp;
  int ball_xdirection;
  int bounce;
  
  PImage [] img;
  
  boolean out_of_bound = false;

  //xpeed is the most important here.
  int xspeed = 3;

  int block_width;
  int block_height;
  int block_controlx;
  int block_controly;

  int score = 0;

  /*I found that the line of collsion had to be 
   *changed as the level incresed
   */
  int ball_speed_cond = 0;

  Collision(Ball ball, Block block) {
    ball_xpos = ball.xpos;  
    ball_ypos = ball.ypos;
    ball_ypos_temp = ball.ypos;  
    ball_rad = ball.rad;
    ball_xdirection = ball.xdirection;

    block_width = block.width;
    block_height = block.height;
    block_controlx = block.controlx;
    block_controly = block.controly;
  }


  public boolean detectColliosion() {

    boolean collide = false;

    /*
     System.out.println("ball_ypos + ball_rad= "+ (ball_ypos + ball_rad));
     System.out.println("(520+ball_speed_cond) = "+ (520 + ball_speed_cond));
     System.out.println("(520-ball_speed_cond) = "+ (520 - ball_speed_cond));
     System.out.println("(520) = "+ (520));
     System.out.println("ball_xspeed = " + xspeed);
     */

    /*Sometimes the line of collision is either one or two positions away from, 520
     *so I'm trying to find that spot
     */

    if ((ball_ypos + ball_rad) <= (520 + 10) && (ball_ypos + ball_rad) >= (520 - 10)) {
      //System.out.println("Collision!!------");
      if ((ball_xpos < (block_controlx+block_width/2)) && (ball_xpos > (block_controlx-block_width/2)) ||
          (ball_xpos+ ball_rad < (block_controlx+block_width/2)) && (ball_xpos+ball_rad > (block_controlx-block_width/2)) ||
          (ball_xpos - ball_rad < (block_controlx+block_width/2)) && (ball_xpos- ball_rad > (block_controlx-block_width/2)) ) {
        //System.out.println("Collision!!------");
        bounce *= -bounce;
        collide = true;
      }
    }
    return collide;
  }

  public void out_of_play() {
    if ((ball_ypos + ball_rad) >= (520 + block_height)) {
      out_of_bound = true;
    }
    
  }
  
  public void setup(){
    img = new PImage [num_of_hearts];
  }
  
  public void draw(){
    int num =0;
    int posx = 1;
    
    while(num < num_of_hearts){
      img[num] = loadImage("heart.png");
      image(img[num], posx, 10, 35, 35);
      num++;
      posx +=40;
    } 
  }
}

final int PROJECT_COLOR = color(125, 50, 200);
final int SECONDARY_COLOR = color(100);
final int SCREEN_COLOR = color(2, 2, 2);

final int SCREEN_X = 900;
final int SCREEN_Y = 650;

final int PLAY = 1;
final int REPLAY = 100;
final int QUIT = 50;
final int NOTHING = 20;

final int EVENT_NULL=1000;

class Game {
  Ball ball;
  Block block;
//  Lives[] lives;
  Collision cl;
  Timer time;
  int level, sec;
  int score = 0;
  //Screen current_screen;

  ButtonWidget replay, exit;

  boolean on_quit, on_replay = false;
  int quit_x = 280;
  int quit_y = 300;
  int quit_w = 130;
  int quit_h = 60;

  int play_x = 535;
  int play_y = 300;
  int play_w = 300;
  int play_h = 60;

  Game() {
    background(0, 0, 0);
    ball = new Ball();
    block = new Block();
    cl = new Collision(ball, block);
    
    time = new Timer();
    time.ball_ypos = ball.ypos;
    time.getStart();
    end_screen = new Screen(SCREEN_COLOR);
    replay = new ButtonWidget(play_x, play_y, play_w, play_h, " ", color(0, 0, 255), stdFont, REPLAY);
    exit = new ButtonWidget(quit_x, quit_y, quit_w, quit_h, " ", color(0, 0, 255), stdFont, QUIT);
    end_screen.add(replay);
    end_screen.add(exit);
  }

  public void draw() {
    background(0, 255, 255);
    fill(255, 0, 0);

    time.ball_xspeed = ball.xspeed;
    time.ball_yspeed = ball.yspeed;

    time.ball_xpos = ball.xpos;
    time.ball_ypos = ball.ypos;

    cl.block_controlx = block.controlx;
    cl.block_controly = block.controly;
    time.block_controly = block.controly;

    level = time.level;
    sec = time.sec;

    ball.move();
    fill(154, 205, 50);
    block.Control();
    stroke(0);
    block.display();
    boolean collide = false;
    fill(0);
    time.draw();

    cl.ball_speed_cond = time.ball_speed_cond;

    //Here is updates things for the new level.
    if (time.out_of_time == true) {
      ball.ypos = time.ball_ypos;

      ball.xdirection = time.ball_xdirection;
      ball.bounce = time.bounce;

      ball.bounce = time.bounce; 
      cl.bounce = time.bounce;

      ball.xspeed = time.ball_xspeed;
      ball.yspeed = time.ball_yspeed;

      cl.xspeed = ball.xspeed;

      time.out_of_time= false;
      block.l_r = time.l_r;

      cl.block_controlx = block.controlx;
      cl.block_controly = block.controly;
    }

    //time.ball_xpos = ball.xpos;
    //time.ball_ypos = ball.ypos;
    time.ball_xdirection = ball.xdirection;

    time.bounce = ball.bounce;
    cl.bounce = ball.bounce;

    collide = cl.detectColliosion();
    cl.out_of_play();

    if (cl.out_of_bound == true) {
      if ((ball.ypos + ball.rad) <= (520 + block.height) && (ball.ypos + ball.rad) >= (520 - block.height)) {
        cl.score -= 1000;
        cl.num_of_hearts--;
        cl.img = new PImage[cl.num_of_hearts];
      }
      time.score = cl.score/3;
      cl.out_of_bound = false;
    }

    if (collide == true) {
      cl.score += 30;
      time.score = cl.score/3;
      ball.bounce = cl.bounce;
      collide = false;
    }

    //update ball position
    cl.ball_xpos = ball.xpos;
    cl.ball_ypos_temp = ball.ypos;
    cl.ball_ypos = ball.ypos;

    cl.block_width = block.width;
    cl.block_height = block.height;
    cl.block_controlx = block.controlx;

    if (cl.num_of_hearts == 0) {
      
        ball.xspeed = 0;
        ball.yspeed = 0;

        current_screen = end_screen;
        level = 1;

        score = time.score;
        cl.score = 0;
        
        time.getStart();
        ball = new Ball();
        block.l_r = 5;
    }
    
    cl.setup();
    cl.draw();
  }
}

class Lives {
  
  
  public void setup(){
    
  }
  
  public void draw(){
    
  }
  
  
}

class Screen {
  ArrayList screenWidgets;
  int screenColor;

  Screen(int in_color) {
    screenWidgets=new ArrayList();
    screenColor=in_color;
  }
  public void add(Widget w) {
    screenWidgets.add(w);
  }

  public void draw() {
    background(screenColor);
    for (int i = 0; i<screenWidgets.size(); i++) {
      Widget aWidget = (Widget)screenWidgets.get(i);
      aWidget.draw();
    }
  }

  public int getEvent(int mx, int my) {
    //System.out.println("MouseX = "+ mx + " MouseY = " + my);
    for (int i = 0; i< screenWidgets.size(); i++) {
      Widget aWidget = (Widget) screenWidgets.get(i);
      //System.out.println("MouseX = "+ mouseX + " MouseY = " + mouseY);
      int event = aWidget.getEvent(mouseX, mouseY);
      if (event != EVENT_NULL) {
        return event;
      }
    }
    return EVENT_NULL;
  }
}

class Timer {
  
  int sec =0;
  int  level=1;
  int next;
  boolean bool, out_of_time;
  
  int score;  
  int ball_speed_cond = 0;

  int ball_xpos;
  int ball_ypos = 0;
  
  int block_controly = 0;

  Ball ball;
  

  int ball_xdirection = 1;
  int bounce = 1;

  int ball_yspeed;
  int ball_xspeed;
  boolean check = true;

  int l_r = 5;
  public void getStart() {
    score = 0;
    sec = 20;
    level = 1;
    bool = true;
    out_of_time = false;
  }
  public void draw() {
    if (bool == true) {
      next = second()+1;
      bool = false;
    }
    int now = second();

    if (now == next) {
      sec--;
      if (next == 59) {
        next = 1;
      }
      else {
        next = second()+1;
      }
    }
    
     //change levels every 5 sec - this is temporary I intend to use 20/30 secs
    if (sec == 0){
      
      //if time-out, go to next level
      //reset time
      sec=20;
      level++;
      out_of_time = true;
      
      ball_speed_cond++;
      check = false;
      
      //See block class
      l_r +=2 ;
      
      /*
       This is probably where the problem lies
       I'm trying to find that spot, where the ball hit s the collision line prefectly
       But I can't seem to get it, I works for some level, but for all
       */
       
       
      if ((ball_ypos % 2) == 0) {
        ball_xspeed += 2;
        ball_yspeed += 2;
      }
      else {
        ball_ypos -= 1;
        ball_xspeed += 2;
        ball_yspeed += 2;
      }
    }
    
    text("Level "+ level, 350, 40);
    //text("Lives: ", 10, 40);    
    //text(score, 170, 40);
    text("Time:", 660, 40);
    text(sec, 820, 40);
  }
}

class Widget {
  int x, y, width, height;
  String label; 
  int event;
  int widgetColor, labelColor;
  PFont widgetFont;
  boolean isTextWidget; 


  public void draw() {
    fill(widgetColor);
    if (mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height && !isTextWidget) {
      stroke(255);
    }
    else {
      stroke(255, 0, 0);
    }
    //rectMode(CENTER);
    rect(x, y, width, height);
  }

  public int getEvent(int mX, int mY) {
    if (mX > x && mX < x+width && mY > y && mY <y+height) {
      return event;
    }
    return EVENT_NULL;
  }
}

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Bounce_Game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
