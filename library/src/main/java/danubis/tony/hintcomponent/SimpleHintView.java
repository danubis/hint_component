package danubis.tony.hintcomponent;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static danubis.tony.hintcomponent.HintGridView.girdViewHeight;
import static danubis.tony.hintcomponent.HintComponent.isExpanded;
import static danubis.tony.hintcomponent.HintComponent.screenHeight;


public class SimpleHintView extends RelativeLayout implements View.OnClickListener {

    public SimpleHintView(Context context) {
        super(context);
    }

    public SimpleHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleHintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SimpleHintView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public static int hintViewHeight;
    private int numColumns;

    private ArrayList<String[]> hintsGroups;
    private int hintGroupIndex = 0;
    private AnimatorSet hintAnimation;

    private LinearLayout hintContainer1;
    private LinearLayout hintContainer2;
    private TextView[] hintTextViews;
    private ImageView showHintButton;

    private HintListener listener;

    private LayoutInflater inflater;


    public void init(int hintViewHeight, int numColumns, HintListener listener) {

        this.hintViewHeight = hintViewHeight;
        this.numColumns = numColumns;
        this.listener = listener;

        hintTextViews = new TextView[numColumns * 2];

        RelativeLayout layout = (RelativeLayout) inflate(getContext(), R.layout.simple_hint_view, this);

        inflater = LayoutInflater.from(getContext());

        hintContainer1 = (LinearLayout) layout.findViewById(R.id.hint_container_1);

        for (int i = 0; i < numColumns; i++) {
            hintTextViews[i] = (TextView) inflater.inflate(R.layout.hint_text_view, hintContainer1, false);
            hintTextViews[i].setOnClickListener(this);
            hintContainer1.addView(hintTextViews[i]);
        }

        hintContainer2 = (LinearLayout) layout.findViewById(R.id.hint_container_2);
        for (int i = numColumns; i < numColumns * 2; i++) {
            hintTextViews[i] = (TextView) inflater.inflate(R.layout.hint_text_view, hintContainer2, false);
            hintTextViews[i].setOnClickListener(this);
            hintContainer2.addView(hintTextViews[i]);
        }

        setY(screenHeight - hintViewHeight);

        showHintButton = (ImageView) layout.findViewById(R.id.show_hint_button);
        setOnClickListener(this);
        setVisibility(GONE);
    }


    public void updateHints(ArrayList<String> hintList) {

        getLayoutParams().height = hintViewHeight;
        requestLayout();

        hintsGroups = listToListGroup(hintList);

        for (TextView hintTextView : hintTextViews) {
            hintTextView.setText("");
        }

        stopHintAnimation();

        setVisibility(VISIBLE);

        if (hintsGroups.size() > 1) {

            String[] hints = hintsGroups.get(0);
            for (int i = 0; i < hints.length; i++) {
                hintTextViews[i].setText(hints[i]);
            }

            hintContainer2.setY(hintViewHeight);
            startHintAnimation();

        } else {
            hintContainer2.setVisibility(View.GONE);

            String[] hints = hintsGroups.get(0);
            for (int i = 0; i < hints.length; i++) {
                hintTextViews[i].setText(hints[i]);
            }
        }


    }


