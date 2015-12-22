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
public class CategoriesListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Category> categoriesList;

    public CategoriesListViewAdapter(Context context, List<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getCount() {
        return this.categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = (Category) getItem(position);

        //TODO: Change the layout
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.categories_list_row, null);
        }

        TextView textViewCategoryName = (TextView) convertView.findViewById(R.id.textViewCategoryName);
        textViewCategoryName.setText(category.getCategoryName());

        return convertView;
    }
}
