/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actionbarsherlock.widget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.KeyEventCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.R;
import com.actionbarsherlock.view.CollapsibleActionView;
import com.actionbarsherlock.view.LinearLayoutExt;
import com.actionbarsherlock.view.LinearLayoutExt.LayoutChangeListener;

/**
 * A widget that provides a user interface for the user to enter a search query and submit a request
 * to a search provider. Shows a list of query suggestions or results, if available, and allows the
 * user to pick a suggestion or result to launch into.
 *
 * <p>
 * When the SearchView is used in an ActionBar as an action view for a collapsible menu item, it
 * needs to be set to iconified by default using {@link #setIconifiedByDefault(boolean)
 * setIconifiedByDefault(true)}. This is the default, so nothing needs to be done.
 * </p>
 * <p>
 * If you want the search field to always be visible, then call setIconifiedByDefault(false).
 * </p>
 *
 * <p>
 * For more information, see the <a href="{@docRoot}guide/topics/search/index.html">Search</a>
 * documentation.
 * <p>
 *
 * @see android.view.MenuItem#SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW
 * @attr ref android.R.styleable#SearchView_iconifiedByDefault
 * @attr ref android.R.styleable#SearchView_imeOptions
 * @attr ref android.R.styleable#SearchView_inputType
 * @attr ref android.R.styleable#SearchView_maxWidth
 * @attr ref android.R.styleable#SearchView_queryHint
 */
public class SearchViewSherlock extends LinearLayout implements CollapsibleActionView {

    private static final boolean DBG = false;
    private static final String LOG_TAG = "SearchView";

    /**
     * Private constant for removing the microphone in the keyboard.
     */
    private static final String IME_OPTION_NO_MICROPHONE = "nm";

    private OnQueryTextListener mOnQueryChangeListener;
    private OnCloseListener mOnCloseListener;
    private OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private OnSuggestionListener mOnSuggestionListener;
    private OnClickListener mOnSearchClickListener;
    private QuerySuggestionProvider mQuerySuggestionProvider;

    private boolean mIconifiedByDefault;
    private boolean mIconified;
    private ArrayAdapter<Spanned> mSuggestionsAdapter;
    private View mSearchButton;
    private View mSubmitButton;
    private View mSearchPlate;
    private View mSubmitArea;
    private ImageView mCloseButton;
    private View mSearchEditFrame;
    private View mVoiceButton;
    private SearchAutoComplete mQueryTextView;
    private LinearLayoutExt mDropDownAnchor;
    private ImageView mSearchHintIcon;
    private boolean mSubmitButtonEnabled;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private boolean mClearingFocus;
    private int mMaxWidth;
    private boolean mVoiceButtonEnabled;
    private CharSequence mOldQueryText;
    private CharSequence mUserQuery;
    private boolean mExpandedInActionView;
    private int mCollapsedImeOptions;

    private Bundle mAppSearchData;
    
    /*
     * SearchView can be set expanded before the IME is ready to be shown during
     * initial UI setup. The show operation is asynchronous to account for this.
     */
    private Runnable mShowImeRunnable = new Runnable() {
        public void run() {
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
//                imm.showSoftInputUnchecked(0, null);
                imm.showSoftInput(null, 0);
            }
        }
    };

    private Runnable mUpdateDrawableStateRunnable = new Runnable() {
        public void run() {
            updateFocusedState();
        }
    };
    
    private Runnable mReleaseCursorRunnable = new Runnable() {
        public void run() {
            //TODO support v4
//            if (mSuggestionsAdapter != null && mSuggestionsAdapter instanceof SuggestionsAdapter) {
//                mSuggestionsAdapter.changeCursor(null);
//            }
        }
    };

    // For voice searching
    private final Intent mVoiceWebSearchIntent;
    private final Intent mVoiceAppSearchIntent;

    // A weak map of drawables we've gotten from other packages, so we don't load them
    // more than once.
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache =
            new WeakHashMap<String, Drawable.ConstantState>();
    
    private QuerySuggestionAsyncTask mQuerySuggestionTask;
    private boolean mDisableSuggestion = false;
    
    /**
     * Callbacks for changes to the query text.
     */
    public interface OnQueryTextListener {

        /**
         * Called when the user submits the query. This could be due to a key press on the
         * keyboard or due to pressing a submit button.
         * The listener can override the standard behavior by returning true
         * to indicate that it has handled the submit request. Otherwise return false to
         * let the SearchView handle the submission by launching any associated intent.
         *
         * @param query the query text that is to be submitted
         *
         * @return true if the query has been handled by the listener, false to let the
         * SearchView perform the default action.
         */
        boolean onQueryTextSubmit(String query);

        /**
         * Called when the query text is changed by the user.
         *
         * @param newText the new content of the query text field.
         *
         * @return false if the SearchView should perform the default action of showing any
         * suggestions if available, true if the action was handled by the listener.
         */
        boolean onQueryTextChange(String newText);
    }

    public interface OnCloseListener {

        /**
         * The user is attempting to close the SearchView.
         *
         * @return true if the listener wants to override the default behavior of clearing the
         * text field and dismissing it, false otherwise.
         */
        boolean onClose();
    }

    /**
     * Callback interface for selection events on suggestions. These callbacks
     * are only relevant when a SearchableInfo has been specified by {@link #setSearchableInfo}.
     */
    public interface OnSuggestionListener {

        /**
         * Called when a suggestion was selected by navigating to it.
         * @param position the absolute position in the list of suggestions.
         *
         * @return true if the listener handles the event and wants to override the default
         * behavior of possibly rewriting the query based on the selected item, false otherwise.
         */
        boolean onSuggestionSelect(int position);

        /**
         * Called when a suggestion was clicked.
         * @param position the absolute position of the clicked item in the list of suggestions.
         *
         * @return true if the listener handles the event and wants to override the default
         * behavior of launching any intent or submitting a search query specified on that item.
         * Return false otherwise.
         */
        boolean onSuggestionClick(int position);
        
    }
    
    public SearchViewSherlock(Context context) {
        this(context, null);
    }

    public SearchViewSherlock(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.search_view, this, true);

        mSearchButton = findViewById(R.id.search_button);
        mQueryTextView = (SearchAutoComplete) findViewById(R.id.search_src_text);
        mQueryTextView.setSearchView(this);

        mSearchEditFrame = findViewById(R.id.search_edit_frame);
        mSearchPlate = findViewById(R.id.search_plate);
        mSubmitArea = findViewById(R.id.submit_area);
        mSubmitButton = findViewById(R.id.search_go_btn);
        mCloseButton = (ImageView) findViewById(R.id.search_close_btn);
        mVoiceButton = findViewById(R.id.search_voice_btn);
        mSearchHintIcon = (ImageView) findViewById(R.id.search_mag_icon);

        mSearchButton.setOnClickListener(mOnClickListener);
        mCloseButton.setOnClickListener(mOnClickListener);
        mSubmitButton.setOnClickListener(mOnClickListener);
        mVoiceButton.setOnClickListener(mOnClickListener);
        mQueryTextView.setOnClickListener(mOnClickListener);

        mQueryTextView.addTextChangedListener(mTextWatcher);
