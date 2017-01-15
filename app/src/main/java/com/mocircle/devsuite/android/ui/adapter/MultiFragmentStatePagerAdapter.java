package com.mocircle.devsuite.android.ui.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Fork from {@link android.support.v4.app.FragmentStatePagerAdapter},
 * to support 2 dimension fragment state.
 */
@SuppressLint("LongLogTag")
public abstract class MultiFragmentStatePagerAdapter extends PagerAdapter {

    private static final String TAG = "MultiFragmentStatePagerAdapter";
    private static final boolean DEBUG = true;

    private final FragmentManager mFragmentManager;
    private FragmentTransaction mCurTransaction = null;

    // CHANGE: change list content from object to SparseArray<Object>
    private ArrayList<SparseArray<Fragment.SavedState>> mSavedState = new ArrayList<>();
    private ArrayList<SparseArray<Fragment>> mFragments = new ArrayList<>();
    private Fragment mCurrentPrimaryItem = null;

    // NEW: Add a new field for supporting "position->view mode" mapping
    private SparseIntArray viewModes = new SparseIntArray();
    private Map<Fragment, Integer> positionMap = new HashMap<>();
    private Map<Fragment, Integer> viewModeMap = new HashMap<>();

    public MultiFragmentStatePagerAdapter(FragmentManager fm) {
        mFragmentManager = fm;
    }

    /**
     * Return the Fragment associated with a specified position and view mode.
     */
    public abstract Fragment getItem(int position, int viewMode);

