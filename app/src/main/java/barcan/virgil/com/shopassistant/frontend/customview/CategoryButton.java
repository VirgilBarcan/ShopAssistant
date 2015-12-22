package barcan.virgil.com.shopassistant.frontend.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Category;

/**
 * Created by virgil on 20.12.2015.
 */
public class CategoryButton extends LinearLayout {

    private Context context;

    private ImageView categoryImage;
    private TextView categoryName;
    private OnClickListener listener;

    private int imageSrc;
    private String categoryNameString;

    private Category category;

    /**
     * CategoryButton constructor
     * @param context the context
     */
    public CategoryButton(Context context) {
        super(context);

        System.out.println("CategoryButton.CategoryButton1");

        imageSrc = R.mipmap.product_image;

        initializeView(context);
    }

    /**
     * CategoryButton constructor
     * @param context the context
     * @param attrs the attributes
     */
    public CategoryButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        System.out.println("CategoryButton.CategoryButton2");

        extractAttributes(context, attrs);

        initializeView(context);
    }

    /**
     * CategoryButton constructor
     * @param context the context
     * @param attrs the attributes
     * @param defStyleAttr the style attributes
     */
    public CategoryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        System.out.println("CategoryButton.CategoryButton3");

        extractAttributes(context, attrs);

        initializeView(context);
    }

    /*
    public ProductButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        System.out.println("CategoryButton.CategoryButton4");

        extractAttributes(context, attrs);

        initializeView(context);
    }
     */

    /**
     * This method initializes the view
     * @param context the context
     */
    private void initializeView(Context context) {
        this.context = context;

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.category_button_layout, this);

        setClickable(true);

        /*setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, getWidth(), getHeight());
            }
        });
        */
    }

    /**
     * This method extracts the attributes: imageSrc, categoryName
     * @param context the context
     * @param attrs the attributes
     */
    private void extractAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.CategoryButton);
        imageSrc = typedArray.getResourceId(R.styleable.CategoryButton_categoryImageSrc, R.mipmap.ic_launcher);
        categoryNameString = typedArray.getString(R.styleable.ProductButton_productName);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        categoryImage = (ImageView) this.findViewById(R.id.categoryButtonImage);
        categoryImage.setImageResource(imageSrc);

        categoryName = (TextView) this.findViewById(R.id.categoryButtonCategoryName);
        categoryName.setText(categoryNameString);
    }

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) {
                listener.onClick(this);
            }
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * This method is used to register the OnClickListener
     * @param listener the OnClickListener object
     */
    public void setOnClickListener(OnClickListener listener) {
        System.out.println("CategoryButton.setOnClickListener");
        this.listener = listener;
    }

    /**
     * Set the image source
     * @param imageSrc the image source
     */
    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;

        if (categoryImage == null) {
            categoryImage = (ImageView) this.findViewById(R.id.categoryButtonImage);
        }
        categoryImage.setImageResource(this.imageSrc);

        this.invalidate();
    }

    /**
     * Set the category name
     * @param categoryNameString the category name
     */
    public void setCategoryNameString(String categoryNameString) {
        this.categoryNameString = categoryNameString;

        if (categoryName == null) {
            categoryName = (TextView) this.findViewById(R.id.categoryButtonCategoryName);
        }
        categoryName.setText(this.categoryNameString);

        this.invalidate();
    }

    /**
     * Get the category that is represented by this view
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Set the category that will be represented by this view
     * @param category the category
     */
    public void setCategory(Category category) {
        this.category = category;

        setCategoryNameString(this.category.getCategoryName());
    }
}