//        mQueryTextView.setOnEditorActionListener(mOnEditorActionListener);
        mQueryTextView.setOnItemClickListener(mOnItemClickListener);
        mQueryTextView.setOnItemSelectedListener(mOnItemSelectedListener);
        mQueryTextView.setOnKeyListener(mTextKeyListener);
        // Inform any listener of focus changes
        mQueryTextView.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (mOnQueryTextFocusChangeListener != null) {
                    mOnQueryTextFocusChangeListener.onFocusChange(SearchViewSherlock.this, hasFocus);
                }
            }
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SearchView, 0, 0);
        setIconifiedByDefault(a.getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        int maxWidth = a.getDimensionPixelSize(R.styleable.SearchView_maxWidth, -1);
        if (maxWidth != -1) {
            setMaxWidth(maxWidth);
        }
        CharSequence queryHint = a.getText(R.styleable.SearchView_queryHint);
        if (!TextUtils.isEmpty(queryHint)) {
            setQueryHint(queryHint);
        }
        int imeOptions = a.getInt(R.styleable.SearchView_imeOptions, -1);
        if (imeOptions != -1) {
            setImeOptions(imeOptions);
        }
        int inputType = a.getInt(R.styleable.SearchView_inputType, -1);
        if (inputType != -1) {
            setInputType(inputType);
        }

        a.recycle();

        boolean focusable = true;

        //TODO support v4
//        a = context.obtainStyledAttributes(attrs, android.R.styleable.View, 0, 0);
//        focusable = a.getBoolean(android.R.styleable.View_focusable, focusable);
        a.recycle();
        setFocusable(focusable);

        // Save voice intent for later queries/launching
        mVoiceWebSearchIntent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        mVoiceWebSearchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mVoiceWebSearchIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        mVoiceAppSearchIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mVoiceAppSearchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //TODO support v4
        mDropDownAnchor = (LinearLayoutExt) findViewById(mQueryTextView.getDropDownAnchor());
        if (mDropDownAnchor != null) {
            mDropDownAnchor.setOnLayoutChangeListener(new LayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom) {
                    adjustDropDownSizeAndPosition();
                }

            });
        }

        updateViewsVisibility(mIconifiedByDefault);
        updateQueryHint();
        
        setSuggestionsAdapter(new SuggestionsAdapter(context, this,
                new ArrayList<Spanned>()));
    }

    //TODO support v4
    /**
     * Sets the SearchableInfo for this SearchView. Properties in the SearchableInfo are used
     * to display labels, hints, suggestions, create intents for launching search results screens
     * and controlling other affordances such as a voice button.
     *
     * @param searchable a SearchableInfo can be retrieved from the SearchManager, for a specific
     * activity or a global search provider.
     */
