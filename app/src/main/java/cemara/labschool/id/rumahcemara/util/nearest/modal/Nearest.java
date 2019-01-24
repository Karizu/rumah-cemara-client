package cemara.labschool.id.rumahcemara.util.nearest.modal;

import android.widget.ImageView;

public class Nearest {
    private ImageView nearestImg;
    private int nearestSrc;
    private String nearestName;
    private String nearestRange;
    private String nearestId;

    public Nearest(int nearestSrc, String nearestName, String nearestRange,String nearestId) {
        this.nearestSrc = nearestSrc;
        this.nearestName = nearestName;
        this.nearestRange = nearestRange;
        this.nearestId = nearestId;
    }

    public Nearest(ImageView nearestImg, String nearestName, String nearestRange) {
        this.nearestImg = nearestImg;
        this.nearestName = nearestName;
        this.nearestRange = nearestRange;
    }

    public int getNearestSrc() {
        return nearestSrc;
    }

    public void setNearestSrc(int nearestSrc) {
        this.nearestSrc = nearestSrc;
    }

    public void setNearestName(String nearestName) {
        this.nearestName = nearestName;
    }

    public String getNearestName() {
        return nearestName;
    }
    public void setNearestImg(ImageView nearestImg) {
        this.nearestImg = nearestImg;
    }

    public ImageView getNearestImg() {
        return nearestImg;
    }

    public void setNearestRange(String nearestRange) {
        this.nearestRange = nearestRange;
    }

    public String getNearestRange() {
        return nearestRange;
    }
}

