package ir.Peaky.checkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

import ir.Peaky.checkit.R;
import ir.Peaky.checkit.model.IntroItem;
import ir.Peaky.checkit.utils.BoldTextView;
import ir.Peaky.checkit.utils.RegularTextView;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mContex;
    List<IntroItem> mListScreen;

    public IntroViewPagerAdapter(Context mContex, List<IntroItem> mListScreen) {
        this.mContex = mContex;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        LayoutInflater inflater=(LayoutInflater)mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen=inflater.inflate(R.layout.intro_layout,null);
        AppCompatImageView introImg=layoutScreen.findViewById(R.id.intro_img);
        BoldTextView title=layoutScreen.findViewById(R.id.txt_title);
        RegularTextView description=layoutScreen.findViewById(R.id.txt_description);

        title.setText(mListScreen.get(position).getTitle());
        description.setText(mListScreen.get(position).getDescription());
        introImg.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
