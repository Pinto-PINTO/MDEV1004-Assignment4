// File Name: RegisterActivity.java
// Student Name: Rajat Rajat
// Student ID: 200519561
// Date: 17th August 2023
package ca.georgiancollege.mdev1004_m2023_assignment4_android;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.Callback
{
    private final MovieAdapter adapter;
    private final Drawable deleteIcon;
    private final ColorDrawable background;

    public SwipeToDeleteCallback(Context context, MovieAdapter adapter)
    {
        this.adapter = adapter;
        this.deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        this.background = new ColorDrawable(Color.RED);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder)
    {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
    {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive)
    {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        if (dX > 0)
        {
            background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
        } else
        {
            background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }

        background.draw(c);

        int iconMargin = (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

        if (dX > 0)
        {
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + deleteIcon.getIntrinsicWidth();
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        } else
        {
            int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        }

        deleteIcon.draw(c);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
    {
        int position = viewHolder.getAdapterPosition();

        AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
        builder.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, id) -> adapter.deleteItem(position))
                .setNegativeButton("Cancel", (dialog, id) ->
                {
                    adapter.notifyItemChanged(position); // Refresh the view
                    dialog.dismiss();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
