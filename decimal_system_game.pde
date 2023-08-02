int num = 0;
int m, c, d, u;
int orders[] = {m, c, d, u};
boolean max;
boolean showMessage;
float w70, w30, h50;
color yellow = color(243, 182, 31);
color green = color(83, 221, 108);
color gray = color(98, 124, 133);
color red = color(255, 22, 84);
color black = color(0);
color messageColor;
PFont yeon, bree;
PImage cake, bundle, shulker, chest, cakeStore, bundleStore, shulkerStore, chestStore;   
ArrayList<Button> buttons = new ArrayList<Button>();
ArrayList<Watcher> watchers = new ArrayList<Watcher>();
ArrayList<ArrayList<Figure>> figures = new ArrayList<ArrayList<Figure>>();
ArrayList<Phase> phases = new ArrayList<Phase>();
Phase currentPhase;
String message;

void setup() {
    fullScreen();
    orientation(LANDSCAPE);
    
    w70 = 0.7 * width;
    w30 = 0.3 * width;
    h50 = 0.5 * height;
    
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

void draw() {
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
        fill(messageColor); textSize(w30 * 0.1); 
        text(message, w70 + w30 / 2, h50 / 2 - 0.3 * h50);}
}

void mousePressed() {
    //checks if a button was clicked
    for (Button b : buttons) {
        b.checkClick(mouseX, mouseY);
    }
}