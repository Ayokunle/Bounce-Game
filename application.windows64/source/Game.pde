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

  void draw() {
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

