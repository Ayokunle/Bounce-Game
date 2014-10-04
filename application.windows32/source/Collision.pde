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


  boolean detectColliosion() {

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

  void out_of_play() {
    if ((ball_ypos + ball_rad) >= (520 + block_height)) {
      out_of_bound = true;
    }
    
  }
  
  void setup(){
    img = new PImage [num_of_hearts];
  }
  
  void draw(){
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