//    public void setSearchableInfo(SearchableInfo searchable) {
//        mSearchable = searchable;
//        if (mSearchable != null) {
//            updateSearchAutoComplete();
//            updateQueryHint();
//        }
//        // Cache the voice search capability
//        mVoiceButtonEnabled = hasVoiceSearch();
//
//        if (mVoiceButtonEnabled) {
//            // Disable the microphone on the keyboard, as a mic is displayed near the text box
//            // TODO: use imeOptions to disable voice input when the new API will be available
//            mQueryTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
//        }
//        updateViewsVisibility(isIconified());
//    }

    /**
     * Sets the APP_DATA for legacy SearchDialog use.
     * @param appSearchData bundle provided by the app when launching the search dialog
     * @hide
     */
    public void setAppSearchData(Bundle appSearchData) {
        mAppSearchData = appSearchData;
    }

    /**
     * Sets the IME options on the query text field.
     *
     * @see TextView#setImeOptions(int)
     * @param imeOptions the options to set on the query text field
     *
     * @attr ref android.R.styleable#SearchView_imeOptions
     */
    public void setImeOptions(int imeOptions) {
        mQueryTextView.setImeOptions(imeOptions);
    }

    /**
     * Sets the input type on the query text field.
     *
     * @see TextView#setInputType(int)
     * @param inputType the input type to set on the query text field
     *
     * @attr ref android.R.styleable#SearchView_inputType
     */
    public void setInputType(int inputType) {
        mQueryTextView.setInputType(inputType);
    }

    /** @hide */
    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        // Don't accept focus if in the middle of clearing focus
        if (mClearingFocus) return false;
        // Check if SearchView is focusable.
        if (!isFocusable()) return false;
        // If it is not iconified, then give the focus to the text field
        if (!isIconified()) {
            boolean result = mQueryTextView.requestFocus(direction, previouslyFocusedRect);
            if (result) {
                updateViewsVisibility(false);
            }
            return result;
        } else {
            return super.requestFocus(direction, previouslyFocusedRect);
        }
    }

    /** @hide */
    @Override
    public void clearFocus() {
        mClearingFocus = true;
        setImeVisibility(false);
        super.clearFocus();
        mQueryTextView.clearFocus();
        mClearingFocus = false;
    }

    /**
     * Sets a listener for user actions within the SearchView.
     *
     * @param listener the listener object that receives callbacks when the user performs
     * actions in the SearchView such as clicking on buttons or typing a query.
     */
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        mOnQueryChangeListener = listener;
    }

    /**
     * Sets a listener to inform when the user closes the SearchView.
     *
     * @param listener the listener to call when the user closes the SearchView.
     */
    public void setOnCloseListener(OnCloseListener listener) {
        mOnCloseListener = listener;
    }

    /**
     * Sets a listener to inform when the focus of the query text field changes.
     *
     * @param listener the listener to inform of focus changes.
     */
    public void setOnQueryTextFocusChangeListener(OnFocusChangeListener listener) {
        mOnQueryTextFocusChangeListener = listener;
    }

    /**
     * Sets a listener to inform when a suggestion is focused or clicked.
     *
     * @param listener the listener to inform of suggestion selection events.
     */
    public void setOnSuggestionListener(OnSuggestionListener listener) {
        mOnSuggestionListener = listener;
    }

    /**
     * Sets a listener to inform when the search button is pressed. This is only
     * relevant when the text field is not visible by default. Calling {@link #setIconified
     * setIconified(false)} can also cause this listener to be informed.
     *
     * @param listener the listener to inform when the search button is clicked or
     * the text field is programmatically de-iconified.
     */
    public void setOnSearchClickListener(OnClickListener listener) {
        mOnSearchClickListener = listener;
    }
    
    public void setQuerySuggestionProvider(QuerySuggestionProvider provider) {
        mQuerySuggestionProvider = provider;
    }

    /**
     * Returns the query string currently in the text field.
     *
     * @return the query string
     */
    public CharSequence getQuery() {
        return mQueryTextView.getText();
    }

    /**
     * Sets a query string in the text field and optionally submits the query as well.
     *
     * @param query the query string. This replaces any query text already present in the
     * text field.
     * @param submit whether to submit the query right now or only update the contents of
     * text field.
     */
    public void setQuery(CharSequence query, boolean submit) {
        setQueryWithoutSuggestion(query);
        
        if (query != null) {
            mQueryTextView.setSelection(query.length());
            mUserQuery = query;
        }

        // If the query is not empty and submit is requested, submit the query
        if (submit && !TextUtils.isEmpty(query)) {
            onSubmitQuery();
        }
    }

    /**
     * Sets the hint text to display in the query text field. This overrides any hint specified
     * in the SearchableInfo.
     *
     * @param hint the hint text to display
     *
     * @attr ref android.R.styleable#SearchView_queryHint
     */
    public void setQueryHint(CharSequence hint) {
        mQueryHint = hint;
        updateQueryHint();
    }

    /**
     * Sets the default or resting state of the search field. If true, a single search icon is
     * shown by default and expands to show the text field and other buttons when pressed. Also,
     * if the default state is iconified, then it collapses to that state when the close button
     * is pressed. Changes to this property will take effect immediately.
     *
     * <p>The default value is true.</p>
     *
     * @param iconified whether the search field should be iconified by default
     *
     * @attr ref android.R.styleable#SearchView_iconifiedByDefault
     */
    public void setIconifiedByDefault(boolean iconified) {
        if (mIconifiedByDefault == iconified) return;
        mIconifiedByDefault = iconified;
        updateViewsVisibility(iconified);
        updateQueryHint();
    }

    /**
     * Returns the default iconified state of the search field.
     * @return
     */
    public boolean isIconfiedByDefault() {
        return mIconifiedByDefault;
    }

    /**
     * Iconifies or expands the SearchView. Any query text is cleared when iconified. This is
     * a temporary state and does not override the default iconified state set by
     * {@link #setIconifiedByDefault(boolean)}. If the default state is iconified, then
     * a false here will only be valid until the user closes the field. And if the default
     * state is expanded, then a true here will only clear the text field and not close it.
     *
     * @param iconify a true value will collapse the SearchView to an icon, while a false will
     * expand it.
     */
    public void setIconified(boolean iconify) {
        if (iconify) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    /**
     * Returns the current iconified state of the SearchView.
     *
     * @return true if the SearchView is currently iconified, false if the search field is
     * fully visible.
     */
    public boolean isIconified() {
        return mIconified;
    }

    /**
     * Enables showing a submit button when the query is non-empty. In cases where the SearchView
     * is being used to filter the contents of the current activity and doesn't launch a separate
     * results activity, then the submit button should be disabled.
     *
     * @param enabled true to show a submit button for submitting queries, false if a submit
     * button is not required.
     */
    public void setSubmitButtonEnabled(boolean enabled) {
        mSubmitButtonEnabled = enabled;
        updateViewsVisibility(isIconified());
    }

    /**
     * Returns whether the submit button is enabled when necessary or never displayed.
     *
     * @return whether the submit button is enabled automatically when necessary
     */
    public boolean isSubmitButtonEnabled() {
        return mSubmitButtonEnabled;
    }

    /**
     * Specifies if a query refinement button should be displayed alongside each suggestion
     * or if it should depend on the flags set in the individual items retrieved from the
     * suggestions provider. Clicking on the query refinement button will replace the text
     * in the query text field with the text from the suggestion. This flag only takes effect
     * if a SearchableInfo has been specified with {@link #setSearchableInfo(SearchableInfo)}
     * and not when using a custom adapter.
     *
     * @param enable true if all items should have a query refinement button, false if only
     * those items that have a query refinement flag set should have the button.
     *
     * @see SearchManager#SUGGEST_COLUMN_FLAGS
     * @see SearchManager#FLAG_QUERY_REFINEMENT
     */
    public void setQueryRefinementEnabled(boolean enable) {
        mQueryRefinement = enable;
//        if (mSuggestionsAdapter instanceof SuggestionsAdapter) {
//            ((SuggestionsAdapter) mSuggestionsAdapter).setQueryRefinement(
//                    enable ? SuggestionsAdapter.REFINE_ALL : SuggestionsAdapter.REFINE_BY_ENTRY);
//        }
    }

    /**
     * Returns whether query refinement is enabled for all items or only specific ones.
     * @return true if enabled for all items, false otherwise.
     */
    public boolean isQueryRefinementEnabled() {
        return mQueryRefinement;
    }

    /**
     * You can set a custom adapter if you wish. Otherwise the default adapter is used to
     * display the suggestions from the suggestions provider associated with the SearchableInfo.
     *
     * @see #setSearchableInfo(SearchableInfo)
     */
    public void setSuggestionsAdapter(ArrayAdapter<Spanned> adapter) {
        mSuggestionsAdapter = adapter;
        mQueryTextView.setAdapter(mSuggestionsAdapter);
    }

    /**
     * Returns the adapter used for suggestions, if any.
     * @return the suggestions adapter
     */
    public ArrayAdapter<Spanned> getSuggestionsAdapter() {
        return mSuggestionsAdapter;
    }

    /**
     * Makes the view at most this many pixels wide
     *
     * @attr ref android.R.styleable#SearchView_maxWidth
     */
    public void setMaxWidth(int maxpixels) {
        mMaxWidth = maxpixels;

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Let the standard measurements take effect in iconified state.
        if (isIconified()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        switch (widthMode) {
        case MeasureSpec.AT_MOST:
            // If there is an upper limit, don't exceed maximum width (explicit or implicit)
            if (mMaxWidth > 0) {
                width = Math.min(mMaxWidth, width);
            } else {
                width = Math.min(getPreferredWidth(), width);
            }
            break;
        case MeasureSpec.EXACTLY:
            // If an exact width is specified, still don't exceed any specified maximum width
            if (mMaxWidth > 0) {
                width = Math.min(mMaxWidth, width);
            }
            break;
        case MeasureSpec.UNSPECIFIED:
            // Use maximum width, if specified, else preferred width
            width = mMaxWidth > 0 ? mMaxWidth : getPreferredWidth();
            break;
        }
        widthMode = MeasureSpec.EXACTLY;
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, widthMode), heightMeasureSpec);
    }

    private int getPreferredWidth() {
        return getContext().getResources()
                .getDimensionPixelSize(R.dimen.search_view_preferred_width);
    }

    private void updateViewsVisibility(final boolean collapsed) {
        mIconified = collapsed;
        // Visibility of views that are visible when collapsed
        final int visCollapsed = collapsed ? VISIBLE : GONE;
        // Is there text in the query
        final boolean hasText = !TextUtils.isEmpty(mQueryTextView.getText());

        mSearchButton.setVisibility(visCollapsed);
        updateSubmitButton(hasText);
        mSearchEditFrame.setVisibility(collapsed ? GONE : VISIBLE);
        mSearchHintIcon.setVisibility(mIconifiedByDefault ? GONE : VISIBLE);
        updateCloseButton();
        updateVoiceButton(!hasText);
        updateSubmitArea();
    }

    //TODO support v4
