package com.example.melanoma.View.Melanoma.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.melanoma.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import androidx.cardview.widget.CardView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private List<String> listDataGambar;
    private HashMap<String,List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap,List<String> listDataGambar) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        this.listDataGambar = listDataGambar;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group,null);
        }
        TextView LvlListHeader = (TextView)convertView.findViewById(R.id.lvlListHeader);
        LvlListHeader.setTypeface(null, Typeface.BOLD);
        LvlListHeader.setText(headerTitle);

        CardView cardView = convertView.findViewById(R.id.melanoma_card_parent);

        if (isExpanded) {
            cardView.setBackgroundResource(R.drawable.cardview_background_selected);
        }
        else {
            cardView.setBackgroundResource(R.drawable.cardview_background_normal);
        }
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String)getChild(groupPosition, childPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        TextView LvlListItem = (TextView)convertView.findViewById(R.id.LvlListItem);
        ImageView LvlListGambar = (ImageView)convertView.findViewById(R.id.Listgambar);
        ProgressBar pb = convertView.findViewById(R.id.progress_bar);
        pb.setVisibility(View.GONE);
        LvlListGambar.setVisibility(View.GONE);
        pb.getIndeterminateDrawable().setColorFilter(convertView.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        switch (groupPosition){
            case 1 :
                pb.setVisibility(View.VISIBLE);
                LvlListGambar.setVisibility(View.VISIBLE);
                Picasso.get().load(listDataGambar.get(0)).into(LvlListGambar);
                Log.e("kambing","badak goreng");
                break;
            case 2:
                pb.setVisibility(View.VISIBLE);
                LvlListGambar.setVisibility(View.VISIBLE);
                Picasso.get().load(listDataGambar.get(1)).into(LvlListGambar);
                Log.e("kambing","babi goreng");
                break;
            case 3:
                pb.setVisibility(View.VISIBLE);
                LvlListGambar.setVisibility(View.VISIBLE);
                Picasso.get().load(listDataGambar.get(2)).into(LvlListGambar);
                Log.e("kambing","kambing goreng");
                break;
            case 4:
                pb.setVisibility(View.VISIBLE);
                LvlListGambar.setVisibility(View.VISIBLE);
                Picasso.get().load(listDataGambar.get(3)).into(LvlListGambar);
                Log.e("kambing","asu goreng");
                break;
            default:
                break;
        }
        LvlListItem.setText(childText);
        LinearLayout cardView = convertView.findViewById(R.id.melanoma_card_child);
        cardView.setBackgroundResource(R.drawable.cardview_background_child);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
