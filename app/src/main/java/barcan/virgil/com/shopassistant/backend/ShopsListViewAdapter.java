package barcan.virgil.com.shopassistant.backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Category;
import barcan.virgil.com.shopassistant.model.Company;

/**
 * Created by virgil on 29.11.2015.
 */
public class ShopsListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Company> companiesList;

    public ShopsListViewAdapter(Context context, List<Company> companiesList) {
        this.context = context;
        this.companiesList = companiesList;
    }

    @Override
    public int getCount() {
        return this.companiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.companiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Company shop = (Company) getItem(position);

        System.out.println("ShopsListViewAdapter.getView: shop=" + shop);

        //TODO: Change the layout
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shops_list_row, null);
        }

        TextView textViewShopName = (TextView) convertView.findViewById(R.id.textViewShopName);
        textViewShopName.setText(shop.getCompanyName());

        if (shop.getLocation() != null) {
            TextView textViewShopLocation = (TextView) convertView.findViewById(R.id.textViewShopLocation);
            textViewShopLocation.setText(shop.getLocation().getFullAddress());
        }
        return convertView;
    }
}
