package com.mousavi.card.gmail.easyaccount;

import android.util.Log;

class L {
 
  boolean logEnable;

  public L(boolean logEnable) {
    this.logEnable = logEnable;
  }

  void d(String message){
    Log.d("EasyAccount ---->  ",message);
  }
  void d(String tag,String message){
    Log.d("EasyAccount ---->  "," "+tag+"  :"+message);
  }

}
