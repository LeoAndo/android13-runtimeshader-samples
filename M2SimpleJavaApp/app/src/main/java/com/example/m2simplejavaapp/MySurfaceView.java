package com.example.m2simplejavaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RuntimeShader;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "MySurfaceView";
    private final SurfaceHolder holder;
    private static final String SkSLShader =
            "uniform half u_time;                                  " +
                    "uniform half u_w;                                     " +
                    "uniform half u_h;                                     " +

                    "float f(vec3 p) {                                     " +
                    "   p.z -= u_time * 10.;                               " +
                    "   float a = p.z * .1;                                " +
                    "   p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));     " +
                    "   return .1 - length(cos(p.xy) + sin(p.yz));         " +
                    "}                                                     " +

                    "half4 main(vec2 fragcoord) {                          " +
                    "   vec3 d = .5 - fragcoord.xy1 / u_h;                 " +
                    "   vec3 p=vec3(0);                                    " +
                    "   for (int i = 0; i < 32; i++) p += f(p) * d;        " +
                    "   return ((sin(p) + vec3(2, 5, 9)) / length(p)).xyz1;" +
                    "}";
    private static final String SkSLShader2 =
            "uniform half u_time;                             " +
                    "uniform half u_w;                                " +
                    "uniform half u_h;                                " +

                    "half4 main(vec2 fragcoord) {                     " +
                    "   vec3 c;" +
                    "   float l;" +
                    "   float z=u_time;" +
                    "   for(int i=0;i<3;i++) {" +
                    "       vec2 p=fragcoord.xy/vec2(u_w,u_h);" +
                    "       vec2 uv=p;" +
                    "       p-=.5;" +
                    "       p.x*=u_w/u_h;" +
                    "       z+=.07;" +
                    "       l=length(p);" +
                    "       uv+=p/l*(sin(z)+1.)*abs(sin(l*9.-z*2.));" +
                    "       c[i]=.01/length(abs(mod(uv,1.)-.5));" +
                    "   }" +
                    "   return half4(c/l,u_time);" +
                    "}";
    private final float timeBase;
    private final Handler handler;
    private final Runnable runnable;

    public MySurfaceView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        holder.addCallback(this);
        timeBase = System.currentTimeMillis();


        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                draw();
                handler.postDelayed(this, 1500);
            }
        };
    }
    /*
    public MySurfaceView(Context context) {
        super(context);
        holder = getHolder();
        holder.addCallback(this);
    }
     */

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.d(TAG, "surfaceChanged() holder: " + holder.hashCode() + " format: " + format + " width: " + width + " height: " + height);
        handler.post(runnable);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated() holder: " + holder.hashCode());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed() holder: " + holder.hashCode());
        handler.removeCallbacks(runnable);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            draw();
        }
        return true;
    }

    private void draw() {
        final Canvas canvas = holder.lockCanvas();
        final float uniformTime = (System.currentTimeMillis() - timeBase) / 1000;
        Log.d(TAG, "draw: timeBase: " + timeBase);
        Log.d(TAG, "draw: uniformTime: " + uniformTime);
        renderFrame(canvas, uniformTime, getWidth(), getHeight());
        holder.unlockCanvasAndPost(canvas);
    }

    private void renderFrame(Canvas canvas, float uniformTime, int canvasWidth, int canvasHeight) {
        Log.d(TAG, "renderFrame: uniformTime: " + uniformTime);
        Log.d(TAG, "renderFrame: canvasWidth: " + canvasWidth);
        Log.d(TAG, "renderFrame: canvasHeight: " + canvasHeight);
        var shader = new RuntimeShader(SkSLShader);
        shader.setFloatUniform("u_time", uniformTime);
        shader.setFloatUniform("u_w", canvasWidth);
        shader.setFloatUniform("u_h", canvasHeight);
        Paint p = new Paint();
        p.setShader(shader);
        canvas.drawRect(0, 0, canvasWidth, canvasHeight, p);
    }
}