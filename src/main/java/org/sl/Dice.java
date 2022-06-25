package org.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Dice {

    private final List<Integer> values;
    private final int size;

    public Dice(int min, int max){
        values = new ArrayList<>();
        for(int i=min; i<=max; i++) {
            values.add(i);
        }
        size = values.size();
    }

    public Dice(List<Integer> values){
        this.values = values;
        this.size = values.size();
    }

    public Integer roll(){
        return values.get( ThreadLocalRandom.current().nextInt(0, size) );
    }
}