//    private boolean hasVoiceSearch() {
//        if (mSearchable != null && mSearchable.getVoiceSearchEnabled()) {
//            Intent testIntent = null;
//            if (mSearchable.getVoiceSearchLaunchWebSearch()) {
//                testIntent = mVoiceWebSearchIntent;
//            } else if (mSearchable.getVoiceSearchLaunchRecognizer()) {
//                testIntent = mVoiceAppSearchIntent;
//            }
//            if (testIntent != null) {
//                ResolveInfo ri = getContext().getPackageManager().resolveActivity(testIntent,
//                        PackageManager.MATCH_DEFAULT_ONLY);
//                return ri != null;
//            }
//        }
//        return false;
//    }

    private boolean isSubmitAreaEnabled() {
        return (mSubmitButtonEnabled || mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean hasText) {
        int visibility = GONE;
        if (mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus()
                && (hasText || !mVoiceButtonEnabled)) {
            visibility = VISIBLE;
        }
        mSubmitButton.setVisibility(visibility);
    }

    private void updateSubmitArea() {
        int visibility = GONE;
        if (isSubmitAreaEnabled()
                && (mSubmitButton.getVisibility() == VISIBLE
                        || mVoiceButton.getVisibility() == VISIBLE)) {
            visibility = VISIBLE;
        }
        mSubmitArea.setVisibility(visibility);
    }

    private void updateCloseButton() {
        final boolean hasText = !TextUtils.isEmpty(mQueryTextView.getText());
        // Should we show the close button? It is not shown if there's no focus,
        // field is not iconified by default and there is no text in it.
        final boolean showClose = hasText || (mIconifiedByDefault && !mExpandedInActionView);
        mCloseButton.setVisibility(showClose ? VISIBLE : GONE);
        mCloseButton.getDrawable().setState(hasText ? ENABLED_STATE_SET : EMPTY_STATE_SET);
    }

    private void postUpdateFocusedState() {
        post(mUpdateDrawableStateRunnable);
    }

    private void updateFocusedState() {
        boolean focused = mQueryTextView.hasFocus();
        mSearchPlate.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
        mSubmitArea.getBackground().setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(mUpdateDrawableStateRunnable);
        post(mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    private void setImeVisibility(final boolean visible) {
        if (visible) {
            post(mShowImeRunnable);
        } else {
            removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (imm != null) {
                imm.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

    /**
     * Called by the SuggestionsAdapter
     * @hide
     */
    /* package */void onQueryRefine(CharSequence queryText) {
        setQuery(queryText);
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {

        public void onClick(View v) {
            if (v == mSearchButton) {
                onSearchClicked();
            } else if (v == mCloseButton) {
                onCloseClicked();
            } else if (v == mSubmitButton) {
                onSubmitQuery();
            } else if (v == mVoiceButton) {
                onVoiceClicked();
            } else if (v == mQueryTextView) {
//                forceSuggestionQuery();
            }
        }
    };

    /**
     * Handles the key down event for dealing with action keys.
     *
     * @param keyCode This is the keycode of the typed key, and is the same value as
     *        found in the KeyEvent parameter.
     * @param event The complete event record for the typed key
     *
     * @return true if the event was handled here, or false if not.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //TODO support v4
//        if (mSearchable == null) {
//            return false;
//        }

        // if it's an action specified by the searchable activity, launch the
        // entered query with the action key
//        SearchableInfo.ActionKeyInfo actionKey = mSearchable.findActionKey(keyCode);
//        if ((actionKey != null) && (actionKey.getQueryActionMsg() != null)) {
//            launchQuerySearch(keyCode, /*actionKey.getQueryActionMsg()*/ getContext().getString(R.string.searchview_description_search), mQueryTextView.getText()
//                    .toString());
//            return true;
//        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * React to the user typing "enter" or other hardwired keys while typing in
     * the search box. This handles these special keys while the edit box has
     * focus.
     */
    View.OnKeyListener mTextKeyListener = new View.OnKeyListener() {
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // guard against possible race conditions
          //TODO support v4
//            if (mSearchable == null) {
//                return false;
//            }

            if (DBG) {
                Log.d(LOG_TAG, "mTextListener.onKey(" + keyCode + "," + event + "), selection: "
                        + mQueryTextView.getListSelection());
            }

            // If a suggestion is selected, handle enter, search key, and action keys
            // as presses on the selected suggestion
            if (mQueryTextView.isPopupShowing()
                    && mQueryTextView.getListSelection() != ListView.INVALID_POSITION) {
                return onSuggestionsKey(v, keyCode, event);
            }

            // If there is text in the query box, handle enter, and action keys
            // The search key is handled by the dialog's onKeyDown().
            if (!mQueryTextView.isEmpty() && KeyEventCompat.hasNoModifiers(event)) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        v.cancelLongPress();

                        // Launch as a regular search.
                        launchQuerySearch(KeyEvent.KEYCODE_UNKNOWN, null, mQueryTextView.getText()
                                .toString());
                        return true;
                    }
                }
                //support v4
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    //TODO support v4
////                    SearchableInfo.ActionKeyInfo actionKey = mSearchable.findActionKey(keyCode);
////                    if ((actionKey != null) && (actionKey.getQueryActionMsg() != null)) {
//                        launchQuerySearch(keyCode, /*actionKey.getQueryActionMsg()*/ getContext().getString(R.string.searchview_description_search), mQueryTextView
//                                .getText().toString());
//                        return true;
////                    }
//                }
            }
            return false;
        }
    };

    /**
     * React to the user typing while in the suggestions list. First, check for
     * action keys. If not handled, try refocusing regular characters into the
     * EditText.
     */
    private boolean onSuggestionsKey(View v, int keyCode, KeyEvent event) {
        // guard against possible race conditions (late arrival after dismiss)
      //TODO support v4
//        if (mSearchable == null) {
//            return false;
//        }
        if (mSuggestionsAdapter == null) {
            return false;
        }
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEventCompat.hasNoModifiers(event)) {
            // First, check for enter or search (both of which we'll treat as a
            // "click")
            if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH
                    || keyCode == KeyEvent.KEYCODE_TAB) {
                int position = mQueryTextView.getListSelection();
                return onItemClicked(position, KeyEvent.KEYCODE_UNKNOWN, null);
            }

            // Next, check for left/right moves, which we use to "return" the
            // user to the edit view
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                // give "focus" to text editor, with cursor at the beginning if
                // left key, at end if right key
                // TODO: Reverse left/right for right-to-left languages, e.g.
                // Arabic
                int selPoint = (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) ? 0 : mQueryTextView
                        .length();
                mQueryTextView.setSelection(selPoint);
                mQueryTextView.setListSelection(0);
                mQueryTextView.clearListSelection();
                //TODO support v4
                mQueryTextView.invokeEnsureImeVisible(true);

                return true;
            }

            // Next, check for an "up and out" move
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP && 0 == mQueryTextView.getListSelection()) {
                // TODO: restoreUserQuery();
                // let ACTV complete the move
                return false;
            }

            //TODO support v4
            // Next, check for an "action key"
//            SearchableInfo.ActionKeyInfo actionKey = mSearchable.findActionKey(keyCode);
//            if ((actionKey != null)
//                    && ((actionKey.getSuggestActionMsg() != null) || (actionKey
//                            .getSuggestActionMsgColumn() != null))) {
//                // launch suggestion using action key column
//                int position = mQueryTextView.getListSelection();
//                if (position != ListView.INVALID_POSITION) {
//                    Cursor c = mSuggestionsAdapter.getCursor();
//                    if (c.moveToPosition(position)) {
//                        final String actionMsg = getActionKeyMessage(c, actionKey);
//                        if (actionMsg != null && (actionMsg.length() > 0)) {
//                            return onItemClicked(position, keyCode, actionMsg);
//                        }
//                    }
//                }
//            }
        }
        return false;
    }

    /**
     * For a given suggestion and a given cursor row, get the action message. If
     * not provided by the specific row/column, also check for a single
     * definition (for the action key).
     *
     * @param c The cursor providing suggestions
     * @param actionKey The actionkey record being examined
     *
     * @return Returns a string, or null if no action key message for this
     *         suggestion
     */
    //TODO support v4
