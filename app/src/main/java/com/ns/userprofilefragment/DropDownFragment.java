package com.ns.userprofilefragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ns.loginfragment.BaseFragmentTHP;
import com.ns.thpremium.R;

import java.util.ArrayList;
import java.util.List;

public class DropDownFragment extends BaseFragmentTHP {

    private String mFrom;

    private final String gender = "gender";
    private final String country = "country";
    private final String state = "state";

    @Override
    public int getLayoutRes() {
        if(mFrom != null && mFrom.equalsIgnoreCase(gender)) {
            return R.layout.fragment_dropdown;
        }
        else if(mFrom != null && mFrom.equalsIgnoreCase(country)) {
            return R.layout.fragment_dropdown_country;
        }
        else if(mFrom != null && mFrom.equalsIgnoreCase(state)) {
            return R.layout.fragment_dropdown_country;
        }
        else {
            return R.layout.fragment_dropdown;
        }
    }


    public static DropDownFragment getInstance(String from) {
        DropDownFragment fragment = new DropDownFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mFrom = getArguments().getString("from");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(R.id.listView);

        view.findViewById(R.id.dropDownParentLayout).setOnTouchListener((v, e)->{
            return true;
        });


        List<String> standardList = new ArrayList<>();

        if(mFrom != null && mFrom.equalsIgnoreCase(gender)) {
            standardList.add("Male");
            standardList.add("Female");
            standardList.add("Trans");
        }


        StandardAdapter adapter = new StandardAdapter(getActivity(), R.layout.item_standard_list_popup, standardList);
        listView.setAdapter(adapter);

    }


    private class StandardAdapter extends ArrayAdapter<String> {

        private List<String> mStandardList;
        private LayoutInflater inflater=null;

        public StandardAdapter(@NonNull Context context, int resource, List<String> standardList) {
            super(context, resource, standardList);
            mStandardList = standardList;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if(mStandardList == null) {
                return 0;
            }
            return mStandardList.size();
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View vi=convertView;
            if(convertView==null) {
                vi = inflater.inflate(R.layout.item_standard_list_popup, null);
            }

            TextView title = vi.findViewById(R.id.option_Txt);

            title.setText(getItem(position));

            title.setOnClickListener(v->{
                if(mOnDropdownItemSelection != null) {
                    mOnDropdownItemSelection.onDropdownItemSelection(mFrom, getItem(position));
                }
            });

            return vi;
        }


    }

    private OnDropdownItemSelection mOnDropdownItemSelection;

    public void setOnDropdownitemSelection(OnDropdownItemSelection onStandardPopupItemSelect) {
        mOnDropdownItemSelection = onStandardPopupItemSelect;
    }

    public interface OnDropdownItemSelection {
        void onDropdownItemSelection(String from, String value);
    }


}
