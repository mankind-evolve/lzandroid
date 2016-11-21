package com.lazy.android.baseui.common;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.lazy.android.R;

public class CustomImageView extends ImageView {

  public static final String TAG = "CustomImageView";
  public static final float DEFAULT_RADIUS = 0f;
  public static final float DEFAULT_BORDER_WIDTH = 0f;
  private static final ScaleType[] SCALE_TYPES = {
      ScaleType.MATRIX,
      ScaleType.FIT_XY,
      ScaleType.FIT_START,
      ScaleType.FIT_CENTER,
      ScaleType.FIT_END,
      ScaleType.CENTER,
      ScaleType.CENTER_CROP,
      ScaleType.CENTER_INSIDE
  };

  private float cornerRadius = DEFAULT_RADIUS;
  private float borderWidth = DEFAULT_BORDER_WIDTH;
  private ColorStateList borderColor =
      ColorStateList.valueOf(CustomImageViewDrawable.DEFAULT_BORDER_COLOR);
  private boolean isOval = false;
  private boolean mutateBackground = false;

  private int mResource;
  private Drawable mDrawable;
  private Drawable mBackgroundDrawable;

  private ScaleType mScaleType;

  public CustomImageView(Context context) {
    super(context);
  }

  public CustomImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);

    int index = a.getInt(R.styleable.CustomImageView_android_scaleType, -1);
    if (index >= 0) {
      setScaleType(SCALE_TYPES[index]);
    } else {
      // default scaletype to FIT_CENTER
      setScaleType(ScaleType.FIT_CENTER);
    }

    cornerRadius = a.getDimensionPixelSize(R.styleable.CustomImageView_corner_radius, -1);
    borderWidth = a.getDimensionPixelSize(R.styleable.CustomImageView_border_width, -1);

    // don't allow negative values for radius and border
    if (cornerRadius < 0) {
      cornerRadius = DEFAULT_RADIUS;
    }
    if (borderWidth < 0) {
      borderWidth = DEFAULT_BORDER_WIDTH;
    }

    borderColor = a.getColorStateList(R.styleable.CustomImageView_border_color);
    if (borderColor == null) {
      borderColor = ColorStateList.valueOf(CustomImageViewDrawable.DEFAULT_BORDER_COLOR);
    }

    mutateBackground = a.getBoolean(R.styleable.CustomImageView_mutate_background, false);
    isOval = a.getBoolean(R.styleable.CustomImageView_oval, false);

    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(true);

    a.recycle();
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    invalidate();
  }

  /**
   * Return the current scale type in use by this ImageView.
   *
   * @attr ref android.R.styleable#ImageView_scaleType
   * @see android.widget.ImageView.ScaleType
   */
  @Override
  public ScaleType getScaleType() {
    return mScaleType;
  }

  /**
   * Controls how the image should be resized or moved to match the size
   * of this ImageView.
   *
   * @param scaleType The desired scaling mode.
   * @attr ref android.R.styleable#ImageView_scaleType
   */
  @Override
  public void setScaleType(ScaleType scaleType) {
    assert scaleType != null;

    if (mScaleType != scaleType) {
      mScaleType = scaleType;

      switch (scaleType) {
        case CENTER:
        case CENTER_CROP:
        case CENTER_INSIDE:
        case FIT_CENTER:
        case FIT_START:
        case FIT_END:
        case FIT_XY:
          super.setScaleType(ScaleType.FIT_XY);
          break;
        default:
          super.setScaleType(ScaleType.FIT_XY);
          break;
      }

      updateDrawableAttrs();
      updateBackgroundDrawableAttrs(false);
      invalidate();
    }
  }

  @Override
  public void setImageDrawable(Drawable drawable) {
    mResource = 0;
    mDrawable = CustomImageViewDrawable.fromDrawable(drawable);
    updateDrawableAttrs();
    super.setImageDrawable(mDrawable);
  }

  @Override
  public void setImageBitmap(Bitmap bm) {
    mResource = 0;
    mDrawable = CustomImageViewDrawable.fromBitmap(bm);
    updateDrawableAttrs();
    super.setImageDrawable(mDrawable);
  }

  @Override
  public void setImageResource(int resId) {
    if (mResource != resId) {
      mResource = resId;
      mDrawable = resolveResource();
      updateDrawableAttrs();
      super.setImageDrawable(mDrawable);
    }
  }

  @Override public void setImageURI(Uri uri) {
    super.setImageURI(uri);
    setImageDrawable(getDrawable());
  }

  private Drawable resolveResource() {
    Resources rsrc = getResources();
    if (rsrc == null) { return null; }

    Drawable d = null;

    if (mResource != 0) {
      try {
        d = rsrc.getDrawable(mResource);
      } catch (Exception e) {
        Log.w(TAG, "Unable to find resource: " + mResource, e);
        // Don't try again.
        mResource = 0;
      }
    }
    return CustomImageViewDrawable.fromDrawable(d);
  }

  public void setBackground(Drawable background) {
    setBackgroundDrawable(background);
  }

  private void updateDrawableAttrs() {
    updateAttrs(mDrawable);
  }

  private void updateBackgroundDrawableAttrs(boolean convert) {
    if (mutateBackground) {
      if (convert) {
        mBackgroundDrawable = CustomImageViewDrawable.fromDrawable(mBackgroundDrawable);
      }
      updateAttrs(mBackgroundDrawable);
    }
  }

  private void updateAttrs(Drawable drawable) {
    if (drawable == null) { return; }

    if (drawable instanceof CustomImageViewDrawable) {
      ((CustomImageViewDrawable) drawable)
          .setScaleType(mScaleType)
          .setCornerRadius(cornerRadius)
          .setBorderWidth(borderWidth)
          .setBorderColor(borderColor)
          .setOval(isOval);
    } else if (drawable instanceof LayerDrawable) {
      // loop through layers to and set drawable attrs
      LayerDrawable ld = ((LayerDrawable) drawable);
      for (int i = 0, layers = ld.getNumberOfLayers(); i < layers; i++) {
        updateAttrs(ld.getDrawable(i));
      }
    }
  }

  @Override
  @Deprecated
  public void setBackgroundDrawable(Drawable background) {
    mBackgroundDrawable = background;
    updateBackgroundDrawableAttrs(true);
    super.setBackgroundDrawable(mBackgroundDrawable);
  }

  public float getCornerRadius() {
    return cornerRadius;
  }

  public void setCornerRadius(int resId) {
    setCornerRadius(getResources().getDimension(resId));
  }

  public void setCornerRadius(float radius) {
    if (cornerRadius == radius) { return; }

    cornerRadius = radius;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
  }

  public float getBorderWidth() {
    return borderWidth;
  }

  public void setBorderWidth(int resId) {
    setBorderWidth(getResources().getDimension(resId));
  }

  public void setBorderWidth(float width) {
    if (borderWidth == width) { return; }

    borderWidth = width;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }

  public int getBorderColor() {
    return borderColor.getDefaultColor();
  }

  public void setBorderColor(int color) {
    setBorderColor(ColorStateList.valueOf(color));
  }

  public ColorStateList getBorderColors() {
    return borderColor;
  }

  public void setBorderColor(ColorStateList colors) {
    if (borderColor.equals(colors)) { return; }

    borderColor =
        (colors != null) ? colors : ColorStateList.valueOf(CustomImageViewDrawable.DEFAULT_BORDER_COLOR);
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    if (borderWidth > 0) {
      invalidate();
    }
  }

  public boolean isOval() {
    return isOval;
  }

  public void setOval(boolean oval) {
    isOval = oval;
    updateDrawableAttrs();
    updateBackgroundDrawableAttrs(false);
    invalidate();
  }

  public boolean isMutateBackground() {
    return mutateBackground;
  }

  public void setMutateBackground(boolean mutate) {
    if (mutateBackground == mutate) { return; }

    mutateBackground = mutate;
    updateBackgroundDrawableAttrs(true);
    invalidate();
  }


    //bitmap转换成圆形, 用于圆形头像的裁剪
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


}
