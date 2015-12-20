package barcan.virgil.com.shopassistant.frontend;

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

/**
 * Created by virgil on 20.12.2015.
 */
public class ProductButton extends LinearLayout {

    private ImageView productImage;
    private TextView productName;
    private TextView productSeller;
    private TextView productPrice;
    private OnClickListener listener;

    private int imageSrc;

    public ProductButton(Context context) {
        super(context);

        System.out.println("ProductButton.ProductButton1");

        imageSrc = R.mipmap.product_image;

        initializeView(context);
    }

    public ProductButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        System.out.println("ProductButton.ProductButton2");

        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProductButton);
        imageSrc = typedArray.getResourceId(R.styleable.ProductButton_imageSrc, R.mipmap.ic_launcher);
        typedArray.recycle();

        System.out.println("ProductButton.ProductButton3: imageSrc=" + imageSrc);

        initializeView(context);
    }

    public ProductButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        System.out.println("ProductButton.ProductButton3");

        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProductButton);
        imageSrc = typedArray.getResourceId(R.styleable.ProductButton_imageSrc, R.mipmap.ic_launcher);
        typedArray.recycle();

        System.out.println("ProductButton.ProductButton3: imageSrc=" + imageSrc);

        initializeView(context);
    }

    /*
    public ProductButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        System.out.println("ProductButton.ProductButton4");

        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProductButton);
        imageSrc = typedArray.getResourceId(R.styleable.ProductButton_imageSrc, R.mipmap.ic_launcher);
        typedArray.recycle();

        System.out.println("ProductButton.ProductButton4: imageSrc=" + imageSrc);

        initializeView(context);
    }
     */

    private void initializeView(Context context) {
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        productImage = (ImageView) this.findViewById(R.id.productButtonImage);
        productImage.setImageResource(imageSrc);

        productName = (TextView) this.findViewById(R.id.productButtonProductName);
        productSeller = (TextView) this.findViewById(R.id.productButtonProductSeller);
        productPrice = (TextView) this.findViewById(R.id.productButtonProductPrice);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("ProductButton.dispatchTouchEvent");
        System.out.println("ProductButton.dispatchTouchEvent: event.getAction()=" + event.getAction());
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) {
                listener.onClick(this);
            }
        }

        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("ProductButton.dispatchKeyEvent");
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        System.out.println("ProductButton.setOnClickListener");
        this.listener = listener;
    }
}
