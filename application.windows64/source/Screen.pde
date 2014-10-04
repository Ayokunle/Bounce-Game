class Screen {
  ArrayList screenWidgets;
  color screenColor;

  Screen(color in_color) {
    screenWidgets=new ArrayList();
    screenColor=in_color;
  }
  void add(Widget w) {
    screenWidgets.add(w);
  }

  void draw() {
    background(screenColor);
    for (int i = 0; i<screenWidgets.size(); i++) {
      Widget aWidget = (Widget)screenWidgets.get(i);
      aWidget.draw();
    }
  }

  int getEvent(int mx, int my) {
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

