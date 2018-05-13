package com.mousavi.card.gmail.easyaccount;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Arrays;

final class EasyAccountAuthenticator extends AbstractEasyAccountAuthenticator {

  private String authTokenType;
  private String accountType;
  private Class loginClass;
  private L l;
  private Intent tokenIntent;
  private Service service;

  EasyAccountAuthenticator(Service service, Class loginClass, L l, Intent tokenIntent) {
    super(service);
    this.loginClass = loginClass;
    this.l = l;
    this.tokenIntent = tokenIntent;
    this.service = service;
  }

  @Override
  public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
      String[] requiredFeatures, Bundle options) {
    if (loginClass != null) {
      final Intent intent = new Intent(service, loginClass);
      intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
      l.d("addAccount", intentToString(intent));
      final Bundle bundle = new Bundle();
      bundle.putParcelable(AccountManager.KEY_INTENT, intent);
      return bundle;
    }
    return null;
  }

  @Override
  public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) {
    Bundle bundle = new Bundle();
    if (this.authTokenType.equals(authTokenType)) {
      AccountManager am = AccountManager.get(service);
      String authToken = am.peekAuthToken(account, authTokenType);
      if (authToken != null) {
        l.d("authToken", authToken);
      }
      if (TextUtils.isEmpty(authToken)) {
        l.d("auth token is empty");
        final Intent intent = new Intent(service, loginClass);
        am.setAuthToken(account, this.authTokenType, null);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, account.name);
        if (tokenIntent != null) {
          intent.putExtras(tokenIntent);
        }
        l.d("startActivity(" + loginClass.getSimpleName() + ")", "intent : " + intentToString(intent));
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
      }
      bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
      bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
      bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);

      l.d("sent token", "intent : " + bundleToString(bundle));
      return bundle;
    }
    l.d("invalid authTokenType\n"
        + "1: " + authTokenType + "\n"
        + "2: " + this.authTokenType);
    bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
    return bundle;
  }

  public void setAuthTokenType(String authTokenType) {
    this.authTokenType = authTokenType;
  }

  public String getAuthTokenType() {
    return authTokenType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountType() {
    return accountType;
  }

  private String intentToString(Intent intent) {
    if (intent == null) {
      return null;
    }

    return intent.toString() + " " + bundleToString(intent.getExtras());
  }

  private String bundleToString(Bundle bundle) {
    StringBuilder out = new StringBuilder("Bundle[");

    if (bundle == null) {
      out.append("null");
    } else {
      boolean first = true;
      for (String key : bundle.keySet()) {
        if (!first) {
          out.append(", ");
        }

        out.append(key).append('=');

        Object value = bundle.get(key);

        if (value instanceof int[]) {
          out.append(Arrays.toString((int[]) value));
        } else if (value instanceof byte[]) {
          out.append(Arrays.toString((byte[]) value));
        } else if (value instanceof boolean[]) {
          out.append(Arrays.toString((boolean[]) value));
        } else if (value instanceof short[]) {
          out.append(Arrays.toString((short[]) value));
        } else if (value instanceof long[]) {
          out.append(Arrays.toString((long[]) value));
        } else if (value instanceof float[]) {
          out.append(Arrays.toString((float[]) value));
        } else if (value instanceof double[]) {
          out.append(Arrays.toString((double[]) value));
        } else if (value instanceof String[]) {
          out.append(Arrays.toString((String[]) value));
        } else if (value instanceof CharSequence[]) {
          out.append(Arrays.toString((CharSequence[]) value));
        } else if (value instanceof Parcelable[]) {
          out.append(Arrays.toString((Parcelable[]) value));
        } else if (value instanceof Bundle) {
          out.append(bundleToString((Bundle) value));
        } else {
          out.append(value);
        }

        first = false;
      }
    }

    out.append("]");
    return out.toString();
  }
}
