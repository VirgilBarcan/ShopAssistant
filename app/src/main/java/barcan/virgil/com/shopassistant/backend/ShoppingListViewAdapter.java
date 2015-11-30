package barcan.virgil.com.shopassistant.backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 29.11.2015.
 */
public class ShoppingListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Product> shoppingList;

    public ShoppingListViewAdapter(Context context, List<Product> shoppingList) {
        this.context = context;
        this.shoppingList = shoppingList;
    }

    @Override
    public int getCount() {
        return this.shoppingList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.shoppingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = (Product) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.shopping_list_row, null);
        }

        TextView textViewProductName = (TextView) convertView.findViewById(R.id.textViewProductName);
        textViewProductName.setText(product.getProductName());

        TextView textViewProductCategory = (TextView) convertView.findViewById(R.id.textViewProductCategory);
        textViewProductCategory.setText(product.getProductCategory().getCategoryName());

        TextView textViewProductSeller = (TextView) convertView.findViewById(R.id.textViewCompanySelling);
        textViewProductSeller.setText(product.getProductSeller().getCompanyName());

        TextView textViewProductPrice = (TextView) convertView.findViewById(R.id.textViewProductPrice);
        textViewProductPrice.setText(product.getProductPrice().getPriceValue() + " " + product.getProductPrice().getPriceCurrency());

        return convertView;
    }
}
