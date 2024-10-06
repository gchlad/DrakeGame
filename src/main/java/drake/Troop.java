package drake;

import java.util.List;

public class Troop {
    private final String name;
    private final Offset2D aversPivot;
    private final Offset2D reversPivot;

    private final List<TroopAction> aversActions;
    private final List<TroopAction> reversActions;

    public Troop(String name, Offset2D aversPivot, Offset2D reversPivot, List<TroopAction> aversActions, List<TroopAction> reversActions){
        this.name = name;
        this.aversPivot = aversPivot;
        this.reversPivot = reversPivot;
        this.aversActions = aversActions;
        this.reversActions = reversActions;
    }

    // both pivots same value
    public Troop(String name, Offset2D pivot, List<TroopAction> aversActions, List<TroopAction> reversActions){
        this(name, pivot, pivot, aversActions, reversActions);
    }

    // both pivots value [1, 1]
    public Troop(String name, List<TroopAction> aversActions, List<TroopAction> reversActions){
        this(name, new Offset2D(1, 1), aversActions, reversActions);
        //this(name, new Offset2D(1, 1), new Offset2D(1, 1), aversActions, reversActions);
    }

    public String name(){
        return this.name;
    }

    public Offset2D pivot(TroopFace face){
        return face == TroopFace.AVERS ? aversPivot : reversPivot;
    }

    public List<TroopAction> actions(TroopFace face) {
        return face == TroopFace.AVERS ? aversActions : reversActions;
    }
}