//    private static String getActionKeyMessage(Cursor c, SearchableInfo.ActionKeyInfo actionKey) {
//        String result = null;
//        // check first in the cursor data, for a suggestion-specific message
//        final String column = actionKey.getSuggestActionMsgColumn();
//        if (column != null) {
//            result = SuggestionsAdapter.getColumnString(c, column);
//        }
//        // If the cursor didn't give us a message, see if there's a single
//        // message defined
//        // for the actionkey (for all suggestions)
//        if (result == null) {
//            result = actionKey.getSuggestActionMsg();
//        }
//        return result;
//    }

    private int getSearchIconId() {
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.searchViewSearchIcon,
                outValue, true);
        return outValue.resourceId;
    }

    private CharSequence getDecoratedHint(CharSequence hintText) {
        // If the field is always expanded, then don't add the search icon to the hint
        if (!mIconifiedByDefault) return hintText;

        SpannableStringBuilder ssb = new SpannableStringBuilder("   "); // for the icon
        ssb.append(hintText);
        Drawable searchIcon = getContext().getResources().getDrawable(getSearchIconId());
        int textSize = (int) (mQueryTextView.getTextSize() * 1.25);
        searchIcon.setBounds(0, 0, textSize, textSize);
        ssb.setSpan(new ImageSpan(searchIcon), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    private void updateQueryHint() {
      //TODO support 4
        if (mQueryHint != null) {
            mQueryTextView.setHint(getDecoratedHint(mQueryHint));
            //TODO support v4
//        } else if (mSearchable != null) {
//            CharSequence hint = null;
//            int hintId = mSearchable.getHintId();
//            if (hintId != 0) {
//                hint = getContext().getString(hintId);
//            }
//            if (hint != null) {
//                mQueryTextView.setHint(getDecoratedHint(hint));
//            }
        } else {
            mQueryTextView.setHint(getDecoratedHint(""));
        }
    }

    /**
     * Updates the auto-complete text view.
     */
    private void updateSearchAutoComplete() {
      //TODO support v4
//        mQueryTextView.setDropDownAnimationStyle(0); // no animation
//        mQueryTextView.setThreshold(mSearchable.getSuggestThreshold());
//        mQueryTextView.setImeOptions(mSearchable.getImeOptions());
//        int inputType = mSearchable.getInputType();
        // We only touch this if the input type is set up for text (which it almost certainly
        // should be, in the case of search!)
//        if ((inputType & InputType.TYPE_MASK_CLASS) == InputType.TYPE_CLASS_TEXT) {
//            // The existence of a suggestions authority is the proxy for "suggestions
//            // are available here"
//            inputType &= ~InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
//            if (mSearchable.getSuggestAuthority() != null) {
//                inputType |= InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;
//            }
//        }
//        mQueryTextView.setInputType(inputType);
//        if (mSuggestionsAdapter != null) {
//            mSuggestionsAdapter.changeCursor(null);
//        }
        // attach the suggestions adapter, if suggestions are available
        // The existence of a suggestions authority is the proxy for "suggestions available here"
//        if (mSearchable.getSuggestAuthority() != null) {
//            mSuggestionsAdapter = new SuggestionsAdapter(getContext(),
//                    this, mSearchable, mOutsideDrawablesCache);
//            mQueryTextView.setAdapter(mSuggestionsAdapter);
//            ((SuggestionsAdapter) mSuggestionsAdapter).setQueryRefinement(
//                    mQueryRefinement ? SuggestionsAdapter.REFINE_ALL
//                    : SuggestionsAdapter.REFINE_BY_ENTRY);
//        }
    }

    /**
     * Update the visibility of the voice button.  There are actually two voice search modes,
     * either of which will activate the button.
     * @param empty whether the search query text field is empty. If it is, then the other
     * criteria apply to make the voice button visible.
     */
    private void updateVoiceButton(boolean empty) {
        int visibility = GONE;
        if (mVoiceButtonEnabled && !isIconified() && empty) {
            visibility = VISIBLE;
            mSubmitButton.setVisibility(GONE);
        }
        mVoiceButton.setVisibility(visibility);
    }

    private final OnEditorActionListener mOnEditorActionListener = new OnEditorActionListener() {

        /**
         * Called when the input method default action key is pressed.
         */
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                onSubmitQuery();
            }
            return true;
        }
    };

    private void onTextChanged(CharSequence newText) {
        CharSequence text = mQueryTextView.getText();
        mUserQuery = text;
        boolean hasText = !TextUtils.isEmpty(text);
        updateSubmitButton(hasText);
        updateVoiceButton(!hasText);
        updateCloseButton();
        updateSubmitArea();
        if (mOnQueryChangeListener != null && !TextUtils.equals(newText, mOldQueryText)) {
            mOnQueryChangeListener.onQueryTextChange(newText.toString());
        }
        mOldQueryText = newText.toString();
    }
    
    private void afterTextChanged(Editable newText) {
        if (!mDisableSuggestion) {
            querySuggsetion(newText.toString());
        }
    }

    private void onSubmitQuery() {
      //TODO support v4
        CharSequence query = mQueryTextView.getText();
        if (query != null && TextUtils.getTrimmedLength(query) > 0) {
            if (mOnQueryChangeListener == null
                    || !mOnQueryChangeListener.onQueryTextSubmit(query.toString())) {
//                if (mSearchable != null) {
//                    launchQuerySearch(KeyEvent.KEYCODE_UNKNOWN, null, query.toString());
//                    setImeVisibility(false);
//                }
                dismissSuggestions();
            }
        }
    }

    private void dismissSuggestions() {
        clearSuggestions();
        mQueryTextView.dismissDropDown();
    }

    private void clearSuggestions() {
        setSuggestionsAdapter(new SuggestionsAdapter(getContext(), this, new ArrayList<Spanned>()));
    }

    private void onCloseClicked() {
        CharSequence text = mQueryTextView.getText();
        if (TextUtils.isEmpty(text)) {
            if (mIconifiedByDefault) {
                // If the app doesn't override the close behavior
                if (mOnCloseListener == null || !mOnCloseListener.onClose()) {
                    // hide the keyboard and remove focus
                    clearFocus();
                    // collapse the search field
                    updateViewsVisibility(true);
                }
            }
        } else {
            mQueryTextView.setText("");
            mQueryTextView.requestFocus();
            setImeVisibility(true);
        }

    }

    private void onSearchClicked() {
        requestFocus();
        updateViewsVisibility(false);
        mQueryTextView.requestFocus();
        setImeVisibility(true);
        if (mOnSearchClickListener != null) {
            mOnSearchClickListener.onClick(this);
        }
    }

    private void onVoiceClicked() {
        // guard against possible race conditions
      //TODO support v4
//        if (mSearchable == null) {
//            return;
//        }
//        SearchableInfo searchable = mSearchable;
//        try {
//            if (searchable.getVoiceSearchLaunchWebSearch()) {
//                Intent webSearchIntent = createVoiceWebSearchIntent(mVoiceWebSearchIntent,
//                        searchable);
//                getContext().startActivity(webSearchIntent);
//            } else if (searchable.getVoiceSearchLaunchRecognizer()) {
//                Intent appSearchIntent = createVoiceAppSearchIntent(mVoiceAppSearchIntent,
//                        searchable);
//                getContext().startActivity(appSearchIntent);
//            }
//        } catch (ActivityNotFoundException e) {
//            // Should not happen, since we check the availability of
//            // voice search before showing the button. But just in case...
//            Log.w(LOG_TAG, "Could not find voice search activity");
//        }
    }

    void onTextFocusChanged() {
        updateViewsVisibility(isIconified());
        // Delayed update to make sure that the focus has settled down and window focus changes
        // don't affect it. A synchronous update was not working.
        postUpdateFocusedState();
        if (mQueryTextView.hasFocus()) {
            //TODO support v4
//            forceSuggestionQuery();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);

        postUpdateFocusedState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionViewCollapsed() {
        clearFocus();
        updateViewsVisibility(true);
        mQueryTextView.setImeOptions(mCollapsedImeOptions);
        mExpandedInActionView = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onActionViewExpanded() {
      //TODO support v4
        if (mExpandedInActionView) return;

        mExpandedInActionView = true;
        mCollapsedImeOptions = mQueryTextView.getImeOptions();
        mQueryTextView.setImeOptions(mCollapsedImeOptions /*| EditorInfo.IME_FLAG_NO_FULLSCREEN*/);
        mQueryTextView.setText("");
        setIconified(false);
    }

    private void adjustDropDownSizeAndPosition() {
        if (mDropDownAnchor.getWidth() > 1) {
            Resources res = getContext().getResources();
            int anchorPadding = mSearchPlate.getPaddingLeft();
            Rect dropDownPadding = new Rect();
            int iconOffset = mIconifiedByDefault
                    ? res.getDimensionPixelSize(R.dimen.dropdownitem_icon_width)
                    + res.getDimensionPixelSize(R.dimen.dropdownitem_text_padding_left)
                    : 0;
            mQueryTextView.getDropDownBackground().getPadding(dropDownPadding);
            mQueryTextView.setDropDownHorizontalOffset(-(dropDownPadding.left + iconOffset)
                    + anchorPadding);
            mQueryTextView.setDropDownWidth(mDropDownAnchor.getWidth() + dropDownPadding.left
                    + dropDownPadding.right + iconOffset - (anchorPadding));
        }
    }

    private boolean onItemClicked(int position, int actionKey, String actionMsg) {
        if (mOnSuggestionListener == null
                || !mOnSuggestionListener.onSuggestionClick(position)) {
            //TODO support v4
            
            Spanned query = mSuggestionsAdapter.getItem(position);
            
            if (mOnQueryChangeListener != null) {
                mOnQueryChangeListener.onQueryTextSubmit(query.toString());
            }
            
//            launchSuggestion(position, KeyEvent.KEYCODE_UNKNOWN, null);
            setImeVisibility(false);
            dismissSuggestions();
            
            setQueryWithoutSuggestion(query);
            return true;
        }
        return false;
    }

    private boolean onItemSelected(int position) {
        if (mOnSuggestionListener == null
                || !mOnSuggestionListener.onSuggestionSelect(position)) {
            rewriteQueryFromSuggestion(position);
            return true;
        }
        return false;
    }

    private final OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        /**
         * Implements OnItemClickListener
         */
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (DBG) Log.d(LOG_TAG, "onItemClick() position " + position);
            onItemClicked(position, KeyEvent.KEYCODE_UNKNOWN, null);
        }
    };

    private final OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

        /**
         * Implements OnItemSelectedListener
         */
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (DBG) Log.d(LOG_TAG, "onItemSelected() position " + position);
            SearchViewSherlock.this.onItemSelected(position);
        }

        /**
         * Implements OnItemSelectedListener
         */
        public void onNothingSelected(AdapterView<?> parent) {
            if (DBG)
                Log.d(LOG_TAG, "onNothingSelected()");
        }
    };

    /**
     * Query rewriting.
     */
    private void rewriteQueryFromSuggestion(int position) {
        CharSequence oldQuery = mQueryTextView.getText();

        CharSequence newQuery = mSuggestionsAdapter.getItem(position);
        if (newQuery != null) {
            // The suggestion rewrites the query.
            // Update the text field, without getting new suggestions.
            setQuery(newQuery);
        } else {
            // The suggestion does not rewrite the query, restore the user's
            // query.
            setQuery(oldQuery);
        }
    }

    /**
     * Launches an intent based on a suggestion.
     *
     * @param position The index of the suggestion to create the intent from.
     * @param actionKey The key code of the action key that was pressed,
     *        or {@link KeyEvent#KEYCODE_UNKNOWN} if none.
     * @param actionMsg The message for the action key that was pressed,
     *        or <code>null</code> if none.
     * @return true if a successful launch, false if could not (e.g. bad position).
     */
    //TODO support v4
