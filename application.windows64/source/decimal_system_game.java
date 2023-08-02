import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Random; 
import java.util.Timer; 
import java.util.TimerTask; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class decimal_system_game extends PApplet {

int num = 0;
int m, c, d, u;
int orders[] = {m, c, d, u};
boolean max;
boolean showMessage;
float w70, w30, h50;
int yellow = color(243, 182, 31);
int green = color(83, 221, 108);
int gray = color(98, 124, 133);
int red = color(255, 22, 84);
int black = color(0);
int messageColor;
PFont yeon, bree;
PImage cake, bundle, shulker, chest, cakeStore, bundleStore, shulkerStore, chestStore;   
ArrayList<Button> buttons = new ArrayList<Button>();
ArrayList<Watcher> watchers = new ArrayList<Watcher>();
ArrayList<ArrayList<Figure>> figures = new ArrayList<ArrayList<Figure>>();
ArrayList<Phase> phases = new ArrayList<Phase>();
Phase currentPhase;
String message;

public void setup() {
    
    orientation(LANDSCAPE);
    
    w70 = 0.7f * width;
    w30 = 0.3f * width;
    h50 = 0.5f * height;
    
    yeon = createFont("YeonSung-Regular.ttf", 40);
    bree = createFont("BreeSerif-Regular.ttf", 40);
    
    for (int i = 0; i < 6; i++) {
        Button button = new Button();
        buttons.add(button);   
        
        if (i < 4) {
            Watcher watcher = new Watcher(orders[i]);
            watchers.add(watcher);
            figures.add(new ArrayList<Figure>());
        }
    }
    
    cake = loadImage("cake.png");
    bundle = loadImage("bundle.png");
    shulker = loadImage("shulkerbox-300-464.png");
    chest = loadImage("chest.png");
    
    cakeStore = loadImage("cakeStore.png");
    bundleStore = loadImage("bundleStore.png");
    shulkerStore = loadImage("shulkerStore.png");
    chestStore = loadImage("chestStore.png");
}

public void draw() {
    //background
    background(yellow);
    
    //number rect
    fill(green);
    noStroke();
    rect(w70, 0, w30, h50);
    
    //button rect
    fill(0); rect(w70, h50, w30, h50);
    //buttons
    for (Button b : buttons) {
        b.show();   
    }
    
    if (phases.size() == 0) {phases.add(new Phase());}
    currentPhase = phases.get(phases.size() - 1);
    currentPhase.run();
    
    if (showMessage == true) {
        fill(messageColor); textSize(w30 * 0.1f); 
        text(message, w70 + w30 / 2, h50 / 2 - 0.3f * h50);}
}

public void mousePressed() {
    //checks if a button was clicked
    for (Button b : buttons) {
        b.checkClick(mouseX, mouseY);
    }
}
class Button {
    float buttonWidth = w30 / 2.5f, buttonHeight = h50 / 2.5f, buttonRadius = 0.1f * w30;
    float buttonXPos, buttonYPos;
    float captionSize = 40.0f;
    PFont captionFont = bree;
    String caption = "+", subCaption;
    int buttonColor;
    int defaultColor = gray;
    int targetColor = yellow;
    boolean isClicked;
    float colorTransition = 0.0f;
    
    Button() { 
        switch(buttons.size()) {
            case 0:
                buttonXPos = w70 + w30 / 4;
                buttonYPos = h50 + h50 / 4;
                caption += "1";
                subCaption = "U";
                break;
            case 1:
                buttonXPos = w70 + 3 * w30 / 4;    
                buttonYPos = h50 + h50 / 4;
                caption += "10";
                subCaption = "D";
                break;
            case 2:
                buttonXPos = w70 + w30 / 4;
                buttonYPos = h50 + 3 * h50 / 4;
                caption += "100";
                subCaption = "C";
                break;
            case 3:
                buttonXPos = w70 + 3 * w30 / 4;
                buttonYPos = h50 + 3 * h50 / 4;
                caption += "1000";
                subCaption = "M";
                break;
            case 4:
                buttonXPos = w70 + w30 / 4;
                buttonYPos = h50 - 0.2f * h50;
                caption = "RESET";  
                captionSize = 0.7f * captionSize;
                captionFont = yeon;
                buttonWidth = 0.45f * w30;
                buttonHeight = 0.23f * w30;
                defaultColor = red;
                targetColor = color(255, 22, 200);
                buttonRadius = 0.05f * w30; 
                break;
            case 5:
                buttonXPos = w70 + 3 * w30 / 4;
                buttonYPos = h50 - 0.2f * h50;
                caption = "CONFIRM";
                captionSize = 0.7f * captionSize;
                captionFont = yeon;
                buttonWidth = 0.45f * w30;
                buttonHeight = 0.23f * w30;
                defaultColor = yellow;
                targetColor = gray;
                buttonRadius = 0.05f * w30; 
                break;
        }
        buttonColor = defaultColor;
    }
    
    public void show() {   
        if (isClicked == true) {
            if (colorTransition > 1.0f) {
                isClicked = false;
            }
            else {
                buttonColor = lerpColor(defaultColor, targetColor, colorTransition); 
                colorTransition += 0.3f;}
        }
        else { 
            if (0.0f < colorTransition) {
                buttonColor = lerpColor(defaultColor, targetColor, colorTransition); colorTransition -= 0.1f;
            }
        }
        
        
        rectMode(CENTER); 
        fill(buttonColor); rect(buttonXPos, buttonYPos, buttonWidth, buttonHeight, buttonRadius);
        
        textMode(CENTER); textSize(captionSize); textAlign(CENTER, BOTTOM); textFont(captionFont);
        fill(0); text(caption, buttonXPos, buttonYPos + captionSize / 1.5f);
        
        if (this.subCaption != null) {
            textSize(0.5f * captionSize); textAlign(CENTER, CENTER);
            fill(buttonColor); text(subCaption, buttonXPos - buttonWidth / 2, buttonYPos - buttonHeight / 2);
        } 
        rectMode(CORNER);
    }
    
    public void checkClick(int touchX, int touchY) {
        if (buttonXPos - buttonWidth / 2 < touchX && touchX < buttonXPos + buttonWidth / 2 && buttonYPos - buttonHeight / 2 < touchY && touchY < buttonYPos + buttonHeight / 2) {
            if (caption == "RESET") {num = 0; max = false;} 
            else if (caption == "CONFIRM") {
                currentPhase.checkInput();
                showMessage = true;
            }
            else if (caption == "CONTINUE") {
                for (int i = 0; i < figures.size(); i++) {
                    figures.get(i).clear();
                    orders[i] = 0;
                }
                num = 0;
                phases.add(new Phase()); 
                currentPhase = phases.get(phases.size() - 1);
                showMessage = false;
                caption = "CONFIRM";
            }
            else {
                int sum = Integer.parseInt(caption.replace("+", ""));
                num += sum;
                if (num >= 10000) { 
                    num = 10000;
                    max = true;
                }
            }
            //Phase phase = phases.get(phases.size() - 1);
            if (currentPhase.showFigures == true) {currentPhase.updateOrders(num);}
            isClicked = true;
        }
    }
}
class Figure {
    PImage figureImage, storeImage;
    int rectColor;
    int imageWidth, imageHeight, xLimit;
    float xPos, yPos, yGoal;
    float ySpeed = 1;
    String order;
    int figIndex;
    float storeX, storeY, storeWidth, storeHeight, displayWidth; 
    int transparency;
    
    Figure(int orderPos, int index) {
        figIndex = index;
        transparency = 128;
        switch(orderPos) {
            case 0 : 
                order = "M";
                figureImage = chest;
                imageWidth = Math.round(w70 / 12);
                imageHeight = Math.round(w70 / 12);
                storeImage = chestStore;
                storeX = w70 / 2;
                storeY = h50 * 1.1f;
                storeWidth = w70;
                storeHeight = w70 / 23.33f;  
                yGoal = storeY - storeHeight - imageHeight / 2;
                transparency = 0;
                displayWidth = storeWidth;
                xLimit = imageWidth;
                break;
            case 2 :
                order = "D";
                figureImage = bundle;
                imageWidth = 60;
                imageHeight = 60;
                storeImage = bundleStore;
                storeX =  w70 * orderPos / 4;
                storeY = height - imageHeight * 2;
                storeWidth = Math.round(271 / 1.3f);
                storeHeight = Math.round(383 / 1.3f);
                yGoal = storeY;
                displayWidth = storeWidth;
                xLimit = 0;
                break;
            case 3 : 
                order = "U";
                figureImage = cake;
                imageWidth = 70; 
                imageHeight = 70;
                storeImage = cakeStore;
                storeX =  w70 * orderPos / 4;
                storeY = height - imageHeight * 2;
                storeWidth = Math.round(imageWidth * 4.5f);
                storeHeight = Math.round(imageHeight * 4.5f);
                yGoal = storeY - storeHeight * 0.1f;
                displayWidth = storeWidth;
                xLimit = 0;
                break;
            case 1 : 
                order = "C";
                figureImage = shulker;
                imageWidth = Math.round(225 / 4);
                imageHeight = Math.round(364 / 4);
                storeImage = shulkerStore;
                storeX =  w70 * orderPos / 4;
                storeY = height - imageHeight * 1.5f;
                storeWidth = Math.round(194 * 1.3f);
                storeHeight = Math.round(251 * 1.3f);
                yGoal = storeY - imageHeight / 3;
                displayWidth = storeWidth * 0.85f;
                xLimit = 0;
                break;
        }
        if (order == "M") {
            xPos = storeX + imageWidth / 2 - displayWidth / 2 + index * (displayWidth - xLimit) / 9;
            yPos = 0 - imageHeight / 2;
            
        } else {
            if (index < 5) {
                xPos = storeX + imageWidth / 2 - displayWidth / 2 + index * (displayWidth - xLimit) / 5;
                yPos = 0 - imageHeight / 2;
            } else {
                xPos = storeX + imageWidth / 2 - displayWidth / 2 + (index - 5) * (displayWidth) / 4;
                yPos = 0 - imageHeight;
            }
        }
    }       
    
    public void show() {     
        if (yGoal - yPos < 0) {} 
        else {
            yPos += ySpeed;
            ySpeed += 0.05f * imageHeight;
        }
        imageMode(CENTER);
        image(figureImage, xPos, yPos, imageWidth, imageHeight);
    }   
    
    public void showFigureStore(String order) {
        imageMode(CENTER);
        tint(255, transparency);
        image(storeImage, storeX, storeY, storeWidth, storeHeight);
        fill(black);
        if (order == "M") {
            text(order, w70 / 2, 4 * height / 10);
        } else {
            text(order, storeX, 6 * height / 9);
        }
        tint(255, 255);
    }
}


class Phase {
    Random rand = new Random();
    int type; 
    int numSnap;
    boolean showOrders, showFigures;
    boolean areOrdersUpdated = false;
    
    Phase() {
        type = rand.nextInt(2);
        numSnap = rand.nextInt(9999);
    }
    
    public void run() {
        switch(type) {
            case 0:
                //Display-number-only-mode
                showOrders = false;
                showFigures = true;
                orders(num);
                showNumber(numSnap);
                showFigures();
                break;
            case 1:
                //Display-figures-only-mode
                showOrders = false;
                showFigures = false;
                orders(num);
                if (areOrdersUpdated == false) {updateOrders(numSnap); areOrdersUpdated = true;}
            showNumber(num);
            showFigures();
            break;
        }        
    }
    
    public void showNumber(int showingNumber) {
        fill(0);
        textMode(CENTER);
        textSize(60);
        textFont(yeon, 60);
        text(showingNumber, w70 + w30 / 2, h50 / 2);
    }
    
    public void showFigures() {
        for (ArrayList < Figure > figureGroup : figures) {
            if (figureGroup.isEmpty() == true) {}
            else{
                figureGroup.get(0).showFigureStore(figureGroup.get(0).order);
                for (Figure figure : figureGroup) {
                    figure.show();
                }
            }
        }
    }
    
    public void orders(int ordersNumber) {
        int orderIndex = 1; int orderX = 4;
        for (int i = orders.length - 1; i >= 0; i--) {
            if (ordersNumber == 0) {}
            else if (orderIndex > Integer.toString(ordersNumber).length()) {break;}
            else if (showOrders == true) {fill(0); text(orders[i], orderX * w70 / 5, height / 4);}
            orderX -= 1; orderIndex += 1;
            watchers.get(orderX).test(orders[i], orderX);
        }
    }
    
    public void updateOrders(int ordersNum) {
        String numString = Integer.toString(ordersNum);
        switch(numString.length()) {
            case 1 : numString = "000" + numString; break;
            case 2 : numString = "00" + numString; break;
            case 3 : numString = "0" + numString; break;
            case 4 : break;
        }   
        
        for (int i = 0; i < orders.length; i++) {
            int c = Character.getNumericValue((numString.charAt(i)));
            orders[i] = c;
        }   
        if (max == true) {orders[0] = 10;}
    }
    
    public void checkInput() {
        Button checkButton = buttons.get(buttons.size() - 1);
        if (num == numSnap) {
            message = "CONGRATULATIONS!!!";
            messageColor = yellow;
            checkButton.caption = "CONTINUE";
        } else {
            message = "Not there yet...";
            messageColor = black;
        }
    }
}



class Watcher {
    int watching; 
    Watcher(int watched) {
        watching = watched;
    }
    
    public void test(int newValue, int index) {
        if (watching == newValue) {}
        else if (watching > newValue) {figures.get(index).clear();}
        else {
            for (int i = 0; i < (newValue - watching); i++) {
                this.preChange(newValue, figures.get(index), index);
            }
        }
        watching = newValue;
    }
    
    public void preChange(int newValue, ArrayList<Figure> figureGroup, int index) {
        if (newValue == 0) {figureGroup.clear();}   
        else {
            figureGroup.add(new Figure(index, figureGroup.size()));
        }
    }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "decimal_system_game" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
