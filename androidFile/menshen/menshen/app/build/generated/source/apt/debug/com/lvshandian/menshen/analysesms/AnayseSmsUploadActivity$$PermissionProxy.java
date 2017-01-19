// Generated code. Do not modify!
package com.lvshandian.menshen.analysesms;

import com.zhy.m.permission.*;

public class AnayseSmsUploadActivity$$PermissionProxy implements PermissionProxy<AnayseSmsUploadActivity> {
@Override
 public void grant(AnayseSmsUploadActivity source , int requestCode) {
switch(requestCode) {case 100:source.requestSdcardSuccess();break;}  }
@Override
 public void denied(AnayseSmsUploadActivity source , int requestCode) {
switch(requestCode) {case 100:source.requestSdcardFailed();break;}  }
@Override
 public void rationale(AnayseSmsUploadActivity source , int requestCode) {
switch(requestCode) {}  }
@Override
 public boolean needShowRationale(int requestCode) {
switch(requestCode) {}
return false;  }

}
