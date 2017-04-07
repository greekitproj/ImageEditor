package com.example.android.imagetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stevens on 4/6/2017.
 */

public class CanvasView extends ImageView{

    // used to determine whether user moved a finger enough to draw again
    private static final float TOUCH_TOLERANCE = 10;

    private Bitmap bitmap, picBitmap; // drawing area for displaying or saving
    private Canvas bitmapCanvas; // used to to draw on the bitmap
    private Paint paintScreen; // used to draw bitmap onto screen
    private Paint paintLine; // used to draw lines onto bitmap
    private Paint paintText;
    private Drawable d;

    // Maps of current Paths being drawn and Points in those Paths
    private final Map<Integer, Path> pathMap = new HashMap<>();
    private final Map<Integer, Point> previousPointMap =  new HashMap<>();

    // CanvasView constructor initializes the CanvasView
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs); // pass context to View's constructor

        paintScreen = new Paint(); // used to display bitmap onto screen

        // set the initial display settings for the painted line
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // smooth edges of drawn line
        paintLine.setColor(Color.BLACK); // default color is black
        paintLine.setStyle(Paint.Style.STROKE); // solid line
        paintLine.setStrokeWidth(5); // set the default line width
        paintLine.setStrokeCap(Paint.Cap.ROUND); // rounded line ends

//        d = (BitmapDrawable)this.getDrawable();
//        d.setBounds(10, 10, 30, 30);


//        paintText = new Paint();
//        paintText.setColor(Color.WHITE);
//        paintText.setStyle(Paint.Style.FILL);
//
//        paintText.setTextSize(20);

    }


    // creates Bitmap and Canvas based on View's size
    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH) {

        //this.onMeasure();

        Bitmap bmp = ((BitmapDrawable)this.getDrawable()).getBitmap();
        bmp.copy(Bitmap.Config.ARGB_8888, true);

        // bmp is your Bitmap object
        int imgHeight = bmp.getHeight();
        int imgWidth = bmp.getWidth();
        Log.w("onszchangeIWi", new Float(imgWidth).toString());
        Log.w("onszchangeIhe", new Float(imgHeight).toString());


//        int containerHeight = this.getHeight();
//        int containerWidth = this.getWidth();

        int containerHeight = 900;
        int containerWidth = 700;


//        int containerHeight = this.getMeasuredHeight();
//        int containerWidth = this.getMeasuredWidth();

        boolean ch2cw = containerHeight > containerWidth;
        float ch2cws = (float)containerHeight/(float)containerWidth;

        Log.w("onszchangeoWi", new Float(containerWidth).toString());
        Log.w("onszchangeohe", new Float(containerHeight).toString());

        float h2w = (float) imgHeight / (float) imgWidth;

        Log.w("onszch2w", new Float(h2w).toString());
        Log.w("onszch2cws", new Float(ch2cws).toString());
        float newContainerHeight, newContainerWidth;
        Log.w("onszchange","before if");
        if (h2w > 1) {
            // height is greater than width
            if (ch2cw) {
                if(ch2cws < h2w){
                    newContainerHeight = (float) containerHeight;
                    newContainerWidth = newContainerHeight / h2w;
                    Log.w("s","d");
                }
                else{
                    newContainerWidth = (float) containerWidth;
                    newContainerHeight = newContainerWidth * h2w;

                }

                Log.w("onszchangeWi", new Float(newContainerWidth).toString());
                Log.w("onszchangehe", new Float(newContainerHeight).toString());
            } else {
                newContainerHeight = (float) containerHeight;
                newContainerWidth = newContainerHeight / h2w;
            }
        } else {
            // width is greater than height
            if (ch2cw) {
                newContainerWidth = (float) containerWidth;
                newContainerHeight = newContainerWidth / h2w;
            } else {
                newContainerWidth = (float) containerHeight;
                newContainerHeight = newContainerWidth * h2w;
            }
        }
        Log.w("onszchange","after if");
        Log.w("onszchangeafWi", new Float(newContainerWidth).toString());
        Log.w("onszchangeafhe", new Float(newContainerHeight).toString());
        Bitmap copy = Bitmap.createScaledBitmap(bmp, (int) newContainerWidth, (int) newContainerHeight, false);
        //this.setBackgroundDrawable(new BitmapDrawable(copy));
        this.setBackground(new BitmapDrawable(copy));
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


        //params.
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)newContainerWidth, (int)newContainerHeight);

        params.leftMargin = 45;

        //params.gravity = Gravity.BOTTOM | Gravity.CENTER;

        this.setLayoutParams(params);
        this.setMaxHeight((int) newContainerHeight);
        this.setMaxWidth((int) newContainerWidth);
        //this.he
        //this.
        //this.set

        Log.w("onszchangeeWi", new Float(this.getWidth()).toString());
        Log.w("onszchangeehe", new Float(this.getHeight()).toString());

        //this.setg

        //;

        /*

        this.setScaleType(ScaleType.FIT_CENTER);
        //this.set

        this.setAdjustViewBounds(true);
        this.setBackground((BitmapDrawable)this.getDrawable());

        */
        //this.parent


        Log.w("CanvasView: ",this.getScaleType().toString());

        //this.setImageURI();
        //picBitmap = ().getBitmap();
        //picBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //picBitmap.set

        //bitmapCanvas = new Canvas(bitmap);
