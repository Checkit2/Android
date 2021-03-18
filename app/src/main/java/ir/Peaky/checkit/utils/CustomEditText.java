package ir.Peaky.checkit.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(@NonNull Context context) {
        super(context);
        setFont(context);

    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public CustomEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);

    }
    private void setFont(Context context) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/IRANYekanMobileRegular.ttf");
        setTypeface(typeface);
    }
}
