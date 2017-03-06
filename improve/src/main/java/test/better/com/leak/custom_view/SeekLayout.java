package test.better.com.leak.custom_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import test.better.com.leak.R;

/**
 * https://github.com/momodae/widget/blob/master/app/src/main/java/com/cz/app/widget/SeekLayout.java
 */
public class SeekLayout extends LinearLayout {
    private OnSeekProgressChangeListener listener;

    public SeekLayout(Context context) {
        this(context, null);
    }

    public SeekLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.seek_layout, this);
        final TextView infoView = (TextView) findViewById(R.id.title);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SeekLayout);
        seekBar.setMax(a.getInteger(R.styleable.SeekLayout_sl_max, 100));
        final String info = a.getString(R.styleable.SeekLayout_sl_info);
        infoView.setText(info);
        a.recycle();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                infoView.setText(info + ":" + progress);
                if (null != listener) {
                    listener.onProgressChanged(seekBar, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }

    public void setOnSeekProgressChangeListener(OnSeekProgressChangeListener listner) {
        this.listener = listner;
    }

    public interface OnSeekProgressChangeListener {
        void onProgressChanged(SeekBar seekBar, int progress);
    }
}