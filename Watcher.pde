import java.util.Timer;
import java.util.TimerTask;

class Watcher {
    int watching; 
    Watcher(int watched) {
        watching = watched;
    }
    
    void test(int newValue, int index) {
        if (watching == newValue) {}
        else if (watching > newValue) {figures.get(index).clear();}
        else {
            for (int i = 0; i < (newValue - watching); i++) {
                this.preChange(newValue, figures.get(index), index);
            }
        }
        watching = newValue;
    }
    
    void preChange(int newValue, ArrayList<Figure> figureGroup, int index) {
        if (newValue == 0) {figureGroup.clear();}   
        else {
            figureGroup.add(new Figure(index, figureGroup.size()));
        }
    }
}