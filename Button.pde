class Button {
    float buttonWidth = w30 / 2.5, buttonHeight = h50 / 2.5, buttonRadius = 0.1 * w30;
    float buttonXPos, buttonYPos;
    float captionSize = 40.0;
    PFont captionFont = bree;
    String caption = "+", subCaption;
    color buttonColor;
    color defaultColor = gray;
    color targetColor = yellow;
    boolean isClicked;
    float colorTransition = 0.0;
    
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
                buttonYPos = h50 - 0.2 * h50;
                caption = "RESET";  
                captionSize = 0.7 * captionSize;
                captionFont = yeon;
                buttonWidth = 0.45 * w30;
                buttonHeight = 0.23 * w30;
                defaultColor = red;
                targetColor = color(255, 22, 200);
                buttonRadius = 0.05 * w30; 
                break;
            case 5:
                buttonXPos = w70 + 3 * w30 / 4;
                buttonYPos = h50 - 0.2 * h50;
                caption = "CONFIRM";
                captionSize = 0.7 * captionSize;
                captionFont = yeon;
                buttonWidth = 0.45 * w30;
                buttonHeight = 0.23 * w30;
                defaultColor = yellow;
                targetColor = gray;
                buttonRadius = 0.05 * w30; 
                break;
        }
        buttonColor = defaultColor;
    }
    
    void show() {   
        if (isClicked == true) {
            if (colorTransition > 1.0) {
                isClicked = false;
            }
            else {
                buttonColor = lerpColor(defaultColor, targetColor, colorTransition); 
                colorTransition += 0.3;}
        }
        else { 
            if (0.0 < colorTransition) {
                buttonColor = lerpColor(defaultColor, targetColor, colorTransition); colorTransition -= 0.1;
            }
        }
        
        
        rectMode(CENTER); 
        fill(buttonColor); rect(buttonXPos, buttonYPos, buttonWidth, buttonHeight, buttonRadius);
        
        textMode(CENTER); textSize(captionSize); textAlign(CENTER, BOTTOM); textFont(captionFont);
        fill(0); text(caption, buttonXPos, buttonYPos + captionSize / 1.5);
        
        if (this.subCaption != null) {
            textSize(0.5 * captionSize); textAlign(CENTER, CENTER);
            fill(buttonColor); text(subCaption, buttonXPos - buttonWidth / 2, buttonYPos - buttonHeight / 2);
        } 
        rectMode(CORNER);
    }
    
    void checkClick(int touchX, int touchY) {
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