package danubis.tony.hintcomponent;

import android.animation.AnimatorSet;

/**
 * Created by yiluo on 1/11/16.
 */

public interface HintListener {

    void onHintSelected(String hint);

    void onHintButtonClicked(AnimatorSet hintViewAnimation);
}