//    private boolean launchSuggestion(int position, int actionKey, String actionMsg) {
//        Cursor c = mSuggestionsAdapter.getCursor();
//        if ((c != null) && c.moveToPosition(position)) {
//
//            Intent intent = createIntentFromSuggestion(c, actionKey, actionMsg);
//
//            // launch the intent
//            launchIntent(intent);
//
//            return true;
//        }
//        return false;
//    }

    /**
     * Launches an intent, including any special intent handling.
     */
    //TODO support v4
//    private void launchIntent(Intent intent) {
//        if (intent == null) {
//            return;
//        }
//        try {
//            // If the intent was created from a suggestion, it will always have an explicit
//            // component here.
//            getContext().startActivity(intent);
//        } catch (RuntimeException ex) {
//            Log.e(LOG_TAG, "Failed launch activity: " + intent, ex);
//        }
//    }

    /**
     * Sets the text in the query box, without updating the suggestions.
     */
    private void setQuery(CharSequence query) {
        mQueryTextView.setText(query);
        
        // Move the cursor to the end
        mQueryTextView.setSelection(TextUtils.isEmpty(query) ? 0 : query.length());
    }
    
    private void setQueryWithoutSuggestion(CharSequence query) {
        mDisableSuggestion = true;
        setQuery(query);
        mDisableSuggestion = false;
    }

    private void launchQuerySearch(int actionKey, String actionMsg, String query) {
        //TODO support v4
//        String action = Intent.ACTION_SEARCH;
//        Intent intent = createIntent(action, null, null, query, actionKey, actionMsg);
//        getContext().startActivity(intent);
        if (mOnQueryChangeListener != null) {
            mOnQueryChangeListener.onQueryTextSubmit(query);
        }
        dismissSuggestions();
    }

    /**
     * Constructs an intent from the given information and the search dialog state.
     *
     * @param action Intent action.
     * @param data Intent data, or <code>null</code>.
     * @param extraData Data for {@link SearchManager#EXTRA_DATA_KEY} or <code>null</code>.
     * @param query Intent query, or <code>null</code>.
     * @param actionKey The key code of the action key that was pressed,
     *        or {@link KeyEvent#KEYCODE_UNKNOWN} if none.
     * @param actionMsg The message for the action key that was pressed,
     *        or <code>null</code> if none.
     * @param mode The search mode, one of the acceptable values for
     *             {@link SearchManager#SEARCH_MODE}, or {@code null}.
     * @return The intent.
     */
