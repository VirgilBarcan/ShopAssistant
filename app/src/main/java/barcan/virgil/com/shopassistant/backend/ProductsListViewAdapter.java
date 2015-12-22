package barcan.virgil.com.shopassistant.backend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 29.11.2015.
 */
public class ProductsListViewAdapter extends BaseAdapter {

    private Controller controller;
    private Context context;
    private List<Product> productsList;

    public ProductsListViewAdapter(Context context, List<Product> productsList) {
        this.context = context;
        this.productsList = productsList;

        this.controller = Controller.getInstance();
    }

    @Override
    public int getCount() {
        return this.productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productsList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.products_list_row, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewProductImageSmall);
        imageView.setImageBitmap(controller.getProductImage(product, 60, 60));

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