    @Override
    public void startUpdate(ViewGroup container) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // If we already have this item instantiated, there is nothing
        // to do.  This can happen when we are restoring the entire pager
        // from its saved state, where the fragment manager has already
        // taken care of restoring the fragments we previously had instantiated.
        if (mFragments.size() > position) {
            Fragment f = getCurrentFragment(position);
            if (f != null) {
                return f;
            }
        }

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }

        int currentViewMode = getCurrentViewMode(position);
        Fragment fragment = getItem(position, currentViewMode);
        // NEW: record the fragment's position and view mode
        viewModeMap.put(fragment, currentViewMode);
        positionMap.put(fragment, position);
        if (DEBUG)
            Log.v(TAG, "Adding item #" + position + "-" + currentViewMode + ": f=" + fragment);
        if (mSavedState.size() > position) {
            Fragment.SavedState fss = getCurrentSavedState(position);
            if (fss != null) {
                fragment.setInitialSavedState(fss);
            }
        }
        while (mFragments.size() <= position) {
            mFragments.add(null);
        }
        fragment.setMenuVisibility(false);
        fragment.setUserVisibleHint(false);
        setCurrentFragment(position, fragment);

        mCurTransaction.add(container.getId(), fragment);

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;

        if (mCurTransaction == null) {
            mCurTransaction = mFragmentManager.beginTransaction();
        }
        if (DEBUG) Log.v(TAG, "Removing item #" + position + ": f=" + object
                + " v=" + ((Fragment) object).getView());
        while (mSavedState.size() <= position) {
            mSavedState.add(null);
        }
        int viewMode = findRealViewMode(position, object);
        if (viewMode >= 0) {
            setSavedState(position, viewMode, fragment.isAdded()
                    ? mFragmentManager.saveFragmentInstanceState(fragment) : null);
            setFragment(position, viewMode, null);
        }

        mCurTransaction.remove(fragment);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Fragment fragment = (Fragment) object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        if (mCurTransaction != null) {
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public Parcelable saveState() {
        Bundle state = null;
        if (mSavedState.size() > 0) {
            state = new Bundle();
            for (int i = 0; i < mSavedState.size(); i++) {
                state.putSparseParcelableArray("states" + i, mSavedState.get(i));
            }
        }
        for (int i = 0; i < mFragments.size(); i++) {
            SparseArray<Fragment> fragments = mFragments.get(i);
            for (int j = 0; j < fragments.size(); j++) {
                int k = fragments.keyAt(j);
                Fragment f = fragments.get(k);
                if (f != null && f.isAdded()) {
                    if (state == null) {
                        state = new Bundle();
                    }
                    String key = "f" + i + "-" + k;
                    mFragmentManager.putFragment(state, key, f);
                }
            }
        }
        return state;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        if (state != null) {
            Bundle bundle = (Bundle) state;
            bundle.setClassLoader(loader);
            mSavedState.clear();
            mFragments.clear();

            restoreSavedState(bundle);
            restoreFragments(bundle);
        }
    }

    @Override
    public int getItemPosition(Object object) {
        Integer position = positionMap.get(object);
        Integer viewMode = viewModeMap.get(object);
        if (position == null || viewMode == null) {
            return POSITION_NONE;
        }
        if (getCurrentViewMode(position) != viewMode) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    public void setViewMode(int position, int viewMode) {
        viewModes.put(position, viewMode);
    }

    private int getCurrentViewMode(int position) {
        return viewModes.get(position, 0);
    }

    private int findRealViewMode(int position, Object object) {
        Integer viewMode = viewModeMap.get(object);
        if (viewMode != null) {
            return viewMode;
        } else {
            return -1;
        }
    }

    // NEW: get fragment with position and current view mode
    private Fragment getCurrentFragment(int position) {
        SparseArray<Fragment> fragments = mFragments.get(position);
        if (fragments != null) {
            return fragments.get(getCurrentViewMode(position));
        } else {
            return null;
        }
    }

    // NEW: get saved state with position and current view mode
    private Fragment.SavedState getCurrentSavedState(int position) {
        SparseArray<Fragment.SavedState> states = mSavedState.get(position);
        if (states != null) {
            return states.get(getCurrentViewMode(position));
        } else {
            return null;
        }
    }

    // NEW: set fragment with position and view mode
    private void setFragment(int position, int viewMode, Fragment fragment) {
        SparseArray<Fragment> fragments = mFragments.get(position);
        if (fragments == null) {
            mFragments.set(position, new SparseArray<Fragment>());
        }
        mFragments.get(position).put(viewMode, fragment);
    }

    // NEW: set fragment with position and current view mode
    private void setCurrentFragment(int position, Fragment fragment) {
        setFragment(position, getCurrentViewMode(position), fragment);
    }

    // NEW: set saved state with position and view mode
    private void setSavedState(int position, int viewMode, Fragment.SavedState savedState) {
        SparseArray<Fragment.SavedState> states = mSavedState.get(position);
        if (states == null) {
            mSavedState.set(position, new SparseArray<Fragment.SavedState>());
        }
        mSavedState.get(position).put(viewMode, savedState);
    }

    // NEW: set saved state with position and current view mode
    private void setCurrentSavedState(int position, Fragment.SavedState savedState) {
        setSavedState(position, getCurrentViewMode(position), savedState);
    }

    // NEW: restore saved state from bundle
    private void restoreSavedState(Bundle bundle) {
        Iterable<String> keys = bundle.keySet();
        for (String key : keys) {
            if (key.startsWith("states")) {
                int position = Integer.parseInt(key.substring("states".length()));
                while (mSavedState.size() <= position) {
                    mSavedState.add(null);
                }
                SparseArray<Fragment.SavedState> states = bundle.getSparseParcelableArray(key);
                mSavedState.set(position, states);
            }
        }
    }

    // NEW: restore fragments from bundle
    private void restoreFragments(Bundle bundle) {
        Iterable<String> keys = bundle.keySet();
        for (String key : keys) {
            if (key.startsWith("f")) {
                // key sample "f1-2"
                int position = Integer.parseInt(key.substring(1, key.indexOf("-")));
                int viewMode = Integer.parseInt(key.substring(key.indexOf("-") + 1));
                Fragment f = mFragmentManager.getFragment(bundle, key);
                if (f != null) {
                    while (mFragments.size() <= position) {
                        mFragments.add(null);
                    }
                    f.setMenuVisibility(false);
                    setFragment(position, viewMode, f);
                } else {
                    Log.w(TAG, "Bad fragment at key " + key);
                }
            }
        }
    }

}
