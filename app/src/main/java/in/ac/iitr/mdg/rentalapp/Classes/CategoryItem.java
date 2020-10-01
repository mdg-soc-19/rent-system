package in.ac.iitr.mdg.rentalapp.Classes;


public class CategoryItem {
    private int mImageResource;
    private String mText;

    public CategoryItem(int imageResource, String text) {
        this.mImageResource = imageResource;
        this.mText = text;

    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getText() {
        return mText;
    }


}
