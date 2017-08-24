package ru.nuts_coon.traduttore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Michael on 20.08.2017.
 * Адаптер для наполнения экрана выбора цвета
 */

class DesignAdapter extends ArrayAdapter<DesignColor> {

    private int Resources;

    DesignAdapter(Context context, int Resources, List<DesignColor> list) {
        super(context, Resources, list);
        this.Resources = Resources;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DesignColor designColor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(Resources, null);
        }
        ((TextView) convertView.findViewById(R.id.colorName))
                .setText(designColor.colorName);
        ((ImageView) convertView.findViewById(R.id.colorImage))
                .setImageResource(designColor.colorImage);
        return convertView;
    }
}
