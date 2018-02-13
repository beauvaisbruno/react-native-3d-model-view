/**
 * Created by johankasperi 2018-02-05.
 */

package se.bonniernews.rn3d;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.widget.RelativeLayout;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.util.DisplayMetrics;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableNativeMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class RN3DViewManager extends SimpleViewManager<RN3DView> {
  public static final String REACT_CLASS = "RCT3DScnModelView";
  private RN3DView view;

  public static final int COMMAND_RELOAD = 1;
  public static final int COMMAND_START_ANIMATION = 2;
  public static final int COMMAND_STOP_ANIMATION = 3;
  public static final int COMMAND_SET_PROGRESS = 4;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  protected RN3DView createViewInstance(ThemedReactContext themedReactContext) {
    this.view = create3DView(themedReactContext);
    return this.view;
  }

  @ReactProp(name = "modelSrc")
  public void setModelSrc(final RN3DView view, final String modelSrc) {
    view.setModelSrc(modelSrc);
  }

  @ReactProp(name = "textureSrc")
  public void setTextureSrc(final RN3DView view, final String textureSrc) {
    view.setTextureSrc(textureSrc);
  }

  @ReactProp(name = "backgroundColor", customType = "Color")
  public void setBackgroundColor(final RN3DView view, final Integer color) {
    view.setBackgroundColor(color);
  }

  @ReactProp(name = "scale")
  public void setScale(final RN3DView view, final float scale) {
    view.setScale(scale);
  }

  @ReactProp(name = "autoPlayAnimations")
  public void setAutoPlayAnimations(final RN3DView view, final boolean autoPlay) {
    view.setPlay(autoPlay);
  }

  @NonNull
  public static RN3DView create3DView(ThemedReactContext context) {
    return new RN3DView(context);
  }

  @Override
  public Map<String,Integer> getCommandsMap() {
    return MapBuilder.of(
            "reload",
            COMMAND_RELOAD,
            "startAnimation",
            COMMAND_START_ANIMATION,
            "stopAnimation",
            COMMAND_STOP_ANIMATION,
            "setProgress",
            COMMAND_SET_PROGRESS);
  }

  @Override
  public void receiveCommand(
          RN3DView view,
          int commandType,
          @Nullable ReadableArray args) {
    Assertions.assertNotNull(view);
    Assertions.assertNotNull(args);
    switch (commandType) {
      case COMMAND_RELOAD: {
        return;
      }
      case COMMAND_START_ANIMATION: {
        view.setPlay(true);
        return;
      }
      case COMMAND_STOP_ANIMATION: {
        view.setPlay(false);
        return;
      }
      case COMMAND_SET_PROGRESS: {
        view.setProgress((float)args.getDouble(0));
        return;
      }
      default:
        throw new IllegalArgumentException(String.format(
                "Unsupported command %d received by %s.",
                commandType,
                getClass().getSimpleName()));
    }
  }

  @Override
  @Nullable
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
    return builder
            .put("loadModelSuccess", MapBuilder.of("registrationName", "loadModelSuccess"))
            .put("loadModelError", MapBuilder.of("registrationName", "loadModelError"))
            .build();
  }
}