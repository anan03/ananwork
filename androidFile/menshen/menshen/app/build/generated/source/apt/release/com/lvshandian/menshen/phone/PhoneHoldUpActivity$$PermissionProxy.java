// Generated code. Do not modify!
package com.lvshandian.menshen.phone;

import com.zhy.m.permission.*;

public class PhoneHoldUpActivity$$PermissionProxy implements PermissionProxy<PhoneHoldUpActivity> {
@Override
 public void grant(PhoneHoldUpActivity source , int requestCode) {
switch(requestCode) {case 202:source.requestSdcardSuccess();break;}  }
@Override
 public void denied(PhoneHoldUpActivity source , int requestCode) {
switch(requestCode) {case 202:source.requestSdcardFailed();break;}  }
@Override
 public void rationale(PhoneHoldUpActivity source , int requestCode) {
switch(requestCode) {}  }
@Override
 public boolean needShowRationale(int requestCode) {
switch(requestCode) {}
return false;  }

}
