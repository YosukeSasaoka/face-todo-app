/*
 * Copyright 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.camera2basic;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class CameraActivity extends AppCompatActivity {
    TextView text;
    Camera2BasicFragment camera2;
    private static Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // モニタ解像度の取得
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        size = new Point();
        display.getRealSize(size);
        System.out.println(size.x + ":" + size.y);
        // TextViewをViewに追加
        text = findViewById(R.id.todo);
        text.setText("");
        if (null == savedInstanceState) {
            camera2 = Camera2BasicFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, camera2)
                    .commit();
        }
        // todo,x,yの更新処理
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    text.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(camera2.getTodo());
                            text.setTranslationX(camera2.getX());
                            text.setTranslationY(camera2.getY());
                        }
                    });
                }
            }
        }).start();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float pointX = event.getX();
        float pointY = event.getY();
        text.setTranslationX(pointX);
        text.setTranslationY(pointY);
        return true;
    }
    public static Point getSize() {
        return size;
    }
    public static int getWidth() {
        return size.x;
    }
    public static int getHeight(){
        return size.y;
    }
}
