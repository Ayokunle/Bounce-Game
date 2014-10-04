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

  void display() {
    rectMode(CENTER);
    rect(controlx, controly, width, height);
  }

  void Control() {
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

