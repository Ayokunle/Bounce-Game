Game g;
Screen start_screen, current_screen, game_screen, end_screen;
PFont stdFont;
ButtonWidget game_name, play, exit;

Timer time;
Ball ball;
Block block;
Collision cl;
int score;

void setup() {
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

void draw() {
  
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

void mousePressed() {
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

