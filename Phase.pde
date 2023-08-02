import java.util.Random;

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
    
    void run() {
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
    
    void showNumber(int showingNumber) {
        fill(0);
        textMode(CENTER);
        textSize(60);
        textFont(yeon, 60);
        text(showingNumber, w70 + w30 / 2, h50 / 2);
    }
    
    void showFigures() {
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
    
    void orders(int ordersNumber) {
        int orderIndex = 1; int orderX = 4;
        for (int i = orders.length - 1; i >= 0; i--) {
            if (ordersNumber == 0) {}
            else if (orderIndex > Integer.toString(ordersNumber).length()) {break;}
            else if (showOrders == true) {fill(0); text(orders[i], orderX * w70 / 5, height / 4);}
            orderX -= 1; orderIndex += 1;
            watchers.get(orderX).test(orders[i], orderX);
        }
    }
    
    void updateOrders(int ordersNum) {
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
    
    void checkInput() {
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