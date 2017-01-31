package danubis.tony.hintcomponent;


import android.animation.AnimatorSet;
import android.app.Activity;

import android.support.annotation.NonNull;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by yiluo on 1/11/16.
 */

public class HintComponent implements HintListener {

    private RelativeLayout hintLayout;
    private SimpleHintView hintView;
    private HintGridView hintGridView;

    static int screenHeight;
    static boolean isExpanded = false;

    private HintComponentListener hintComponentListener;

    private Map<String, String> hintKeywordMap;


    public static class Builder {

        private Activity activity;

        private float heightPercentage = 0f;
        private int hintViewHeight = 25;
        private int rowHeight = 40;
        private int numColumns = 2;
        private int offset = 0;
        private HintComponentListener listener;

        public Builder(@NonNull Activity activity) {
            this.activity = activity;
        }

        public Builder hintComponentListener(HintComponentListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder heightPercentage(float heightPercentage) {
            this.heightPercentage = heightPercentage;
            return this;
        }

        public Builder hintViewHeight(int hintViewHeight) {
            this.hintViewHeight = hintViewHeight;
            return this;
        }

        public Builder rowHeight(int rowHeight) {
            this.rowHeight = rowHeight;
            return this;
        }

        public Builder numColumns(int numColumns) {
            this.numColumns = numColumns;
            return this;
        }

        public Builder screenHeightOffset(int offset) {
            this.offset = offset;
            return this;
        }

        public HintComponent build() {
            return new HintComponent(activity, hintViewHeight,
                    heightPercentage, numColumns, rowHeight, offset, listener);
        }
    }


    private HintComponent(Activity activity, int hintViewHeight,
                          float heightPercentage, int numColumns, int rowHeight,
                          int offset, HintComponentListener hintComponentListener) {

        hintLayout = (RelativeLayout) View.inflate(activity, R.layout.new_hint_component, null);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels + offset;

        initSimpleHintView(hintViewHeight, numColumns);
        initHintGridView(heightPercentage, numColumns, rowHeight);

        activity.addContentView(hintLayout, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.hintComponentListener = hintComponentListener;
    }


    private void initSimpleHintView(int hintViewHeight, int numColumns) {
        hintView = (SimpleHintView) hintLayout.findViewById(R.id.hint_view);
        hintView.init(hintViewHeight, numColumns, this);
    }


    private void initHintGridView(float heightPercentage, int numColumns, int rowHeight) {
        hintGridView = (HintGridView) hintLayout.findViewById(R.id.hint_grid_view);
        hintGridView.init(heightPercentage, numColumns, rowHeight, this);
    }


    public void bringToFront() {
        hintLayout.bringToFront();
    }


    //put hint in hint view and start the rolling animation
    public void updateHintView(HashMap<String, String> hintKeywordMap) {

        //same hint keyword map received, we do not do anything
        if (this.hintKeywordMap != null && !this.hintKeywordMap.isEmpty()) {
            if (this.hintKeywordMap.keySet().equals(hintKeywordMap.keySet())) {
                return;
            }
        }

        this.hintKeywordMap = hintKeywordMap;

        ArrayList<String> hintList = new ArrayList<>(hintKeywordMap.keySet());

        hintView.updateHints(hintList);
        hintGridView.updateHints(hintList);
    }


    public void hideHintView() {
//        hintView.hideHints();
        if (!isExpanded) {
            hintGridView.moveHintGridView();
        }
    }


    public void setVisibility(int visibility) {
        hintLayout.setVisibility(visibility);
    }


    @Override
    public void onHintSelected(String hint) {
        String keyword = hintKeywordMap.get(hint);
        hintComponentListener.onHintClicked(keyword);
        if (isExpanded) {
            hintView.onClick(hintView);
        }
    }


    @Override
    public void onHintButtonClicked(AnimatorSet hintViewAnimation) {
        hintViewAnimation.playTogether(hintGridView.moveHintGridView());
        hintViewAnimation.setDuration(200);
        hintViewAnimation.start();
        isExpanded = !isExpanded;
    }
}
