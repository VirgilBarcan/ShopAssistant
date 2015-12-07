package barcan.virgil.com.shopassistant.backend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.RegularUser;
import barcan.virgil.com.shopassistant.model.User;

/**
 * Created by virgil on 07.12.2015.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String navTitles[];
    private int navIcons[];

    private User connectedUser;

    public RecyclerViewAdapter() {
        this.connectedUser = new RegularUser();
        this.connectedUser.setFullName("Virgil Barcan");
        this.connectedUser.setUsername("virgil.barcan");

        navTitles = new String[]{"Change Password", "Change Location", "Edit Favourites"};
        navIcons = new int[]{R.drawable.ic_change_password, R.drawable.ic_edit_location, R.drawable.ic_edit_favourites};
    }

    public RecyclerViewAdapter(User connectedUser) {
        this.connectedUser = connectedUser;

        if (connectedUser instanceof RegularUser) {
            System.out.println("RecyclerViewAdapter.RecyclerViewAdapter: RegularUser connected");
            navTitles = new String[]{"Change Password", "Change Location", "Edit Favourites"};
            navIcons = new int[]{R.drawable.ic_change_password, R.drawable.ic_edit_location, R.drawable.ic_edit_favourites};
        }
        else {
            System.out.println("RecyclerViewAdapter.RecyclerViewAdapter: CompanyUser connected");
            //TODO: Do the same for CompanyUser
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_item_row, parent, false);

            ViewHolder viewHolder = new ViewHolder(view, viewType);

            return viewHolder;
        }
        else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_drawer_header, parent, false);

            ViewHolder viewHolder = new ViewHolder(view, viewType);

            return viewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder((RecyclerViewAdapter.ViewHolder) holder, position);
    }

    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        if (holder.getHolderID() == 1) {
            holder.textViewRowText.setText(navTitles[position - 1]);
            holder.imageViewRowIcon.setImageResource(navIcons[position - 1]);
        }
        else {
            //TODO: Fill the circle with the user's photo
            //holder.imageViewProfileCircle.setImageResource();

            holder.textViewFullName.setText(connectedUser.getFullName());
            holder.textViewUsername.setText(connectedUser.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return navTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    /**
     * Creating a ViewHolder which extends the RecyclerView View Holder
     * ViewHolder are used to to store the inflated views in order to recycle them
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        int holderID;
        TextView textViewRowText;
        ImageView imageViewRowIcon;
        ImageView imageViewProfileCircle;
        TextView textViewFullName;
        TextView textViewUsername;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_ITEM) {
                textViewRowText = (TextView) itemView.findViewById(R.id.rowText);
                imageViewRowIcon = (ImageView) itemView.findViewById(R.id.rowIcon);
                holderID = 1;
            }
            else {
                textViewFullName = (TextView) itemView.findViewById(R.id.fullname);
                textViewUsername = (TextView) itemView.findViewById(R.id.username);
                imageViewProfileCircle = (ImageView) itemView.findViewById(R.id.circleView);
                holderID = 0;
            }
        }

        public int getHolderID() {
            return holderID;
        }
    }
}
