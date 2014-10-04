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


  void display() {
    ellipseMode(CENTER);
    ellipse(xpos, ypos, 410, 40);
  }

  void move() {
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

