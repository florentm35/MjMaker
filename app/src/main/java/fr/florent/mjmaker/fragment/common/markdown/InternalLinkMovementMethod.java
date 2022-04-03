package fr.florent.mjmaker.fragment.common.markdown;

import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Set this on a textview and then you can potentially open links locally if applicable <br/>
 * Show :
 * <ul>
 *     <li>https://stackoverflow.com/questions/50338333/intercept-link-linkmovementmethod-with-a-yes-no-dialog</li>
 *     <li>https://gitlab.com/Commit451/LabCoat/-/commit/0da57c371815902f4ba24fcd7bceaa1e7a8d7bb7#1869e1cd937878326e16d1ab7139f68380c48172</li>
 * </ul>
 */
public class InternalLinkMovementMethod extends LinkMovementMethod {

    private final OnLinkClickedListener mOnLinkClickedListener;

    public InternalLinkMovementMethod(OnLinkClickedListener onLinkClickedListener) {
        mOnLinkClickedListener = onLinkClickedListener;
    }

    public boolean onTouchEvent(TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
        int action = event.getAction();

        //http://stackoverflow.com/questions/1697084/handle-textview-link-click-in-my-android-app
        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            if (link.length != 0) {
                String url = link[0].getURL();
                boolean handled = mOnLinkClickedListener.onLinkClicked(url);
                if (handled) {
                    return true;
                }
                return super.onTouchEvent(widget, buffer, event);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    public interface OnLinkClickedListener {
        boolean onLinkClicked(String url);
    }
}
