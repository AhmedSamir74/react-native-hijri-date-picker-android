package com.hijridatepicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.eltohamy.materialhijricalendarview.CalendarDay;
import com.github.eltohamy.materialhijricalendarview.MaterialHijriCalendarView;
import com.github.eltohamy.materialhijricalendarview.OnDateSelectedListener;

import javax.annotation.Nullable;

import static com.hijridatepicker.HijriDatePickerDialogFragment.customizeHijriCalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Mohamed Habib on 16/10/2016.
 */
public class SupportHijriDatePickerDialogFragment extends DialogFragment implements OnDateSelectedListener {

    @Nullable
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    @Nullable
    private DialogInterface.OnDismissListener mOnDismissListener;
    @Nullable
    private HijriDatePickerAndroidModule.OnExceptionListener mOnExceptionListener;

    Button doneBtn;
    MaterialHijriCalendarView widget;
    int dayOfMonth = 0;
    int monthOfYear = 0;
    int year = 0;

    Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_basic, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        args = getArguments();
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doneBtn = (Button) view.findViewById(R.id.done_button);
        widget = (MaterialHijriCalendarView) view.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);

        customizeHijriCalendarView(getActivity(), widget, args, mOnExceptionListener);
        onDateSelected(widget, widget.getSelectedDate(), true);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDateSetListener != null) {
                    mOnDateSetListener.onDateSet(null, year, monthOfYear, dayOfMonth);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialHijriCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        dayOfMonth = date.getDay();
        monthOfYear = date.getMonth();
        year = date.getYear();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    void setOnDateSetListener(@Nullable DatePickerDialog.OnDateSetListener onDateSetListener) {
        mOnDateSetListener = onDateSetListener;
    }

    void setOnExceptionListener(@Nullable HijriDatePickerAndroidModule.OnExceptionListener onExceptionListener) {
        mOnExceptionListener = onExceptionListener;
    }

    void setOnDismissListener(@Nullable DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }
}

