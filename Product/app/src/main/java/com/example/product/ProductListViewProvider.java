package com.example.product;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

public class ProductListViewProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<Product> listItemList;
    private Context context;
    private int appWidgetId;

    ProductDbHelper mDatabaseHandler;

    public ProductListViewProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        mDatabaseHandler = new ProductDbHelper(context);
        populateListItem();
    }

    private void populateListItem() {
        listItemList = mDatabaseHandler.getProducts();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_item);

        Product p = listItemList.get(position);
        remoteView.setTextViewText(R.id.widgetId, String.valueOf(p.getId()));
        remoteView.setTextViewText(R.id.widgetName, p.getName());
        remoteView.setTextViewText(R.id.widgetMrp, String.format("%.0f", p.getMrp()));
        remoteView.setTextViewText(R.id.widgetPrice, String.format("%.0f", p.getPrice()));

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}