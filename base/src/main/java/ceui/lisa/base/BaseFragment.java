package ceui.lisa.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


public abstract class BaseFragment<Layout extends ViewDataBinding> extends Fragment {

    protected View rootView;
    protected Layout baseBind;
    protected String className = getClass().getSimpleName();

    protected int mLayoutID = -1;

    protected FragmentActivity mActivity;
    protected Context mContext;
    private boolean isVertical;
    protected Handler mainHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainHandler = new Handler();

        mActivity = requireActivity();
        mContext = requireContext();

        Bundle bundle = getArguments();
        if (bundle != null) {
            initBundle(bundle);
        }

        Intent intent = mActivity.getIntent();
        if (intent != null) {
            Bundle activityBundle = intent.getExtras();
            if (activityBundle != null) {
                initActivityBundle(activityBundle);
            }
        }

        //获取屏幕方向
        int ori = getResources().getConfiguration().orientation;
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            isVertical = false;
        } else if (ori == Configuration.ORIENTATION_PORTRAIT) {
            isVertical = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initLayout();

        if (mLayoutID != -1) {
            baseBind = DataBindingUtil.inflate(inflater, mLayoutID, container, false);
            if (baseBind != null) {
                rootView = baseBind.getRoot();
            } else {
                rootView = inflater.inflate(mLayoutID, container, false);
            }
            return rootView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isVertical) {
            vertical();
        } else {
            horizon();
        }
        initView();
        initData();
    }

    protected abstract void initLayout();

    protected void initBundle(Bundle bundle) {

    }

    protected void initActivityBundle(Bundle bundle) {

    }

    protected void initView() {

    }

    protected void initData() {

    }

    public void horizon() {

    }

    public void vertical() {

    }

    @Override
    public void onDestroy() {
        mainHandler = null;
        super.onDestroy();
    }
}
