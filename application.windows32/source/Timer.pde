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
  void getStart() {
    score = 0;
    sec = 20;
    level = 1;
    bool = true;
    out_of_time = false;
  }
  void draw() {
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

