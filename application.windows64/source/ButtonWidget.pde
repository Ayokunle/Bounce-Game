class ButtonWidget extends Widget {

  ButtonWidget(int x, int y, int width, int height, 
  String label, color widgetColor, PFont font, int event ){

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

