package danubis.tony.hintcomponent;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import static danubis.tony.hintcomponent.HintComponent.isExpanded;
import static danubis.tony.hintcomponent.HintComponent.screenHeight;
import static danubis.tony.hintcomponent.SimpleHintView.hintViewHeight;

/**
 * Created by yiluo on 1/11/16.
 */

public class HintGridView extends GridView implements AdapterView.OnItemClickListener {

    public HintGridView(Context context) {
        super(context);
    }

    public HintGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HintGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HintGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public static int girdViewHeight;

    private float heightPercentage;
    private int numColumns;
    private int rowHeight;
    private HintListener listener;

    private ArrayList<String> hintList;
    private HintAdapter hintAdapter;


    public void init(float heightPercentage, int numColumns, int rowHeight, final HintListener listener) {

        this.heightPercentage = heightPercentage;
        this.numColumns = numColumns;
        this.rowHeight = rowHeight;
        this.listener = listener;

        setNumColumns(numColumns);
        setY(screenHeight);

        setOnItemClickListener(this);
    }


    public void updateHints(ArrayList<String> hintList) {
        this.hintList = hintList;

        int listSize = hintList.size();
        int numRows = (listSize % numColumns) == 0 ? listSize / numColumns : listSize / numColumns + 1;

        if ((numRows * rowHeight + numRows) <= screenHeight * heightPercentage) {
            girdViewHeight = numRows * rowHeight + numRows;
        } else {
            girdViewHeight = (int) (screenHeight * heightPercentage);
        }
        getLayoutParams().height = girdViewHeight;
        requestLayout();

        hintAdapter = new HintAdapter(getContext(), R.layout.hint_item, hintList, rowHeight);
        setAdapter(hintAdapter);
    }


    public ObjectAnimator moveHintGridView() {

        float fromY;
        float toY;

        if (isExpanded) {
            fromY = getY();
            toY = screenHeight;

        } else {
            fromY = screenHeight;
            toY = screenHeight - girdViewHeight;
        }

        return ObjectAnimator.ofFloat(this, "y", fromY, toY);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        listener.onHintSelected(hintList.get(i));
    }

}
