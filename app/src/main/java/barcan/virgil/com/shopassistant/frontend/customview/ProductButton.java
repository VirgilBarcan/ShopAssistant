package barcan.virgil.com.shopassistant.frontend.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import barcan.virgil.com.shopassistant.R;
import barcan.virgil.com.shopassistant.model.Product;

/**
 * Created by virgil on 20.12.2015.
 */
public class ProductButton extends LinearLayout {

    private Context context;

    private ImageView productImage;
    private TextView productName;
    private TextView productSeller;
    private TextView productPrice;
    private OnClickListener listener;

    private int imageSrc;
    private String productNameString;
    private String productSellerString;
    private String productPriceString;

    private Product product;

    /**
     * ProductButton constructor
     * @param context the context
     */
    public ProductButton(Context context) {
        super(context);

        System.out.println("ProductButton.ProductButton1");

        imageSrc = R.mipmap.product_image;

        initializeView(context);
    }

    /**
     * ProductButton constructor
     * @param context the context
     * @param attrs the attributes
     */
    public ProductButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        System.out.println("ProductButton.ProductButton2");

        extractAttributes(context, attrs);

        System.out.println("ProductButton.ProductButton3: imageSrc=" + imageSrc);

        initializeView(context);
    }

    /**
     * ProductButton constructor
     * @param context the context
     * @param attrs the attributes
     * @param defStyleAttr the style attributes
     */
    public ProductButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        System.out.println("ProductButton.ProductButton3");

        extractAttributes(context, attrs);

        System.out.println("ProductButton.ProductButton3: imageSrc=" + imageSrc);

        initializeView(context);
    }

    /*
    public ProductButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        System.out.println("ProductButton.ProductButton4");

        extractAttributes(context, attrs);

        System.out.println("ProductButton.ProductButton4: imageSrc=" + imageSrc);

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
        layoutInflater.inflate(R.layout.product_button_layout, this);

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
     * This method extracts the attributes: imageSrc, productName, productSeller, productPrice
     * @param context the context
     * @param attrs the attributes
     */
    private void extractAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProductButton);
        imageSrc = typedArray.getResourceId(R.styleable.ProductButton_productImageSrc, R.mipmap.ic_launcher);
        productNameString = typedArray.getString(R.styleable.ProductButton_productName);
        productSellerString = typedArray.getString(R.styleable.ProductButton_productSeller);
        productPriceString = typedArray.getString(R.styleable.ProductButton_productPrice);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        productImage = (ImageView) this.findViewById(R.id.productButtonImage);
        productImage.setImageResource(imageSrc);

        productName = (TextView) this.findViewById(R.id.productButtonProductName);
        productName.setText(productNameString);

        productSeller = (TextView) this.findViewById(R.id.productButtonProductSeller);
        productSeller.setText(productSellerString);

        productPrice = (TextView) this.findViewById(R.id.productButtonProductPrice);
        productPrice.setText(productPriceString);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) {
                listener.onClick(this);
            }
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
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
        System.out.println("ProductButton.setOnClickListener");
        this.listener = listener;
    }

    /**
     * Set the image source of this view
     * @param imageSrc the image source id
     */
    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;

        if (productImage == null) {
            productImage = (ImageView) this.findViewById(R.id.productButtonImage);
        }
        productImage.setImageResource(this.imageSrc);

        this.invalidate();
    }

    /**
     * Set the product name
     * @param productNameString the product name
     */
    public void setProductNameString(String productNameString) {
        this.productNameString = productNameString;

        if (productName == null) {
            productName = (TextView) this.findViewById(R.id.productButtonProductName);
        }
        productName.setText(this.productNameString);

        this.invalidate();
    }

    /**
     * Set the product seller
     * @param productSellerString product seller
     */
    public void setProductSellerString(String productSellerString) {
        this.productSellerString = productSellerString;

        if (productSeller == null) {
            productSeller = (TextView) this.findViewById(R.id.productButtonProductSeller);
        }
        productSeller.setText(this.productSellerString);

        this.invalidate();
    }

    /**
     * Set the product price
     * @param productPriceString the product price
     */
    public void setProductPriceString(String productPriceString) {
        this.productPriceString = productPriceString;

        if (productPrice == null) {
            productPrice = (TextView) this.findViewById(R.id.productButtonProductPrice);
        }
        productPrice.setText(this.productPriceString);

        this.invalidate();
    }

    /**
     * Get the product that the view represents
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Set the product that the view represents
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;

        setProductNameString(product.getProductName());
        setProductSellerString(product.getProductSeller().getCompanyName());
        setProductPriceString(product.getProductPriceString());
    }
}
