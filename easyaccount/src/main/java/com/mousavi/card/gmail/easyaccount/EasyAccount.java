package com.mousavi.card.gmail.easyaccount;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EasyAccount {

  private final EasyAccountAuthenticator authenticator;

  private EasyAccount(Builder builder) {
    String accountType = builder.accountType;
    String authTokenType = builder.authTokenType;
    Class loginClass = builder.loginClass;
    L l = new L(builder.enable);
    Intent tokenIntent = builder.tokenIntent;
    Service service = builder.service;
    authenticator = new EasyAccountAuthenticator(service, loginClass, l, tokenIntent);
    authenticator.setAccountType(accountType);
    authenticator.setAuthTokenType(authTokenType);
  }

  public IBinder getIBinder() {
    return authenticator.getIBinder();
  }

  public static Builder newBuilder(Service service) {
    if (service == null) {
      throw new NullPointerException("service is null");
    }
    return new Builder(service);
  }

  public static final class Builder {

    private String accountType = "com.mousavi.card.gmail.account";
    private String authTokenType = "FullAccess";
    private Class loginClass;
    private boolean enable;
    private Intent tokenIntent;
    private Service service;

    private Builder(Service service) {
      this.service = service;
      AccountManager accountManager = AccountManager.get(service);
    }

    public Builder accountType(String accountType) {
      this.accountType = accountType;
      return this;
    }

    public Builder destinationClass(Class loginClass) {
      this.loginClass = loginClass;
      return this;
    }

    public Builder enableLogger(boolean enable) {
      this.enable = enable;
      return this;
    }

    public Builder tokenIntent(Intent tokenIntent) {
      this.tokenIntent = tokenIntent;
      return this;
    }

    public EasyAccount build() {
      return new EasyAccount(this);
    }
  }


}
