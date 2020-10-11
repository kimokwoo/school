package com.smart.school.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.smart.school.R;

/**
 * Created by surf on 2018. 1. 4..
 */

public class CustomEditText extends FrameLayout{

    EditText editText;
    Button btn_Clear;

    public CustomEditText(Context context) {
        super(context);
        initView();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        getAttrs(attrs, defStyleAttr);
    }

    private void initView(){

        inflate(getContext(), R.layout.custom_edit_text, this);

        editText = (EditText)findViewById(R.id.et_name);
        btn_Clear = (Button)findViewById(R.id.btn_clear);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    btn_Clear.setVisibility(VISIBLE);
                }else{
                    btn_Clear.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_Clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        String hint = typedArray.getString(R.styleable.CustomEditText_hint);
        editText.setHint(hint);

        typedArray.recycle();
    }

    public void setText(String str){
        editText.setText(str);
        editText.setSelection(editText.length());
    }

    public void setHint(String str){
        editText.setHint(str);
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void setImeOptions(int imeOptions){
        editText.setImeOptions(imeOptions);
    }

    public void setCursorVisible(boolean isVisible){
        editText.setCursorVisible(isVisible);
    }

    public void setFocusable(boolean isFocusable){
        editText.setFocusable(isFocusable);
    }

    public View getEditText(){
        return editText;
    }

    public void showKeyboard(Activity act){
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

//        KeyboardHelper.showKeyboard(act, editText);
    }
}
