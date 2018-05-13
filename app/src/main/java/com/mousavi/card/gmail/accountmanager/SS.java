package com.mousavi.card.gmail.accountmanager;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.mousavi.card.gmail.easyaccount.EasyAccountService;

public class SS extends Service {

  @Nullable
  @Override
  public IBinder onBind(Intent ibinderIntent) {
    EasyAccountService easyAccount = EasyAccountService.newBuilder(this)
        .accountType("com.mousavi.card.gmail.accountmanager")
        .destinationClass(MainActivity.class)
        .enableLogger(true)
        .build();
    return easyAccount.getIBinder();
  }
}
