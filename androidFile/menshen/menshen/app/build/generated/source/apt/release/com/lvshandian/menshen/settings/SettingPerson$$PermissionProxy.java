// Generated code. Do not modify!
package com.lvshandian.menshen.settings;

import com.zhy.m.permission.*;

public class SettingPerson$$PermissionProxy implements PermissionProxy<SettingPerson> {
@Override
 public void grant(SettingPerson source , int requestCode) {
switch(requestCode) {case 2001:source.requestSdcardSuccess();break;case 2002:source.requeststorage();break;}  }
@Override
 public void denied(SettingPerson source , int requestCode) {
switch(requestCode) {case 2001:source.requestSdcardFailed();break;case 2002:source.requesttoage();break;}  }
@Override
 public void rationale(SettingPerson source , int requestCode) {
switch(requestCode) {}  }
@Override
 public boolean needShowRationale(int requestCode) {
switch(requestCode) {}
return false;  }

}
