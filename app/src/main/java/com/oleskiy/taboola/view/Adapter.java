package com.oleskiy.taboola.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oleskiy.taboola.view.model.Item;
import com.oleskiy.taboola.R;
import com.taboola.android.TaboolaWidget;
import com.taboola.android.listeners.TaboolaEventListener;
import com.taboola.android.utils.SdkDetailsHelper;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context cntx;
    private ArrayList<Item> widgets;

    public Adapter(Context context, ArrayList<Item> widgets){
        this.widgets = widgets;
        cntx= context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(cntx).inflate(R.layout.item_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(holder.viewItem.getChildCount()>0) {
           holder.viewItem.removeAllViews();
       }
           holder.addView( widgets.get(position) );
    }

    @Override
    public int getItemCount() {
        return widgets.size();
    }


    public void updateList(ArrayList<Item> newList){
        widgets.clear();
        widgets.addAll(newList);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        FrameLayout viewItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItem = (FrameLayout)itemView;
        }

        public void addView(Item model){
            if(model.getViewType()== Item.ViewType.TABOOLA_WIDGET){
                new TaboolaWidgetView(viewItem,model);
            }else if (model.getViewType()== Item.ViewType.TABOOLA_FEED){
                new TaboolaFeedView(viewItem,model);
            }else{
                new RegularView(viewItem,model);
            }

            if(model.getColor()!=0){
                viewItem.setBackgroundColor(model.getColor());
            }else{
                viewItem.setBackgroundColor(0);
            }
        }
    }

    class  RegularView {
        View v;
        TextView name;
        ImageView thumbnail;
        TextView description;

        public RegularView(FrameLayout view, Item model){
            v = View.inflate(cntx, R.layout.regular_widget,null);
            name = v.findViewById(R.id.regular_widget_name);
            name.setText(model.getName());

            thumbnail = v.findViewById(R.id.regular_widget_image);

            Glide.with(cntx)
                    .asBitmap()
                    .load(model.getThumbnail())
                    .into(thumbnail);
            view.addView(v);
        }
    }

    class TaboolaWidgetView{

        public TaboolaWidgetView(FrameLayout view, Item model) {
            TaboolaWidget taboolaWidget;
            taboolaWidget = new TaboolaWidget(cntx);
            taboolaWidget.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            view.addView(taboolaWidget);
            taboolaWidget.setProgressBarEnabled(true);
            taboolaWidget.setPublisher("sdk-tester")
                    .setMode("alternating-widget-without-video")
                    .setPlacement("Below Article")
                    .setPageUrl("http://google.com")
                    .setPageType("article")
                    .setTargetType("mix");


            taboolaWidget.fetchContent();

            taboolaWidget.setTaboolaEventListener(new TaboolaEventListener() {
                @Override
                public boolean taboolaViewItemClickHandler(String url, boolean isOrganic) {
                    Toast.makeText(cntx,"Click Item Widget",Toast.LENGTH_LONG).show();
                    return false;
                }

                @Override
                public void taboolaViewResizeHandler(TaboolaWidget widget, int height) {
                    Toast.makeText(cntx,"Resize Widget",Toast.LENGTH_LONG).show();

                }
            });
        }

    }

    class TaboolaFeedView{
        public TaboolaFeedView(FrameLayout view, Item model){
            TaboolaWidget taboolaWidget;
            taboolaWidget = new TaboolaWidget(cntx);
            int height = SdkDetailsHelper.getDisplayHeight(taboolaWidget.getContext()) * 2;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            taboolaWidget.setLayoutParams(params);
            taboolaWidget.setProgressBarEnabled(true);
            taboolaWidget.setProgressBarDuration(1);
            taboolaWidget.setProgressBarColor(Color.MAGENTA);
            taboolaWidget.setPublisher("sdk-tester")
                    .setMode("thumbs-feed-01")
                    .setPlacement("Feed without video")
                    .setPageUrl("http://google.com")
                    .setPageType("article")
                    .setTargetType("mix");
            view.addView(taboolaWidget);
            taboolaWidget.fetchContent();
        }
    }
}
