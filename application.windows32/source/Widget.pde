class Widget {
  int x, y, width, height;
  String label; 
  int event;
  color widgetColor, labelColor;
  PFont widgetFont;
  boolean isTextWidget; 


  void draw() {
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

  int getEvent(int mX, int mY) {
    if (mX > x && mX < x+width && mY > y && mY <y+height) {
      return event;
    }
    return EVENT_NULL;
  }
}

