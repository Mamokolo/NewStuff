package com.example.newstuff.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.newstuff.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    public interface FragmentBackHandler{
        boolean onBackPressed();
    }
    public static class BackHandlerHelper{
        public static boolean handleBackPress(FragmentManager fragmentManager){
            List<Fragment> fragments = fragmentManager.getFragments();

            if(fragments == null){
                return false;
            }
            for(int i=fragments.size()-1;i>=0;i--){
                Fragment child = fragments.get(i);

                if(isFragmentBackHandled(child)){
                    return true;
                }
            }
            if(fragmentManager.getBackStackEntryCount() > 0){
                fragmentManager.popBackStack();
                return true;
            }
            return false;
        }
        public static boolean handleBackPress(Fragment fragment){
            return handleBackPress(fragment.getChildFragmentManager());
        }
        public static boolean isFragmentBackHandled(Fragment fragment){
            return fragment != null
                    && fragment.isVisible()
                    && fragment.getUserVisibleHint()
                    && fragment instanceof FragmentBackHandler
                    && ((FragmentBackHandler) fragment).onBackPressed();
        }
    }
    */
}