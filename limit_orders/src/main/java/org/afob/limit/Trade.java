package org.afob.limit;

public enum Trade {
    Buy ('B'),
    Sell ('S');

    private final Character trade;

    Trade(Character trade){
        this.trade = trade;
    }

    Character getTrade(){
        return this.trade;
    }

}
