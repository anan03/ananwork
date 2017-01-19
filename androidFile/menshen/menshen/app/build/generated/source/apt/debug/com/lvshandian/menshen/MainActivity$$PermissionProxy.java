// Generated code. Do not modify!
package com.lvshandian.menshen;

import com.zhy.m.permission.*;

public class MainActivity$$PermissionProxy implements PermissionProxy<MainActivity> {
@Override
 public void grant(MainActivity source , int requestCode) {
switch(requestCode) {case 300:source.requestSdcardSuccess();break;}  }
@Override
 public void denied(MainActivity source , int requestCode) {
switch(requestCode) {case 300:source.requestSdcardFailed();break;}  }
@Override
 public void rationale(MainActivity source , int requestCode) {
switch(requestCode) {}  }
@Override
 public boolean needShowRationale(int requestCode) {
switch(requestCode) {}
return false;  }

}
