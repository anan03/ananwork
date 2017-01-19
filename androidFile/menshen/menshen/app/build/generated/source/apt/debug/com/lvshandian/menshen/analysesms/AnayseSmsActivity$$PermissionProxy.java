// Generated code. Do not modify!
package com.lvshandian.menshen.analysesms;

import com.zhy.m.permission.*;

public class AnayseSmsActivity$$PermissionProxy implements PermissionProxy<AnayseSmsActivity> {
@Override
 public void grant(AnayseSmsActivity source , int requestCode) {
switch(requestCode) {case 201:source.requestSdcardSuccess();break;}  }
@Override
 public void denied(AnayseSmsActivity source , int requestCode) {
switch(requestCode) {case 201:source.requestSdcardFailed();break;}  }
@Override
 public void rationale(AnayseSmsActivity source , int requestCode) {
switch(requestCode) {}  }
@Override
 public boolean needShowRationale(int requestCode) {
switch(requestCode) {}
return false;  }

}
