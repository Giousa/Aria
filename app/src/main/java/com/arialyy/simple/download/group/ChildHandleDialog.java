/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.simple.download.group;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.arialyy.annotations.DownloadGroup;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupTask;
import com.arialyy.simple.R;
import com.arialyy.simple.base.BaseDialog;
import com.arialyy.simple.databinding.DialogSubTaskHandlerBinding;
import com.arialyy.simple.widget.HorizontalProgressBarWithNumber;
import java.util.List;

/**
 * Created by Aria.Lao on 2017/9/5.
 */
@SuppressLint("ValidFragment") public class ChildHandleDialog
    extends BaseDialog<DialogSubTaskHandlerBinding> {
  @Bind(R.id.sub_task) TextView mSub;
  @Bind(R.id.task_group) TextView mGroup;
  @Bind(R.id.pb) HorizontalProgressBarWithNumber mPb;
  private String mGroupName;
  private String mChildName;
  private DownloadEntity mChildEntity;

  public ChildHandleDialog(Context context, String groupAliaName, DownloadEntity childEntity) {
    super(context);
    setStyle(STYLE_NO_TITLE, R.style.Theme_Light_Dialog);
    mChildEntity = childEntity;
    mGroupName = groupAliaName;
    mChildName = childEntity.getFileName();
  }

  @Override protected void init(Bundle savedInstanceState) {
    super.init(savedInstanceState);
    Aria.download(this).register();
    initWidget();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    Aria.download(this).unRegister();
  }

  private void initWidget() {
    mGroup.setText("任务组：" + mGroupName);
    mSub.setText("子任务：" + mChildName);
    mPb.setProgress((int) (mChildEntity.getCurrentProgress() * 100 / mChildEntity.getFileSize()));

    Window window = getDialog().getWindow();
    window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
    WindowManager.LayoutParams p = window.getAttributes();
    p.width = ViewGroup.LayoutParams.MATCH_PARENT;
    window.setAttributes(p);
    window.setWindowAnimations(R.style.dialogStyle);
  }

  @DownloadGroup.onTaskResume void onTaskResume(DownloadGroupTask task) {
    mSub.setText("子任务：" + mChildName + "，状态：下载中");
  }

  @DownloadGroup.onTaskCancel void onTaskCancel(DownloadGroupTask task) {
    mSub.setText("子任务：" + mChildName + "，状态：取消下载");
  }

  @DownloadGroup.onTaskRunning void onTaskRunning(DownloadGroupTask task) {
    mPb.setProgress((int) (mChildEntity.getCurrentProgress() * 100 / mChildEntity.getFileSize()));
  }

  @DownloadGroup.onTaskStop void onTaskStop(DownloadGroupTask task) {
    mSub.setText("子任务：" + mChildName + "，状态：任务停止");
  }

  @DownloadGroup.onTaskComplete void onTaskComplete(DownloadGroupTask task) {
    mSub.setText("子任务：" + mChildName + "，状态：任务完成");
    mPb.setProgress(100);
  }

  @Override protected int setLayoutId() {
    return R.layout.dialog_sub_task_handler;
  }

  @OnClick({ R.id.start, R.id.stop, R.id.cancel }) void onClick(View view) {
    switch (view.getId()) {
      case R.id.start:
        break;
      case R.id.stop:
        break;
      case R.id.cancel:
        break;
    }
  }

  @Override protected void dataCallback(int result, Object obj) {

  }
}