//    private Intent createIntent(String action, Uri data, String extraData, String query,
//            int actionKey, String actionMsg) {
//        // Now build the Intent
//        Intent intent = new Intent(action);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        // We need CLEAR_TOP to avoid reusing an old task that has other activities
//        // on top of the one we want. We don't want to do this in in-app search though,
//        // as it can be destructive to the activity stack.
//        if (data != null) {
//            intent.setData(data);
//        }
//        intent.putExtra(SearchManager.USER_QUERY, mUserQuery);
//        if (query != null) {
//            intent.putExtra(SearchManager.QUERY, query);
//        }
//        if (extraData != null) {
//            intent.putExtra(SearchManager.EXTRA_DATA_KEY, extraData);
//        }
//        if (mAppSearchData != null) {
//            intent.putExtra(SearchManager.APP_DATA, mAppSearchData);
//        }
//        if (actionKey != KeyEvent.KEYCODE_UNKNOWN) {
//            intent.putExtra(SearchManager.ACTION_KEY, actionKey);
//            intent.putExtra(SearchManager.ACTION_MSG, actionMsg);
//        }
//      //TODO support v4
////        intent.setComponent(mSearchable.getSearchActivity());
//        return intent;
//    }

//    private void forceSuggestionQuery() {
//        mQueryTextView.doBeforeTextChanged();
//        mQueryTextView.doAfterTextChanged();
//    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Callback to watch the text field for empty/non-empty
     */
    private TextWatcher mTextWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int before, int after) { }

        public void onTextChanged(CharSequence s, int start,
                int before, int after) {
            SearchViewSherlock.this.onTextChanged(s);
        }

        public void afterTextChanged(Editable s) {
            SearchViewSherlock.this.afterTextChanged(s);
        }
    };

    /**
     * Local subclass for AutoCompleteTextView.
     * @hide
     */
    public static class SearchAutoComplete extends AutoCompleteTextView {

        private int mThreshold;
        private SearchViewSherlock mSearchView;

        public SearchAutoComplete(Context context) {
            super(context);
            mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attrs) {
            super(context, attrs);
            mThreshold = getThreshold();
        }

        public SearchAutoComplete(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            mThreshold = getThreshold();
        }

        void setSearchView(SearchViewSherlock searchView) {
            mSearchView = searchView;
        }

        @Override
        public void setThreshold(int threshold) {
            super.setThreshold(threshold);
            mThreshold = threshold;
        }

        /**
         * Returns true if the text field is empty, or contains only whitespace.
         */
        private boolean isEmpty() {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        /**
         * We override this method to avoid replacing the query box text when a
         * suggestion is clicked.
         */
        @Override
        protected void replaceText(CharSequence text) {
        }

        /**
         * We override this method to avoid an extra onItemClick being called on
         * the drop-down's OnItemClickListener by
         * {@link AutoCompleteTextView#onKeyUp(int, KeyEvent)} when an item is
         * clicked with the trackball.
         */
        @Override
        public void performCompletion() {
        }

        /**
         * We override this method to be sure and show the soft keyboard if
         * appropriate when the TextView has focus.
         */
        @Override
        public void onWindowFocusChanged(boolean hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);

            if (hasWindowFocus && mSearchView.hasFocus() && getVisibility() == VISIBLE) {
                InputMethodManager inputManager = (InputMethodManager) getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(this, 0);
                // If in landscape mode, then make sure that
                // the ime is in front of the dropdown.
                if (isLandscapeMode(getContext())) {
                    invokeEnsureImeVisible(true);
                }
            }
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
            mSearchView.onTextFocusChanged();
        }

        /**
         * We override this method so that we can allow a threshold of zero,
         * which ACTV does not.
         */
        @Override
        public boolean enoughToFilter() {
            return mThreshold <= 0 || super.enoughToFilter();
        }
        
        public void invokeEnsureImeVisible(boolean visible) {
            try {
                Method method = getClass().getMethod("ensureImeVisible", boolean.class);
                method.invoke(this, visible);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                // special case for the back key, we do not even try to send it
                // to the drop down list but instead, consume it immediately
                if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.startTracking(event, this);
                    }
                    return true;
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    KeyEvent.DispatcherState state = getKeyDispatcherState();
                    if (state != null) {
                        state.handleUpEvent(event);
                    }
                    if (event.isTracking() && !event.isCanceled()) {
                        mSearchView.clearFocus();
                        mSearchView.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(keyCode, event);
        }

    }
    
    public interface QuerySuggestionProvider {
        public List<Spanned> provideSuggestions(String query);
    }
    
    public class QuerySuggestionAsyncTask extends AsyncTask<Void, Void, List<Spanned>> {
        private String mQuery;
        
        public QuerySuggestionAsyncTask (String query) {
            mQuery = query;
        }
        
        @Override
        protected List<Spanned> doInBackground(Void... params) {
            return mQuerySuggestionProvider.provideSuggestions(mQuery);
        }

        @Override
        protected void onPostExecute(List<Spanned> result) {
            if (result == null || result.size() == 0) {
                dismissSuggestions();
            } else {
                setSuggestionsAdapter(new SuggestionsAdapter(getContext(), SearchViewSherlock.this, result));
                try {
                    mQueryTextView.showDropDown();//TODO refine this widget
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        
    }
    
    private void querySuggsetion(String query) {
        if (mQuerySuggestionTask != null && mQuerySuggestionTask.getStatus() == Status.RUNNING) {
            mQuerySuggestionTask.cancel(true);
        }
        
        mQuerySuggestionTask = new QuerySuggestionAsyncTask(query);
        mQuerySuggestionTask.execute();
    }
}