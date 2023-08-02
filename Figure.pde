class Figure {
    PImage figureImage, storeImage;
    color rectColor;
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
                storeY = h50 * 1.1;
                storeWidth = w70;
                storeHeight = w70 / 23.33;  
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
                storeWidth = Math.round(271 / 1.3);
                storeHeight = Math.round(383 / 1.3);
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
                storeWidth = Math.round(imageWidth * 4.5);
                storeHeight = Math.round(imageHeight * 4.5);
                yGoal = storeY - storeHeight * 0.1;
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
                storeY = height - imageHeight * 1.5;
                storeWidth = Math.round(194 * 1.3);
                storeHeight = Math.round(251 * 1.3);
                yGoal = storeY - imageHeight / 3;
                displayWidth = storeWidth * 0.85;
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
    
    void show() {     
        if (yGoal - yPos < 0) {} 
        else {
            yPos += ySpeed;
            ySpeed += 0.05 * imageHeight;
        }
        imageMode(CENTER);
        image(figureImage, xPos, yPos, imageWidth, imageHeight);
    }   
    
    void showFigureStore(String order) {
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