package com.mousavi.card.gmail.easyaccount;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import java.util.Map;

public class EasyCRUD {

  private final String accountType;
  private final AccountManager accountManager;

  public EasyCRUD(Context context, String accountType) {
    accountManager = AccountManager.get(context);
    this.accountType = accountType;
  }

  public boolean newAccount(String username, String password, String token, Bundle userData) {
    boolean isCreated;
    Account account = new Account(username, accountType);
    isCreated = accountManager.addAccountExplicitly(account, password, userData);
    accountManager.setAuthToken(account, accountType, token);
    return isCreated;
  }

  public Account getAccount(String username) {
    if (username.isEmpty()) {
      throw new IllegalArgumentException(username + " not available ");
    }
    return new Account(username, accountType);
  }

  public Account[] getAllAccounts() {
    return accountManager.getAccountsByType(accountType);
  }

  public Account[] getAllAccounts(String accountType) {
    return accountManager.getAccountsByType(accountType);
  }

  public boolean isAvailableAccount(String username) {
    for (Account account : getAllAccounts()) {
      if (account.name.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAvailableAccount(String username, String accountType) {
    for (Account account : getAllAccounts(accountType)) {
      if (account.name.equals(username)) {
        return true;
      }
    }
    return false;
  }

  public void removeAccount(Account account) {
    if (VERSION.SDK_INT < VERSION_CODES.M) {
      accountManager.removeAccount(account, null, null);
    } else {
      accountManager.removeAccountExplicitly(account);
    }
  }

  public void updateToken(Account account, String token) {
    accountManager.setAuthToken(account, EasyAccount.AUTH_TOKEN_TYPE, token);
  }

  public void updateUserData(Account account, Map<String, String> userData) {
    for (Map.Entry<String, String> entry : userData.entrySet()) {
      accountManager.setUserData(account, entry.getKey(), entry.getValue());
    }
  }

  public void updateAccount(Account account, String token, Map<String, String> userData) {
    updateToken(account, token);
    updateUserData(account, userData);
  }

  public AccountManager getAccountManager() {
    return accountManager;
  }

  public String getUserData(Account account, String key) {
    return accountManager.getUserData(account, key);
  }
}
