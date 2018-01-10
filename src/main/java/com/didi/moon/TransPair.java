package com.didi.moon;

import com.didichuxing.sec.data.dp.util.Rule;

/**
 * Created by lyorall on 2018/1/10.
 */
public class TransPair {
    private String coloumName;
    private Rule transRule;

    public TransPair(String coloumName, Rule transRule) {
        this.coloumName = coloumName;
        this.transRule = transRule;
    }

    public Rule getTransRule() {
        return transRule;
    }

    public void setTransRule(Rule transRule) {
        this.transRule = transRule;
    }

    public String getColoumName() {

        return coloumName;
    }

    public void setColoumName(String coloumName) {
        this.coloumName = coloumName;
    }
}
