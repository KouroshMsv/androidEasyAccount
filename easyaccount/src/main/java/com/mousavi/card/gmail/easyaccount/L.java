package com.mousavi.card.gmail.easyaccount;

import android.util.Log;

class L {

  private final boolean logEnable;

  L(boolean logEnable) {
    this.logEnable = logEnable;
  }

  void d(String message) {
    if (logEnable) {
      Log.d("EasyAccount ---->  ", message);
    }
  }

  void d(String tag, String message) {
    if (logEnable) {
      Log.d("EasyAccount ---->  ", " " + tag + "  :" + message);
    }
  }

}
