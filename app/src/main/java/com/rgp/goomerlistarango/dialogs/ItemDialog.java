package com.rgp.goomerlistarango.dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.rgp.goomerlistarango.R;
import com.rgp.goomerlistarango.models.Item;

public class ItemDialog {

    @Nullable
    private View itemDialogView;
    private ImageView itemImageView;
    private TextView itemNameTextView;
    private TextView itemPriceTextView;

    @Nullable
    private AlertDialog alertDialog;

    public void createDialogView(@NonNull LayoutInflater inflater, int layoutId) throws InflateException {
        this.itemDialogView = inflater.inflate(layoutId, null);
    }

    /**
     * Create and set listeners to children views.
     *
     * @throws NullPointerException
     */
    public void setupChildrenViewsOnClickListener() throws NullPointerException {
        ImageButton closeButton = itemDialogView.findViewById(R.id.closeImageButton);
        closeButton.setOnClickListener(v -> alertDialog.dismiss());
    }

    /**
     * Create an {@link AlertDialog} and {@link View.OnClickListener}s to positive and negative buttons.
     * It is necessary to configure listeners with function getButton().setOnClickListener() because the function setPositiveButton dismiss the alertDialog when clicked even an input error occurs.
     *
     * @param context
     */

    public void createAlertDialog(@NonNull Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(itemDialogView);

        this.itemImageView = itemDialogView.findViewById(R.id.itemImageView);
        this.itemNameTextView = itemDialogView.findViewById(R.id.itemNameTextView);
        this.itemPriceTextView = itemDialogView.findViewById(R.id.priceTextView);
        this.alertDialog = builder.create();
    }

    public void show(@NonNull Item toShowItem, @NonNull Context context) {
        if (this.alertDialog != null) {

            //itemImageView
            String imageUri = toShowItem.getImage();

            Glide
                    .with(context)
                    .load(imageUri)
                    .apply(new RequestOptions().transform(new CenterCrop()))
                    .fallback(new ColorDrawable(Color.GRAY))
                    .into(this.itemImageView);

            //itemNameTextView
            this.itemNameTextView.setText(toShowItem.getName());

            //currentItemPriceTextView
            String price = context.getResources().getString(R.string.price_unit);
            if (toShowItem.getPrice() > 0) {
                price = price + " " + toShowItem.getPrice();
            } else {
                price = price + " " + "-";
            }

            this.itemPriceTextView.setText(price);

            this.alertDialog.show();
        }
    }

    public void dismiss() {
        if (this.alertDialog != null) this.alertDialog.dismiss();
    }
}
