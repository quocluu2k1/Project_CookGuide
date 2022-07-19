package com.example.cookguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cookguide.R;
import com.example.cookguide.models.Ingredient;

import java.util.List;

public class MaterialAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Ingredient> ingredientList;

    public MaterialAdapter(Context context, int layout, List<Ingredient> ingredientList) {
        this.context = context;
        this.layout = layout;
        this.ingredientList = ingredientList;
    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredientList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutItemMaterial);
        if(i%2==1){
            linearLayout.setBackgroundColor(0xFFFFFF);
        }
        TextView txtName = (TextView) view.findViewById(R.id.textViewName);
        txtName.setText(ingredientList.get(i).name);

        TextView txtAmount = (TextView) view.findViewById(R.id.textViewAmount);
        txtAmount.setText(ingredientList.get(i).amount);

        return view;
    }
}