    private void startHintAnimation() {

        ObjectAnimator firstContainerMoveOut = ObjectAnimator.ofFloat(hintContainer1, "y", 0f, -hintViewHeight);
        ObjectAnimator secondContainerMoveIn = ObjectAnimator.ofFloat(hintContainer2, "y", hintViewHeight, 0f);

        AnimatorSet transition1 = new AnimatorSet();
        transition1.playTogether(firstContainerMoveOut, secondContainerMoveIn);
        transition1.setDuration(1000);
        transition1.setStartDelay(5000);
        transition1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                hintGroupIndex = hintGroupIndex < hintsGroups.size() - 1 ? hintGroupIndex + 1 : 0;
                updateTextViews(2, hintGroupIndex);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });


        ObjectAnimator firstContainerMoveIn = ObjectAnimator.ofFloat(hintContainer1, "y", hintViewHeight, 0f);
        ObjectAnimator secondContainerMoveOut = ObjectAnimator.ofFloat(hintContainer2, "y", 0f, -hintViewHeight);

        AnimatorSet transition2 = new AnimatorSet();
        transition2.playTogether(firstContainerMoveIn, secondContainerMoveOut);
        transition2.setDuration(1000);
        transition2.setStartDelay(5000);
        transition2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                hintGroupIndex = hintGroupIndex < hintsGroups.size() - 1 ? hintGroupIndex + 1 : 0;
                updateTextViews(1, hintGroupIndex);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        hintAnimation = new AnimatorSet();
        hintAnimation.playSequentially(transition1, transition2);
        hintAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        hintAnimation.start();
    }


    private void pauseHintAnimation() {
        if (hintAnimation != null) {
            hintAnimation.pause();
        }
    }


    private void resumeHintAnimation() {
        if (hintAnimation != null) {
            hintAnimation.resume();
        }
    }


    private void stopHintAnimation() {

        hintGroupIndex = 0;

        if (hintAnimation != null) {
            hintAnimation.cancel();
            hintAnimation.removeAllListeners();
        }

        if (hintContainer1.getAnimation() != null) {
            hintContainer1.getAnimation().cancel();
            hintContainer1.clearAnimation();
        }

        if (hintContainer2.getAnimation() != null) {
            hintContainer2.getAnimation().cancel();
            hintContainer2.clearAnimation();
        }

        hintContainer1.setY(0f);
        hintContainer2.setY(0f);
    }


    private ArrayList<String[]> mapToListGroup(HashMap<String, String> hintKeywordMap) {

        //map to array
        String[] hints = new String[hintKeywordMap.size()];
        int index = 0;
        for (Map.Entry<String, String> entry : hintKeywordMap.entrySet()) {
            hints[index] = entry.getKey();
            index++;
        }

        //array to array list of string array
        int numOfHintGroups = hints.length / numColumns + (hints.length % numColumns == 0 ? 0 : 1);

        ArrayList<String[]> hintsGroups = new ArrayList<>();
        String[] hintGroup;

        for (int i = 0; i < numOfHintGroups; i++) {

            if (i != numOfHintGroups - 1) {
                hintGroup = new String[numColumns];
            } else {
                int j = hints.length - (numOfHintGroups - 1) * numColumns;
                hintGroup = new String[j];
            }

//            for (int k = 0; k < hintGroup.length; k++) {
//                hintGroup[k] = hints[i * 4 + k];
//            }

            System.arraycopy(hints, i * numColumns, hintGroup, 0, hintGroup.length);
            hintsGroups.add(hintGroup);

        }
        return hintsGroups;
    }


    private ArrayList<String[]> listToListGroup(ArrayList<String> hintList) {

        //map to array
        String[] hints = new String[hintList.size()];
        int index = 0;
        for (String hint : hintList) {
            hints[index] = hint;
            index++;
        }

        //array to array list of string array
        int numOfHintGroups = hints.length / numColumns + (hints.length % numColumns == 0 ? 0 : 1);

        ArrayList<String[]> hintsGroups = new ArrayList<>();
        String[] hintGroup;

        for (int i = 0; i < numOfHintGroups; i++) {

            if (i != numOfHintGroups - 1) {
                hintGroup = new String[numColumns];
            } else {
                int j = hints.length - (numOfHintGroups - 1) * numColumns;
                hintGroup = new String[j];
            }

//            for (int k = 0; k < hintGroup.length; k++) {
//                hintGroup[k] = hints[i * 4 + k];
//            }

            System.arraycopy(hints, i * numColumns, hintGroup, 0, hintGroup.length);
            hintsGroups.add(hintGroup);

        }
        return hintsGroups;
    }


    private void updateTextViews(int containerIndex, int hintGroupIndex) {

        String[] hints = hintsGroups.get(hintGroupIndex);

        for (int i = 0; i < numColumns; i++) {
            if (containerIndex == 1) {
                hintTextViews[i].setText("");

                if (i < hints.length) {
                    hintTextViews[i].setText(hints[i]);
                }

            } else if (containerIndex == 2) {
                hintTextViews[i + numColumns].setText("");

                if (i < hints.length) {
                    hintTextViews[i + numColumns].setText(hints[i]);
                }
            }
        }
    }


    private AnimatorSet getMoveHintViewAnimator() {

        float fromY;
        float toY;
        float rotationFrom;
        float rotationTo;
        float fromAlpha;
        float toAlpha;

        if (isExpanded) {
            fromY = getY();
            toY = screenHeight - getHeight();
            rotationFrom = -180f;
            rotationTo = 0f;
            fromAlpha = 0.5f;
            toAlpha = 1f;

        } else {
            fromY = getY();
            toY = screenHeight - girdViewHeight - getHeight();
            rotationFrom = 0f;
            rotationTo = -180f;
            fromAlpha = 1f;
            toAlpha = 0.5f;

            hintContainer1.setVisibility(View.GONE);
            hintContainer2.setVisibility(View.GONE);
            pauseHintAnimation();
        }

        ObjectAnimator moveHintView = ObjectAnimator.ofFloat(this, "y", fromY, toY);
        ObjectAnimator rotateArrow = ObjectAnimator.ofFloat(showHintButton, "rotation", rotationFrom, rotationTo);
        ObjectAnimator changeAlpha = ObjectAnimator.ofFloat(this, "alpha", fromAlpha, toAlpha);

        AnimatorSet moveHintAnimation = new AnimatorSet();
        moveHintAnimation.playTogether(moveHintView, rotateArrow, changeAlpha);
        moveHintAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                setOnClickListener(SimpleHintView.this);

                if (!isExpanded) {
                    hintContainer1.setVisibility(View.VISIBLE);
                    hintContainer2.setVisibility(View.VISIBLE);
                    resumeHintAnimation();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        return moveHintAnimation;
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.hint_view) {
            setOnClickListener(null);
            listener.onHintButtonClicked(getMoveHintViewAnimator());

        } else {
            TextView clickedTextView = (TextView) view;
            String hint = clickedTextView.getText().toString();

            if (!hint.equals("")) {
                listener.onHintSelected(hint);
            }
        }
    }
}
