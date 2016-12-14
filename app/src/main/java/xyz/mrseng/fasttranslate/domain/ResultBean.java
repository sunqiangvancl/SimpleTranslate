package xyz.mrseng.fasttranslate.domain;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by MrSeng on 2016/12/12.
 */

public class ResultBean  implements Serializable{
    public String from;
    public String to;
    public ArrayList<TransResult> trans_result;

    public static class TransResult {
        public String src;
        public String dst;
    }
}