//        bitmap.eraseColor(Color.WHITE); // erase the Bitmap with white




        bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);

        //d.draw(bitmapCanvas);
        //bitmap.eraseColor(Color.WHITE); // erase the Bitmap with white
    }

    // clear the painting
    public void clear() {
        pathMap.clear(); // remove all paths
        previousPointMap.clear(); // remove all previous points
        bitmap.eraseColor(Color.WHITE); // clear the bitmap
        invalidate(); // refresh the screen
    }

    // set the painted line's color
    public void setDrawingColor(int color) {
        paintLine.setColor(color);
    }

    // return the painted line's color
    public int getDrawingColor() {
        return paintLine.getColor();
    }

    // set the painted line's width
    public void setLineWidth(int width) {
        paintLine.setStrokeWidth(width);
    }

    // return the painted line's width
    public int getLineWidth() {
        return (int) paintLine.getStrokeWidth();
    }

    // perform custom drawing when the CanvasView is refreshed on screen
    @Override
    protected void onDraw(Canvas canvas) {


        // draw the background screen
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);

        // for each path currently being drawn
        for (Integer key : pathMap.keySet())
            canvas.drawPath(pathMap.get(key), paintLine); // draw line

//        canvas.drawPaint(paintText);
//        canvas.drawText("Some Text", 10, 25, paintText);
    }

    // handle touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked(); // event type
        int actionIndex = event.getActionIndex(); // pointer (i.e., finger)

        // determine whether touch started, ended or is moving
        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex), event.getY(actionIndex),
                    event.getPointerId(actionIndex));
        }
        else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP) {
            touchEnded(event.getPointerId(actionIndex));
        }
        else {
            touchMoved(event);
        }

        invalidate(); // redraw
        return true;
    }

    // called when the user touches the screen
    private void touchStarted(float x, float y, int lineID) {
        Path path; // used to store the path for the given touch id
        Point point; // used to store the last point in path

        // if there is already a path for lineID
        if (pathMap.containsKey(lineID)) {
            path = pathMap.get(lineID); // get the Path
            path.reset(); // resets the Path because a new touch has started
            point = previousPointMap.get(lineID); // get Path's last point
        }
        else {
            path = new Path();
            pathMap.put(lineID, path); // add the Path to Map
            point = new Point(); // create a new Point
            previousPointMap.put(lineID, point); // add the Point to the Map
        }

        // move to the coordinates of the touch
        path.moveTo(x, y);
        point.x = (int) x;
        point.y = (int) y;
    }

    // called when the user drags along the screen
    private void touchMoved(MotionEvent event) {
        // for each of the pointers in the given MotionEvent
        for (int i = 0; i < event.getPointerCount(); i++) {
            // get the pointer ID and pointer index
            int pointerID = event.getPointerId(i);
            int pointerIndex = event.findPointerIndex(pointerID);

            // if there is a path associated with the pointer
            if (pathMap.containsKey(pointerID)) {
                // get the new coordinates for the pointer
                float newX = event.getX(pointerIndex);
                float newY = event.getY(pointerIndex);

                // get the path and previous point associated with
                // this pointer
                Path path = pathMap.get(pointerID);
                Point point = previousPointMap.get(pointerID);

                // calculate how far the user moved from the last update
                float deltaX = Math.abs(newX - point.x);
                float deltaY = Math.abs(newY - point.y);

                // if the distance is significant enough to matter
                if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE) {
                    // move the path to the new location
                    path.quadTo(point.x, point.y, (newX + point.x) / 2,
                            (newY + point.y) / 2);

                    // store the new coordinates
                    point.x = (int) newX;
                    point.y = (int) newY;
                }
            }
        }
    }

    // called when the user finishes a touch
    private void touchEnded(int lineID) {
        Path path = pathMap.get(lineID); // get the corresponding Path
        bitmapCanvas.drawPath(path, paintLine); // draw to bitmapCanvas
        path.reset(); // reset the Path
    }

    // save the current image to the Gallery
    public void saveImage() {
        // use "CanvasView" followed by current time as the image name
        final String name = "CanvasView" + System.currentTimeMillis() + ".jpg";

        // insert the image on the device
        String location = MediaStore.Images.Media.insertImage(
                getContext().getContentResolver(), bitmap, name,
                "CanvasView Drawing");

        if (location != null) {
            // display a message indicating that the image was saved
//            Toast message = Toast.makeText(getContext(),
//                    R.string.message_saved,
//                    Toast.LENGTH_SHORT);

            Toast message = Toast.makeText(getContext(),
                    "save",
                    Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
        else {
            // display a message indicating that there was an error saving
//            Toast message = Toast.makeText(getContext(),
//                    R.string.message_error_saving, Toast.LENGTH_SHORT);

            Toast message = Toast.makeText(getContext(),
                    "error", Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
                    message.getYOffset() / 2);
            message.show();
        }
    }


//    // print the current image
//    public void printImage() {
//        if (PrintHelper.systemSupportsPrint()) {
//            // use Android Support Library's PrintHelper to print image
//            PrintHelper printHelper = new PrintHelper(getContext());
//
//            // fit image in page bounds and print the image
//            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
//            printHelper.printBitmap("CanvasView Image", bitmap);
//        }
//        else {
//            // display message indicating that system does not allow printing
//            Toast message = Toast.makeText(getContext(),
//                    R.string.message_error_printing, Toast.LENGTH_SHORT);
//            message.setGravity(Gravity.CENTER, message.getXOffset() / 2,
//                    message.getYOffset() / 2);
//            message.show();
//        }
//    }

}
