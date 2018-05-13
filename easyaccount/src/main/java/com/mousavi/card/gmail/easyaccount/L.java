package com.mousavi.card.gmail.easyaccount;

import android.util.Log;

class L {
 
  boolean logEnable;

  public L(boolean logEnable) {
    this.logEnable = logEnable;
  }

  void d(String message){
    Log.d("EasyAccountService ---->  ",message);
  }
  void d(String tag,String message){
    Log.d("EasyAccountService ---->  "," "+tag+"  :"+message);
  }

}
